package boggle.game;

import java.util.*;
import java.io.*;

public class BoardGenerator {
    
    public static String[][] generateBoard()
    {
        String[][] board = new String[4][4];
        Random rand = new Random();
        ArrayList<String> letters = new ArrayList<String>();
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(new File("../data/dice.txt")));
            String line = br.readLine();
            for(int i = 0;i < 16;i++)
            {
                String[] dice = line.split(" ");
                letters.add(dice[rand.nextInt(6)]);
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }

        Collections.shuffle(letters);
        for(int i = 0;i < 16;i++)
            board[i / 4][i % 4] = letters.get(i);

        return board;
    }
}