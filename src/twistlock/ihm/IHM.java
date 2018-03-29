package twistlock.ihm;

import twistlock.Controleur;
import twistlock.metier.Conteneur;
import twistlock.metier.Joueur;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Représente la fenêtre principale de jeu.
 * Est le coeur de la partie IHM
 */
public class IHM extends JFrame {

	public static final String[] PLACES_MAIN = new String[]{"West", "East", "North", "South"};

	public static final Color[] COULEURS = new Color[]{
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

	/**
	 * Prépare l'IHM de la fenêtre
	 */
	private void preparer() {
		this.setTitle("Jeu des Twistlocks");
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		this.mains = new MainJoueur[4];

		// Mise en place du plateau
		this.plateau = new Plateau(this, this.controleur);
		this.add(this.plateau, BorderLayout.CENTER);
	}

	/**
	 * Lance la partie IHM et ouvre la fenêtre de jeu
	 */
	public void lancer(boolean unFocus) {
		ArrayList<Joueur> joueurs = this.controleur.getJoueurs();

		int i = 0;
		for (Joueur joueur : joueurs) {
			this.mains[i] = new MainJoueur(joueur);
			this.add(this.mains[i], PLACES_MAIN[i++]);
		}

		this.plateau.preparer(unFocus);

		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	/**
	 * Appelée lorsqu'un joueur souhaite poser un twistlock
	 *
	 * @param conteneur Conteneur concerné par la pose
	 * @param coin      Coin dans lequel poser le twistlock
	 */
	public void choixTwistlock(Conteneur conteneur, int coin) {
		if (!this.controleur.jouerTwistlock(conteneur, coin)) {
			// le joueur n'a pas pu poser de twistlock
			JOptionPane.showMessageDialog(
					this,
					"Cet emplacement est déjà utilisé. Tu perds un twistlock de pénalité.",
					"Impossible de jouer ici",
					JOptionPane.ERROR_MESSAGE
			);
		}

		boolean nouveauTour = this.controleur.nouveauTour();

		this.miseAJour();

		if (!nouveauTour) {
			messageFin();
		}
	}
	
	public void messageFin(){
		this.setEnabled( true );
		JOptionPane.showMessageDialog(
				this,
				this.controleur.getClassement(),
				"FIN DE PARTIE - Tableau des scores",
				JOptionPane.CLOSED_OPTION
		);
		System.exit( 0 );
	}

	/**
	 * Mise à jour de toute l'IHM
	 */
	public void miseAJour() {
		for (MainJoueur mainJoueur : this.mains)
			if (mainJoueur != null)
				mainJoueur.miseAJour(this.controleur.getJoueurCourant() == mainJoueur.getJoueur());

		this.plateau.miseAJour();
	}
}
