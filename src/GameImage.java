import javax.swing.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class GameImage {

    ImageIcon icon;

    GameImage(String address) {
        try {
            URL url = new File(address).toURI().toURL();
            icon = new ImageIcon(url);

        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Developer error, missing image");
        }
    }

    GameImage(ImageIcon im){
        icon = im;
    }

    public void display(Fenetre f, int x, int y) { // afficher l'image
        f.obtenirZoneGraphique().drawImage(icon.getImage(),x,y,f);
    }

}
