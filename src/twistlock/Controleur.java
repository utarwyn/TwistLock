package twistlock;

import twistlock.ihm.IHM;
import twistlock.metier.Conteneur;
import twistlock.metier.Joueur;
import twistlock.metier.Metier;
import twistlock.metier.TwistLock;

import java.util.ArrayList;

public class Controleur {

	private static final int NB_LIG = 10;
	private static final int NB_COL = 7;

	private Metier metier;

	private IHM ihm;

	private Controleur() {
		IHM application = new IHM();
		application.launch(IHM.class);
		application.setControleur(this);

		this.metier = new Metier(Controleur.NB_LIG, Controleur.NB_COL);
	}


	/* ----------------------------- */
	/*  GESTION DE LA GRILLE DE JEU  */
	/* ----------------------------- */

	public int getNbLig() {
		return this.metier.getNbLig();
	}

	public int getNbCol() {
		return this.metier.getNbCol();
	}

	public Conteneur getConteneur(int lig, int col) {
		return this.metier.getConteneur(lig, col);
	}

	/* ------------------------ */
	/*  GESTION DES TWISTLOCKS  */
	/* ------------------------ */

	public TwistLock[][] getTwistLocks() {
		return this.metier.getTwistLocks();
	}

	public boolean isTwistlock(int lig, int col) {
		return this.metier.isTwistlock(lig, col);
	}

	public boolean jouerTwistlock(int lig, int col, Joueur joueur) {
		return this.metier.jouerTwistlock(lig, col, joueur);
	}

	/* --------------------- */
	/*  GESTION DES JOUEURS  */
	/* --------------------- */

	public void ajouterJoueur(String nom) {
		this.metier.ajouterJoueur(nom);
	}

	public ArrayList<Joueur> getJoueurs() {
		return this.metier.getJoueurs();
	}

	public void lancerPartie() {
		this.metier.lancerPartie();
	}

	public boolean nouveauTour() {
		return this.metier.nouveauTour();
	}

	public static void main(String[] args) {
		new Controleur();
	}

}
