import fdmBoggle.ai.WordGetter;
import fdmBoggle.game.*;
import fdmBoggle.tournament.*;
import java.io.*;

public class DefaultPlayer2 extends BogglePlayer
{
    public void buildDictionary(File file)
    {
        //Do nothing
    }

    public void playGame(Boggle boggle)
    {
        for(String word : WordGetter.getLegalWords(boggle))
            boggle.enterWord(word);
    }
}