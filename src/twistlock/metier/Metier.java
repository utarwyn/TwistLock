package twistlock.metier;

import java.util.ArrayList;

public class Metier {

	private ArrayList<Conteneur> conteneurs;

	public Metier(int nbLig, int nbCol) {
		this.conteneurs = new ArrayList<>();

		this.initialiser(nbLig, nbCol);
	}

	private void initialiser(int nbLig, int nbCol) {
		ArrayList<Integer> valeurs = new ArrayList<>();
		int valeur, indice;

		// Valeurs des conteneurs
		for (int nb = 1; nb <= nbLig * nbCol; nb++)
			valeurs.add(nb);

		// Création des conteneurs
		for (int lig = 1; lig <= nbLig; lig++)
			for (char col = 'A'; col < (char) ('A' + nbCol); col++) {
				indice = (int) (Math.random() * valeurs.size());
				valeur = valeurs.get(indice);

				this.conteneurs.add(new Conteneur(lig, col, valeur));
				valeurs.remove(indice);
			}
	}

	public ArrayList<Conteneur> getConteneurs() {
		return conteneurs;
	}

	public twistlock.metier.TwistLock[][] getTwistLocks() {
		// TODO: à coder
		return new twistlock.metier.TwistLock[0][0];
	}

}
