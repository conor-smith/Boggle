package fdmBoggle.gui;

import java.awt.Color;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JButton;
import fdmBoggle.game.Boggle;

/**
 * ActionListener that is added to all BoggleButtons
 * Is used to keep track of all active buttons and available buttons, as well as entering words
 * "Active" buttons are buttons currently being used to construct a word. The order in which they are selected is recorded
 * "Available" buttons are buttons that can legally be appended onto the end of the Active buttons list
 * Also colours buttons appropriately
 */
public class ButtonManager implements ActionListener{

    //Stores buttons in the position they would be represented in on the gui
    private BoggleButton[][] buttons;
    //Buttons that are currently being used to construct a word. Stored in order of selection
    private ArrayList<BoggleButton> activeButtons = new ArrayList<BoggleButton>();
    //Buttons that can be appended onto active buttons
    private ArrayList<BoggleButton> availableButtons = new ArrayList<BoggleButton>();
    //The game in question
    private Boggle boggle;
    //The gui the game is being played on
    private Gui gui;

    //Colours representing the board
    private final Color DEFAULT_COLOUR = Color.LIGHT_GRAY;
    private final Color ACTIVE_COLOUR = Color.WHITE;
    private final Color AVAILABLE_COLOUR = Color.CYAN;

    /**
     * Default constructor
     * @param buttons - All buttons this listener has been added to, in the positions they are represented in the GUI
     * @param gui - The gui the game is being played on
     */
    public ButtonManager(BoggleButton[][] buttons, Gui gui)
    {
        this.buttons = buttons;
        this.gui = gui;
    }

    /**
     * Used to set private Boggle game field
     * @param boggle - The active Boggle object being played
     */
    public void setBoggle(Boggle boggle)
    {
        this.boggle = boggle;
    }

    /**
     * Is called whenever a BoggleButton is clicked or when the enter button is pushed
     * If the button is the enter button, the text in all active buttons are combined to form a word, then this word is entered into the active boggle game via enterword
     * If enterWord() returns true, the score in the gui is updated and the word is added to the list of entered words
     * If the button is an active button, it and all following buttons are removed from the active buttons list
     * If the button is an available button, it is appended to active buttons list
     * If the button is neither active nor available, the activeButtons list is wiped clean and a new one is started from the selected button
     * After any of these operations, the available buttons list is updated and the board is coloured appropriately
     */
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

    /*
     * Builds a word from active buttons and enters it into the active Boggle game
     * The order of the active buttons list is used to construct the word
     */
    private void enter()
    {
        String word = "";
        for(BoggleButton diceValue : activeButtons)
            word += diceValue.getText();

        //If word is correct, add it to the entered words list
        if(boggle.enterWord(word))
            gui.wordArea.append(word.toLowerCase() + "\n");

        resetActiveButtons();
        updateAvailableButtons();
        gui.updateScore();
    }

    /**
     * Is called whenever the active Boggle game has been reset
     * Sets all the text of the BoggleButtons and resets active and available buttons
     */
    public void reset()
    {
        resetActiveButtons();
        updateAvailableButtons();


        for(BoggleButton[] tempA : buttons)
            for(BoggleButton tempB : tempA)
                tempB.setEnabled(true);
    }

    /**
     * Is called when the game times out
     * Disables all BoggleButtons
     */
    public void finish()
    {
        for(BoggleButton[] tempA : buttons)
            for(BoggleButton tempB : tempA)
                tempB.setEnabled(false);
        
        resetActiveButtons();
        updateAvailableButtons();
    }

    // Starts a new list of active buttons with the entered button being the only member
    private void newWord(BoggleButton source)
    {
        resetActiveButtons();
        addLetters(source);
    }
    
    //Removes the selected button and all following buttons from the list of active buttons
    private void removeLetters(BoggleButton source)
    {
        java.util.List<BoggleButton> toRemove = activeButtons.subList(activeButtons.indexOf(source), activeButtons.size());
        for(BoggleButton temp : toRemove)
            temp.setBackground(DEFAULT_COLOUR);

        activeButtons.removeAll(toRemove);
    }

    //Adds source to list of active buttons
    private void addLetters(BoggleButton source)
    {
        activeButtons.add(source);
    }

    //Empties active buttons list
    private void resetActiveButtons()
    {
        for(BoggleButton temp : activeButtons)
            temp.setBackground(DEFAULT_COLOUR);
        
        activeButtons = new ArrayList<BoggleButton>();
    }

    /*
     * Updates available buttons based on the last member of the active buttons list
     * If there are no active buttons, available buttons is set as an empty list
     * Also colours buttons
     */
    private void updateAvailableButtons()
    {
        for(BoggleButton temp : availableButtons)
            temp.setBackground(DEFAULT_COLOUR);

        availableButtons = new ArrayList<BoggleButton>();

        if(activeButtons.size() > 0)
        {
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
                temp.setBackground(ACTIVE_COLOUR);
                
            for(BoggleButton temp : availableButtons)
                temp.setBackground(AVAILABLE_COLOUR);
        }
    }
}