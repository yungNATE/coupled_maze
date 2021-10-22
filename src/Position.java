
public class Position implements Cloneable{
    public int posX, posY;

    public Position(int x, int y) {
        posX = x;
        posX = y;
    }

    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
}

