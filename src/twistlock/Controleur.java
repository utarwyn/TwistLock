package twistlock;

import twistlock.ihm.FormChoixServeurPlateau;
import twistlock.ihm.FormJoueur;
import twistlock.ihm.FormServeur;
import twistlock.ihm.IHM;
import twistlock.metier.Conteneur;
import twistlock.metier.Joueur;
import twistlock.metier.Metier;
import twistlock.net.Serveur;

import java.util.ArrayList;

public class Controleur
{
    
    private Metier metier;
    private Serveur serveur;
    
    private FormServeur formServeur;
    private FormJoueur  formJoueur;
    private IHM         ihm;
    
    private int lignes, colonnes, portConnexion, nbJoueurs;
    
    private Controleur( )
    {
        new FormChoixServeurPlateau( this );
    }
    
    public static void main( String[] args )
    {
        new Controleur( );
    }
    
    public void lancerForm( int choix )
    {
        if( choix == 1 ) formJoueur = new FormJoueur( this );
        else formServeur = new FormServeur( this );
    }
    
    public void chargerMetier( int nbLig , int nbCol )
    {
        this.metier = new Metier( nbLig , nbCol );
    }
    
    /* ----------------------------- */
    /*  GESTION DE LA GRILLE DE JEU  */
    /* ----------------------------- */
    
    public void chargerIHM( )
    {
        this.ihm = new IHM( this );
        this.ihm.lancer( );
    }
    
    public int getNbLig( )
    {
        return this.metier.getNbLig( );
    }
    
    public int getNbCol( )
    {
        return this.metier.getNbCol( );
    }
    
    public Conteneur getConteneur( int lig , int col )
    {
        return this.metier.getConteneur( lig , col );
    }
    
    /* --------------------- */
    /*  GESTION DES JOUEURS  */
    /* --------------------- */
    
    /* ------------------------ */
    /*  GESTION DES TWISTLOCKS  */
    /* ------------------------ */
    public boolean jouerTwistlock( Conteneur conteneur , int coin )
    {
        return this.metier.jouerTwistlock( conteneur , coin );
    }
    
    public void ajouterJoueur( String nom , int tL )
    {
        this.metier.ajouterJoueur( nom , tL );
    }
    
    public ArrayList< Joueur > getJoueurs( )
    {
        return this.metier.getJoueurs( );
    }
    
    public Joueur getJoueurCourant( )
    {
        return this.metier.getJoueurCourant( );
    }
    
    public boolean nouveauTour( )
    {
        return this.metier.nouveauTour( );
    }
    
    public void lancerServeur( int portConnexion , int lignes , int colonnes , int nbJoueurs )
    {
        this.portConnexion = portConnexion;
        this.lignes = lignes;
        this.colonnes = colonnes;
        this.nbJoueurs = nbJoueurs;
        
        this.serveur = new Serveur( this );
        //TODO lancer serveur
    }
    
    public int getLignes( )
    {
        return lignes;
    }
    
    public int getColonnes( )
    {
        return colonnes;
    }
    
    public int getPortConnexion( )
    {
        return portConnexion;
    }
    
    public int getNbJoueurs( )
    {
        return nbJoueurs;
    }
}
