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

public class GameMap extends Fenetre {

    //PROP
    HashMap<Position, Tile> structure;
    Fenetre f;

    //CONSTR
    public GameMap(){
        super(0,0);
        structure = getData();
        afficherResultat(structure);



        setVisible(true);
    }

    //METH
    public HashMap getData(){
        JSONParser parser = new JSONParser();
        int i = 0,
                tailleTitle = 50, //px
                posXTile = 0,
                posYTile = -tailleTitle;

        HashMap<Position, Tile> structureContrs = new HashMap<>();

        try {
            Object obj = parser.parse(new FileReader("ressources/maps.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject map = (JSONObject) jsonObject.get("map");

            JSONArray premierNiveau = (JSONArray) map.get("left"); // pas besoin de prévoir le programme pour gérer n labyrinthes
            JSONArray secondNiveau = (JSONArray) map.get("right"); // donc on récupère à la main le 1er ainsi que le second
            System.out.println(map);
            JSONArray[] niveaux = {premierNiveau, secondNiveau};

            for (JSONArray niveau : niveaux) {
                System.out.println(niveau);
                for (Object line: niveau){
                    posYTile+=tailleTitle;
                    posXTile = -tailleTitle;
                    for (Object tile : (ArrayList)line){
                        posXTile+=tailleTitle;
                        structureContrs.put(new Position(posXTile,posYTile),  new Tile(Tile.TypeCase.valueOf(tile.toString())));
                        //System.out.print(new Position(posXTile,posYTile));
                        //System.out.print(posXTile + ":" + posYTile + "; | ");
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return structureContrs;

    }
    private void afficherResultat(HashMap<Position,Tile> struct) {
        for (var entry : struct.entrySet()) {
            entry.getValue().afficher(this, entry.getKey());
            //System.out.print(entry.getKey().posX);
        }
    }
}
