package twistlock.metier;

public class Joueur {

	private int id;

	private int score;

	private int nbTwistLock;

	private String nom;

	public Joueur(int id, String nom) {
		this.id = id;
		this.nom = nom;
		this.score = 0;
		this.nbTwistLock = 20;
	}

	public int getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}

	public int getScore() {
		return score;
	}

	public int getNbTwistLockwistLock() {
		return nbTwistLock;
	}

	public boolean peutJouer() {
		return this.nbTwistLock > 0;
	}

	public void addScore(int score) {
		this.score += score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	// méthode enlevant un twist-lock si la personne fait une action invalide
	public void penalite() {
		this.nbTwistLock--;

		if(this.nbTwistLock < 0)
			this.nbTwistLock = 0;
	}

}
