package twistlock.ihm;
import twistlock.Controleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FormChoixServeurPlateau extends JFrame
{
    
    private Controleur controleur;
    
    private JLabel jLabelTitre;
    
    private JButton buttonQuitter;
    private JButton buttonLocal;
    private JButton buttonServeur;
    
    public FormChoixServeurPlateau( Controleur controleur )
    {
        this.controleur = controleur;
        
        this.setTitle( "Jeu des Twistlocks - Choix du jeu" );
        
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
        
        //pour quitter
        buttonQuitter = new JButton( "Quitter" );
        buttonQuitter.addActionListener( e -> System.exit( 0 ) );
    
        //pour passer au jeu
        buttonLocal = new JButton( "Partie en local" );
        buttonLocal.addActionListener( e -> {
            lancerLocal( 1 );
        } );
        //pour passer au jeu
        buttonServeur = new JButton( "Partie en réseau" );
        buttonServeur.addActionListener( e -> {
            lancerLocal( 2 );
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
        
        gridBagConstraints.gridwidth = 3;
        this.add( jLabelTitre , gridBagConstraints );
        
        gridBagConstraints.gridwidth = 1;
        
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridx = 0;
        this.add( buttonQuitter , gridBagConstraints );
        
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.gridx = 1;
        this.add( buttonLocal , gridBagConstraints );
        
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;

        gridBagConstraints.gridx = 2;
        this.add( buttonServeur , gridBagConstraints );
        
    }
    
    /**
     * Lance le jeu et ouvre la fenêtre avec le plateau du jeu Ajoute les joueurs
     */
    private void lancerLocal( int i )
    {
        controleur.lancerForm( i );
        this.dispose( );
    }
}
