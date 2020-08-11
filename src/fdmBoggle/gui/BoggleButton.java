package fdmBoggle.gui;

import javax.swing.JButton;

public class BoggleButton extends JButton
{
    public int bx, by;

    public BoggleButton(int bx, int by)
    {
        this.bx = bx;
        this.by = by;
    }
}