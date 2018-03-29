package twistlock.net;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class IATest
{
	
	private DatagramSocket ds;
	private int[][]        tabConteneur;
	private boolean[][]    tabTL;
	
	public IATest( ) throws IOException
	{
		ds = new DatagramSocket( );
		BufferedReader entreeClavier = new BufferedReader( new InputStreamReader( System.in ) );
		
		String adresse;
		do {
			
			System.out.print( "Adresse du serveur : " );
			adresse = entreeClavier.readLine( );
		} while( adresse.equals( "" ) );
		
		String stringPort;
		
		int port;
		do {
			
			port = 0;
			
			do {
				System.out.print( "Port du serveur    : " );
				stringPort = entreeClavier.readLine( );
			} while( stringPort.equals( "" ) );
			
			try {
				port = Integer.parseInt( stringPort );
			} catch( Exception ignored ) {
				System.out.println( "Numéro de prot incorrect !" );
			}
		} while( port == 0 );
		
		System.out.println(
				"Nouvelle partie de TwistLock\n" + "\n" + "Vous pouvez envoyer les messages :\n" + "    MAP : donne la map du " + "plateau\n" +
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
				if( message.equalsIgnoreCase( "KILL" ) ) System.exit( 0 );
				if( message.substring( 0 , 3 ).equals( "MAP" ) ) {
					String   map  = message.substring( 4 );
					String[] part = map.split( "\\|" );
					for( int i = 0 ; i < part.length ; i++ ) {
						
						String[] nombre = part[ i ].split( ":" );
						for( int j = 0 ; j < nombre.length ; j++ ) {
							tabConteneur = new int[ part.length ][ nombre.length ];
							tabConteneur[ i ][ j ] = Integer.parseInt( nombre[ j ] );
							System.out.println( tabConteneur[ i ][ j ] );
						}
					}
				}
				if( message.substring( 0 , 2 ).equalsIgnoreCase( "20" ) ) {
					String code = message.substring( message.length( ) - 2 );
					int    lig  = ( int ) code.charAt( 0 ) - '0';
					int    col  = ( int ) code.charAt( 1 ) - 'A';
					int    coin = ( int ) code.charAt( 2 ) - '0';
					
					switch( coin ) {
						case 2:
							col++;
							break;
						
						case 3:
							lig++;
							col++;
							break;
						
						case 4:
							lig++;
							break;
						
						default:
					}
					
					tabTL[ lig ][ col ] = true;
				}
				else System.out.println( "\n" + message );
			} while( ds != null );
		} ).start( );
		
		System.out.print( "\nVotre nom d'équipe  : " );
		
		do {
			String message = entreeClavier.readLine( );
			if( message.equalsIgnoreCase( "q" ) ) ds = null;
			else {
				DatagramPacket envoi = new DatagramPacket( message.getBytes( ) , message.length( ) , InetAddress.getByName( adresse ) , port );
				ds.send( envoi );
			}
		} while( ds != null );
		System.exit( 0 );
	}
	
	public static void main( String[] args ) throws IOException
	{
		new IATest( );
	}
}
