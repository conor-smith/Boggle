package fdmBoggle.game;

import java.util.ArrayList;
import java.util.HashSet;
import fdmBoggle.ai.WordGetter;

/**
 * This object manages the game logic
 * Is made to function both in tournament mode and game mode
 * @author Conor Smith
 */
public class Boggle
{
    /* Stores the 4*4 board of 'dice'. Uses String to account for 'Qu' piece */
    private String[][] board;
    /* Stores all correct words that have been entered. Ensures player cannot get points by entering the same word multiple times */
    private ArrayList<String> guessedWords;
    /* 
     * A set of all legal words for this game. Was used to make it easier during tournaments as it allows players to enter a String as opposed to a list of positions 
     * Does take time to build
     */
    private HashSet<String> legalWords;
    /* The game score so far */
    private int score;

    /* Signifies that this is a tournament */
    boolean tournamentMode;
    long tStartTime, tEndTime;

    /** 
     * Calls reset()
     * Sets private field tournament mode to false
     */
    public Boggle()
    {
        tournamentMode = false;
        reset();
    }

    /**
     * Calls reset() - sets private field tournamentMode to entered parameter
     * @param tournamentMode - sets tournamentMode field
     */
    public Boggle(boolean tournamentMode)
    {
        this.tournamentMode = tournamentMode;
        reset();
    }

    /** 
     * Calls reset(String[][] board)
     * @param tournamentMode - sets tounamentMode field
     * @param board - passed into reset(String[][] board)
     * @throws BoggleException - if reset(String[][] board) throws this exception
     */

    public Boggle(String[][] board, boolean tournamentMode) throws BoggleException
    {
        this.tournamentMode = tournamentMode;
        reset(board);
    }

    /** 
     * Calls BoardGenerator and WordGetter to generate board and legalWords
     * Sets score to 0
     * If field tournamentMode is set to true, this object will record the time taken by the WordGetter
     */
    public void reset()
    {
        board = BoardGenerator.generateBoard();
        /*
         * Record time taken for wordGetter
         * This is used for the 'Default' player in tournament mode
         */
        
        if(tournamentMode)
        {
            tStartTime = System.currentTimeMillis();
            legalWords = WordGetter.getLegalWords(this);
            tEndTime = System.currentTimeMillis();
        }
        else
        {
            legalWords = WordGetter.getLegalWords(this);
        }
        score = 0;
        guessedWords = new ArrayList<String>();
    }

    /**
     * Sets param board to entered value
     * Generates new set of legalWords for this entered board
     * Resets score to 0
     * @param board - sets board field
     * @throws BoggleException - if board is of incorrect dimensions
     */
    public void reset(String[][] board) throws BoggleException
    {
        if(board.length > 4 || board[0].length > 4)
            throw new BoggleException("Board must be of size 4x4");
        this.board = board;
        legalWords = WordGetter.getLegalWords(this);
        score = 0;
        guessedWords = new ArrayList<String>();
    }

    /** 
     * Sets param score to 0 and field guessedWords to an empty list
     * Used to 'recycle' a game in tournament mode
     */
    public void resetScoreAndGuesses()
    {
        this.guessedWords = new ArrayList<String>();
        this.score = 0;
    }

    /**
     * Used to enter a word - Returns true and increments score appropriately if word is valid
     * score is incremented by 1 if length = 3-4, 2 if length = 5, 3 if length = 6, 5 if length = 7, 11 if length = 8 or higher
     * Will return false if word is valid but has already been entered. Will not increment score
     * If tournamentMode is enabled, it decrements score by 1 for incorrect words
     * @param word - A string representing a word that can be constructed with the board
     * @return true if word is valid and has not been entered before, otherwise returns false
     */
    public boolean enterWord(String word)
    {
        if(!guessedWords.contains(word) && legalWords.contains(word))
        {
            score += getWordScore(word);
            guessedWords.add(word);
            return true;
        }
        else
        {
            if(tournamentMode)
                score--;
            return false;
        }
    }

    /**
     * Used in tournaments to get time taken for the 'Default' player
     * @return a long representing time in milliseconds for the WordGetter
     */
    public long getDefaultTime()
    {
        return tEndTime - tStartTime;
    }

    /**
     * Used in tournaments to get total score for 'Default player'
     * @return an int representing the total combined score of all legalWords present for this board
     */
    public int getDefaultScore()
    {
        int defaultScore = 0;
        for(String word : legalWords)
            defaultScore += getWordScore(word);
        return defaultScore;
    }

    /**
     * Prints 4 * 4 prepresentation of param board to the CLI
     */
    public void printBoard()
    {
        for(int i = 0;i < board.length;i++)
        {
            for(int j = 0;j < board[0].length;j++)
                System.out.print(board[j][i] + " ");
            System.out.println();
        }
    }

    /**
     * Returns 4 * 4 2D String array representing board 
     * @return board
     */
    public String[][] getBoard()
    {
        return board;
    }

    /** 
     * returns current score from all entered words since last reset
     * @return score
     */
    public int getScore()
    {
        return score;
    }

    /*
     * Returns score for an entered word based on it's length
     * Score is 1 if Word length = 3-4, 2 if length = 5, 3 if length = 6, 5 if length = 7, 11 if length >=8
     */
    private int getWordScore(String word)
    {
        if(word.length() <= 4)
                return 1;
            else if(word.length() <= 5)
                return 2;
            else if(word.length() <= 6)
                return 3;
            else if(word.length() <= 7)
                return 5;
            else
                return 11;
    }
}