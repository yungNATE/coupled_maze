import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameEngine implements KeyListener {

    private static GameMap map;
    private static Player player1;
    private static Player player2;
    private ArrayList<Box> boxes;

    public void setUpGame(String mapName) {
        map = new GameMap(mapName);

        Position start = map.left.entrySet().stream().filter(v -> v.getValue().type == Tile.TypeCase.START).findFirst().orElseThrow().getKey();
        player1 = new Player(start.posX, start.posY, "ressources/cat.jpg", map);

        start = map.right.entrySet().stream().filter(v -> v.getValue().type == Tile.TypeCase.START).findFirst().orElseThrow().getKey();
        player2 = new Player(start.posX, start.posY, "ressources/cat.jpg", map);
        boxes = new ArrayList<>();
        for (Position pos : map.caisses) {
            boxes.add(new Box(pos.posX, pos.posY, "ressources/balle.png", map));
        }
        map.addKeyListener(this);
    }

    public void movePlayers(Direction direction) {
        for (Player player : List.of(player1, player2)) {
            HashMap<Position, Tile> mapCurr = map.right;
            if (player == player1) mapCurr = map.left;
            Position posDifference = player.getFuturePosition(direction);
            moveEntity(player, mapCurr, direction, posDifference);

        }
    }

    // Returns if movement was succesfully completed
    Boolean moveEntity(Entity e, HashMap<Position, Tile> map, Direction direction, Position posDifference) {
        if (!e.isMoving) {
            Tile depart = map.entrySet().stream().filter(v -> v.getKey().equals(e.pos)).findFirst().orElseThrow().getValue();
            Tile arrivee = map.entrySet().stream().filter(v -> v.getKey().equals(posDifference)).findFirst().orElse(new AbstractMap.SimpleEntry<Position, Tile>(null, new Tile(Tile.TypeCase.OUT_OF_BOUNDS))).getValue();
            e.currentTile = depart;


            if (depart.type == Tile.TypeCase.END && arrivee.type == Tile.TypeCase.WALL) {
                arrivee.type = Tile.TypeCase.END;
                e.nextTile = depart;
            } else if (arrivee.type != Tile.TypeCase.OUT_OF_BOUNDS) {
                e.nextTile = arrivee;
            }

            switch (arrivee.type) {
                case END:   // bouger : OK | Choper position case des 2 cases END, choper position 2 players, si == pour les deux => terminer gagnant
                    if (depart.type != Tile.TypeCase.END) e.move(direction);
                    checkForWin((Player) e);

                    return true;
                case HOLE:  // tomber() => terminer perdant
                    e.fall(direction);
                    if (e instanceof Box) boxes.remove(e);
                    return true;
                case START:
                case FLOOR:
                    Box box = boxes.stream().filter(b -> b.pos.equals(posDifference)).findFirst().orElse(null);
                    if (box != null)  // check si caisse
                    {
                        Position newBoxPosition = box.getFuturePosition(direction);
                        if (moveEntity(box, map, direction, newBoxPosition)) e.move(direction);

                    } else {
                        e.move(direction);
                    }
                    return true;

                case WALL:
                    e.hitWall(direction); // hitwall(direction)

                case OUT_OF_BOUNDS:

            }
        }
        return false;
    }

    // prend en paramètre un joueur : vérifie si l'autre joueur est positionné sur la case de fin, alors le jeu est gagné
    private void checkForWin(Player player) {
        Player otherPlayer = player1;

        if (player == player1) { // choix de l'autre joueur
            otherPlayer = player2;
        }

        if (otherPlayer.nextTile != null && otherPlayer.nextTile.type == Tile.TypeCase.END) { // si les 2 sur la case END -> return true

            JOptionPane.showMessageDialog(player.fenetre, "YOU WIN!","Well done...", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Direction direction;
        Position pos;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                direction = Direction.UP;
                break;
            case KeyEvent.VK_LEFT:
                direction = Direction.LEFT;
                break;
            case KeyEvent.VK_RIGHT:
                direction = Direction.RIGHT;
                break;
            case KeyEvent.VK_DOWN:
                direction = Direction.DOWN;
                break;
            default:
                direction = null;
                break;
        }
        if (direction != null) {
            try {
                movePlayers(direction);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
