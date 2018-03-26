package twistlock.metier;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Conteneur {

	private int ligne;

	private char colonne;

	private int valeur;

	private TwistLock[] coins;

	Conteneur(int ligne, char colonne, int valeur) {
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

	public TwistLock[] getCoins() {
		return coins;
	}

	public Joueur getProprietaire() {
		Map<Joueur, Integer> map = new HashMap<>();

		for (TwistLock coin : this.coins)
			if (coin != null && coin.getJoueur() != null)
				map.put(coin.getJoueur(), map.getOrDefault(coin.getJoueur(), 0) + 1);

		int max = -1;
		Joueur joueur = null;

		for (Map.Entry<Joueur, Integer> props : map.entrySet()) {
			if (props.getValue() > max) {
				joueur = props.getKey();
				max = props.getValue();
			} else if (props.getValue() == max) {
				joueur = null;
			}
		}

		return joueur;
	}

	@Override
	public String toString() {
		return "Conteneur{" +
				"ligne=" + ligne +
				", colonne=" + colonne +
				", valeur=" + valeur +
				", coins=" + Arrays.toString(getCoins()) + "}\n";
	}

}