import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class EntityAnimation extends Thread {

    enum Animation {HIT, MOVE, FALL}

    final int STEP_PIXELS = 50;
    volatile Animation ani;
    volatile Entity e;
    Fenetre fenetre;

    public EntityAnimation(Entity entity, Animation a) {
        e = entity;
        ani = a;
        fenetre = entity.fenetre;
    }

    @Override
    public void run() {
        if (!e.isMoving) {
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
            e.display();
            e.isMoving = false; // L'animation est arrêtée
        }

    }


    private void moveOneTile() {

        for (int i = 1; i <= STEP_PIXELS / 2; i++) {
            e.currentTile.afficher(fenetre); // afficher tile en dessous du joueur
            moveTwoPixels(e.currentDirection);
            if (i % 5 == 0) e.nextTile.afficher(fenetre); // pour éviter trop de refresh (on refresh la tile sur laquelle on arrive
        }

    }

    private void moveTwoPixels(Direction d) {
        switch (d) {
            case DOWN:
                e.icon.display(fenetre, e.pos.posX, e.pos.posY += 2);
                break;
            case UP:
                e.icon.display(fenetre, e.pos.posX, e.pos.posY -= 2);
                break;
            case RIGHT:
                e.icon.display(fenetre, e.pos.posX += 2, e.pos.posY);
                break;
            case LEFT:
                e.icon.display(fenetre, e.pos.posX -= 2, e.pos.posY);
                break;
            default:
                break;
        }
        try {
            sleep(20); // délai entre les mouvements
        } catch (InterruptedException exception) {
            e.isMoving = false; // Thread interrompu => arrêt
        }
    }

    private void hitWall() {
        playSound("hitWall");
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
        e.display();

    }

    private void hitWallAnimation(Direction d, Direction opposite) {

        for (int i = 1; i <= STEP_PIXELS / 8; i++) {
            moveTwoPixels(d);
        }
        e.nextTile.afficher(fenetre);
        for (int i = 1; i <= STEP_PIXELS / 16; i++) {
            moveTwoPixels(opposite);
        }

        for (int i = 1; i <= STEP_PIXELS / 16; i++) {
            moveTwoPixels(d);
        }

        for (int i = 1; i <= STEP_PIXELS / 8; i++) {
            moveTwoPixels(opposite);
        }
        e.nextTile.afficher(fenetre);
    }

    public void fall() {
        moveOneTile(); // on avance sur le trou

        playSound("fall");

        for (int i = 0; i < STEP_PIXELS; i += STEP_PIXELS / 10) {
            Image newimg = e.icon.icon.getImage().getScaledInstance(STEP_PIXELS - i, STEP_PIXELS - i, java.awt.Image.SCALE_SMOOTH);
            GameImage small = new GameImage(new ImageIcon(newimg));
            e.nextTile.afficher(fenetre);
            small.display(fenetre, e.pos.posX + i / 2, e.pos.posY + i / 2);
            try {
                sleep(50);

            } catch (InterruptedException interruptedException) {
                e.isMoving = false;
            }

        }
        e.nextTile.afficher(fenetre);

        if (e instanceof Player) {
            playSound("gameOver");

            int input = JOptionPane.showConfirmDialog(null, "GAME OVER!", "Too bad...", JOptionPane.DEFAULT_OPTION);
            if (input == JOptionPane.OK_OPTION) {
                System.exit(0);
            }

        }

    }

    private void playSound(String s) {
        try {
            Clip monClip = AudioSystem.getClip();
            AudioInputStream ligne = AudioSystem.getAudioInputStream(new File("ressources/sounds/" + s + ".wav"));
            monClip.open(ligne);
            monClip.start();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

