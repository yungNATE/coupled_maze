import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
            e.currentTile.afficher(fenetre);

            moveTwoPixels(e.currentDirection);

            if (i%5 == 0) e.nextTile.afficher(fenetre);
        }

    }

    private void moveTwoPixels(Direction d) {

        //e.currentTile.afficher(fenetre); // afficher tile en dessous du joueur

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
            sleep(20); // délai entre les mouvements des flocons (img/s)
        } catch (InterruptedException exception) {
             e.isMoving = false; // Thread interrompu => arrêt
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

    public void fall(){
        moveOneTile();
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
            JOptionPane.showMessageDialog(fenetre, "GAME OVER!","Oops...", JOptionPane.ERROR_MESSAGE);
        }
        try{
            //play fall sound
            URL url = this.getClass().getClassLoader().getResource("sounds/fall.mp3");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException unsupportedAudioFileException) {
            unsupportedAudioFileException.printStackTrace();
        } catch (LineUnavailableException lineUnavailableException) {
            lineUnavailableException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}

