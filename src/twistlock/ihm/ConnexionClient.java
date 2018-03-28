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
        
        jLabelPort = new JLabel( "Port de connexion : " + portConnexion );
        
        jLabelJ1 = new JLabel( "Joueur 1 : " );
        jLabelJ2 = new JLabel( "Joueur 2 : " );
        jLabelJ3 = new JLabel( "Joueur 3 : " );
        jLabelJ4 = new JLabel( "Joueur 4 : " );
        
        //pour quitter
        buttonQuitter = new JButton( "Quitter" );
        buttonQuitter.addActionListener( e -> System.exit( 0 ) );
        
        //pour passer au jeu
        buttonValider = new JButton( "Valider" );
        buttonValider.addActionListener( e -> {
//            if( verification( ) ) lancer( );
        } );
    }
    
    /**
     * Remplie l'interface graphique et met en forme les éléments
     */
    private void remplir( )
    {
        this.setLayout( new GridBagLayout( ) );
        
        GridBagConstraints gridBagConstraints = new GridBagConstraints( );
        
        gridBagConstraints.insets = new Insets( 10 , 0 , 10 , 0 );
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        
        gridBagConstraints.gridwidth = 2;
        this.add( jLabelTitre , gridBagConstraints );
        
        gridBagConstraints.gridwidth = 1;
        
//        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
//        gridBagConstraints.gridy = 1;
//        gridBagConstraints.gridx = 0;
//        this.add( jLabelPortConnexion , gridBagConstraints );
//
//        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
//        gridBagConstraints.gridx = 1;
//        this.add( textFieldPortConnexion , gridBagConstraints );
//
//        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
//        gridBagConstraints.gridy = 2;
//        gridBagConstraints.gridx = 0;
//        this.add( jLabelNbLignes , gridBagConstraints );
//
//        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
//        gridBagConstraints.gridx = 1;
//        this.add( textFieldNbLignes , gridBagConstraints );
//
//        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
//        gridBagConstraints.gridy = 3;
//        gridBagConstraints.gridx = 0;
//        this.add( jLabelNbColonnes , gridBagConstraints );
//
//        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
//        gridBagConstraints.gridx = 1;
//        this.add( textFieldNbColonnes , gridBagConstraints );
//
//        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
//        gridBagConstraints.gridy = 4;
//        gridBagConstraints.gridx = 0;
//        this.add( jLabelNbTL , gridBagConstraints );
//
//        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
//        gridBagConstraints.gridx = 1;
//        this.add( textFieldNbTL , gridBagConstraints );
//
//        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
//        gridBagConstraints.gridy = 5;
//        gridBagConstraints.gridx = 0;
//        this.add( jLabelNombreJoueurs , gridBagConstraints );
//
//        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
//        gridBagConstraints.gridx = 1;
//        this.add( textFieldNombreJoueurs , gridBagConstraints );
        
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
     * Vérifie les donées saisies et affiche des messages d'erreur en cas d'erreur
     *
     * @return si les données sont valides
     */
//    private boolean verification( ){}
//    {
//        boolean bOk = true;
//
//        //port de connexion
//        if( textFieldPortConnexion.getText( ).equals( "" ) ) {
//            bOk = false;
//            erreur( "Saississez un port de connexion entre 1 et 65635" );
//        }
//        else
//
//            try {
//                if( Integer.parseInt( textFieldPortConnexion.getText( ) ) < 0 || Integer.parseInt( textFieldPortConnexion.getText( ) ) > 65635 ) {
//                    bOk = false;
//                    erreur( "Saississez un port de connexion entre 1 et 65635" );
//                }
//            } catch( Exception e ) {
//                bOk = false;
//                erreur( "Saississez un port de connexion entre 1 et 65635" );
//            }
//
//        //nombre de lignes
//        if( textFieldNbLignes.getText( ).equals( "" ) ) {
//            bOk = false;
//            erreur( "Saississez un nombre de lignes entre 1 et 9" );
//        }
//        else
//
//            try {
//                Integer.parseInt( textFieldNbLignes.getText( ) );
//            } catch( Exception e ) {
//                bOk = false;
//                erreur( "Saississez un nombre de lignes entre 1 et 9" );
//            }
//
//        //nombre de colonnes
//        if( textFieldNbColonnes.getText( ).equals( "" ) ) {
//            bOk = false;
//            erreur( "Saississez un nombre de colonnes entre 1 et 9" );
//        }
//        else
//
//            try {
//                Integer.parseInt( textFieldNbColonnes.getText( ) );
//            } catch( Exception e ) {
//                bOk = false;
//                erreur( "Saississez un nombre de colonnes entre 1 et 9" );
//            }
//
//        //nombre de twistlocks
//        if( textFieldNbTL.getText( ).equals( "" ) ) {
//            bOk = false;
//            erreur( "Saississez un nombre de Twistlocks" );
//        }
//        else
//
//            try {
//                Integer.parseInt( textFieldNbTL.getText( ) );
//            } catch( Exception e ) {
//                bOk = false;
//                erreur( "Saississez un nombre de Twistlocks" );
//            }
//
//        //nombre de joueurs
//        if( textFieldPortConnexion.getText( ).equals( "" ) ) {
//            bOk = false;
//            erreur( "Saississez un nombre de joueurs entre 2 et 4" );
//        }
//        else
//
//            try {
//                Integer.parseInt( textFieldNbLignes.getText( ) );
//            } catch( Exception e ) {
//                bOk = false;
//                erreur( "Saississez un nombre de joueurs entre 2 et 4" );
//            }
//
//        return bOk;
//    }
    
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
}
