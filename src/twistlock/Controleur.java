package twistlock;

import twistlock.ihm.FormChoixServeurPlateau;
import twistlock.ihm.IHM;
import twistlock.metier.Conteneur;
import twistlock.metier.Joueur;
import twistlock.metier.Metier;
import twistlock.net.Serveur;

import java.util.ArrayList;

public class Controleur {

	private Metier metier;

	private Serveur serveur;

	private IHM ihm;

	private Controleur() {
		new FormChoixServeurPlateau(this);
	}

	public static void main(String[] args) {
		new Controleur();
	}

	public void chargerMetier(int nbLig, int nbCol) {
		this.metier = new Metier(nbLig, nbCol);
	}


	/* ----------------------------- */
	/*  GESTION DE LA GRILLE DE JEU  */
	/* ----------------------------- */
	public void chargerIHM() {
		this.ihm = new IHM(this);
		this.ihm.lancer();
	}

	public int getNbLig() {
		return this.metier.getNbLig();
	}

	public int getNbCol() {
		return this.metier.getNbCol();
	}

	public Conteneur getConteneur(int lig, int col) {
		return this.metier.getConteneur(lig, col);
	}

	public String getRepresentationPlateau() {
		return this.metier.getRepresentationPlateau();
	}


	/* ------------------------ */
	/*  GESTION DES TWISTLOCKS  */
	/* ------------------------ */
	public boolean jouerTwistlock(Conteneur conteneur, int coin) {
		return this.metier.jouerTwistlock(conteneur, coin);
	}


	/* --------------------- */
	/*  GESTION DES JOUEURS  */
	/* --------------------- */
	public Joueur ajouterJoueur(String nom, int tL) {
		return this.metier.ajouterJoueur(nom, tL);
	}

	public ArrayList<Joueur> getJoueurs() {
		return this.metier.getJoueurs();
	}

	public Joueur getGagnant() {
		return this.metier.getGagnant();
	}

	public Joueur getJoueurCourant() {
		return this.metier.getJoueurCourant();
	}

	public boolean nouveauTour() {
		return this.metier.nouveauTour();
	}

	public String getClassement() {
		return this.metier.getClassement();
	}

	/* -------------------- */
	/*  GESTION DU SERVEUR  */
	/* -------------------- */
	public Serveur getServeur() {
		return this.serveur;
	}

	public void lancerServeur(int portConnexion, int lignes, int colonnes, int tL, int nbJoueurs) {
	    
	    this.chargerMetier(lignes, colonnes);
		this.serveur = new Serveur(this, portConnexion, nbJoueurs, tL);
	}

	public void miseAJourIHM() {
		if (this.ihm != null)
			this.ihm.miseAJour();
	}

}
