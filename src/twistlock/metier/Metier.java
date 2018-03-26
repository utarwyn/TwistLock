package twistlock.metier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Metier {

	private int nbLig, nbCol;

	private ArrayList<Joueur> joueurs;

	private Joueur joueurCourant;

	private Conteneur[][] conteneurs;

	public Metier(int nbLig, int nbCol) {
		this.nbLig = nbLig;
		this.nbCol = nbCol;

		this.conteneurs = new Conteneur[this.nbLig][this.nbCol];
		this.joueurs = new ArrayList<>();

		this.initialiser();
	}

	private void initialiser() {
		// Création des conteneurs
		for (int lig = 0; lig < this.nbLig; lig++)
			for (char col = 0; col < this.nbCol; col++) {
				this.conteneurs[lig][col] = new Conteneur(
						lig + 1, (char) ('A' + col),
						(int) (Math.random() * (54 - 5) + 5)
				);
			}
	}

	public int getNbLig() {
		return this.nbLig;
	}

	public int getNbCol() {
		return this.nbCol;
	}

	public Conteneur getConteneur(int lig, int col) {
		if (lig < 0 || col < 0 || lig >= this.nbLig || col >= this.nbCol)
			return null;

		return this.conteneurs[lig][col];
	}

	public ArrayList<Joueur> getJoueurs() {
		return this.joueurs;
	}

	public TwistLock[][] getTwistLocks() {
		TwistLock[][] locks = new TwistLock[this.getNbLig() + 1][this.getNbCol() + 1];
		Conteneur conteneur;

		for (int lig = 0; lig < this.conteneurs.length; lig++)
			for (int col = 0; col < this.conteneurs[lig].length; col++) {
				conteneur = this.conteneurs[lig][col];

				locks[lig    ][col    ] = conteneur.getCoins()[0];
				locks[lig    ][col + 1] = conteneur.getCoins()[1];
				locks[lig + 1][col + 1] = conteneur.getCoins()[2];
				locks[lig + 1][col    ] = conteneur.getCoins()[3];
			}

		return locks;
	}

	public boolean isTwistlock(int lig, int col) {
		return this.getTwistLocks()[lig][col] != null;
	}

	public void recalculerScores() {
		for (Joueur joueur : this.joueurs)
			joueur.setScore(0);

		Conteneur conteneur;
		Joueur proprietaire;

		for (int lig = 0; lig < this.nbLig; lig++) {
			for (int col = 0; col < this.nbCol; col++) {
				conteneur = this.getConteneur(lig, col);
				proprietaire = conteneur.getProprietaire();

				if (proprietaire != null) {
					for (Joueur joueur : this.joueurs)
						if (joueur == proprietaire) {
							joueur.addScore(conteneur.getValeur());
							break;
						}
				}
			}
		}
	}

	public void ajouterJoueur(String nom) {
		this.joueurs.add(new Joueur(this.joueurs.size() + 1, nom));
	}

	/**
	 * Lance la partie
	 */
	public void lancerPartie() {
		this.joueurCourant = this.joueurs.get(0);
	}

	/**
	 * Passe le tour au joueur suivant
	 * @return Vrai si le tour a pu être passé, faux si la partie est terminée.
	 */
	public boolean nouveauTour() {
		this.recalculerScores();

		// Suppression d'un twistlock pour le joueur courant
		if (this.joueurCourant != null)
			this.joueurCourant.penalite();

		/* Vérifications de fin de partie:
		 *   - Aucun emplacement de Twistlock disponible
		 *   - Plus aucun joueur n'a de twistlock         */
		boolean finDePartie = true;
		TwistLock[][] locks = this.getTwistLocks();

		for (int lig = 0; lig < locks.length; lig++)
			for (int col = 0; col < locks[lig].length; col++)
				if (locks[lig][col] == null)
					finDePartie = false;

		for (Joueur joueur : this.joueurs)
			if (joueur.getNbTwistLockwistLock() > 0)
				finDePartie = false;

		if (finDePartie) {
			this.joueurCourant = null;
			return false;
		}

		// Détermination du prochain joueur (il doit avoir des twistlocks)
		do {
			this.prochainJoueur();
		} while (this.joueurCourant.getNbTwistLockwistLock() == 0);

		return true;
	}

	private void prochainJoueur() {
		this.joueurCourant = this.joueurs.get(this.joueurCourant.getId() % this.joueurs.size());
	}

	public boolean jouerTwistlock(int lig, int col, Joueur joueur) {
		Map<Conteneur, Integer> conteneurs = new HashMap<>();
		Conteneur conteneur;

		// Il y a déjà un twistlock où le joueur souhaite jouer
		if (this.isTwistlock(lig, col)) {
			joueur.penalite();
			return false;
		}

		// Création du twistlock
		TwistLock twistlock = new TwistLock(joueur);

		if ((conteneur = this.getConteneur(lig - 1,col - 1)) != null)
			conteneurs.put(conteneur, 3);
		if ((conteneur = this.getConteneur(lig - 1,col)) != null)
			conteneurs.put(conteneur, 4);
		if ((conteneur = this.getConteneur(lig,col)) != null)
			conteneurs.put(conteneur, 1);
		if ((conteneur = this.getConteneur(lig, col - 1)) != null)
			conteneurs.put(conteneur, 2);

		for (Map.Entry<Conteneur, Integer> conteneurEntry : conteneurs.entrySet())
			conteneurEntry.getKey().getCoins()[conteneurEntry.getValue() - 1] = twistlock;

		return true;
	}

}
