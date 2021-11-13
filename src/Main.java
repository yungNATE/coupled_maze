
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


public class Main {
    public static void main(String[] args) throws InterruptedException, CloneNotSupportedException, IOException {
        /*GameMap map = new GameMap("map");
        Player p = new Player(20,20,"ressources/cat.jpg", map);

        p.move(Direction.DOWN);
        p.move(Direction.DOWN);

        p.move(Direction.RIGHT); */
        GameEngine.setUpGame("map");


        /* //Window creation
        JFrame window = new JFrame("Labyrinth");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(1200,600);
        window.setResizable(false);
        window.setPreferredSize(new Dimension(1200,600));
        window.setLocationRelativeTo(null);
        window.getContentPane().setBackground(Color.black);

        //Set picture
        ImageIcon picture = new ImageIcon("ressources/laby.jpg");
        JLabel background = new JLabel(picture, JLabel.CENTER);
        window.add(background);

        //Elements creation
        Border bline = new LineBorder(Color.BLACK);
        Border bmargin = new EmptyBorder(5, 15, 5, 15);
        Border compound = new CompoundBorder(bline, bmargin);

        JButton btn_new = new JButton("NEW GAME");
        btn_new.setBackground(new Color(243,101,71));
        btn_new.setForeground(new Color(255,255,255));
        btn_new.setBorderPainted(false);
        btn_new.setBorder(compound);
        btn_new.setFocusPainted(false);
        btn_new.setFocusable(false);
        btn_new.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_new.setFont(new Font("Tahoma", Font.BOLD, 20));
        btn_new.setPreferredSize(new Dimension(250,30));
        btn_new.setMinimumSize(new Dimension(150,30));
        btn_new.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //create new frame
                JFrame new_game = new JFrame("Start new game");
                new_game.setVisible(true);
                new_game.setSize(new Dimension(600,600));
                new_game.getContentPane().setBackground(new Color(243,101,71));
                new_game.setResizable(false);
                new_game.requestFocus();
                new_game.toFront();
                new_game.setLocationRelativeTo(null);
                window.setVisible(false); //hidden frame

                //adding selection of labyrinth
                JMenuBar menu_new = new JMenuBar();
                menu_new.setBackground(new Color(243,101,71));
                menu_new.contains(50,10);
                menu_new.setBorderPainted(false);
                menu_new.setOpaque(true);

                JLabel title = new JLabel("Commencer une nouvelle partie :");
                title.setFont(new Font("Tahoma", Font.BOLD, 20));
                title.setForeground(new Color(255,255,255));

                JButton btn_return = new JButton("<-");
                btn_return.setBackground(new Color(243,101,71));
                btn_return.setForeground(new Color(255,255,255));
                btn_return.setBorderPainted(false);
                btn_return.setBorder(compound);
                btn_return.setFocusPainted(false);
                btn_return.setFocusable(false);
                btn_return.setAlignmentX(Component.CENTER_ALIGNMENT);
                btn_return.setFont(new Font("Tahoma", Font.BOLD, 20));
                btn_return.setPreferredSize(new Dimension(250,30));
                btn_return.setMinimumSize(new Dimension(150,30));
                btn_return.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        new_game.setVisible(false);
                        window.setVisible(true);
                    }
                });

                menu_new.add(title);
                menu_new.add(new JSeparator());
                menu_new.add(btn_return);

                new_game.setJMenuBar(menu_new);
                new_game.revalidate();

            }
            
        });
        btn_new.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                btn_new.setBackground(new Color(193, 76, 56));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                btn_new.setBackground(new Color(243,101,71));
            }
        });

        JButton btn_load = new JButton("LOAD GAME");
        btn_load.setBackground(new Color(243,101,71));
        btn_load.setForeground(new Color(255,255,255));
        btn_load.setBorderPainted(false);
        btn_load.setBorder(compound);
        btn_load.setFocusPainted(false);
        btn_load.setFocusable(false);
        btn_load.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_load.setFont(new Font("Tahoma", Font.BOLD, 20));
        btn_load.setPreferredSize(new Dimension(250,30));
        btn_load.setMinimumSize(new Dimension(150,30));
        btn_load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //create new frame
                JFrame load_game = new JFrame("Load game");
                load_game.setVisible(true);
                load_game.setSize(new Dimension(600,600));
                load_game.getContentPane().setBackground(new Color(243,101,71));
                load_game.setResizable(false);
                load_game.requestFocus();
                load_game.toFront();
                load_game.setLocationRelativeTo(null);
                window.setVisible(false); //hidden frame

                //adding selection of labyrinth
                JMenuBar menu_load = new JMenuBar();
                menu_load.setBackground(new Color(243,101,71));
                menu_load.contains(50,10);
                menu_load.setBorderPainted(false);
                menu_load.setOpaque(true);

                JLabel title = new JLabel("Charger une carte existante :");
                title.setFont(new Font("Tahoma", Font.BOLD, 20));
                title.setForeground(new Color(255,255,255));

                JButton btn_return = new JButton("<-");
                btn_return.setBackground(new Color(243,101,71));
                btn_return.setForeground(new Color(255,255,255));
                btn_return.setBorderPainted(false);
                btn_return.setBorder(compound);
                btn_return.setFocusPainted(false);
                btn_return.setFocusable(false);
                btn_return.setAlignmentX(Component.CENTER_ALIGNMENT);
                btn_return.setFont(new Font("Tahoma", Font.BOLD, 20));
                btn_return.setPreferredSize(new Dimension(250,30));
                btn_return.setMinimumSize(new Dimension(150,30));
                btn_return.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        load_game.setVisible(false);
                        window.setVisible(true);
                    }
                });

                menu_load.add(title);
                menu_load.add(new JSeparator());
                menu_load.add(btn_return);

                load_game.setJMenuBar(menu_load);
                load_game.revalidate();

            }
        });
        btn_load.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                btn_load.setBackground(new Color(193, 76, 56));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                btn_load.setBackground(new Color(243,101,71));
            }
        });

        JButton btn_leave = new JButton("LEAVE");
        btn_leave.setBackground(new Color(243,101,71));
        btn_leave.setForeground(new Color(255,255,255));
        btn_leave.setBorderPainted(false);
        btn_leave.setBorder(compound);
        btn_leave.setFocusable(false);
        btn_leave.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_leave.setFont(new Font("Tahoma", Font.BOLD, 20));
        btn_leave.setPreferredSize(new Dimension(250,30));
        btn_leave.setMinimumSize(new Dimension(150,30));
        btn_leave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.dispose();
            }
        });
        btn_leave.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                btn_leave.setBackground(new Color(193, 76, 56));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                btn_leave.setBackground(new Color(243,101,71));
            }
        });

        JButton baby_separator = new JButton("OUI");
        baby_separator.setBackground(Color.black);
        baby_separator.setForeground(Color.black);
        baby_separator.setBorderPainted(false);
        baby_separator.setFocusable(false);
        baby_separator.setAlignmentX(Component.CENTER_ALIGNMENT);
        baby_separator.setFont(new Font("Tahoma", Font.BOLD, 20));
        baby_separator.setPreferredSize(new Dimension(20,30));
        baby_separator.setSelected(false);

        //Menu creation
        JMenuBar menu_component = new JMenuBar();

        menu_component.add(btn_new);
        menu_component.add(baby_separator);
        menu_component.add(btn_load);
        menu_component.add(new JSeparator());
        menu_component.add(btn_leave);
        menu_component.setBackground(Color.black);
        menu_component.contains(50,10);
        menu_component.setBorderPainted(false);
        menu_component.setOpaque(true);
        menu_component.revalidate();

        window.setJMenuBar(menu_component);

        window.setVisible(true);
        window.pack(); */
    }
}


