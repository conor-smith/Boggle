package fdmBoggle.gui;

import javax.swing.JButton;

public class BoggleButton extends JButton
{
    private static final long serialVersionUID = -3286353213911920288L;
    
    public int bx;
    public int by;

    public void setCoordinates(int bx, int by)
    {
        this.bx = bx;
        this.by = by;
    }
}