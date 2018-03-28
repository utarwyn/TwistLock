package twistlock.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * classe qui se connecte au serveur et interargie avec l'utilisateur
 */
public class ClientTest
{
    private DatagramSocket ds;
    
    public ClientTest( ) throws IOException
    {
        ds = new DatagramSocket( );
        BufferedReader entreeClavier = new BufferedReader( new InputStreamReader( System.in ) );
        
        System.out.println( "Nouvelle partie de TwistLock\n" + "\n" + "Vous pouvez envoyer les messages :\n" + "    MAP : donne la map du plateau\n" +
                            "    ??? : permet de jouer un coup avec \n" + "              1 - ligne avec un numéro de 1 à 9\n" +
                            "              2 - colonne avec une lettre A à I\n" + "              3 - le coin de 1 à 4\n" +
                            "                      1 - pour en haut à gauche\n" + "                      2 - pour en haut à droite\n" +
                            "                      3 - pour en bas  à droite\n" + "                      4 - pour en bas  à gauche\n" +
                            "    q   : pour quitter\n" );
        
        new Thread( ( ) -> {
            do {
                DatagramPacket msg = new DatagramPacket( new byte[ 1024 ] , 1024 );
                try {
                    ds.receive( msg );
                } catch( IOException e ) {
                    e.printStackTrace( );
                }

                String message = new String( msg.getData( ) ).trim( );

                if ( message.equals("KILL") ) System.exit( 0 );
				else System.out.println( "\n" + message );
            } while( ds != null );
        } ).start( );
    
        System.out.print( "\nVotre nom d'équipe  : " );
        
        do {
            String message = entreeClavier.readLine( );
            if( message.equalsIgnoreCase( "q" ) ) ds = null;
            else {
                DatagramPacket envoi = new DatagramPacket( message.getBytes( ) , message.length( ) , InetAddress.getByName( "127.0.0.1" ) , 2684 );
                ds.send( envoi );
            }
        } while( ds != null );
        System.exit( 0 );
    }
    
    public static void main( String args[] ) throws Exception
    {
        new ClientTest( );
    }
}