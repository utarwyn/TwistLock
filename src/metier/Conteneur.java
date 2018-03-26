package metier;

import java.util.ArrayList;

public class Conteneur {

	private int ligne;

	private char colonne;

	private int valeur;

	private ArrayList<TwistLock> coins;

	public Conteneur(int ligne, char colonne, int valeur) {
		this.ligne = ligne;
		this.colonne = colonne;
		this.valeur = valeur;

		this.coins = new ArrayList<>();
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