package fdmBoggle.ai;

import java.io.*;
import java.util.HashSet;

/**
 * Class used by WordGetter to store and search through a dictionary
 * Cannot be instantiated
 */
public class BoggleDictionary
{
    /*
     * Stores all potential valid words
     * Is a HashSet to reduce time complexity when searching for word
     */
    private static HashSet<String> dict;

    //Exists solely to prevent instantiation
    private BoggleDictionary()
    {}

    /**
     * Builds internal dictionary
     * Will be called automatically if not called before wordIsLegal()
     * Uses dictionary file "dict.txt" at "$HOME/Documents/data/"
     * Automatically shuts down JVM if file does not exist
     */
    public static void init()
    {
        dict = new HashSet<String>(65100);
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.home") + "/Documents/data/dict.txt"));
            String line = br.readLine();

            //Reads through all lines in file
            while(line != null)
            {
                //Automatically converts word to lowercase
                dict.add(line.toLowerCase());
                line = br.readLine();
            }
            br.close();
        }
        catch(IOException e)
        {
            System.out.println("Error: " + e.getMessage() + " at BoggleDictionary.init(). Aborting");
            System.exit(1);
        }
    }

    /**
     * Returns true if entered word is legal
     * Will call init if it hasn't already been called
     * @param word - String representing an entered word
     * @return true if word is valid
     */
    public static boolean wordIsLegal(String word)
    {
        if(dict == null)
            init();

        return(dict.contains(word.toLowerCase()));
    }
}