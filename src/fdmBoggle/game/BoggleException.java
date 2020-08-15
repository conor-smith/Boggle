package fdmBoggle.game;

/**
 * Custom exception class for this game
 */
public class BoggleException extends Exception
{

    private static final long serialVersionUID = 1L;

    public BoggleException(String message)
    {
        super(message);
    }
}