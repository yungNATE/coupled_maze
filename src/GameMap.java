import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map;

public class GameMap {

    //prop
    Map<Position, Object> structure;

    //constr
    public GameMap(){
        structure = getData();

    }

    //meth
    public Map getData(){
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("ressources/maps.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject map = (JSONObject) jsonObject.get("map");
            JSONArray premierNiveau = (JSONArray) map.get("left");

            int i = 0,
                posXTitle = 0,
                posYTitle = 0,
                tailleTitle = 50;
            Map<Position, Object> structureContrs;

            for (Object line: premierNiveau){
                for (Object tile : (ArrayList)line){
                    structureContrs.put(new Position(posXTitle,posYTitle),  new Tile() ) ;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
