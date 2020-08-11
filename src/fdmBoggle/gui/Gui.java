package fdmBoggle.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import fdmBoggle.game.Boggle;

public class Gui extends JFrame implements ActionListener
{
    private static final long serialVersionUID = 3781965546199397230L;

    BoggleButton[][] board;
    JButton reset, enter;
    JLabel title, timer, scoreLabel, score, words;
    JScrollPane wordbox;
    ButtonManager buttonManager;
    JTextArea wordArea;
    Boggle boggle;

    public Gui()
    {
        super("Boggle");
        
        //components
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(4, 4));
        buttons.setBounds(50, 200, 400, 400);
        board = new BoggleButton[4][4];
        buttonManager = new ButtonManager(board, this);
        for(int i = 0;i < 4;i++)
            for(int j = 0;j < 4;j++)
            {
                board[i][j] = new BoggleButton(i, j);
                board[i][j].addActionListener(buttonManager);
                buttons.add(board[i][j]);
            }

        reset = new JButton("Reset");
        reset.setBounds(50, 650, 100, 30);
        reset.addActionListener(this);
        enter = new JButton("Enter");
        enter.setBounds(350, 650, 100, 30);
        enter.addActionListener(buttonManager);

        title = new JLabel("Boggle");
        title.setBounds(0, 0, 700, 100);
        timer = new JLabel("Timer");
        timer.setBounds(100, 100, 200, 40);
        scoreLabel = new JLabel("Score");
        scoreLabel.setBounds(500, 100, 50, 40);
        score = new JLabel("0");
        score.setBounds(550, 100, 50, 40);

        wordArea = new JTextArea();
        wordArea.setEditable(false);

        wordbox = new JScrollPane(wordArea);
        wordbox.setBounds(500, 200, 200, 500);
        

        add(buttons);
        add(reset);
        add(title);
        add(enter);
        add(timer);
        add(scoreLabel);
        add(score);
        add(wordbox);

        setSize(700, 750);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        reset();

        buttonManager.setBoggle(boggle);
    }

    public void reset()
    {
        if(boggle == null)
            boggle = new Boggle();
        else
            boggle.reset();

        String[][] gameBoard = boggle.getBoard();
        for(int i = 0;i < 4;i++)
            for(int j = 0;j < 4;j++)
                board[i][j].setText(gameBoard[i][j]);
        
        wordArea.setText("");
        updateScore();
    }

    public void updateScore()
    {
        score.setText(Integer.toString(boggle.getScore()));
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        reset();
        buttonManager.reset();
    }
}