package twistlock.ihm;
import twistlock.Controleur;

import javax.swing.*;
import java.awt.*;

public class ConnexionClient extends JFrame
{
	private Controleur controleur;
	
	private JLabel jLabelTitre;
	private JLabel jLabelAdresseIP;
	private JLabel jLabelAdresseIPValue;
	private JLabel jLabelPort;
	private JLabel jLabelPortValue;
	private JLabel jLabelJ1;
	private JLabel jLabelJ1Value;
	private JLabel jLabelJ2;
	private JLabel jLabelJ2Value;
	private JLabel jLabelJ3;
	private JLabel jLabelJ3Value;
	private JLabel jLabelJ4;
	private JLabel jLabelJ4Value;
	
	private JButton buttonValider;
	private JButton buttonQuitter;
	
	private int nbJoueurs;
	
	public ConnexionClient( Controleur controleur , int nbJoueurs )
	{
		this.controleur = controleur;
		this.nbJoueurs = nbJoueurs;
		
		this.setTitle( "Jeu des Twistlocks - Connexion des Joueurs" );
		this.setSize( 1200 , 800 );
		
		//prépare les éléments graphique
		preparer( );
		
		//ajoute les élments graphiques
		remplir( );
		
		this.setLocationRelativeTo( null );
		
		this.setVisible( true );
	}
	
	private void preparer( )
	{
		
		jLabelTitre = new JLabel( "Configurez votre partie" );
		
		jLabelAdresseIP = new JLabel( "Adresse de connexion : " );
		jLabelAdresseIPValue = new JLabel( controleur.getServeur( ).getAdresseIP( ) );
		
		jLabelPort = new JLabel( "Port de connexion : " );
		jLabelPortValue = new JLabel( String.valueOf( controleur.getServeur( ).getPortConnexion( ) ) );
		
		jLabelJ1 = new JLabel( "Joueur 1 : " );
		jLabelJ1Value = new JLabel( );
		jLabelJ2 = new JLabel( "Joueur 2 : " );
		jLabelJ2Value = new JLabel( );
		jLabelJ3 = new JLabel( "Joueur 3 : " );
		jLabelJ3Value = new JLabel( );
		jLabelJ4 = new JLabel( "Joueur 4 : " );
		jLabelJ4Value = new JLabel( );
		
		//pour quitter
		buttonQuitter = new JButton( "Quitter" );
		buttonQuitter.addActionListener( e -> System.exit( 0 ) );
		
		//pour passer au jeu
		buttonValider = new JButton( "Fermer la fenêtre" );
		buttonValider.addActionListener( e -> {
			this.dispose( );
		} );
	}
	
	/**
	 * Remplie l'interface graphique et met en forme les éléments
	 */
	private void remplir( )
	{
		this.setLayout( new GridBagLayout( ) );
		
		GridBagConstraints gridBagConstraints = new GridBagConstraints( );
		
		gridBagConstraints.insets = new Insets( 10 , 10 , 10 , 10 );
		gridBagConstraints.anchor = GridBagConstraints.CENTER;
		
		gridBagConstraints.gridwidth = 2;
		this.add( jLabelTitre , gridBagConstraints );
		
		gridBagConstraints.gridwidth = 1;
		
		gridBagConstraints.anchor = GridBagConstraints.LINE_END;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridx = 0;
		this.add( jLabelPort , gridBagConstraints );
		
		gridBagConstraints.anchor = GridBagConstraints.LINE_START;
		gridBagConstraints.gridx = 1;
		this.add( jLabelPortValue , gridBagConstraints );
		
		gridBagConstraints.anchor = GridBagConstraints.LINE_END;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridx = 0;
		this.add( jLabelAdresseIP , gridBagConstraints );
		
		gridBagConstraints.anchor = GridBagConstraints.LINE_START;
		gridBagConstraints.gridx = 1;
		this.add( jLabelAdresseIPValue , gridBagConstraints );
		
		gridBagConstraints.anchor = GridBagConstraints.LINE_END;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridx = 0;
		this.add( jLabelJ1 , gridBagConstraints );
		
		gridBagConstraints.anchor = GridBagConstraints.LINE_START;
		gridBagConstraints.gridx = 1;
		this.add( jLabelJ1Value , gridBagConstraints );
		
		gridBagConstraints.anchor = GridBagConstraints.LINE_END;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.gridx = 0;
		this.add( jLabelJ2 , gridBagConstraints );
		
		gridBagConstraints.anchor = GridBagConstraints.LINE_START;
		gridBagConstraints.gridx = 1;
		this.add( jLabelJ2Value , gridBagConstraints );
		
		if( nbJoueurs > 2 ) {
			gridBagConstraints.anchor = GridBagConstraints.LINE_END;
			gridBagConstraints.gridy = 5;
			gridBagConstraints.gridx = 0;
			this.add( jLabelJ3 , gridBagConstraints );
			
			gridBagConstraints.anchor = GridBagConstraints.LINE_START;
			gridBagConstraints.gridx = 1;
			this.add( jLabelJ3Value , gridBagConstraints );
		}
		
		if( nbJoueurs > 3 ) {
			gridBagConstraints.anchor = GridBagConstraints.LINE_END;
			gridBagConstraints.gridy = 6;
			gridBagConstraints.gridx = 0;
			this.add( jLabelJ4 , gridBagConstraints );
			
			gridBagConstraints.anchor = GridBagConstraints.LINE_START;
			gridBagConstraints.gridx = 1;
			this.add( jLabelJ4Value , gridBagConstraints );
		}
		
		gridBagConstraints.anchor = GridBagConstraints.CENTER;
		gridBagConstraints.gridy = 7;
		gridBagConstraints.gridx = 0;
		this.add( buttonQuitter , gridBagConstraints );
		
		gridBagConstraints.gridx = 1;
		this.add( buttonValider , gridBagConstraints );
	}
	
	/**
	 * Lance le jeu et ouvre la fenêtre avec le plateau du jeu Ajoute les joueurs
	 */
	private void lancer( )
	{
		this.dispose( );
	}
	
	/**
	 * fenêtre d'erreur
	 *
	 * @param message
	 * 		a afficher dans la fenêtre d'erreur
	 */
	private void erreur( String message )
	{
		JOptionPane.showMessageDialog( null , message );
	}
	
	public void setJLabel( int i , String nom )
	{
		switch( i ) {
			case 1:
				setjLabelJ1Value( nom );
				break;
			case 2:
				setjLabelJ2Value( nom );
				break;
			case 3:
				setjLabelJ3Value( nom );
				break;
			case 4:
				setjLabelJ4Value( nom );
				break;
			default:
		}
	}
	
	public void setjLabelJ1Value( String jLabelJ1Value )
	{
		this.jLabelJ1Value.setText( jLabelJ1Value );
	}
	
	public void setjLabelJ2Value( String jLabelJ2Value )
	{
		this.jLabelJ2Value.setText( jLabelJ2Value );
	}
	
	public void setjLabelJ3Value( String jLabelJ3Value )
	{
		this.jLabelJ3Value.setText( jLabelJ3Value );
	}
	
	public void setjLabelJ4Value( String jLabelJ4Value )
	{
		this.jLabelJ4Value.setText( jLabelJ4Value );
	}
}
