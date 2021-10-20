
import java.io.FileReader;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class Main {

    public static void main(String[] args) throws InterruptedException {
        // setup test
        System.out.println("cc kiki");
        // lecture fichier test
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("ressources/maps.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject map = (JSONObject) jsonObject.get("map");
            //System.out.println(map);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // lecture labyrinth test
        JSONParser parser2 = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("ressources/maps.json"));
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
        Player p = new Player(50,50, "ressources/balle.png", f);
        p.icon.afficher(f, 50,50);
        p.move(Direction.DOWN);
        p.move(Direction.UP);
    }

}

