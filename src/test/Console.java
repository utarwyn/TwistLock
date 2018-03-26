package test;

import metier.Metier;

public class Console {

	private Metier metier;

	private Console() {
		this.metier = new Metier(10, 7);
	}

	public static void main(String[] args) {
		new Console();
	}

}
