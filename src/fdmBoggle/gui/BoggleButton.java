package fdmBoggle.gui;

import javax.swing.JButton;

public class BoggleButton extends JButton
{
    private static final long serialVersionUID = 1L;
    
    public int bx, by;

    public BoggleButton(int bx, int by)
    {
        this.bx = bx;
        this.by = by;
    }
}