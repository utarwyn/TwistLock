package twistlock.net;

import twistlock.Controleur;
import twistlock.metier.Conteneur;
import twistlock.metier.Joueur;

import javax.naming.ldap.Control;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Serveur
{
    
    private Controleur controleur;

    public Serveur( Controleur controleur ){
        
        this.controleur = controleur;
    
        DatagramSocket ds  = null;
        try {
            ds = new DatagramSocket( controleur.getPortConnexion() );
        } catch( SocketException e ) {
            e.printStackTrace( );
        }
        DatagramPacket msg = new DatagramPacket( new byte[ 100 ] , 100 );

        new Thread( new Runnable( )
        {
            @Override
            public void run( )
            {
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
                }while( true );
            }
        } ).run( );
        

        ds.close( );
    }
    public static void main( String args[] ) throws Exception
    {

    }
}