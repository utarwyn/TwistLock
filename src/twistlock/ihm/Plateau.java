package twistlock.ihm;

import twistlock.Controleur;

import javax.swing.*;
import java.awt.*;

class Plateau extends JPanel {

	private IHM ihm;

	private Controleur controleur;

	Plateau(IHM ihm, Controleur controleur) {
		this.ihm = ihm;
		this.controleur = controleur;
	}

	void preparer() {
		this.setLayout(new GridLayout(controleur.getNbLig(), controleur.getNbCol()));

		for (int lig = 0; lig < this.controleur.getNbLig(); lig++)
			for (int col = 0; col < this.controleur.getNbCol(); col++)
				this.add(new ConteneurGraphique(this.ihm, this.controleur.getConteneur(lig, col)));

		this.repaint();
	}

	void miseAJour() {
		// TODO: à coder
	}

}
