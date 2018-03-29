package twistlock.metier;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Représente un conteneur dans la grille de jeu
 */
public class Conteneur {

	private int ligne;

	private char colonne;

	private int valeur;

	private TwistLock[] coins;

	public Conteneur(int ligne, char colonne, int valeur) {
		this.ligne = ligne;
		this.colonne = colonne;
		this.valeur = valeur;

		this.coins = new TwistLock[4];
	}

	/**
	 * Retourne la ligne où se situe le conteneur
	 *
	 * @return Ligne du conteneur
	 */
	public int getLigne() {
		return ligne;
	}

	/**
	 * Retourne la colonne où se situe le conteneur
	 *
	 * @return Colonne du conteneur en format caractère
	 */
	public char getColonne() {
		return colonne;
	}

	/**
	 * Retourne la valeur portée par le conteneur
	 *
	 * @return Valeur du conteneur
	 */
	public int getValeur() {
		return valeur;
	}

	/**
	 * Permet de savoir si un coin du conteneur est occupé par un twistlock ou non
	 *
	 * @param nb Numéro du coin
	 * @return Vrai si un twistlock est présent dans le coin
	 */
	boolean estOccupe( int nb ) {
		return this.coins[nb - 1] != null;
	}

	/**
	 * Permet de savoir si un conteneur est occupé à 100% (tous les twistlocks)
	 *
	 * @return Vrai si le conteneur est occupé, faux sinon.
	 */
	boolean estEntoure( ) {
		for (TwistLock twistLock : this.coins)
			if (twistLock == null)
				return false;

		return true;
	}

	/**
	 * Retourne les twistlocks aux coins du conteneur
	 *
	 * @return Tableau des coins du conteneur (twistlocks)
	 */
	public TwistLock[] getCoins() {
		return coins;
	}

	/**
	 * Retourne le propriétaire du conteneur. Pour cela la méthode se base sur le nombre de twistlocks de chaque joueur à ses coins (celui qui en a le
	 * plus est le propriétaire ; si même nombre = personne le possède)
	 *
	 * @return Proprétaire s'il y en a un, null sinon.
	 */
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
			} else
				if (props.getValue() == max) {
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
				", coins=" + Arrays.toString(coins) +
				'}';
	}

}