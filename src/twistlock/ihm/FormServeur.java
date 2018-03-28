package twistlock.ihm;
import twistlock.ControleurServeur;

import javax.swing.*;
import java.util.ArrayList;

public class FormServeur extends JFrame
{
    private ControleurServeur controleurServeur;
    
    private JLabel jLabelTitre;
    private JLabel jLabelPortConnexion;
    private JLabel jLabelNbLignes;
    private JLabel jLabelNbColonnes;
    private JLabel jLabelNbTL;
    private JLabel jLabelNombreJoueurs;
    
    private JTextField textFieldPortConnexion;
    private JTextField textFieldNbLignes;
    private JTextField textFieldNbColonnes;
    private JTextField textFieldNbTL;
    private JTextField textFieldNombreJoueurs;
   
    
    private JButton             buttonQuitter;
    private JButton             buttonValider;
    private int                 nbJoueurs;
    private int                 lignes;
    private int                 colonnes;
    private int                 nbTwistlocks;
    private int                 portConnexion;
    
    public FormServeur( ControleurServeur controleurServeur )
    {
        this.controleurServeur = controleurServeur;
    
        this.setTitle( "Jeu des Twistlocks - Ajouter des Joueurs" );
        this.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        this.setSize( 1200 , 800 );
    
        //prépare les éléments graphique
        //preparer( );
    
        //ajoute les élments graphiques
        //remplir( );
    
        this.setLocationRelativeTo( null );
    
        this.setVisible( true );
    }
}
