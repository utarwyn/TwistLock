package twistlock.test;

import twistlock.metier.Metier;

public class Console {

	private Metier metier;

	private Console() {
		this.metier = new Metier(10, 7);
	}

	private void afficherGrille() {

	}

	public static void main(String[] args) {
		new Console();
	}

}
