import javax.swing.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class Image {

    ImageIcon icon;

    Image(String address) {
        try {
            URL url = new File(address).toURI().toURL();
            icon = new ImageIcon(url);

        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Developer error, missing image");
        }
    }

    public void afficher(Fenetre f, int x, int y) { // afficher l'image
        f.obtenirZoneGraphique().drawImage(icon.getImage(),x,y,f);
    }

    public void effacer(Fenetre f, int x, int y) { // effacer l'image
        f.obtenirZoneGraphique().clearRect(x,y,
                icon.getIconWidth(), icon.getIconHeight());
    }
}
