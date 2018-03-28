package twistlock.net;

import twistlock.metier.Joueur;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Serveur {
    public static void main(String args[]) throws Exception {
        DatagramSocket ds = new DatagramSocket(2684);
        DatagramPacket msg = new DatagramPacket(new byte[100], 100);


        for(int i=1;i<=2;i++)
        {
            ds.receive(msg);
            String texteReponse = "";
            String nom = new String(msg.getData()).trim();
            texteReponse+=(i+"-Bonjour Equipe " + new String(msg.getData()).trim());
            texteReponse+=("\nVous etes le Joueur "+i+" (ROUGE)");
            new Joueur(i,nom,20);
            DatagramPacket reponse = new DatagramPacket(texteReponse.getBytes(), texteReponse.length(), msg.getAddress(), msg.getPort());
            ds.send(reponse);
        }

        ds.close();
    }
}