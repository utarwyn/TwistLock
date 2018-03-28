package twistlock.metier;

// état du plateau
// prévoir l'action de chaque joueur
// vérifier que le coup est possible

// estOccupe(int nb) (coins)

import java.util.HashMap;
import java.util.Map;

/**
 * Classe AIGestion qui gère les IA et leurs actions
 */

public class AIGestion {

    private AI aiTest;
    private Joueur joueur;
    private Metier metier;
    private Conteneur[][] jeu;
    private Conteneur contChoisi;
    private int meilleurCheminX,meilleurCheminY,pireCheminX,pireCheminY;
    private static int nbDeplacements = 20;
    private static int[] deplacement = new int[nbDeplacements];

    public static void main(String[] args) {

        // Création du métier
        Metier m = new Metier(5,6);

        // Gestion de l'IA
        new AIGestion(m);
    }

    /**
     * Constructeur de la classe AIGestion
     * @param m métier associé
     */

    private AIGestion(Metier m)
    {
        this.metier=m;
        this.joueur = new Joueur(0,"AI",20);
        this.jeu = m.getConteneurs();
        this.aiTest = new AI(jeu,0,0,3,3);

        contChoisi = null;

        // action à répéter à chaque tour pour redéfinir le chemin de l'IA
        if(RedefinirChemins())
        {
            while(!AppliquerAction())
            {
                RedefinirChemins();
            }
        }
    }

    /**
     * Génération du meilleur et du pire chemin sur deux IA
     * permettant de simuler les déplacements
     */

    private boolean GenererCheminAI()
    {
        AI aiTest = new AI(jeu,this.meilleurCheminX,this.meilleurCheminY,3,3);
        System.out.print("MEILLEUR CHEMIN ["+this.meilleurCheminX+";"+this.meilleurCheminY+"] --> ");
        int total=0;
        for (int pas = 0; pas < nbDeplacements; pas++) {
            deplacement[pas] = aiTest.getMax(aiTest.getX(), aiTest.getY());

            total+=deplacement[pas];
        }

        for (int pas = 0; pas < nbDeplacements; pas++) {
            System.out.print(deplacement[pas] + " --> ");
        }

        System.out.print(" total : " + total);

        System.out.println();

        aiTest = new AI(jeu,this.pireCheminX,this.pireCheminY,3,3);
        System.out.print("PIRE CHEMIN ["+this.pireCheminX+";"+this.pireCheminY+"] --> ");
        total=0;
        for (int pas = 0; pas < nbDeplacements; pas++) {
            deplacement[pas] = aiTest.getMax(aiTest.getX(), aiTest.getY());

            total+=deplacement[pas];
        }

        for (int pas = 0; pas < nbDeplacements; pas++) {
            System.out.print(deplacement[pas] + " --> ");
        }

        System.out.print(" total : " + total);

        return true;
    }

    /**
     * Redéfinition du meilleur et du pire chemin
     * Le meilleur chemin étant celui permettant de rapporter le plus de points
     * Et le pire étant celui rapportant le moins de points
     */

    private boolean RedefinirChemins()
    {

        int total;
        int minimum;
        int meilleurCheminX = 0;
        int meilleurCheminY = 0;

        int meilleurCoup = 0;

        int lastMinimum=9999;
        int pireCheminX = 0;
        int pireCheminY = 0;

        System.out.println("-------");

        for(int i=0;i<jeu.length;i++) {
            for(int j=0;j<jeu.length;j++) {

                AI aiTest = new AI(jeu,i,j,3,3);

                System.out.print("CHEMIN ["+i+";"+j+"] --> ");
                total=0;

                for (int pas = 0; pas < nbDeplacements; pas++) {
                    deplacement[pas] = aiTest.getMax(aiTest.getX(), aiTest.getY());

                    total+=deplacement[pas];
                }

                for (int pas = 0; pas < nbDeplacements; pas++) {
                    System.out.print(deplacement[pas] + " --> ");
                }
                /*System.out.println();
                for (int pas2 = 0; pas2 < nbDeplacements; pas2++) {
                    System.out.print(deplacement[1][pas2] + " --> ");
                }*/

                System.out.print(" total : " + total);

                minimum = total;

                if(GetTotalPointsConteneur(i,j)>meilleurCoup)
                {
                    meilleurCoup = GetTotalPointsConteneur(i,j);
                    meilleurCheminX=i;
                    meilleurCheminY=j;
                }
                else
                {
                    if(minimum<lastMinimum){lastMinimum=minimum; pireCheminX=i; pireCheminY=j;}
                }

                //if(total>lastTotal){lastTotal=total; meilleurCheminX=i; meilleurCheminY=j;}
                //if(minimum<lastMinimum){lastMinimum=minimum; pireCheminX=i; pireCheminY=j;}

                System.out.println();
            }
        }

        this.meilleurCheminX=meilleurCheminX;
        this.meilleurCheminY=meilleurCheminY;
        this.pireCheminX=pireCheminX;
        this.pireCheminY=pireCheminY;

        this.aiTest.ChangerMeilleurChemin(this.meilleurCheminX,this.meilleurCheminY);

        contChoisi = jeu[meilleurCheminX][meilleurCheminY];

        return GenererCheminAI();
    }

    /**
     * Fonction qui valide l'action en appliquant l'action de l'IA sur le jeu
     * Et en effectuant les modifications nécessaires sur le plateau
     */

    private boolean AppliquerAction()
    {
        if(!joueur.peutJouer()){System.out.println("l'IA ne peut pas jouer"); return false;}
        if(this.contChoisi==null){System.out.println("conteneur null"); return false;}
        else
        {
            if(contChoisi.getProprietaire()==joueur)
            {
                System.out.println("L'IA est deja proprietaire");
                return true;
            }
            for (int i = 1; i < 5; i++) {
                if (!this.contChoisi.estOccupe(i)) {
                    // appliquer l'action du joueur sur le plateau
                    //
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Calcule le nombre de points que rapporterait la capture d'un conteneur
     * @param i position X du conteneur
     * @param j position Y du conteneur
     * @return le total de points que rapporterait le conteneur
     */
    private int GetTotalPointsConteneur(int i, int j)
    {
        int total=0;
        HashMap<Conteneur, Integer> voisins = new HashMap<>();
        Conteneur conteneurActuel = this.metier.getConteneur(i,j);

        for(int c=1;c<5;c++) {
            if(!conteneurActuel.estOccupe(c)) {
                voisins.put(conteneurActuel, c);
                voisins.putAll(this.metier.getVoisins(conteneurActuel, c));

                for (Map.Entry<Conteneur, Integer> conteneurEntry : voisins.entrySet())
                    total += conteneurEntry.getKey().getValeur();
                break;
            }
        }
        System.out.print(" pts:"+total);
        return total;
    }

}
