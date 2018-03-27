package twistlock.metier;

import com.sun.xml.internal.bind.v2.TODO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe métier qui gère le coeur du jeu.
 */
public class Metier
{
    
    private int nbLig, nbCol;
    
    private ArrayList< Joueur > joueurs;
    
    private Joueur joueurCourant;
    
    private Conteneur[][] conteneurs;
    
    public Metier( int nbLig , int nbCol )
    {
        this.nbLig = nbLig;
        this.nbCol = nbCol;
        
        this.conteneurs = new Conteneur[ this.nbLig ][ this.nbCol ];
        this.joueurs = new ArrayList<>( );
        
        this.initialiser( );
    }
    
    /**
     * Initialise la grille de jeu avec des conteneurs à valeur aléatoire
     */
    private void initialiser( )
    {
        // Création des conteneurs
        for( int lig = 0 ; lig < this.nbLig ; lig++ )
            for( char col = 0 ; col < this.nbCol ; col++ ) {
                this.conteneurs[ lig ][ col ] = new Conteneur( lig + 1 , ( char ) ( 'A' + col ) , ( int ) ( Math.random( ) * ( 54 - 5 ) + 5 ) );
            }
    }
    
    /**
     * Retourne le nombre de lignes de la grille
     *
     * @return Nombre de lignes
     */
    public int getNbLig( )
    {
        return this.nbLig;
    }
    
    /**
     * Retourne le nombre de colonnes de la grille
     *
     * @return Nombre de colonnes
     */
    public int getNbCol( )
    {
        return this.nbCol;
    }
    
    /**
     * Retourne un conteneur à une position précise dans la grille
     *
     * @param lig
     *         Ligne où chercher le conteneur
     * @param col
     *         Colonne où chercher le conteneur
     *
     * @return Le conteneur trouvé, null si aucun conteneur à la position
     */
    public Conteneur getConteneur( int lig , int col )
    {
        if( lig < 0 || col < 0 || lig >= this.nbLig || col >= this.nbCol ) return null;
        
        return this.conteneurs[ lig ][ col ];
    }
    
    /**
     * Retourne les joueurs qui participent au jeu
     *
     * @return Liste des joueurs participants
     */
    public ArrayList< Joueur > getJoueurs( )
    {
        return this.joueurs;
    }
    
    /**
     * Retourne une grille des twistlocks présents au coin des conteneurs.
     *
     * @return Tableau à deux dimensions des twistlocks de la grille
     */
    public TwistLock[][] getTwistLocks( )
    {
        TwistLock[][] locks = new TwistLock[ this.getNbLig( ) + 1 ][ this.getNbCol( ) + 1 ];
        Conteneur     conteneur;
        
        for( int lig = 0 ; lig < this.conteneurs.length ; lig++ )
            for( int col = 0 ; col < this.conteneurs[ lig ].length ; col++ ) {
                conteneur = this.conteneurs[ lig ][ col ];
                
                locks[ lig ][ col ] = conteneur.getCoins( )[ 0 ];
                locks[ lig ][ col + 1 ] = conteneur.getCoins( )[ 1 ];
                locks[ lig + 1 ][ col + 1 ] = conteneur.getCoins( )[ 2 ];
                locks[ lig + 1 ][ col ] = conteneur.getCoins( )[ 3 ];
            }
        
        return locks;
    }
    
    /**
     * Indique la présence ou non d'un twistlock à une position donnée
     *
     * @param lig
     *         Ligne de la grille à tester
     * @param col
     *         Colonne de la grille à tester
     *
     * @return Vrai si un twistlock se situe à l'endroit cherché
     */
    public boolean isTwistlock( int lig , int col )
    {
        return this.getTwistLocks( )[ lig ][ col ] != null;
    }
    
