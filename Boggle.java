import java.util.ArrayList;

public class Boggle
{
    //The game board with (0,0) at the top left corner
    private char[][] board;
    private ArrayList<String> legalWords;
    private ArrayList<String> guessedWords;
    private int score;

    public Boggle()
    {
        reset();
    }

    public Boggle(char[][] board)
    {
        reset(board);
    }

    public TempName enterWord(String word)
    {
        if(guessedWords.contains(word))
            return TempName.ALREADYENTERED;
        
        if(legalWords.contains(word))
        {
            score++;
            if(score >= legalWords.size())
                return TempName.WIN;
            else
                return TempName.CORRECT;
        }
        else
        {
            return TempName.INCORRECT;
        }
    }

    public void printBoard()
    {
        for(int i = 0;i < board.length;i++)
            for(int j = 0;j < board[0].length;j++)
                System.out.println(board[i][j]);
    }

    public char[][] getBoard()
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

    public void reset(char[][] board)
    {
        this.board = board;
        findLegalWords();
    }

    //TODO
    private void generateBoard()
    {

    }

    //TODO
    private void findLegalWords()
    {

    }
}