package fdmBoggle.gui;

import javax.swing.*;
import java.awt.*;
import fdmBoggle.game.Boggle;

public class Gui extends JFrame
{
    private static final long serialVersionUID = 3781965546199397230L;

    public Gui()
    {
        super("Boggle");
        
        Boggle bog = new Boggle();
        String[][] board = bog.getBoard();

        BoggleButton[][] buttons = new BoggleButton[4][4];

        for(int i = 0;i < 4;i++)
            for(int j = 0;j < 4;j++)
            {
                buttons[i][j] = new BoggleButton();
                buttons[i][j].setCoordinates(i, j);
                buttons[i][j].setText(board[i][j]);
                add(buttons[i][j]);
            }

        setSize(400, 400);
        setLayout(new GridLayout(4,4));
        setVisible(true);
        setDefaultCloseOperation(Gui.EXIT_ON_CLOSE);

        bog.printBoard();
    }
}