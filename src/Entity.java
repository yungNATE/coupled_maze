import javax.swing.*;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

enum Direction {UP, DOWN, RIGHT, LEFT}

abstract public class Entity {

    static int STEP_PIXELS = 50;
    Fenetre fenetre;
    Image icon;

    Position pos;

    Entity(int posX, int posY, String url, Fenetre f) {
        icon = new Image(url);
        pos = new Position(posX, posY);
        fenetre = f;
    }

    void move(Direction direction) throws InterruptedException, CloneNotSupportedException {
        switch (direction) {
            case DOWN:
                moveVertically(Direction.DOWN);
                break;

            case UP:
                moveVertically(Direction.UP);
                pos.posY -= STEP_PIXELS;
                break;

            case RIGHT:
                pos.posY += STEP_PIXELS;
                break;

            case LEFT:
                pos.posY -= STEP_PIXELS;
                break;
        }
    }


    void moveVertically(Direction d) throws InterruptedException, CloneNotSupportedException {
        Position currentPosition = pos, oldPosition;

        for (int i = 1; i <= STEP_PIXELS; i++) {
            oldPosition = (Position) currentPosition.clone();
            TimeUnit.MILLISECONDS.sleep(50);
            icon.effacer(fenetre, oldPosition.posX, oldPosition.posY);
            if (d == Direction.DOWN) icon.afficher(fenetre, currentPosition.posX, ++currentPosition.posY);
            else {
                icon.afficher(fenetre, currentPosition.posX, --currentPosition.posY);
            }
        }


    }

    abstract void fall();

    void hitWall() {

    }

    void show() {

    }

}
