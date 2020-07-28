import java.util.ArrayList;

public class Boggle
{
    //The game board with (0,0) at the top left corner
    private String[][] board;
    private ArrayList<String> legalWords;
    private ArrayList<String> guessedWords;
    private int score;

    public Boggle()
    {
        reset();
    }

    public Boggle(String[][] board)
    {
        reset(board);
    }

    public Result enterWord(String word)
    {
        if(guessedWords.contains(word))
            return Result.ALREADYENTERED;
        
        if(legalWords.contains(word))
        {
            score++;
            if(score >= legalWords.size())
                return Result.WIN;
            else
                return Result.CORRECT;
        }
        else
        {
            return Result.INCORRECT;
        }
    }

    public void printBoard()
    {
        for(int i = 0;i < board.length;i++)
            for(int j = 0;j < board[0].length;j++)
                System.out.println(board[i][j]);
    }

    public String[][] getBoard()
    {
        return board;
    }

    public ArrayList<String> getLegalWords()
    {
        return legalWords;
    }

    public void reset()
    {
        generateBoard();
        findLegalWords();
    }

    public void reset(String[][] board)
    {
        this.board = board;
        findLegalWords();
    }

    //TODO
    private void generateBoard()
    {
        board = BoardGenerator.generateBoard();
    }

    //TODO
    private void findLegalWords()
    {

    }
}