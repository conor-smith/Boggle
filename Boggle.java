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

    public boolean enterWord(String word)
    {
        if(legalWords.contains(word) && !guessedWords.contains(word))
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
                System.out.print(board[i][j] + " ");
            System.out.println();
        }
    }

    public String[][] getBoard()
    {
        return board;
    }

    public ArrayList<String> getLegalWords()
    {
        return legalWords;
    }

    public int getScore()
    {
        return score;
    }

    public int getMaxScore()
    {
        int maxScore = 0;
        for(String legalWord : legalWords)
            maxScore += getWordScore(legalWord);
        
        return maxScore;
    }

    public void reset()
    {
        board = BoardGenerator.generateBoard();
        findLegalWords();
    }

    public void reset(String[][] board)
    {
        this.board = board;
        findLegalWords();
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
                return 4;
            else
                return 11;
    }
    
    //TODO
    private void findLegalWords()
    {

    }
}