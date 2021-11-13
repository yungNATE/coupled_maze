import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public final class GameEngine {

    private static GameMap map;
    private static Player player1;
    private static Player player2;


    public static void setUpGame(String mapName) {
        map = new GameMap(mapName);
        System.out.print(map.left);

        Position start = map.left.entrySet().stream().filter(v -> v.getValue().type == Tile.TypeCase.START).findFirst().orElseThrow().getKey();
        player1 = new Player(start.posX, start.posY, "ressources/cat.jpg", map);

        start = map.right.entrySet().stream().filter(v -> v.getValue().type == Tile.TypeCase.START).findFirst().orElseThrow().getKey();
        player2 = new Player(start.posX, start.posY, "ressources/cat.jpg", map);

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
}
