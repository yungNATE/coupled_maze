
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

enum Direction {UP, DOWN, RIGHT, LEFT}

abstract public class Entity {

    static int STEP_PIXELS = 50;
    Thread currentThread;
    Fenetre fenetre;
    GameImage icon;
    Position pos;
    GameMap map;
    Direction currentDirection;
    Tile currentTile;
    Tile nextTile;
    volatile Boolean isMoving;

    Entity(int posX, int posY, String url, GameMap gameMap) {
        icon = new GameImage(url);
        pos = new Position(posX, posY);
        fenetre = (Fenetre) gameMap;
        map = gameMap;
        isMoving = false;
        display();
    }

    public void display(){
        icon.display(fenetre, pos.posX, pos.posY);
    }

    public void move(Direction direction) {
        this.currentDirection = direction;
        new EntityAnimation(this, EntityAnimation.Animation.MOVE).start();
    }


    public void hitWall(Direction direction) {
        this.currentDirection = direction;
        new EntityAnimation(this, EntityAnimation.Animation.HIT).start();
    }

    public void fall(Direction direction) {
        this.currentDirection = direction;
        new EntityAnimation(this, EntityAnimation.Animation.FALL).start();
    }
}