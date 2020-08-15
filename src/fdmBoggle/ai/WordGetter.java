package fdmBoggle.ai;

import java.util.HashSet;
import fdmBoggle.game.Boggle;

/**
 * A class with one static method to return a set of legalWords for a particular game
 * Class has a private constructor and cannot be instantiated
 */
public class WordGetter
{
    /* Exists solely to prevent instantiation */
    private WordGetter()
    {}

    /**
     * Returns set of legalWords
     * @param game - the boggle game to build a set of words for
     * @return A HashSet containing all legalWords
     */
    public static HashSet<String> getLegalWords(Boggle game)
    {
        String[][] board = game.getBoard();
        HashSet<String> legalWords = new HashSet<String>();
        HashSet<Position> previousPositions = new HashSet<Position>();
        String currentWord = "";

        //Calls getWords on all positions on board
        for(int i = 0;i < 4;i++)
            for(int j = 0;j < 4;j++)
                getWords(board, legalWords, previousPositions, currentWord, new Position(i, j));

        return legalWords;
    }

    /*
     * A recursive function which performs a depth first search from any given position on the board
     * Will check every possible String against dictionary. If the word is valid, it will add it to legalWords
     * Keeps track of already visited positions to ensure words do not contain the same position twice (as is the rules of boggle)
     */
    private static void getWords(String[][] board, HashSet<String> legalWords, HashSet<Position> previousPositions, String currentWord, Position currentPosition)
    {
        //Adds current position to previous positions set
        previousPositions.add(currentPosition);
        //Adds current letter to existing word
        currentWord += board[currentPosition.x][currentPosition.y];

        //Adds current word if it is legal. Words of length < 3 are automatically not legal and shouldn't be checked in the dictionary
        if(currentWord.length() >= 3 && BoggleDictionary.wordIsLegal(currentWord))
            legalWords.add(currentWord);
        
        /*
         * Calls itself on all positions in the surrounding 3 * 3 square
         * Checks to ensure position is legal and hasn't already been checked
         */
        for(int i = currentPosition.x - 1;i <= currentPosition.x + 1;i++)
            for(int j = currentPosition.y - 1;j <= currentPosition.y + 1;j++)
                if(!(i < 0 || i >= 4 || j < 0 || j >= 4))
                {
                    Position temp = new Position(i, j);
                    if(!previousPositions.contains(temp))
                        getWords(board, legalWords, previousPositions, currentWord, temp);
                }

        previousPositions.remove(currentPosition);
    }

    /*
     * A simple class designed to store two values representing a position on the board
     * Has overriden equals() and hashCode() to allow for correct storing in a hashset
     */
    private static class Position {

        public int x;
        public int y;
        
        public Position(int x, int y)
        {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode()
        {
            return x * 10 + y;
        }
    
        @Override
        public boolean equals(Object obj)
        {
            return obj.hashCode() == hashCode();
        }
    }
}