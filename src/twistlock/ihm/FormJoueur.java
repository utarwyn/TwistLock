package twistlock.ihm;

import twistlock.Controleur;

import javax.swing.*;
import java.awt.*;

public class FormJoueur extends JFrame
{
    private Controleur controleur;
    private TextArea   textAreaTitre, textAreaJ1, textAreaJ2, textAreaJ3, textAreaJ4;
    private TextField textFieldJ1, textFieldJ2, textFieldJ3, textFieldJ4;
    
    private int lignes, colonnes;
    private int nbTwistlocks;
    
    public FormJoueur( Controleur controleur )
    {
        this.controleur = controleur;
        
        this.setTitle( "Jeu des Twistlocks - Ajouter des Joueurs" );
        this.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        this.setSize( MAXIMIZED_HORIZ, MAXIMIZED_VERT );
        
        preparer();
        
        this.setVisible( true );
    }
    
    private void preparer( )
    {
        textAreaTitre = new TextArea( "Configurez votre partie" );
        
        textFieldJ1 = new TextField( "Nom du joueur 1 : " );
        textFieldJ2 = new TextField( "Nom du joueur 2 : " );
        textFieldJ3 = new TextField( "Nom du joueur 3 : " );
        textFieldJ4 = new TextField( "Nom du joueur 4 : " );
        
        textFieldJ1 = new TextField( );
        textFieldJ2 = new TextField( );
        textFieldJ3 = new TextField( );
        textFieldJ4 = new TextField( );
    }
    
    private void lancer( )
    {
        controleur.chargerMetier( lignes , colonnes );
//        controleur.ajouterJoueur( );
    }
}
