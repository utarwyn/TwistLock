package twistlock.ihm;

import twistlock.Controleur;
import twistlock.metier.Conteneur;
import twistlock.metier.Joueur;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class IHM extends JFrame {

	public static final String[] MAIN_JOUEURS = new String[] { "West", "East", "North", "South" };

	public static final Color[] COULEURS = new Color[] {
			Color.RED,
			new Color(39, 174, 96),
			new Color(241, 196, 15),
			Color.BLUE
	};

	private Controleur controleur;

	private Plateau plateau;

	private MainJoueur[] mains;

	public IHM(Controleur controleur) {
		this.controleur = controleur;
		this.preparer();
	}

	private void preparer() {
		this.setTitle("Jeu des Twistlocks");
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		this.mains = new MainJoueur[4];

		// Mise en place du plateau
		this.plateau = new Plateau(this, this.controleur);
		this.add(this.plateau, BorderLayout.CENTER);
	}

	public void lancer() {
		ArrayList<Joueur> joueurs = this.controleur.getJoueurs();

		int i = 0;
		for (Joueur joueur : joueurs) {
			this.mains[i] = new MainJoueur(joueur);
			this.add(this.mains[i], MAIN_JOUEURS[i++]);
		}

		this.plateau.preparer();

		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void choixTwistlock(Conteneur conteneur, int coin) {
		if (!this.controleur.jouerTwistlock(conteneur, coin)) {
			// le joueur n'a pas pu poser de twistlock ce gros couillon
			JOptionPane.showMessageDialog(
					this,
					"Cet emplacement est déjà utilisé. Tu perds un twistlock de pénalité.",
					"Impossible de jouer ici",
					JOptionPane.ERROR_MESSAGE
			);
		}

		if (!this.controleur.nouveauTour()) {
			StringBuilder scores = new StringBuilder();

			for (Joueur joueur : this.controleur.getJoueurs())
				scores.append(joueur.getNom()).append(": ").append(joueur.getScore()).append("\n");

			JOptionPane.showMessageDialog(
					this,
					scores.toString(),
					"FIN DE PARTIE - Tableau des scores",
					JOptionPane.INFORMATION_MESSAGE
			);

			this.dispose();
			System.exit(0);
			return;
		}

		for (MainJoueur mainJoueur : this.mains)
			if (mainJoueur != null)
				mainJoueur.miseAJour(this.controleur.getJoueurCourant() == mainJoueur.getJoueur());

		this.plateau.miseAJour();
	}

}
