package fdmBoggle.gui;

import java.awt.Color;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JButton;
import fdmBoggle.game.Boggle;

public class ButtonManager implements ActionListener{

    private BoggleButton[][] buttons;
    private ArrayList<BoggleButton> activeButtons = new ArrayList<BoggleButton>();
    private ArrayList<BoggleButton> availableButtons = new ArrayList<BoggleButton>();
    private Boggle boggle;
    private Gui gui;

    private final Color DEFAULT_COLOUR = Color.LIGHT_GRAY;

    public ButtonManager(BoggleButton[][] buttons, Gui gui)
    {
        this.buttons = buttons;
        this.gui = gui;
    }

    public void setBoggle(Boggle boggle)
    {
        this.boggle = boggle;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(((JButton) e.getSource()).getText() == "Enter")
        {
            enter();
        }
        else
        {
            BoggleButton source = (BoggleButton) e.getSource();

            if(activeButtons.contains(source))
                removeLetters(source);
            else if(!availableButtons.contains(source))
                newWord(source);
            else
                addLetters(source);
        
            updateAvailableButtons();
        }
    }

    public void enter()
    {
        String word = "";
        for(BoggleButton temp : activeButtons)
            word += temp.getText();

        if(boggle.enterWord(word))
            gui.wordArea.append(word + "\n");

        resetActiveButtons();
        updateAvailableButtons();
        gui.updateScore();
    }

    public void reset()
    {
        resetActiveButtons();
        updateAvailableButtons();


        for(BoggleButton[] tempA : buttons)
            for(BoggleButton tempB : tempA)
                tempB.setEnabled(true);
    }

    public void finish()
    {
        for(BoggleButton[] tempA : buttons)
            for(BoggleButton tempB : tempA)
                tempB.setEnabled(false);
    }

    private void newWord(BoggleButton source)
    {
        resetActiveButtons();
        addLetters(source);
    }
    
    private void removeLetters(BoggleButton source)
    {
        java.util.List<BoggleButton> toRemove = activeButtons.subList(activeButtons.indexOf(source), activeButtons.size());
        for(BoggleButton temp : toRemove)
            temp.setBackground(DEFAULT_COLOUR);

        activeButtons.removeAll(toRemove);
    }

    private void addLetters(BoggleButton source)
    {
        activeButtons.add(source);
    }

    private void resetActiveButtons()
    {
        for(BoggleButton temp : activeButtons)
            temp.setBackground(DEFAULT_COLOUR);
        
        activeButtons = new ArrayList<BoggleButton>();
    }

    private void updateAvailableButtons()
    {
        for(BoggleButton temp : availableButtons)
            temp.setBackground(DEFAULT_COLOUR);

        if(activeButtons.size() > 0)
        {
            availableButtons = new ArrayList<BoggleButton>();
            BoggleButton last = activeButtons.get(activeButtons.size() - 1);
            int x = last.bx;
            int y = last.by;

            for(int i = x - 1;i <= x + 1;i++)
                for(int j = y - 1;j <= y + 1;j++)
                    if(i >= 0 && i < 4 && j >= 0 && j < 4)
                    {
                        BoggleButton temp = buttons[i][j];
                        if(!activeButtons.contains(temp))
                            availableButtons.add(temp);
                    }

            for(BoggleButton temp : activeButtons)
                temp.setBackground(Color.WHITE);
                
            for(BoggleButton temp : availableButtons)
                temp.setBackground(Color.CYAN);
        }
    }
}