public class Joueur {

    int id, score;
    int twist_lock = 20;
    String couleur, nom;

    public Joueur(int id, String couleur, String nom, int score, int twist_lock){
        this.id = id;
        this.couleur = couleur;
        this.nom = nom;
        this.score = score;
        this.twist_lock = twist_lock;
    }

    public int getId() { return id; }
    public String getCouleur() { return couleur; }
    public String getNom() { return nom; }
    public int getScore() { return score; }
    public int getTwist_lock() { return twist_lock; }

    public void setId(int id) { this.id = id; }
    public void setCouleur(String couleur) { this.couleur = couleur; }
    public void setNom(String nom) { this.nom = nom; }
    public void setScore(int score) { this.score = score; }
    public void setTwist_lock(int twist_lock) { this.twist_lock = twist_lock; }

    public void penalite(){
        setTwist_lock(this.twist_lock-1);
    }
}
