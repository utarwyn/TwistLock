package twistlock;
import twistlock.ihm.FormServeur;
import twistlock.ihm.IHM;
import twistlock.metier.Conteneur;
import twistlock.metier.Joueur;
import twistlock.metier.Metier;

import java.util.ArrayList;

public class ControleurServeur
{
    private Metier metier;
    
    private IHM ihm;
    
    
    private ControleurServeur() {
//        this.ihm = new IHM();
        new FormServeur(this);
    }
    
    public void lancerServeur(int portConnexion, int nbLig, int nbCol, int nbjoueurs) {
        this.metier = new Metier(nbLig, nbCol);
    }
    
    public void chargerIHM() {
//        this.ihm.lancer();
    }
    
    /* ----------------------------- */
    /*  GESTION DE LA GRILLE DE JEU  */
    /* ----------------------------- */
    
    public int getNbLig() {
        return this.metier.getNbLig();
    }
    
    public int getNbCol() {
        return this.metier.getNbCol();
    }
    
    public Conteneur getConteneur( int lig, int col) {
        return this.metier.getConteneur(lig, col);
    }
    
    /* ------------------------ */
    /*  GESTION DES TWISTLOCKS  */
    /* ------------------------ */
    public boolean jouerTwistlock(Conteneur conteneur, int coin) {
        return this.metier.jouerTwistlock(conteneur, coin);
    }
    
    /* --------------------- */
    /*  GESTION DES JOUEURS  */
    /* --------------------- */
    
    public void ajouterJoueur(String nom, int tL) {
        this.metier.ajouterJoueur(nom, tL);
    }
    
    public ArrayList<Joueur> getJoueurs() {
        return this.metier.getJoueurs();
    }
    
    public Joueur getJoueurCourant() {
        return this.metier.getJoueurCourant();
    }
    
    public boolean nouveauTour() {
        return this.metier.nouveauTour();
    }
    
    public static void main(String[] args) {
        new ControleurServeur();
    }
}
