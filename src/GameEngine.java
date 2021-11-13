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

    public void movePlayers(String direction){
        System.out.println(direction);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        String direction;
        switch(e.getKeyChar()){
            case 'z': direction = "UP";     break;
            case 'q': direction = "LEFT";   break;
            case 's': direction = "DOWN";   break;
            case 'd': direction = "RIGHT";  break;
            default : direction = "";        break;
        }
        if(direction != "") movePlayers(direction);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
