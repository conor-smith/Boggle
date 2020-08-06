package fdmBoggle.ai;

import java.io.*;
import java.util.HashSet;

public class BoggleDictionary
{
    private static HashSet<String> dict;

    public static void init()
    {
        dict = new HashSet<String>(64486);
        try
        {
            BufferedReader br = new BufferedReader(new FileReader("fdmBoggle/data/dict.txt"));
            String line = br.readLine();

            while(line != null)
            {
                dict.add(line);
                line = br.readLine();
            }
            br.close();
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    public static boolean wordIsLegal(String word)
    {
        if(dict == null)
            init();

        return(dict.contains(word.toLowerCase()));
    }
}