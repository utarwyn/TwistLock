package twistlock;

import twistlock.ihm.AjoutJoueur;
import twistlock.ihm.IHM;
import twistlock.metier.Conteneur;
import twistlock.metier.Joueur;
import twistlock.metier.Metier;
import twistlock.metier.TwistLock;

import java.util.ArrayList;

public class Controleur
{
    
    private Metier metier;
    
    private Controleur controleur;
    
    private AjoutJoueur lanceur;
    
    private Controleur( )
    {
        lanceur = new AjoutJoueur( );
        lanceur.launch( AjoutJoueur.class );
        chargerMetier(  );
        IHM application = new IHM( );
        application.launch( IHM.class );
        application.setControleur( this );
    }
    
    public static void main( String[] args )
    {
        new Controleur( );
    }
    
    /* ----------------------------- */
    /*  GESTION DE LA GRILLE DE JEU  */
    /* ----------------------------- */
    
    public void chargerMetier( )
    {
        this.metier = new Metier( lanceur.getLignes(), lanceur.getColonnes() );
        System.out.println( lanceur );
        for( int i = 0 ; i < lanceur.getNbJoueurs( ) ; i++ ) {
            ajouterJoueur( lanceur.getNomJoueurs().get( i ) );
        }
    }
    
    public int getNbLig( )
    {
        return this.metier.getNbLig( );
    }
    
    public int getNbCol( )
    {
        return this.metier.getNbCol( );
    }
    
    /* ------------------------ */
    /*  GESTION DES TWISTLOCKS  */
    /* ------------------------ */
    
    public Conteneur getConteneur( int lig , int col )
    {
        return this.metier.getConteneur( lig , col );
    }
    
    public TwistLock[][] getTwistLocks( )
    {
        return this.metier.getTwistLocks( );
    }
    
    public boolean isTwistlock( int lig , int col )
    {
        return this.metier.isTwistlock( lig , col );
    }
    
    /* --------------------- */
    /*  GESTION DES JOUEURS  */
    /* --------------------- */
    
    public boolean jouerTwistlock( int lig , int col , Joueur joueur )
    {
        return this.metier.jouerTwistlock( lig , col , joueur );
    }
    
    public void ajouterJoueur( String nom )
    {
        this.metier.ajouterJoueur( nom );
    }
    
    public ArrayList< Joueur > getJoueurs( )
    {
        return this.metier.getJoueurs( );
    }
    
    public boolean nouveauTour( )
    {
        return this.metier.nouveauTour( );
    }
}
