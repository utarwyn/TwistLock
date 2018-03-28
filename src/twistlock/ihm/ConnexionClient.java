package twistlock.ihm;
import twistlock.Controleur;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ConnexionClient extends JFrame
{
    private Controleur controleur;
    
    private JLabel jLabelTitre;
    private JLabel jLabelAdresseIP;
    private JLabel jLabelPort;
    private JLabel jLabelJ1;
    private JLabel jLabelJ2;
    private JLabel jLabelJ3;
    private JLabel jLabelJ4;
    
    private JButton buttonValider;
    private JButton buttonQuitter;
    private int     nbJoueurs;
    private int     lignes;
    private int     colonnes;
    private int     nbTwistlocks;
    private int     portConnexion;
    
    public ConnexionClient( Controleur controleur )
    {
        this.controleur = controleur;
        
        this.setTitle( "Jeu des Twistlocks - Connexion des Joueurs" );
        this.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
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
        
        try {
            jLabelAdresseIP = new JLabel( "Adresse IP serveur : " + InetAddress.getLocalHost( ).getHostAddress( ) );
        } catch( UnknownHostException e ) {
            e.printStackTrace( );
        }
        
        jLabelPort = new JLabel( "Port de connexion : " + controleur.getServeur().getPortConnexion() );
        
        jLabelJ1 = new JLabel( "Joueur 1 : " );
        jLabelJ2 = new JLabel( "Joueur 2 : " );
        //if( controleur.get )jLabelJ3 = new JLabel( "Joueur 3 : " );
        jLabelJ4 = new JLabel( "Joueur 4 : " );
        
        //pour quitter
        buttonQuitter = new JButton( "Quitter" );
        buttonQuitter.addActionListener( e -> System.exit( 0 ) );
        
        //pour passer au jeu
        buttonValider = new JButton( "Fermer la fenêtre" );
        buttonValider.addActionListener( e -> {
            this.dispose();
        } );
    }
    
    /**
     * Remplie l'interface graphique et met en forme les éléments
     */
    private void remplir( )
    {
        this.setLayout( new GridBagLayout( ) );
        
        GridBagConstraints gridBagConstraints = new GridBagConstraints( );
        
        gridBagConstraints.insets = new Insets( 10 , 10 , 10 , 10);
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        
        gridBagConstraints.gridwidth = 1;
        this.add( jLabelTitre , gridBagConstraints );
        
        gridBagConstraints.gridwidth = 1;
        
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridx = 0;
        this.add( jLabelPort , gridBagConstraints );

        gridBagConstraints.gridy = 2;
        this.add( jLabelAdresseIP , gridBagConstraints );
        
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.gridy = 6;
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
     *         a afficher dans la fenêtre d'erreur
     */
    private void erreur( String message )
    {
        JOptionPane.showMessageDialog( null , message );
    }
    
    public JLabel getjLabelJ1( )
    {
        return jLabelJ1;
    }
    
    public JLabel getjLabelJ2( )
    {
        return jLabelJ2;
    }
    
    public JLabel getjLabelJ3( )
    {
        return jLabelJ3;
    }
    
    public JLabel getjLabelJ4( )
    {
        return jLabelJ4;
    }
}
