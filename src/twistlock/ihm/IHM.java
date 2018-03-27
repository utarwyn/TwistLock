package twistlock.ihm;

import twistlock.Controleur;

import javax.swing.*;

public class IHM extends JFrame {

	private Controleur controleur;

	private Plateau plateau;

	public IHM(Controleur controleur) {
		this.controleur = controleur;
		this.preparer();
	}

	private void preparer() {
		this.setTitle("Jeu des Twistlocks");
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setState(MAXIMIZED_BOTH);

		// Mise en place du plateau

	}

}
