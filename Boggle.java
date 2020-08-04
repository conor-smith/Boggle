import java.util.ArrayList;
import java.util.LinkedHashSet;

public class Boggle
{
    //The game board with (0,0) at the top left corner
    private String[][] board;
    private ArrayList<String> guessedWords = new ArrayList<String>();
    private int score;

    public Boggle()
    {
        reset();
    }

    public Boggle(String[][] board)
    {
        reset(board);
    }

    public boolean enterWord(LinkedHashSet<Position> positions) throws BoggleException
    {
        Position prev = null;
        String word = "";
        for(Position pos : positions)
        {
            if(pos.x < 0 || pos.x > 15 || pos.y < 0 || pos.y > 15)
                throw new BoggleException("Position " + pos.x + ", " + pos.y + " is out of bounds");
            
            if(prev != null)
                if(!(prev.x >= pos.x - 1 && prev.x <= pos.x + 1 &&
                   prev.y >= pos.y - 1 && prev.y <= pos.y + 1))
                   throw new BoggleException("List of positions is illegal");

            word += board[pos.x][pos.y];
        }

        if(!BoggleDictionary.wordIsLegal(word))
            return false;

        if(!guessedWords.contains(word))
        {
            score += getWordScore(word);
            guessedWords.add(word);
            return true;
        }
        else
        {
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

    public void reset()
    {
        board = BoardGenerator.generateBoard();
    }

    public void reset(String[][] board)
    {
        this.board = board;
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