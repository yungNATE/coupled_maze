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

    public void add(Position posDifference) {
        posX += posDifference.posX;
        posY += posDifference.posY;

    }

    @Override
    public boolean equals(Object posDifference) {
        return (posX == ((Position) posDifference).posX && posY == ((Position) posDifference).posY);
    }
}
