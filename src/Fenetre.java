import java.awt.*;
import java.awt.Image;
import java.awt.event.*;
import javax.swing.*;

/* Definition d'une interface graphique 
   avec une zone de dessin  */

public class Fenetre extends JFrame { // notre interface graphique

	private ZoneGraphique zoneDeDessin; // l'endroit ou on dessine
	private Rafraichir rafraichir;
	private boolean arretRafraichissement;
	private final int TEMPO_RAFRAICH = 40; // une image toutes les 40ms
	private boolean arretProgramme;
	
	public Fenetre(int x, int y) {
		super("Fen�tre de dessin"); // creation de la fenetre
		arretProgramme = true; // arr�t du programme � la fermeture de la fen�tre
		addWindowListener(new GestionFenetre()); // traitements des �v�nements de la fen�tre
		
		setBackground(new Color(255,255,255)); // couleur du fond
		setForeground(new Color(0,0,250)); // couleur de trace
		// On cree le composant zone de dessin
		zoneDeDessin = new ZoneGraphique();
		zoneDeDessin.setBackground(getBackground()); // couleur du fond
		zoneDeDessin.setForeground(getForeground()); // couleur de trace
		zoneDeDessin.setPreferredSize(new Dimension(x,y)); // taille demand�e
	
		// placement du composant
		GridLayout placeur=new GridLayout(1,1,0,0); // placeur
		getContentPane().setLayout(placeur); // associer le placeur a l'interface graphique
	
		// Ajoutet du composant de dessin
		getContentPane().add(zoneDeDessin); 

		//setSize(x,y); // Utilisable � la place du pack si on ne fait pas de setPreferredSize
		pack();
		setVisible(true);
	
		arretRafraichissement = false; // Rafraichissement actif
		rafraichir = new Rafraichir(); // Thread de rafraichissement
		rafraichir.start(); // Rafraichissement lanc�
	}
	
	public synchronized Graphics obtenirZoneGraphique() { 
		return zoneDeDessin.obtenirGraphics();  // renvoie le Graphics pour dessiner
	}
	
	public Color obtenirCouleurFond() { return getBackground(); } // renvoie la couleur de fond
	
	public void delai(int d) { // Utilitaire pour faire un d�lai (en ms)
		try {	Thread.sleep(d); }
		catch (InterruptedException e) {}
	}
	
	public int largeurZoneGraphique() {
		return zoneDeDessin.getWidth(); // renvoie la largeur
	}
	
	public int hauteurZoneGraphique() {
		return zoneDeDessin.getHeight(); // renvoie la hauteur
	}
	
	public void exitOnClose(boolean eoc) { // autoriser/interdire l'arr�t du prog � la fermeture
		arretProgramme = eoc;
	}
	
	public class ZoneGraphique extends JPanel {
	// Extension de la classe JPanel de swing :
	// Contient un tampon en m�moire dans lequel on peut dessiner.
	// ce tampon est affich� � l'�cran chaque fois que l'on appele la m�thode 
	// repaint() ou chaque fois que java fait un paint() 
	// Ce tampon est de la classe Graphics de AWT et on y acc�de par la m�thode
	// obtenirGraphics() on peut lui appliquer toutes les m�thodes de dessin d�finies
	// dans la classe Graphics (voir AWT).
	
	   private Image dessin; // tampon de dessin
	   private int taillex, tailley; // dimensions de la zone de dessin
	   
	   public ZoneGraphique() {
		   super();
		   dessin = null; // Tampon non encore cr��
		   taillex = -1; // -1 car tampon non encore cr��
		   tailley = -1; // -1 car tampon non encore cr��
	   }
	
	   private void creerTampon() { // creation du tampon
			taillex = getSize().width; // taille du tampon
			tailley = getSize().height;
	    	dessin = createImage(taillex, tailley); // creation du tampon
		}
	
	   public Graphics obtenirGraphics() {
	   		// retourne le tampon dans lequel on peut dessiner et qui sera affich�
	   		// � l'�cran par paint() et repaint()
			// voir si la taille de la zone de dessin n'a pas change
			// c'est le cas lorsque l'utilisateur redimensionne la fenetre
			if ((taillex != getSize().width) || (tailley != getSize().height)) {
		    	creerTampon(); // si oui, il faut recreer le tampon
	 		}
	  		return dessin.getGraphics(); // retourne le tampon de dessin
	   }
	
	   public void update(Graphics g) {
	   		// Surd�finition de update() pour qu'elle n'efface pas
	   		paint(g);
	   }
	
	   public void paint(Graphics g) {
	   		// Surd�finition de paint() pour qu'elle affiche le tampon � l'�cran
	   		if (dessin != null) g.drawImage(dessin,0,0,this); // affichage du tampon s'il existe
	   }
	
		public void setBackground(Color c) { // Changement de la couleur de fond
			super.setBackground(c);
			creerTampon(); // Le tampon est recr�e car effac�
		}
	}	
	
	private class GestionFenetre extends WindowAdapter {
		public synchronized void windowClosing(WindowEvent e) { // fermeture de la fen�tre
			arretRafraichissement = true;
			rafraichir.interrupt(); // arr�t du thread de rafraichissement
			if (arretProgramme) System.exit(0); // arr�t du programme
		}
	}

	
	private class Rafraichir extends Thread { // thread de rafraichissement
		public void run() {
			while(!arretRafraichissement) {
				try { 
					sleep(TEMPO_RAFRAICH); // d�lai entre images
					zoneDeDessin.repaint(); // rafraichissement �cran
				} 
				catch(InterruptedException ie) { // fermeture de la fen�tre
					arretRafraichissement = true; 
				}
			}
		}
	}

}