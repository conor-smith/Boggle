package fdmBoggle.gui;

import java.awt.Color;
import java.awt.event.*;
import java.util.ArrayList;

public class ButtonManager implements ActionListener{

    private BoggleButton[][] buttons;
    private ArrayList<BoggleButton> activeButtons = new ArrayList<BoggleButton>();
    private ArrayList<BoggleButton> availableButtons = new ArrayList<BoggleButton>();

    public ButtonManager(BoggleButton[][] buttons)
    {
        this.buttons = buttons;
    }

    @Override
    public void actionPerformed(ActionEvent e)
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

    private void newWord(BoggleButton source)
    {
        for(BoggleButton temp : activeButtons)
            temp.setBackground(Color.LIGHT_GRAY);

        activeButtons = new ArrayList<BoggleButton>();
        activeButtons.add(source);
    }
    
    private void removeLetters(BoggleButton source)
    {
        java.util.List<BoggleButton> toRemove = activeButtons.subList(activeButtons.indexOf(source) + 1, activeButtons.size());
        for(BoggleButton temp : toRemove)
            temp.setBackground(Color.LIGHT_GRAY);

        activeButtons.removeAll(toRemove);
    }

    private void addLetters(BoggleButton source)
    {
        activeButtons.add(source);
    }

    private void updateAvailableButtons()
    {
        for(BoggleButton temp : availableButtons)
            temp.setBackground(Color.LIGHT_GRAY);

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
                temp.setBackground(Color.BLUE);
        }
    }
}