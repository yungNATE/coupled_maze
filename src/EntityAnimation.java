import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class EntityAnimation extends Thread {

    enum Animation {HIT, MOVE, FALL}

    final int STEP_PIXELS = 50;
    volatile Animation ani;
    volatile Entity e;
    volatile boolean isRunning = false;
    Fenetre fenetre;

    public EntityAnimation(Entity entity, Animation a) {
        e = entity;
        ani = a;
        fenetre = entity.fenetre;
    }

    public void setAnimation(Animation animation) {
        ani = animation;
    }

    @Override
    public void run() {
        if (e.isMoving != true) {

                e.isMoving = true;
                switch (ani) {
                    case MOVE:
                        moveOneTile();
                        break;
                    case HIT:
                        hitWall();
                        break;
                    case FALL:
                        fall();
                        break;

            }
        }
        // L'animation est arrêtée
        e.isMoving = false;
        fenetre.repaint(); // redessiner l'image de fond pour effacer les flocons
    }


    private void moveOneTile() {
        for (int i = 1; i <= STEP_PIXELS; i++) {
            moveOnePixel(e.currentDirection);
        }
    }

    private void moveOnePixel(Direction d) {
        Position oldPosition = null;
        try {
            oldPosition = (Position) e.pos.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            cloneNotSupportedException.printStackTrace();
        }
        fenetre.repaint(); //dessiner l'image de fond pour effacer les ancienes positions des flocons
        e.icon.erase(fenetre, oldPosition.posX, oldPosition.posY);
        e.map.drawMaps();
        // fenetre.repaint();

        switch (d) {
            case DOWN:
                e.icon.display(fenetre, e.pos.posX, ++e.pos.posY);
                break;
            case UP:
                e.icon.display(fenetre, e.pos.posX, --e.pos.posY);
                break;
            case RIGHT:
                e.icon.display(fenetre, ++e.pos.posX, e.pos.posY);
                break;
            case LEFT:
                e.icon.display(fenetre, --e.pos.posX, e.pos.posY);
                break;
            default:
                break;
        }

        try {
            sleep(20); // délai entre les mouvements des flocons (img/s)
        } catch (InterruptedException e) {
            isRunning = false; // Thread interrompu => arrêt
        }
    }

    private void hitWall() {
        switch (e.currentDirection) {
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

    private void hitWallAnimation(Direction d, Direction opposite) {

        for (int i = 1; i <= STEP_PIXELS / 2; i++) {
            moveOnePixel(d);
        }
        for (int i = 1; i <= STEP_PIXELS / 8; i++) {
            moveOnePixel(opposite);
        }
        for (int i = 1; i <= STEP_PIXELS / 8; i++) {
            moveOnePixel(d);
        }
        for (int i = 1; i <= STEP_PIXELS / 2; i++) {
            moveOnePixel(opposite);
        }
    }

    public void fall() {

        for (int i = 1; i < STEP_PIXELS; i += STEP_PIXELS / 10) {
            Image newimg = e.icon.icon.getImage().getScaledInstance(STEP_PIXELS - i, STEP_PIXELS - i, java.awt.Image.SCALE_SMOOTH);
            e.icon = new GameImage(new ImageIcon(newimg));
            e.icon.display(fenetre, 60 + i / 2, 60 + i / 2);
            try {
                sleep(50);
                TimeUnit.MILLISECONDS.sleep(50);

            } catch (InterruptedException interruptedException) {
                isRunning = false;
            }
            e.icon.erase(fenetre, 60 + i / 2, 60 + i / 2);


        }
    }
}

