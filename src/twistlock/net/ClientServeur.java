package twistlock.net;

import twistlock.metier.Conteneur;
import twistlock.metier.Joueur;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketAddress;
import java.util.regex.Pattern;

public class ClientServeur {

	private static final Pattern SELECTION_PATTERN = Pattern.compile("^[1-9][A-I][1-4]$");

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

	public boolean monTour() {
		return this.joueur == this.serveur.getControleur().getJoueurCourant();
	}

	public void lancerAction(String message) {
		// Commande "MAP"
		if (message.equalsIgnoreCase("MAP")) {
			this.envoyer(this.serveur.getControleur().getRepresentationPlateau());
			return;
		}

		// Selection de pose du twistlock
		if (this.monTour() && SELECTION_PATTERN.matcher(message).find()) {
			int lig = (int) message.charAt(0) - '0';
			int col = (int) message.charAt(1) - 'A';
			int coin = (int) message.charAt(2) - '0';

			Conteneur conteneur = this.serveur.getControleur().getConteneur(lig-1, col);

			if (conteneur != null && this.serveur.getControleur().jouerTwistlock(conteneur, coin)) {
				this.serveur.envoiMessageAdversaire("20-coup adversaire:" + message);
			} else {
				this.envoyer("21-coup joue illegal");
				this.serveur.envoiMessageAdversaire("22-coup adversaire illegal");
			}

			this.serveur.getControleur().nouveauTour();
			this.serveur.getControleur().miseAJourIHM();
			// TODO Fin de partie
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
		sb.append("Vous êtes le joueur ").append(this.joueur.getId()).append(" (ROUGE) ");

		this.envoyer(sb.toString());
	}

}
