package twistlock.metier;

/**
 * Représente un Twistlock
 */
public class TwistLock {

	private Joueur joueur;

	TwistLock(Joueur joueur) {
		this.joueur = joueur;
	}

	/**
	 * Récupère le joueur qui possède le twistlock
	 *
	 * @return Joueur qui possède le twistlock
	 */
	public Joueur getJoueur() {
		return joueur;
	}
}
