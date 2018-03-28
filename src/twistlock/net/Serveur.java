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

	private ArrayList<ClientServeur> clients;

	private DatagramSocket datagramSocket;
	private DatagramPacket datagramPacketRecu, datagramPacketEnvoie;
	private String messageRecu;
	private String messageEnvoie;

	public Serveur(Controleur controleur, int portConnexion, int nbJoueurs, int tL) {
		this.controleur = controleur;
		this.portConnexion = portConnexion;
		this.nbJoueurs = nbJoueurs;
		this.tL = tL;

		this.clients = new ArrayList<>();

		this.lancer();
	}

	public int getPortConnexion() {
		return portConnexion;
	}

	public int getNbJoueurs() {
		return nbJoueurs;
	}

	public int getNbTwistLocks() {
		return this.tL;
	}

	public Controleur getControleur() {
		return controleur;
	}

	public DatagramSocket getDatagramSocket() {
		return datagramSocket;
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
			} else {
				client.lancerAction(message);
			}
		}
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