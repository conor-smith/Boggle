package fdmBoggle.tournament;

import java.io.File;
import fdmBoggle.game.Boggle;

/**
 * An abstract class created for the tournament
 * All players must have their classes extend this
 */
public abstract class BogglePlayer
{
    /**
     * Build the dictionary using this file
     * The file have one word on each line
     * Hint - use a HashSet to store the words
     * @param file - A text file containing all valid words
     */
    public abstract void buildDictionary(File file);
    /**
     * Enters all words the player can find into the entered boggle game
     * Once this ends, the score will be checked by the tournament object as well as the time this method took to execute
     * @param boggle - the Game the player will be operating on
     */
    public abstract void playGame(Boggle boggle);

    /**
     * Returns the class name
     */
    public String toString()
    {
        return getClass().getName();
    }
}