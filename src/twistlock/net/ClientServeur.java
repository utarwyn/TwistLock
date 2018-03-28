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

    static final String[] COULEURS = new String[]{
            "ROUGE","VERT","JAUNE","BLEU"
    };

	ClientServeur(Serveur serveur, String nom, SocketAddress address) {
		this.serveur = serveur;
		this.address = address;

		this.joueur = this.serveur.getControleur().ajouterJoueur(nom, this.serveur.getNbTwistLocks());
		this.envoyerBienvenue();
	}

	SocketAddress getAddress( ) {
		return address;
	}

	public Joueur getJoueur() {
		return this.joueur;
	}

	boolean monTour( ) {
		return this.joueur == this.serveur.getControleur().getJoueurCourant();
	}

	void lancerAction( String message ) {
		
		message = message.toUpperCase();
		
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
				this.envoyer( "23-vous avez joué " + message );
				this.envoyer( "24-attendez votre tour" );
				this.serveur.envoiMessageAdversaire("20-coup adversaire : " + message);
			} else {
				this.envoyer("21-coup joue illegal");
				this.serveur.envoiMessageAdversaire("22-coup adversaire illegal");
			}

			boolean nouveauTour = this.serveur.getControleur().nouveauTour();
			this.serveur.getControleur().miseAJourIHM();

			// Le joueur n'a plus de twistlock
			if (!this.joueur.peutJouer())
				this.envoyer("50-Vous ne pouvez plus jouer");

			// Fin de partie
			if (!nouveauTour) {
				String classement = this.serveur.getControleur().getClassement();
				Joueur gagnant = this.serveur.getControleur().getGagnant();

				for (ClientServeur client : this.serveur.getClients()) {
					client.envoyer("-- FIN DE PARTIE --");

					if (client.getJoueur() == gagnant)
						client.envoyer("Vous avez gagné avec " + client.getJoueur().getScore() + " points !");
					else
						client.envoyer("Vous avez perdu avec " + client.getJoueur().getScore() + " points !");

					client.envoyer(classement);
					client.envoyer("KILL");
				}
				
				this.serveur.getControleur().getIhm().messageFin();
				
			} else {
				this.serveur.envoiInfoTour();
			}

			return;
		}

		this.envoyer("91-demande non valide");
	}

	void envoyer( String message ) {
		DatagramPacket reponse = new DatagramPacket(message.getBytes(), message.length(), this.address);

		try {
			this.serveur.getDatagramSocket().send(reponse);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void envoyerBienvenue() {
		this.envoyer( String.valueOf( this.joueur.getId( ) ) + "-Bonjour Equipe " + this.joueur.getNom( ) + "\n" + "Vous êtes le joueur " +
		              this.joueur.getId( ) + " (" + COULEURS[ this.joueur.getId( ) - 1 ] + ") ");
	}

}
