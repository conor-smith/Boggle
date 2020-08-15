package fdmBoggle.gui;

import java.awt.Color;

import javax.swing.JButton;

/**
 * A slightly modified JButton with a default background colour, and a set of x, y co-ordinates
 * Used to represent a letter / string on the board
 */
public class BoggleButton extends JButton
{
    private static final long serialVersionUID = 1L;
    
    /**
     * Represents x value of button
     */
    public int bx;
    /**
     * Represents y value of button
     */
    public int by;

    /**
     * Default constructor
     * @param bx - sets bx field
     * @param by - sets by field
     */
    public BoggleButton(int bx, int by)
    {
        this.bx = bx;
        this.by = by;
        setBackground(Color.LIGHT_GRAY);
    }
}