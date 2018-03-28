package twistlock.ihm;

import twistlock.Controleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Objects;

class FormServeur extends JFrame
{
    private Controleur controleur;
    
    private JLabel jLabelTitre;
    private JLabel jLabelAdresseConnexion;
    private JLabel jLabelPortConnexion;
    private JLabel jLabelNbLignes;
    private JLabel jLabelNbColonnes;
    private JLabel jLabelNbTL;
    private JLabel jLabelNombreJoueurs;
    
    private JComboBox< String > jComboBoxAdresse;
    private JTextField          textFieldPortConnexion;
    private JTextField          textFieldNbLignes;
    private JTextField          textFieldNbColonnes;
    private JTextField          textFieldNbTL;
    private JTextField          textFieldNombreJoueurs;
    private JButton             buttonQuitter;
    private JButton             buttonValider;
    
    private String              stringAdresseIP;
    private int                 nbJoueurs;
    private int                 lignes;
    private int                 colonnes;
    private int                 nbTwistlocks;
    private int                 portConnexion;
    
    FormServeur( Controleur controleur )
    {
        this.controleur = controleur;
        
        this.setTitle( "Jeu des Twistlocks - Ajouter des Joueurs" );
        this.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        this.setSize( 1200 , 800 );
        
        //prépare les éléments graphique
        preparer( );
        
        //ajoute les élments graphiques
        remplir( );
        
        this.setLocationRelativeTo( null );
        
        this.setVisible( true );
    }
    
    private JComboBox< String > adresseIP( )
    {
        JComboBox< String > jComboBox = new JComboBox< >( );
        
        Enumeration< NetworkInterface > interfaces = null;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces( );
        } catch( SocketException e ) {
            System.out.println( "absence de cartes réseau" );
            System.exit( 1 );
        }
        
        while( interfaces.hasMoreElements( ) ) {
            NetworkInterface currentInterface = interfaces.nextElement( );
            
            //chaque carte réseau peut disposer de plusieurs adresses IP
            Enumeration< InetAddress > addresses = currentInterface.getInetAddresses( );
            while( addresses.hasMoreElements( ) ) {
                InetAddress currentAddress = addresses.nextElement( );
                jComboBox.addItem( currentAddress.getHostAddress( ) );
            }
        }
        
