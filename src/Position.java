public class Position implements Cloneable {
    public int posX, posY;

    public Position(int x,int y){
        posX = x;
        posY = y;
    }

    public String toString(){
        return posX + ":" + posY + "; ";
    }

    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
}
