package twistlock.net;

import twistlock.metier.Joueur;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketAddress;

public class ClientServeur {

	private Serveur serveur;

	private Joueur joueur;

	private SocketAddress address;

	ClientServeur(Serveur serveur, String nom, SocketAddress address) {
		this.serveur = serveur;
		this.address = address;

		this.joueur = this.serveur.getControleur().ajouterJoueur(nom, this.serveur.getNbTwistLocks());
		this.envoyerBienvenue();
	}

	public SocketAddress getAddress() {
		return address;
	}

	public Joueur getJoueur() {
		return this.joueur;
	}

	public void lancerAction(String message) {
		// Commande "MAP"
		if (message.equalsIgnoreCase("MAP")) {
			this.envoyer(this.serveur.getControleur().getRepresentationPlateau());
			return;
		}

		this.envoyer("25 - Action impossible");
	}

	public void envoyer(String message) {
		DatagramPacket reponse = new DatagramPacket(message.getBytes(), message.length(), this.address);

		try {
			this.serveur.getDatagramSocket().send(reponse);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void envoyerBienvenue() {
		StringBuilder sb = new StringBuilder();

		sb.append(this.joueur.getId()).append("-Bonjour Equipe ").append(this.joueur.getNom()).append("\n");
		sb.append("Vous êtes le joueur ").append(this.joueur.getId()).append(" (ROUGE)");

		this.envoyer(sb.toString());
	}

}
