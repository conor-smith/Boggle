package fdmBoggle.ai;

import java.util.HashSet;
import fdmBoggle.game.Boggle;

public class WordGetter
{

    public static HashSet<String> getLegalWords(Boggle game)
    {
        String[][] board = game.getBoard();
        HashSet<String> legalWords = new HashSet<String>();
        HashSet<Position> previousPositions = new HashSet<Position>();
        String currentWord = "";

        for(int i = 0;i < 4;i++)
            for(int j = 0;j < 4;j++)
                getWords(board, legalWords, previousPositions, currentWord, new Position(i, j));

        return legalWords;
    }

    private static void getWords(String[][] board, HashSet<String> legalWords, HashSet<Position> previousPositions, String currentWord, Position currentPosition)
    {
        previousPositions.add(currentPosition);
        currentWord += board[currentPosition.x][currentPosition.y];

        if(currentWord.length() >= 3 && BoggleDictionary.wordIsLegal(currentWord))
            legalWords.add(currentWord);
        
        for(int i = currentPosition.x - 1;i < currentPosition.x + 1;i++)
            for(int j = currentPosition.y - 1;j < currentPosition.y + 1;j++)
                if(!(i < 0 || i >= 4 || j < 0 || j >= 4))
                {
                    Position temp = new Position(i, j);
                    if(!previousPositions.contains(temp))
                        getWords(board, legalWords, previousPositions, currentWord, temp);
                }

        previousPositions.remove(currentPosition);
    }

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