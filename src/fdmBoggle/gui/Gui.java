package fdmBoggle.gui;

import javax.swing.*;
import java.awt.*;
import fdmBoggle.game.Boggle;

public class Gui extends JFrame
{
    private static final long serialVersionUID = 3781965546199397230L;

    Button[][] board;

    public Gui()
    {
        super("Boggle");

        Boggle bog = new Boggle();
        String[][] gBoard = bog.getBoard();
        
        //components
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(4, 4));
        buttons.setBounds(50, 200, 400, 400);

        boolean firstGame;
        if(board == null)
        {
            firstGame = true;
            board = new Button[4][4];
        }
        else
        {
            firstGame = false;
        }

        JButton reset = new JButton("Reset");
        reset.setBounds(50, 650, 100, 30);

        JButton enter = new JButton("Enter");
        enter.setBounds(350, 650, 100, 30);

        JLabel title = new JLabel("Boggle");
        title.setBounds(0, 0, 700, 100);

        JLabel timer = new JLabel("Timer");
        timer.setBounds(100, 100, 200, 40);

        JLabel score = new JLabel("Score");
        score.setBounds(500, 100, 200, 40);

        JScrollPane words = new JScrollPane();
        words.setBounds(500, 200, 200, 500);

        for(int i = 0;i < 4;i++)
            for(int j = 0;j < 4;j++)
                if(firstGame)
                {
                    board[i][j] = new Button(gBoard[i][j]);
                    buttons.add(board[i][j]);
                }
                else
                {
                    board[i][j].setName(gBoard[i][j]);
                }
        
        add(buttons);
        add(reset);
        add(title);
        add(enter);
        add(timer);
        add(score);
        add(words);

        setSize(700, 750);
        setLayout(null);
        setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}