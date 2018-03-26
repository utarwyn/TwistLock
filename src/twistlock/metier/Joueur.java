package twistlock.metier;

public class Joueur {
    private int id, score;
    private int nbTwistLock;
    private String couleur, nom;

    public Joueur(int id, String couleur, String nom, int score, int nbTwistLock){
        this.id = id;
        this.couleur = couleur;
        this.nom = nom;
        this.score = score;
        this.nbTwistLock = nbTwistLock;
    }

    public int getId() { return id; }
    public String getCouleur() { return couleur; }
    public String getNom() { return nom; }
    public int getScore() { return score; }
    public int getNbTwistLockwistLock() { return nbTwistLock; }

    public void setId(int id) { this.id = id; }
    public void setCouleur(String couleur) { this.couleur = couleur; }
    public void setNom(String nom) { this.nom = nom; }
    public void setScore(int score) { this.score = score; }
    public void setNbTwistLock(int nbTwistLock) { this.nbTwistLock = nbTwistLock; }

    // méthode enlevant un twist-lock si la personne fait une action invalide
    public void penalite(){
        setNbTwistLock(this.nbTwistLock-1);
    }
}
