package twistlock.metier;

public class TwistLock {

    Joueur joueur;
    int coin;

    public TwistLock(Joueur joueur){
        this.joueur = joueur;
    }

    public Joueur getJoueur() { return joueur; }
}
