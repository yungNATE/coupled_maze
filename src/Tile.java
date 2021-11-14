import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Tile {

    enum TypeCase {
        START,
        END,
        WALL,
        HOLE,
        FLOOR,
        OUT_OF_BOUNDS,
        BOX
    }
    Position pos;
    TypeCase type;
    GameImage icon;
    String folder = "ressources/textures/";

    public Tile(TypeCase typeCase){
        type = typeCase;
        icon = new GameImage(getPath(type.toString(), folder));
    }

    public Tile(TypeCase typeCase, Position position){
        type = typeCase;
        icon = new GameImage(getPath(type.toString(), folder));
        pos=position;

    }

    public String toString(){
        return type.toString();
    }

    // parcours le dossier des textures et récupère la texture correspondante au nom donné en paramètres
    public String getPath(String textureName, String folder) {
        File[] files = new File(folder).listFiles();

        for (File file : files) {
            if (file.isFile() && textureName.equals(file.getName().split("\\.")[0])) {
                return file.toString();
            }
        }
        return "error"; //error
    }

    public void afficher(Fenetre f, Position pos){
        icon.display(f, pos.posX, pos.posY);
    }

    public void afficher(Fenetre f){
        icon.display(f, pos.posX, pos.posY);
    }
}