        return jComboBox;
    }
    
    private void preparer( )
    {
        
        jLabelTitre = new JLabel( "Configurez votre partie" );
        jLabelAdresseConnexion = new JLabel( "Adresse d'écoute : " );
        jLabelPortConnexion = new JLabel( "Port d'écoute : " );
        jLabelNbLignes = new JLabel( "Nombre de lignes : " );
        jLabelNbColonnes = new JLabel( "Nombre de colonnes : " );
        jLabelNbTL = new JLabel( "Nombre de twistlocks : " );
        jLabelNombreJoueurs = new JLabel( "Nombre de joueurs (2 à 4) : " );
        
        textFieldPortConnexion = new JTextField( "" );
        jComboBoxAdresse = adresseIP( );
        
        textFieldPortConnexion = new JTextField( "" );
        textFieldPortConnexion.addKeyListener( new KeyAdapter( )
        {
            public void keyTyped( KeyEvent e )
            {
                char caracter = e.getKeyChar( );
                if( ( ( caracter < '0' ) || ( caracter > '9' ) ) && ( caracter != '\b' ) ) {
                    e.consume( );
                }
            }
        } );
        
        textFieldNbLignes = new JTextField( "" );
        textFieldNbLignes.addKeyListener( new KeyAdapter( )
        {
            public void keyTyped( KeyEvent e )
            {
                char caracter = e.getKeyChar( );
                if( ( ( caracter < '1' ) || ( caracter > '9' ) ) && ( caracter != '\b' ) || textFieldNbLignes.getText( ).length( ) > 0 ) {
                    e.consume( );
                }
            }
        } );
        
        textFieldNbColonnes = new JTextField( "" );
        textFieldNbColonnes.addKeyListener( new KeyAdapter( )
        {
            public void keyTyped( KeyEvent e )
            {
                char caracter = e.getKeyChar( );
                if( ( ( caracter < '1' ) || ( caracter > '9' ) ) && ( caracter != '\b' ) || textFieldNbColonnes.getText( ).length( ) > 0 ) {
                    e.consume( );
                }
            }
        } );
        
        textFieldNbTL = new JTextField( "" );
        textFieldNbTL.addKeyListener( new KeyAdapter( )
        {
            public void keyTyped( KeyEvent e )
            {
                char caracter = e.getKeyChar( );
                if( ( ( caracter < '0' ) || ( caracter > '9' ) ) && ( caracter != '\b' ) ) {
                    e.consume( );
                }
            }
        } );
        
        textFieldNombreJoueurs = new JTextField( "" );
        textFieldNombreJoueurs.addKeyListener( new KeyAdapter( )
        {
            public void keyTyped( KeyEvent e )
            {
                char caracter = e.getKeyChar( );
                if( ( ( caracter < '2' ) || ( caracter > '4' ) ) && ( caracter != '\b' ) || textFieldNombreJoueurs.getText( ).length( ) > 0 ) {
                    e.consume( );
                }
            }
        } );
        
        textFieldPortConnexion.setColumns( 20 );
        textFieldNbLignes.setColumns( 20 );
        textFieldNbColonnes.setColumns( 20 );
        textFieldNbTL.setColumns( 20 );
        textFieldNombreJoueurs.setColumns( 20 );
        
        //pour quitter
        buttonQuitter = new JButton( "Quitter" );
        buttonQuitter.addActionListener( e -> System.exit( 0 ) );
        
        //pour passer au jeu
        buttonValider = new JButton( "Valider" );
        buttonValider.addActionListener( e -> {
            if( verification( ) ) {
                
                portConnexion = Integer.parseInt( textFieldPortConnexion.getText( ) );
                lignes = Integer.parseInt( textFieldNbLignes.getText( ) );
                colonnes = Integer.parseInt( textFieldNbColonnes.getText( ) );
                nbTwistlocks = Integer.parseInt( textFieldNbTL.getText( ) );
                nbJoueurs = Integer.parseInt( textFieldNombreJoueurs.getText( ) );
                
                //lancer la nouvelle fanêtre avec le plateau de jeu
                lancer( );
            }
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
    
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridx = 0;
        this.add( jLabelAdresseConnexion , gridBagConstraints );
    
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.gridx = 1;
        this.add( jComboBoxAdresse , gridBagConstraints );
    
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridx = 0;
        this.add( jLabelPortConnexion , gridBagConstraints );
    
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.gridx = 1;
        this.add( textFieldPortConnexion , gridBagConstraints );
        
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridx = 0;
        this.add( jLabelNbLignes , gridBagConstraints );
        
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.gridx = 1;
        this.add( textFieldNbLignes , gridBagConstraints );
        
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridx = 0;
        this.add( jLabelNbColonnes , gridBagConstraints );
        
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.gridx = 1;
        this.add( textFieldNbColonnes , gridBagConstraints );
        
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridx = 0;
        this.add( jLabelNbTL , gridBagConstraints );
        
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.gridx = 1;
        this.add( textFieldNbTL , gridBagConstraints );
        
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridx = 0;
        this.add( jLabelNombreJoueurs , gridBagConstraints );
        
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.gridx = 1;
        this.add( textFieldNombreJoueurs , gridBagConstraints );
        
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
        controleur.lancerServeur( stringAdresseIP, portConnexion , lignes , colonnes , nbTwistlocks , nbJoueurs );
        this.dispose( );
    }
    
    /**
     * Vérifie les donées saisies et affiche des messages d'erreur en cas d'erreur
     *
     * @return si les données sont valides
     */
    private boolean verification( )
    {
        boolean bOk = true;
        
        stringAdresseIP = Objects.requireNonNull( jComboBoxAdresse.getSelectedItem( ) ).toString();
        
        //port de connexion
        if( textFieldPortConnexion.getText( ).equals( "" ) ) {
            bOk = false;
            erreur( "Saississez un port de connexion entre 1 et 65635" );
        }
        else
            
            try {
                if( Integer.parseInt( textFieldPortConnexion.getText( ) ) < 0 || Integer.parseInt( textFieldPortConnexion.getText( ) ) > 65635 ) {
                    bOk = false;
                    erreur( "Saississez un port de connexion entre 1 et 65635" );
                }
            } catch( Exception e ) {
                bOk = false;
                erreur( "Saississez un port de connexion entre 1 et 65635" );
            }
        
        //nombre de lignes
        if( textFieldNbLignes.getText( ).equals( "" ) ) {
            bOk = false;
            erreur( "Saississez un nombre de lignes entre 1 et 9" );
        }
        else
            
            try {
                Integer.parseInt( textFieldNbLignes.getText( ) );
            } catch( Exception e ) {
                bOk = false;
                erreur( "Saississez un nombre de lignes entre 1 et 9" );
            }
        
        //nombre de colonnes
        if( textFieldNbColonnes.getText( ).equals( "" ) ) {
            bOk = false;
            erreur( "Saississez un nombre de colonnes entre 1 et 9" );
        }
        else
            
            try {
                Integer.parseInt( textFieldNbColonnes.getText( ) );
            } catch( Exception e ) {
                bOk = false;
                erreur( "Saississez un nombre de colonnes entre 1 et 9" );
            }
        
        //nombre de twistlocks
        if( textFieldNbTL.getText( ).equals( "" ) ) {
            bOk = false;
            erreur( "Saississez un nombre de Twistlocks" );
        }
        else
            
            try {
                Integer.parseInt( textFieldNbTL.getText( ) );
            } catch( Exception e ) {
                bOk = false;
                erreur( "Saississez un nombre de Twistlocks" );
            }
        
        //nombre de joueurs
        if( textFieldPortConnexion.getText( ).equals( "" ) ) {
            bOk = false;
            erreur( "Saississez un nombre de joueurs entre 2 et 4" );
        }
        else
            
            try {
                Integer.parseInt( textFieldNbLignes.getText( ) );
            } catch( Exception e ) {
                bOk = false;
                erreur( "Saississez un nombre de joueurs entre 2 et 4" );
            }
        
        return bOk;
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
}