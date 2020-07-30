import java.io.*;
import java.util.HashSet;
import java.util.ArrayList;

public class WordFinder
{
    static HashSet<String> dictionary;

    public static ArrayList<String> getWords(String[][] board)
    {
        if(dictionary == null)
        {
            HashSet<String> dictionary = new HashSet<String>(9900);
            try
            {
                BufferedReader br = new BufferedReader(new FileReader("dict.txt"));
                String line = br.readLine();

                while(line != null)
                {
                    dictionary.add(line);
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

        for(int i = 0;i < 16;i++)
        {
            
        }
        
    }    
}