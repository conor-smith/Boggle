package fdmBoggle.tournament;

import java.io.File;
import fdmBoggle.game.Boggle;

public abstract class BogglePlayer
{
    public abstract void buildDictionary(File file);
    public abstract void playGame(Boggle boggle);

    public String toString()
    {
        return getClass().getName();
    }
}