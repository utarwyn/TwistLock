package twistlock.metier;

import java.util.HashMap;
import java.util.Map;

public class Metier {

	private int nbLig, nbCol;

	private Conteneur[][] conteneurs;

	public Metier(int nbLig, int nbCol) {
		this.nbLig = nbLig;
		this.nbCol = nbCol;
		this.conteneurs = new Conteneur[this.nbLig][this.nbCol];

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

	public Conteneur[][] getConteneurs() {
		return conteneurs;
	}

	public boolean jouerTwistlock(int lig, int col, Joueur joueur) {
		Map<Conteneur, Integer> conteneurs = new HashMap<>();
		Conteneur conteneur;

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

		return false;
	}

}
