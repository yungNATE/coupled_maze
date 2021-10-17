package labyrinthe_PROJET;

import java.io.FileReader;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Main {

	public static void main(String[] args) {
		// setup test
		System.out.println("cc kiki");
		
		// lecture labyrinthe test
		JSONParser parser = new JSONParser();
	      try {
	         Object obj = parser.parse(new FileReader("F://LP/JAVA/labyrinth/ressources/maps.json"));
	         JSONObject jsonObject = (JSONObject)obj;
	         JSONObject map = (JSONObject) jsonObject.get("map");    
	         System.out.println(map);
//	         Iterator iterator = subjects.iterator();
//	         while (iterator.hasNext()) {
//	            System.out.println(iterator.next());
//	         }
	      } catch(Exception e) {
	         e.printStackTrace();
	      }

	}

}
