<<<<<<< HEAD

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

=======
public class Position {
    public int posX, posY;
    public Position(int x,int y){
        posX = x;
        posX = y;
    }
    public String toString(){
        return posX + ":" + posY;
    }
}
>>>>>>> map
