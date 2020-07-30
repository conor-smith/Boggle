import java.io.*;
import java.util.HashSet;

public class BoggleDictionary
{
    private static HashSet<String> dict;

    public static boolean wordIsLegal(String word)
    {
        if(dict == null)
        {
            dict = new HashSet<String>(9900);
            try
            {
                BufferedReader br = new BufferedReader(new FileReader("dict.txt"));
                String line = br.readLine();

                while(line != null)
                {
                    dict.add(line);
                    br.readLine();
                }

                br.close();
            }
            catch(IOException e)
            {
                System.out.println(e.getMessage());
                System.exit(1);
            }
        }

        if(dict.contains(word))
            return true;
        else
            return false;
    }
}