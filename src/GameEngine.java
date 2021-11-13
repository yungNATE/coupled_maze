import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameEngine implements KeyListener {

    private static GameMap map;
    private static Player player1;
    private static Player player2;


    public void setUpGame(String mapName) {
        map = new GameMap(mapName);
        System.out.print(map.left);

        Position start = map.left.entrySet().stream().filter(v -> v.getValue().type == Tile.TypeCase.START).findFirst().orElseThrow().getKey();
        player1 = new Player(start.posX, start.posY, "ressources/cat.jpg", map);

        start = map.right.entrySet().stream().filter(v -> v.getValue().type == Tile.TypeCase.START).findFirst().orElseThrow().getKey();
        player2 = new Player(start.posX, start.posY, "ressources/cat.jpg", map);

        map.addKeyListener(this);

        // add event listeners
        /*
        (Fenetre) map.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    System.out.println("down");
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    System.out.println("up");
                }
            }
        });
        */

    }

    public void movePlayers(Direction direction){
        System.out.println(direction);


        // check if hole

        // check if box

        // check if wall
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Direction direction;
        switch(e.getKeyCode()){
            case KeyEvent.VK_UP: direction = Direction.UP;     break;
            case KeyEvent.VK_LEFT: direction = Direction.LEFT;   break;
            case KeyEvent.VK_RIGHT: direction = Direction.RIGHT;   break;
            case KeyEvent.VK_DOWN: direction = Direction.DOWN;  break;
            default : direction = null;       break;
        }
        if(direction != null) movePlayers(direction);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
