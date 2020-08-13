package fdmBoggle.game;

import java.util.ArrayList;
import java.util.HashSet;
import fdmBoggle.ai.WordGetter;

public class Boggle
{
    //The game board with (0,0) at the top left corner
    private String[][] board;
    private ArrayList<String> guessedWords;
    private HashSet<String> legalWords;
    private int score;
    boolean tournamentMode;

    public Boggle()
    {
        tournamentMode = false;
        reset();
    }

    public Boggle(boolean tournamentMode)
    {
        this.tournamentMode = tournamentMode;
        reset();
    }

    public Boggle(String[][] board, boolean tournamentMode) throws BoggleException
    {
        this.tournamentMode = tournamentMode;
        reset(board);
    }

    public void reset()
    {
        board = BoardGenerator.generateBoard();
        legalWords = WordGetter.getLegalWords(this);
        score = 0;
        guessedWords = new ArrayList<String>();
    }

    public void reset(String[][] board) throws BoggleException
    {
        if(board.length > 4 || board[0].length > 4)
            throw new BoggleException("Board must be of size 4x4");
        this.board = board;
        legalWords = WordGetter.getLegalWords(this);
        score = 0;
        guessedWords = new ArrayList<String>();
    }

    public void resetScoreAndGuesses()
    {
        this.guessedWords = new ArrayList<String>();
        this.score = 0;
    }

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

    public void printBoard()
    {
        for(int i = 0;i < board.length;i++)
        {
            for(int j = 0;j < board[0].length;j++)
                System.out.print(board[j][i] + " ");
            System.out.println();
        }
    }

    public String[][] getBoard()
    {
        return board;
    }

    public int getScore()
    {
        return score;
    }

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