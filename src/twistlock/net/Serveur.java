package twistlock.net;

import twistlock.Controleur;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;

public class Serveur extends Thread {

	private Controleur controleur;

	private int portConnexion, nbJoueurs , tL;
	
	private String adresseIP;

	private ArrayList<ClientServeur> clients;

	private DatagramSocket datagramSocket;

	public Serveur(Controleur controleur, String adresseIP, int portConnexion, int nbJoueurs, int tL) {
		this.controleur = controleur;
		this.adresseIP = adresseIP;
		this.portConnexion = portConnexion;
		this.nbJoueurs = nbJoueurs;
		this.tL = tL;

		this.clients = new ArrayList<>();

		this.lancer();
	}

	public int getNbTwistLocks() {
		return this.tL;
	}

	public Controleur getControleur() {
		return controleur;
	}
    
    public int getPortConnexion( )
    {
        return portConnexion;
    }
	
	public String getAdresseIP(){
		return adresseIP;
	}
    
    public DatagramSocket getDatagramSocket() {
		return datagramSocket;
	}

	public ArrayList<ClientServeur> getClients() {
		return clients;
	}

	public ClientServeur getClientParAdresse(SocketAddress address) {
		for (ClientServeur client : this.clients)
			if (client.getAddress().equals(address))
				return client;

		return null;
	}

	@Override
	public void run() {
		while (true) {
			DatagramPacket msg = new DatagramPacket(new byte[1024], 1024);

			try{
				this.datagramSocket.receive( msg );
			} catch( IOException e ) {
				e.printStackTrace( );
			}

			ClientServeur client = this.getClientParAdresse(msg.getSocketAddress());
			String message = new String(msg.getData()).trim();

			// Nouveau joueur!
			if (client == null && this.clients.size() < this.nbJoueurs) {
				client = new ClientServeur(
						this,
						new String(msg.getData()).trim(),
						msg.getSocketAddress()
				);

				this.clients.add(client);
				
				this.controleur.getConnexionClient().setJLabel( clients.size(), client.getJoueur().getNom() );

				// Tous les joueurs sont connectés
				if (this.clients.size() == this.nbJoueurs) {
					this.controleur.chargerIHM();
					this.envoiDemarragePartie();
					this.envoiInfoTour();
				}
			} else {
				if( client != null )
					client.lancerAction(message);
				else{
					envoiMessageAll( "55-un joueur non autorise essaye de se connecter" );
					envoiMessageAll( "56-nom : " + message );
					envoiMessageAll( "57-adresse : " + msg.getAddress().toString() );
				}
			}
		}
	}
	
	public void envoiMessageAdversaire(String message) {
		for (ClientServeur client : this.clients)
			if (!client.monTour())
				client.envoyer(message);
	}
	
	public void envoiMessageAll(String message) {
		for (ClientServeur client : this.clients)
			client.envoyer(message);
	}

	private void envoiDemarragePartie() {
		for (ClientServeur client : this.clients)
			client.envoyer("01-la partie va commencer\n" + this.controleur.getRepresentationPlateau());
	}

	public void envoiInfoTour() {
		for (ClientServeur client : this.clients)
			if (client.monTour())
				client.envoyer("10-A vous de jouer (" + ClientServeur.COULEURS[client.getJoueur().getId() - 1] + ") :");
	}

	private void lancer() {
		try {
			this.datagramSocket = new DatagramSocket(this.portConnexion);
			// Démarrage du Thread du serveur ...
			this.start();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

}