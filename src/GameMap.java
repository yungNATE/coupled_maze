import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GameMap extends JFrame {

    //PROP
    HashMap<Position, Object> structure;

    //CONSTR
    public GameMap(){
        super("Labyby");
        structure = getData();


        setSize(new Dimension(1000, 1000));
        setVisible(true);
    }

    //METH
    public HashMap getData(){
        JSONParser parser = new JSONParser();
        int i = 0,
                posXTile = 0,
                posYTile = 0,
                tailleTitle = 50; //px
        HashMap<Position, Object> structureContrs = new HashMap<>();

        try {
            Object obj = parser.parse(new FileReader("ressources/maps.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject map = (JSONObject) jsonObject.get("map");
            JSONArray premierNiveau = (JSONArray) map.get("left");

            for (Object line: premierNiveau){
                posYTile+=50;
                posXTile = 0;
                for (Object tile : (ArrayList)line){
                    structureContrs.put(new Position(posXTile+=50,posYTile),  new Tile(Tile.TypeCase.valueOf(tile.toString())));
                    System.out.print(posYTile + ":" + + posXTile + ";");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        structureContrs.entrySet().forEach(entry -> {
            //System.out.println(entry.getKey().toString() + " " + entry.getValue().toString());
        });

        return structureContrs;

    }
}
