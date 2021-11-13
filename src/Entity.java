
import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

enum Direction {UP, DOWN, RIGHT, LEFT}

abstract public class Entity {

    static int STEP_PIXELS = 50;
    Fenetre fenetre;
    GameImage icon;
    Position pos;

    Entity(int posX, int posY, String url, Fenetre f) {
        icon = new GameImage(url);
        pos = new Position(posX, posY);
        fenetre = f;
    }

    public void move(Direction direction) throws InterruptedException, CloneNotSupportedException {
        switch (direction) {
            case DOWN:
                moveOneTile(Direction.DOWN);
                break;

            case UP:
                moveOneTile(Direction.UP);
                break;

            case RIGHT:
                moveOneTile(Direction.RIGHT);
                break;

            case LEFT:
                moveOneTile(Direction.LEFT);
                break;
        }
    }


    private void moveOneTile(Direction d) throws InterruptedException, CloneNotSupportedException {
        Position currentPosition = pos;

        for (int i = 1; i <= STEP_PIXELS; i++) {
            moveOnePixel(d, currentPosition);
        }
    }

    public void hitWall(Direction direction) throws CloneNotSupportedException, InterruptedException {
        switch (direction) {
            case DOWN:
                hitWallAnimation(Direction.DOWN, Direction.UP);
                break;

            case UP:
                hitWallAnimation(Direction.UP, Direction.DOWN);
                break;

            case RIGHT:
                hitWallAnimation(Direction.RIGHT, Direction.LEFT);
                break;

            case LEFT:
                hitWallAnimation(Direction.LEFT, Direction.RIGHT);
                break;
        }
    }

    public void fall() throws InterruptedException {
        for (int i = 1 ; i < STEP_PIXELS ; i+=STEP_PIXELS/10) {
            Image newimg = this.icon.icon.getImage().getScaledInstance(STEP_PIXELS-i, STEP_PIXELS-i, java.awt.Image.SCALE_SMOOTH);
            icon = new GameImage(new ImageIcon(newimg));
            icon.display(fenetre, 60+i/2, 60+i/2);
            TimeUnit.MILLISECONDS.sleep(50);
            icon.erase(fenetre, 60+i/2, 60+i/2);
        }
    }


    private void hitWallAnimation(Direction d, Direction opposite) throws CloneNotSupportedException, InterruptedException {
        Position currentPosition = pos;

        for (int i = 1; i <= STEP_PIXELS / 2; i++) {
            moveOnePixel(d, currentPosition);
        }
        for (int i = 1; i <= STEP_PIXELS / 8; i++) {
            moveOnePixel(opposite, currentPosition);
        }
        for (int i = 1; i <= STEP_PIXELS / 8; i++) {
            moveOnePixel(d, currentPosition);
        }
        for (int i = 1; i <= STEP_PIXELS / 2; i++) {
            moveOnePixel(opposite, currentPosition);
        }
    }

    private void moveOnePixel(Direction d, Position currentPosition) throws CloneNotSupportedException, InterruptedException {
        Position oldPosition;
        oldPosition = (Position) currentPosition.clone();
        TimeUnit.MILLISECONDS.sleep(20);
        icon.erase(fenetre, oldPosition.posX, oldPosition.posY);
        //TODO : switch v
        if (d == Direction.DOWN) icon.display(fenetre, currentPosition.posX, ++currentPosition.posY);
        else if (d == Direction.UP) icon.display(fenetre, currentPosition.posX, --currentPosition.posY);
        else if (d == Direction.RIGHT) icon.display(fenetre, ++currentPosition.posX, currentPosition.posY);
        else icon.display(fenetre, --currentPosition.posX, currentPosition.posY);
    }


}