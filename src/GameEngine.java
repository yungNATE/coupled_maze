import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
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
    private int nombrePas = 0;
    private JFrame fscore = null;
    private JLabel score = null ;

    public void setUpGame(String mapName) {
        map = new GameMap(mapName);

        Position start = map.left.entrySet().stream().filter(v -> v.getValue().type == Tile.TypeCase.START).findFirst().orElseThrow().getKey();
        player1 = new Player(start.posX, start.posY, "ressources/pof.png", map);

        start = map.right.entrySet().stream().filter(v -> v.getValue().type == Tile.TypeCase.START).findFirst().orElseThrow().getKey();

        player2 = new Player(start.posX, start.posY, "ressources/paf.png", map);
        boxes = new ArrayList<>();
        for (Position pos : map.caisses) {
            boxes.add(new Box(pos.posX, pos.posY, "ressources/textures/BOX.png", map));
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

                    updateScore(e);

                    if (depart.type != Tile.TypeCase.END) e.move(direction);
                    checkForWin((Player) e);


                    return true;
                case HOLE:  // tomber() => terminer perdant
                    e.fall(direction);
                    if (e instanceof Box) boxes.remove(e);
                    return true;
                case START:
                case FLOOR:
                    updateScore(e);
                    System.out.println(this.nombrePas);
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

    private void updateScore(Entity e) {
        if(e instanceof Player) {
            this.nombrePas++;
            if (fscore == null) {
                fscore = new JFrame("score");
                score = new JLabel(nombrePas + "");
                score.setForeground(Color.BLACK);
                score.setFont(new Font("Verdana", Font.PLAIN, 18));
                Border border = score.getBorder();
                Border margin = new EmptyBorder(50, 20, 50, 20);
                score.setBorder(new CompoundBorder(border, margin));
                score.setHorizontalAlignment(JLabel.CENTER);


                fscore.setLayout(new BorderLayout());
                fscore.getContentPane().add(score, BorderLayout.CENTER);
                fscore.setResizable(false);
                fscore.getContentPane().setBackground(Color.white);
                fscore.pack();
                fscore.setFocusableWindowState(false);
                fscore.setVisible(true);
            } else {
                score.setText(nombrePas + "");
            }
        }
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
