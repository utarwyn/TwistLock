package twistlock.metier;

/**
 * Représente un joueur.
 */
public class Joueur {

	private int id;

	private int score;

	private int nbTwistLock;

	private String nom;

	public Joueur(int id, String nom, int nbTwistLock) {
		this.id = id;
		this.nom = nom;
		this.score = 0;
		this.nbTwistLock = nbTwistLock;
	}

	/**
	 * Retourne l'identifiant du joueur (défini automatiquement au début de la partie)
	 *
	 * @return Identifiant du joueur généré
	 */
	public int getId() {
		return id;
	}

	/**
	 * Retourne le nom du joueur
	 *
	 * @return Nom du joueur
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Retourne le score actuel du joueur
	 *
	 * @return Score du joueur (addition de la valeur des conteneurs qu'il possède)
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Retourne le nombre de twistlock qu'il reste au joueur
	 *
	 * @return Nombre de twistlock du joueur
	 */
	public int getNbTwistlock() {
		return nbTwistLock;
	}

	/**
	 * Permet de savoir si un joueur peut jouer (a un nombre de twistlock suffisant)
	 *
	 * @return Vrai si le joueur peut jouer, faux sinon.
	 */
	public boolean peutJouer() {
		return this.nbTwistLock > 0;
	}

	/**
	 * Ajoute un score précis au joueur
	 *
	 * @param score Score à ajouter
	 */
	void addScore( int score ) {
		this.score += score;
	}

	/**
	 * Réinitialise le score du joueur
	 */
	void resetScore() {
		this.score = 0;
	}

	/**
	 * Enlève un twist-lock au joueur
	 */
	void retirerTwistlock() {
		this.nbTwistLock--;

		if (this.nbTwistLock < 0) this.nbTwistLock = 0;
	}
	
}