    /**
     * Recalcule le score de tous les joueurs en fonction de leur possession de conteneurs dans la grille de jeu.
     */
    private void recalculerScores( )
    {
        // On réinitialise d'abord le score de tous les joueurs
        for( Joueur joueur : this.joueurs )
            joueur.resetScore( );
        
        Conteneur conteneur;
        Joueur    proprietaire;
        
        for( int lig = 0 ; lig < this.nbLig ; lig++ ) {
            for( int col = 0 ; col < this.nbCol ; col++ ) {
                conteneur = this.getConteneur( lig , col );
                proprietaire = conteneur.getProprietaire( );
                
                if( proprietaire != null ) {
                    for( Joueur joueur : this.joueurs )
                        if( joueur == proprietaire ) {
                            joueur.addScore( conteneur.getValeur( ) );
                            break;
                        }
                }
            }
        }
    }
    
    /**
     * Ajoute un joueur lié au jeu avec avec nom
     *
     * @param nom
     *         Nom du joueur
     */
    public void ajouterJoueur( String nom )
    {
        Joueur joueur = new Joueur( this.joueurs.size( ) + 1 , nom );
        
        this.joueurs.add( joueur );
        if( this.joueurCourant == null ) this.joueurCourant = joueur;
    }
    
    /**
     * Passe le tour au joueur suivant
     *
     * @return Vrai si le tour a pu être passé, faux si la partie est terminée.
     */
    public boolean nouveauTour( )
    {
        this.recalculerScores( );
        
        // Suppression d'un twistlock pour le joueur courant
        if( this.joueurCourant != null ) this.joueurCourant.retirerTwistlock( );
        
        /* Vérifications de fin de partie:
         *   - Aucun emplacement de Twistlock disponible
         *   - Plus aucun joueur n'a de twistlock         */
        boolean       finDePartie = true;
        TwistLock[][] locks       = this.getTwistLocks( );
        
        for( int lig = 0 ; lig < locks.length ; lig++ )
            for( int col = 0 ; col < locks[ lig ].length ; col++ )
                if( locks[ lig ][ col ] == null ) finDePartie = false;
        
        for( Joueur joueur : this.joueurs )
            if( joueur.peutJouer( ) ) finDePartie = false;
        
        if( finDePartie ) {
            this.joueurCourant = null;
            return false;
        }
        
        // Détermination du prochain joueur (il doit avoir des twistlocks)
        do {
            
            this.prochainJoueur( );
        } while( ! this.joueurCourant.peutJouer( ) );
        
        return true;
    }
    
    /**
     * Passe la main au prochain joueur
     */
    private void prochainJoueur( )
    {
        this.joueurCourant = this.joueurs.get( this.joueurCourant.getId( ) % this.joueurs.size( ) );
    }
    
    /**
     * Joue un twistlock pour un joueur à une position donnée
     *
     * @param lig
     *         Ligne où jouer le twistlock
     * @param col
     *         Colonne où jouer le twistlock
     * @param joueur
     *         Joueur qui pose le twistlock
     *
     * @return Vrai si le twistlock à pu être posé.
     */
    public boolean jouerTwistlock( int lig , int col , Joueur joueur )
    {
        Map< Conteneur, Integer > conteneurs = new HashMap<>( );
        Conteneur                 conteneur;
        
        // Il y a déjà un twistlock où le joueur souhaite jouer
        if( this.isTwistlock( lig , col ) ) {
            joueur.retirerTwistlock( );
            return false;
        }
        
        // Création du twistlock
        TwistLock twistlock = new TwistLock( joueur );
        
        if( ( conteneur = this.getConteneur( lig - 1 , col - 1 ) ) != null ) conteneurs.put( conteneur , 3 );
        if( ( conteneur = this.getConteneur( lig - 1 , col ) ) != null ) conteneurs.put( conteneur , 4 );
        if( ( conteneur = this.getConteneur( lig , col ) ) != null ) conteneurs.put( conteneur , 1 );
        if( ( conteneur = this.getConteneur( lig , col - 1 ) ) != null ) conteneurs.put( conteneur , 2 );
        
        for( Map.Entry< Conteneur, Integer > conteneurEntry : conteneurs.entrySet( ) )
            conteneurEntry.getKey( ).getCoins( )[ conteneurEntry.getValue( ) - 1 ] = twistlock;
        
        return true;
    }
}
