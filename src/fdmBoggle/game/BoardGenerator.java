package fdmBoggle.game;

import java.util.*;
import java.io.*;

/**
 * This class contains one static method which returns a 4 * 4 board based on a specific text file containing the game "dice"
 * It's constructor is private. This class cannot be instantiated
 */
public class BoardGenerator {

    /** Exists solely to prevent instantiation */
    private BoardGenerator()
    {}
    
    /**
     * Generates a 4 * 4 board
     * Uses a text file '$HOME/Documents/data/dice.txt' to build board. If file is not present, shuts down JVM
     * @return board as represented by String[][]
     */
    public static String[][] generateBoard()
    {
        String[][] board = new String[4][4];
        Random rand = new Random();
        ArrayList<String> letters = new ArrayList<String>();
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(new File(System.getProperty("user.home") + "/Documents/data/dice.txt")));
            String line = br.readLine();
            for(int i = 0;i < 16;i++)
            {
                String[] dice = line.split(" ");
                letters.add(dice[rand.nextInt(6)]);
                line = br.readLine();
            }

        }
        catch(Exception e)
        {
            System.out.println("Error: " + e.getMessage() + " in BoardGenerate.generateBoard(). Aborting");
            System.exit(1);
        }

        Collections.shuffle(letters);
        for(int i = 0;i < 16;i++)
            board[i / 4][i % 4] = letters.get(i);

        return board;
    }
}