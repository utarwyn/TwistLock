package metier;

import java.util.ArrayList;

public class Metier {

	private ArrayList<Conteneur> conteneurs;

	public Metier(int nbLig, int nbCol) {
		this.conteneurs = new ArrayList<>();

		this.initialiser(nbLig, nbCol);
	}

	private void initialiser(int nbLig, int nbCol) {

	}

	public TwistLock[][] getTwistLocks() {
		// TODO: à coder
		return new TwistLock[0][0];
	}

}
