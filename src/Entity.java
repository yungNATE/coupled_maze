import javax.swing.*;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

enum Direction {UP, DOWN, RIGHT, LEFT}

abstract public class Entity {

    static int STEP_PIXELS = 50;
    Fenetre fenetre;
    Image icon;

    int positionX, positionY;

    Entity(int posX, int posY, String url, Fenetre f) {
        icon = new Image(url);
        positionX = posX;
        positionY = posY;
        fenetre = f;
    }

    void move(Direction direction) throws InterruptedException {
        switch (direction) {
            case DOWN:
                moveVertically(Direction.DOWN);
                positionY += STEP_PIXELS;
                break;

            case UP:
                moveVertically(Direction.UP);
                positionY -= STEP_PIXELS;
                break;

            case RIGHT:
                positionX += STEP_PIXELS;
                break;

            case LEFT:
                positionX -= STEP_PIXELS;
                break;
        }
    }


    void moveVertically(Direction d) throws InterruptedException {
        int currentPosition = positionY, oldPosition;
        System.out.print("hey");
        icon.effacer(fenetre, positionX, positionY);
        for (int i = 1; i <= STEP_PIXELS; i++) {
            oldPosition = currentPosition;
            TimeUnit.MILLISECONDS.sleep(50);
            icon.effacer(fenetre, positionX, oldPosition);
            if (d == Direction.DOWN) icon.afficher(fenetre, positionX, ++currentPosition);
            else {
                System.out.print("heyfor");
                icon.afficher(fenetre, positionX, --currentPosition);
            }
        }

    }

    abstract void fall();

    void hitWall() {

    }

    void show() {

    }

}
