package twistlock.net;

import twistlock.metier.Conteneur;
import twistlock.metier.IA;
import twistlock.metier.Metier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class IAClientTest {

	private DatagramSocket ds;

	private Metier fakeMetier;

	private IA ia;

	private IAClientTest() throws IOException {
		ds = new DatagramSocket();
		BufferedReader entreeClavier = new BufferedReader(new InputStreamReader(System.in));

		String adresse = this.entreeAdresse(entreeClavier);
		int port = this.entreePort(entreeClavier);

		System.out.println(
				"Nouvelle partie de TwistLock\n" + "\n" + "Vous pouvez envoyer les messages :\n" + "    MAP : donne la map du " + "plateau\n" +
						"    q   : pour quitter\n");

		new Thread(() -> {
			do {
				DatagramPacket msg = new DatagramPacket(new byte[1024], 1024);
				try {
					ds.receive(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}

				String message = new String(msg.getData()).trim();

				if (message.equalsIgnoreCase("KILL"))
					System.exit(0);

				if (message.startsWith("MAP") || message.startsWith("01-")) {
					String map = message.substring(message.lastIndexOf("=") + 1);
					String[] part = map.split("\\|");
					int[][] tabConteneur = new int[part.length][0];

					for (int i = 0; i < part.length; i++) {
						String[] nombre = part[i].split(":");
						tabConteneur[i] = new int[nombre.length];

						for (int j = 0; j < nombre.length; j++)
							tabConteneur[i][j] = Integer.parseInt(nombre[j]);
					}

					if (this.fakeMetier == null)
						this.initialiserMetier(tabConteneur);
				}

				if (message.startsWith("20-")) {
					String code = message.substring(message.length() - 3);

					int lig = (int) code.charAt(0) - '0';
					int col = (int) code.charAt(1) - 'A';
					int coin = (int) code.charAt(2) - '0';

					// C'est un autre joueur qui joue
					while (this.fakeMetier.getJoueurCourant() == this.ia)
						this.fakeMetier.nouveauTour();

					Conteneur conteneur = this.fakeMetier.getConteneur(lig-1, col);
					if (conteneur != null)
						this.fakeMetier.jouerTwistlock(conteneur, coin);
				}

				// A l'IA de joueur
				if (message.startsWith("10-")) {
					System.out.println("\n" + message);

					// C'est l'IA qui doit jouer
					while (this.fakeMetier.getJoueurCourant() != this.ia)
						this.fakeMetier.nouveauTour();

					this.ia.Jouer();

					String action = this.ia.getDerniereAction();
					System.out.println(action);

					try {
						Thread.sleep(1000);
						this.envoi(action, adresse, port);
					} catch (IOException | InterruptedException e) {
						e.printStackTrace();
					}

					continue;
				}

				System.out.println("\n" + message);
			} while (ds != null);
		}).start();

		System.out.print("\nVotre nom d'équipe  : ");
		this.entree(entreeClavier, adresse, port);
	}

	private void initialiserMetier(int[][] conteneurs) {
		this.fakeMetier = new Metier(null, conteneurs.length, conteneurs[0].length);
		this.ia = (IA) this.fakeMetier.ajouterAI("IA", 20);

		this.fakeMetier.ajouterJoueur("PLAYER", 20);

		// Remplissage des conteneurs dans le faux métier
		for (int lig = 0; lig < conteneurs.length; lig++)
			for (int col = 0; col < conteneurs[lig].length; col++)
				this.fakeMetier.getConteneurs()[lig][col] = new Conteneur(lig+1, (char) ('A' + col), conteneurs[lig][col]);
	}

	private String entreeAdresse(BufferedReader entreeClavier) {
		try {
			String adresse;
			do {
				System.out.print("Adresse du serveur : ");
				adresse = entreeClavier.readLine();
			} while (adresse.equals(""));

			return adresse;
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	private int entreePort(BufferedReader entreeClavier) {
		try {
			String stringPort;

			int port;
			do {

				port = 0;

				do {
					System.out.print("   Port du serveur : ");
					stringPort = entreeClavier.readLine();
				} while (stringPort.equals(""));

				try {
					port = Integer.parseInt(stringPort);
				} catch (Exception ignored) {
					System.out.println("Numéro de prot incorrect !");
				}
			} while (port == 0);

			return port;
		} catch (IOException ex) {
			ex.printStackTrace();
			return -1;
		}
	}

	private void entree(BufferedReader entreeClavier, String adresse, int port) {
		try {
			do {
				String message = entreeClavier.readLine();
				if (message.equalsIgnoreCase("q")) ds = null;
				else this.envoi(message, adresse, port);
			} while (ds != null);
			System.exit(0);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void envoi(String message, String adresse, int port) throws IOException {
		DatagramPacket envoi = new DatagramPacket(message.getBytes(), message.length(), InetAddress.getByName(adresse), port);
		ds.send(envoi);
	}

	public static void main(String[] args) throws IOException {
		new IAClientTest();
	}

}
