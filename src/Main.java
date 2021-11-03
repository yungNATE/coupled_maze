
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.imageio.ImageIO;
import javax.swing.*;


public class Main {

    public static void main(String[] args) throws InterruptedException, CloneNotSupportedException, IOException {
        // setup test
        // lecture fichier test
        /*JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("ressources/maps.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject map = (JSONObject) jsonObject.get("map");
            //System.out.println(map);
        } catch (Exception e) {
            e.printStackTrace();
        } */

        // lecture labyrinth test
        JSONParser parser2 = new JSONParser();
        try {
            Object obj = parser2.parse(new FileReader("ressources/maps.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject map = (JSONObject) jsonObject.get("map");
            JSONArray premierNiveau = (JSONArray) map.get("left");
            int i = 0;
            for (Object line: premierNiveau){

                System.out.print("Ligne: " + ++i);
                for (Object tile : (ArrayList)line){
                    System.out.print(tile);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Fenetre f = new Fenetre(500,500);
        Player p = new Player(50,50, "ressources/cat.jpg", f);
        //p.icon.afficher(f, 50,50);
        p.hitWall(Direction.LEFT);
        f.setVisible(true);
        /*
        p.move(Direction.DOWN);
        p.move(Direction.UP);
        p.move(Direction.RIGHT);
        p.move(Direction.LEFT); */

        p.fall();


    }

}

