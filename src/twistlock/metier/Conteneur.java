package twistlock.metier;

public class Conteneur {

	private int ligne;

	private char colonne;

	private int valeur;

	private twistlock.metier.TwistLock[] coins;

	public Conteneur(int ligne, char colonne, int valeur) {
		this.ligne = ligne;
		this.colonne = colonne;
		this.valeur = valeur;

		this.coins = new twistlock.metier.TwistLock[4];
	}

	public int getLigne() {
		return ligne;
	}

	public char getColonne() {
		return colonne;
	}

	public int getValeur() {
		return valeur;
	}

}