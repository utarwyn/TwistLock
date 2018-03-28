package twistlock.net;

import twistlock.Controleur;
import twistlock.metier.Joueur;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Serveur {

	private Controleur controleur;

	private int portConnexion, nbJoueurs , tL;

	private DatagramSocket datagramSocket;
	private DatagramPacket datagramPacketRecu, datagramPacketEnvoie;
	private String messageRecu;
	private String messageEnvoie;

	public Serveur(Controleur controleur, int portConnexion, int nbJoueurs, int tL) {
		this.controleur = controleur;
		this.portConnexion = portConnexion;
		this.nbJoueurs = nbJoueurs;
		this.tL = tL;

		this.lancer();
	}

	public int getPortConnexion() {
		return portConnexion;
	}

	public int getNbJoueurs() {
		return nbJoueurs;
	}

	private void lancer() {
		try {
			datagramSocket = new DatagramSocket(this.portConnexion);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		DatagramPacket msg = new DatagramPacket(new byte[100], 100);

		new Thread(() -> {
            String messageRecu = "";
            for(int i=1;i<=nbJoueurs;i++)
            {
                  try{
                    System.out.println("salut");
                      datagramSocket.receive( msg );
                  } catch( IOException e ) {
                      e.printStackTrace( );
                  }


                  messageEnvoie = "";
                  String nom = new String( msg.getData( ) ).trim( );
                  messageEnvoie += ( i + "-Bonjour Equipe " + new String( msg.getData( ) ).trim( ) );
                  messageEnvoie += ( "\nVous etes le Joueur " + 1 + " (ROUGE)" ); //TODO: Doit changer la couleur
                new Joueur( i , nom , 20 ); //TODO? : ajout Joueur autre

            }

            //TODO : Partie JEU
        }).start();


		datagramSocket.close();
	}

}