package twistlock.net;

import twistlock.Controleur;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Serveur {

	private Controleur controleur;

	private int portConnexion, nbJoueurs;

	public Serveur(Controleur controleur, int portConnexion, int nbJoueurs) {
		this.controleur = controleur;
		this.portConnexion = portConnexion;
		this.nbJoueurs = nbJoueurs;

		this.lancer();
	}

	public int getPortConnexion() {
		return portConnexion;
	}

	public int getNbJoueurs() {
		return nbJoueurs;
	}

	private void lancer() {
		DatagramSocket ds = null;
		try {
			ds = new DatagramSocket(this.portConnexion);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		DatagramPacket msg = new DatagramPacket(new byte[100], 100);

		new Thread(new Runnable() {
			@Override
			public void run() {
				String messageRecu = "";
				do {
//                    ds.receive( msg );
//                    String texteReponse = "";
//                    String nom          = new String( msg.getData( ) ).trim( );
//                    texteReponse += ( i + "-Bonjour Equipe " + new String( msg.getData( ) ).trim( ) );
//                    texteReponse += ( "\nVous etes le Joueur " + i + " (ROUGE)" );
//                    new Joueur( i , nom , 20 );
//                    DatagramPacket reponse = new DatagramPacket( texteReponse.getBytes( ) , texteReponse.length( ) , msg.getAddress( ) , msg.getPort( ) );
//                    ds.send( reponse );
				} while (true);
			}
		}).run();


		ds.close();
	}

}