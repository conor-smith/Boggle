package fdmBoggle.game;

public class Position {

    public int x;
    public int y;
    
    public Position(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode()
    {
        return x + y * 4;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj.hashCode() == hashCode())
            return true;
        else
            return false;
    }
}