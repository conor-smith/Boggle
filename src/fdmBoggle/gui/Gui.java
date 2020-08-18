package fdmBoggle.gui;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.*;
import fdmBoggle.game.Boggle;

/**
 * A dedicated gui class for the boggle game
 */
public class Gui extends JFrame implements ActionListener
{
    private static final long serialVersionUID = 3781965546199397230L;

    BoggleButton[][] board;
    JButton reset, enter;
    JLabel title, timerLabel, scoreLabel, score, words;
    JScrollPane wordbox;
    ButtonManager buttonManager;
    JTextArea wordArea;
    Boggle boggle;
    Timer timer;
    int time;

    /**
     * The default constructor
     * Sets all fields and Gui components
     * Creates Boggle board instance
     * Creates ButtonManager instance and adds it to all appropriate buttons
     * Adds itself as an actionListener to reset button
     */
    public Gui()
    {
        super("Boggle");
        
        //components
        Font titleFont = new Font("Dialog", Font.PLAIN, 50);
        Font defaultFont = new Font("Dialog", Font.PLAIN, 20);
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
                board[i][j].setFont(defaultFont);
                buttons.add(board[i][j]);
            }

        reset = new JButton("Reset");
        reset.setBounds(50, 650, 100, 30);
        reset.setFont(defaultFont);
        reset.addActionListener(this);
        enter = new JButton("Enter");
        enter.setBounds(350, 650, 100, 30);
        enter.setFont(defaultFont);
        enter.addActionListener(buttonManager);

        title = new JLabel("Boggle", SwingConstants.CENTER);
        title.setBounds(0, 0, 700, 100);
        title.setFont(titleFont);
        timerLabel = new JLabel("Timer");
        timerLabel.setBounds(100, 100, 200, 40);
        timerLabel.setFont(defaultFont);
        scoreLabel = new JLabel("Score");
        scoreLabel.setBounds(500, 100, 70, 40);
        scoreLabel.setFont(defaultFont);
        score = new JLabel("0");
        score.setBounds(570, 100, 50, 40);
        score.setFont(defaultFont);

        wordArea = new JTextArea();
        wordArea.setEditable(false);
        wordArea.setFont(defaultFont);

        wordbox = new JScrollPane(wordArea);
        wordbox.setBounds(500, 200, 200, 500);
        
        //All components are added here
        add(buttons);
        add(reset);
        add(title);
        add(enter);
        add(timerLabel);
        add(scoreLabel);
        add(score);
        add(wordbox);

        //Gui variables are set here
        setSize(700, 750);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        reset();

        buttonManager.setBoggle(boggle);
    }

    /**
     * Calls reset on active Boggle game
     * Updates all appropriate components
     */
    public void reset()
    {
        //Initialises game if it hasn't yet been initialised
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
        time = 180;
        if(timer != null)
            timer.cancel();
        timer = new Timer(true);
        timer.scheduleAtFixedRate(new CountDown(), 1000, 1000);
    }
    
    /**
      * Updates visible score
      */
    public void updateScore()
    {
        score.setText(Integer.toString(boggle.getScore()));
    }

    /**
     * Calls this.reset()
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        reset();
        buttonManager.reset();
    }

    /*
     * A timer task created specifically for the timer in the of the screen
     * Decrements time value by 1 every second
     * Calls finish() on ButtonManager once time runs out
     */
    private class CountDown extends TimerTask
    {

        @Override
        public void run()
        {
            time--;
            
            if(time < 0)
            {
                timer.cancel();
                timerLabel.setText("Times up!");
                buttonManager.finish();
            }
            else
            {
                timerLabel.setText(time / 60 + ":" + (time % 60 < 10 ? "0" + time % 60 : time % 60));
            }
        }
        
    }

    public static void main(String[] args)
    {
        new Gui();
    }
}