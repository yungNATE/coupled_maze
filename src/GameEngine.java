import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
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
    private JLabel moves = null;
    private Fenetre fenetre;

    public void setUpGame(String mapName) {
        fenetre = new Fenetre(0, 0, Color.BLACK);
        fenetre.setLayout(new BorderLayout());

        fenetre.setPreferredSize(new Dimension(1200, 900));
        fenetre.setTitle("Labyrinth");
        fenetre.setLocationRelativeTo(null);

        map = new GameMap(mapName, fenetre);

        Position start = map.left.entrySet().stream().filter(v -> v.getValue().type == Tile.TypeCase.START).findFirst().orElseThrow().getKey();
        player1 = new Player(start.posX, start.posY, "ressources/pof.png", map);

        start = map.right.entrySet().stream().filter(v -> v.getValue().type == Tile.TypeCase.START).findFirst().orElseThrow().getKey();
        player2 = new Player(start.posX, start.posY, "ressources/paf.png", map);
        boxes = new ArrayList<>();
        for (Position pos : map.caisses) {
            boxes.add(new Box(pos.posX, pos.posY, "ressources/textures/BOX.png", map));
        }

        fenetre.addKeyListener(this);
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
            Tile arrivee = map.entrySet().stream().filter(v -> v.getKey().equals(posDifference)).findFirst().orElse(new AbstractMap.SimpleEntry<>(null, new Tile(Tile.TypeCase.OUT_OF_BOUNDS))).getValue();
            e.currentTile = depart;


            if (depart.type == Tile.TypeCase.END && arrivee.type == Tile.TypeCase.WALL) {
                arrivee.type = Tile.TypeCase.END;
                e.nextTile = depart;
            } else if (arrivee.type != Tile.TypeCase.OUT_OF_BOUNDS) {
                e.nextTile = arrivee;
            }

            switch (arrivee.type) {
                case END:
                    updateScore(e);

                    if (depart.type != Tile.TypeCase.END) e.move(direction);
                    checkForWin((Player) e);

                    return true;
                case HOLE:
                    e.fall(direction);
                    if (e instanceof Box) boxes.remove(e);
                    return true;
                case START:
                case FLOOR:
                    updateScore(e);
                    Box box = boxes.stream().filter(b -> b.pos.equals(posDifference)).findFirst().orElse(null);
                    if (box != null)
                    {
                        Position newBoxPosition = box.getFuturePosition(direction);
                        if (moveEntity(box, map, direction, newBoxPosition)) e.move(direction);

                    } else {
                        e.move(direction);
                    }
                    return true;

                case WALL:
                    e.hitWall(direction);

                case OUT_OF_BOUNDS:

            }
        }
        return false;
    }

    private void updateScore(Entity e) {
        if (e instanceof Player) {
            this.nombrePas++;
            if (moves == null) {
                moves = new JLabel("Moves: " + nombrePas);
                moves.setForeground(Color.WHITE);
                moves.setFont(new Font("Verdana", Font.PLAIN, 18));
                moves.setHorizontalAlignment(JLabel.CENTER);

                //Create btn for refreshing page
                JButton btn_refresh = new JButton("RESTART");
                btn_refresh.setBackground(new Color(243,101,71));
                btn_refresh.setForeground(new Color(255,255,255));
                btn_refresh.setBorderPainted(false);
                btn_refresh.setFocusPainted(false);
                btn_refresh.setFocusable(true);
                btn_refresh.requestFocus();
                btn_refresh.setAlignmentX(Component.CENTER_ALIGNMENT);
                btn_refresh.setFont(new Font("Tahoma", Font.BOLD, 20));

                btn_refresh.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        fenetre.dispose();
                        new GameEngine().setUpGame(map.name);
                    }
                });

                // create menu bar to add elements to
                JMenuBar barRes = new JMenuBar();
                barRes.setBackground(Color.BLACK);
                barRes.setBorderPainted(false);
                barRes.add(btn_refresh);
                barRes.add(new JSeparator(SwingConstants.HORIZONTAL));
                barRes.add(moves);

                fenetre.setJMenuBar(barRes);

            } else {
                moves.setText("Moves: " + nombrePas);
            }
        }
    }

    // prend en paramètre un joueur : vérifie si l'autre joueur est aussi positionné sur la case de fin, alors le jeu est gagné
    private void checkForWin(Player player) {
        Player otherPlayer = player1;

        if (player == player1) { // choix de l'autre joueur
            otherPlayer = player2;
        }

        if (otherPlayer.nextTile != null && otherPlayer.nextTile.type == Tile.TypeCase.END) { // si les 2 joueurs sur la case END -> return true
            playSound("win");
            int input = JOptionPane.showOptionDialog(null, "YOU WON!", "What a champion...", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
            if (input == JOptionPane.OK_OPTION) {
                System.exit(0);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Direction direction = null;
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
