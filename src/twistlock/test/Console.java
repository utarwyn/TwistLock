package twistlock.test;

import twistlock.metier.Conteneur;
import twistlock.metier.Joueur;
import twistlock.metier.Metier;
import twistlock.metier.TwistLock;

public class Console {

	private Metier metier;

	private Console() {
		this.metier = new Metier(10, 7);

		this.afficherGrille();
	}

	private void afficherGrille() {
		Conteneur conteneur;
		Joueur joueur;

		Joueur j1 = new Joueur(1,"TATA");
		Joueur j2 = new Joueur(2,"TONTON");

		this.metier.jouerTwistlock(1, 1, j1);
		this.metier.jouerTwistlock(4, 6, j1);
		this.metier.jouerTwistlock(5, 7, j2);
		this.metier.jouerTwistlock(5, 6, j2);

		TwistLock[][] locks = this.metier.getTwistLocks();

		System.out.print("\n " + this.symbolLock(locks[0][0]));
		for (int j = 0; j < this.metier.getNbCol(); j++)
			System.out.print("----------" + this.symbolLock(locks[0][j]));

		System.out.println();

		for (int i = 0; i < this.metier.getNbLig(); i++) {
			System.out.print(" |");
			for (int j = 0; j < this.metier.getNbCol(); j++) {
				conteneur = this.metier.getConteneur(i, j);
				joueur = conteneur.getProprietaire();

				System.out.print("  " + String.format("%02d", conteneur.getValeur()) + " (" + ((joueur != null) ? joueur() : 'X') + ")  |");
			}

			System.out.print("\n " + this.symbolLock(locks[i+1][0]));
			for (int j = 0; j < this.metier.getNbCol(); j++)
				System.out.print("----------" + this.symbolLock(locks[i+1][j+1]));

			System.out.print("\n");
		}
	}

	private char symbolLock(TwistLock lock) {
		if (lock == null) return 'X';
		else return 'O';
	}

	public static void main(String[] args) {
		new Console();
	}

}
