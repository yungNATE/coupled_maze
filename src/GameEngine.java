import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.List;

public class GameEngine implements KeyListener {

    private static GameMap map;
    private static Player player1;
    private static Player player2;


    public void setUpGame(String mapName) {
        map = new GameMap(mapName);
        //System.out.print(map.left);

        Position start = map.left.entrySet().stream().filter(v -> v.getValue().type == Tile.TypeCase.START).findFirst().orElseThrow().getKey();
        player1 = new Player(start.posX, start.posY, "ressources/cat.jpg", map);

        start = map.right.entrySet().stream().filter(v -> v.getValue().type == Tile.TypeCase.START).findFirst().orElseThrow().getKey();
        player2 = new Player(start.posX, start.posY, "ressources/cat.jpg", map);

        map.addKeyListener(this);
    }

    public void movePlayers(Direction direction, Position posDifference) throws CloneNotSupportedException {
        Position posDiffCopie = (Position) posDifference.clone();
        for (Player player : List.of(player1, player2)) {

            Position position = player.pos;
            player.currentDirection = direction; // very important
            posDifference.add(position);

            HashMap<Position, Tile> mapCurr = map.right;
            if (player == player1) mapCurr = map.left;

            moveEntity(player, mapCurr, direction, posDifference);
            posDifference = posDiffCopie;
        }
    }

    void moveEntity(Entity e, HashMap<Position, Tile> map, Direction direction, Position posDifference) {
        //System.out.println(e);
        //System.out.println(posDifference);
        //System.out.println(map);
        Tile.TypeCase arrivee = map.entrySet().stream().filter(v -> v.getKey().equals(posDifference)).findFirst().orElseThrow().getValue().type;



        switch (arrivee) {
            case END:   // bouger : OK | Choper position case des 2 cases END, choper position 2 players, si == pour les deux => terminer gagnant
                e.move(direction);
                checkForWin((Player)e);
                break;
            case WALL:
                e.hitWall(direction); // hitwall(direction)
                break;
            case HOLE:  // tomber() => terminer perdant
                e.fall(direction);
                break;
            case FLOOR:
                e.move(direction); // check si caisse
                break;
        }

    }

    // prend en paramètre un joueur : vérifier si l'autre joueur est positionné sur la case de fin, alors le jeu est gagné
    private boolean checkForWin(Player player) {
        HashMap<Position, Tile> mapCurr = map.left;
        Player otherPlayer = player1;

        System.out.println("ok");

        if (player == player1){
            mapCurr = map.right;
            otherPlayer = player2;
        }

        Position end = mapCurr.entrySet().stream().filter(v -> v.getValue().type == Tile.TypeCase.END).findFirst().orElseThrow().getKey();
        System.out.println(end);
        System.out.println(otherPlayer.pos);

        if(otherPlayer.pos.equals(end)){

            return true;
        }
        return false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Direction direction;
        Position pos;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                direction = Direction.UP;
                pos = new Position(0, -map.tileSize);
                break;
            case KeyEvent.VK_LEFT:
                direction = Direction.LEFT;
                pos = new Position(-map.tileSize, 0);
                break;
            case KeyEvent.VK_RIGHT:
                direction = Direction.RIGHT;
                pos = new Position(map.tileSize, 0);
                break;
            case KeyEvent.VK_DOWN:
                direction = Direction.DOWN;
                pos = new Position(0, map.tileSize);
                break;
            default:
                direction = null;
                pos = null;
                break;
        }
        if (direction != null) {
            try {
                movePlayers(direction, pos);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
