package twistlock.metier;

public class TwistLock {

    Joueur joueur;
    int coin;

    public TwistLock(Joueur joueur, int coin){
        this.joueur = joueur;
        this.coin = coin;
    }

    public Joueur getJoueur() { return joueur; }

    public int getCoin() { return coin; }
}
