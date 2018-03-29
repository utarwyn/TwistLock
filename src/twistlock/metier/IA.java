package twistlock.metier;

// à faire : prévoir l'action de chaque joueur


import java.util.HashMap;
import java.util.Map;

/**
 * Classe AIGestion qui gère les IA et leurs actions
 */
public class IA extends Joueur {

    private IACalcul aiPrincipal;
    private Metier metier;
    private Conteneur[][] jeu;
    private Conteneur contChoisi;
    private int meilleurCheminX,meilleurCheminY,pireCheminX,pireCheminY;
    private int nbDeplacements;

    private int[] deplacement = new int[nbDeplacements];

    public static void main(String[] args) {

        // Création du métier
        Metier m = new Metier(5,6);

        // Gestion de l'IA
        new IA(m, 1, "IA", 20);
    }

    /**
     * Constructeur de la classe AIGestion
     * @param m métier associé
	 * @param id identifiant du joueur lié
	 * @param nom Nom du joueur lié
	 * @param nbTwistLock Nombre de twistlock
     */
    public IA(Metier m, int id, String nom, int nbTwistLock)
    {
        super(id, nom, nbTwistLock);

        this.metier = m;
        this.jeu = m.getConteneurs();
        this.nbDeplacements = nbTwistLock;
        deplacement = new int[nbDeplacements];
        this.aiPrincipal = new IACalcul(jeu,0,0,3,3);

        System.out.println(this.metier.getRepresentationPlateau());

        contChoisi = null;

        Jouer();
    }

    /**
     * Permet d'instancier l'IA, chercher le meilleur chemin et effectuer l'action sur le plateau
     */
    public void Jouer()
    {
        if(RedefinirChemins())
        {
            while(!AppliquerAction())
            {
                System.out.println("Erreur : l'action ne peut pas etre appliquee");
                RedefinirChemins();
            }
        }
    }

    /**
     * Génération du meilleur et du pire chemin sur deux IA
     * permettant de simuler les déplacements
     */

    private void AfficherCheminAI()
    {
        IACalcul aiTest = new IACalcul(jeu,this.meilleurCheminX,this.meilleurCheminY,3,3);
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

        aiTest = new IACalcul(jeu,this.pireCheminX,this.pireCheminY,3,3);
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

                IACalcul aiTest = new IACalcul(jeu,i,j,3,3);

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

        this.aiPrincipal.setPosition(this.meilleurCheminX,this.meilleurCheminY);

        contChoisi = jeu[meilleurCheminX][meilleurCheminY];

        AfficherCheminAI();
        return true;
    }

    /**
     * Fonction qui valide l'action en appliquant l'action de l'IA sur le jeu
     * Et en effectuant les modifications nécessaires sur le plateau
     */

    private boolean AppliquerAction()
    {
        if(!this.peutJouer()){System.out.println("l'IA ne peut pas jouer"); return false;}
        if(this.contChoisi==null){System.out.println("conteneur null"); return false;}

        /*if(contChoisi.getProprietaire()==joueur)
        {
            System.out.println("L'IA est deja proprietaire");
            return true;
        }*/
        int coin = GetMeilleurCoin(contChoisi);

        System.out.println("\nJOUE " + this.contChoisi.getLigne() + this.contChoisi.getColonne() + coin);

        // Mouvement appliqué sur le plateau
        if(metier.getJoueurCourant()==this)
        {
            metier.jouerTwistlock(this.contChoisi, coin);
        }


        return true;
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

        if(conteneurActuel==null){return total;}

        for(int c=1;c<5;c++) {
            if(!conteneurActuel.estOccupe(c)) {
                voisins.put(conteneurActuel, c);
                voisins.putAll(this.metier.getVoisins(conteneurActuel, c));

                for (Map.Entry<Conteneur, Integer> conteneurEntry : voisins.entrySet())
                    total += conteneurEntry.getKey().getValeur();
                break;
            }
        }
        System.out.print(" voisins:"+total);
        return total;
    }

    private int GetMeilleurCoin(Conteneur c)
    {
        if(c==null){return 1;}
        int coin=1;

        int x=-1,y=-1;
        Conteneur[][] cont = metier.getConteneurs();
        Conteneur cSelect = null;

        for(int cpt=0;cpt<cont.length;cpt++)
        {
            for(int cpt2=0;cpt2<cont[cpt].length;cpt2++)
            {
                if(c==cont[cpt][cpt2])
                {
                    x=cpt;
                    y=cpt2;
                }
                if(cont[cpt][cpt2].getLigne()==this.contChoisi.getLigne() && cont[cpt][cpt2].getColonne()==this.contChoisi.getColonne())
                {
                    cSelect=cont[cpt][cpt2];
                }
            }
        }

        if(x==-1 || y==-1){return 1;}
        if(cSelect==null){return 1;}

        int[] xVoisin = new int[4];
        int[] yVoisin = new int[4];
        int[] pointsVoisin = new int[4];

        xVoisin[0] = x + 1; yVoisin[0] = y + 1;
        xVoisin[1] = x - 1; yVoisin[1] = y - 1;
        xVoisin[2] = x + 1; yVoisin[2] = y - 1;
        xVoisin[3] = x - 1; yVoisin[3] = y + 1;

        pointsVoisin[0] = GetTotalPointsConteneur(xVoisin[0], yVoisin[0]);
        pointsVoisin[1] = GetTotalPointsConteneur(xVoisin[1], yVoisin[1]);
        pointsVoisin[2] = GetTotalPointsConteneur(xVoisin[2], yVoisin[2]);
        pointsVoisin[3] = GetTotalPointsConteneur(xVoisin[3], yVoisin[3]);

        System.out.println("\nGain 1 : "+pointsVoisin[0]+" Gain 2 : "+pointsVoisin[1]+" Gain 3 : "+pointsVoisin[2]+" Gain 4 : "+pointsVoisin[3]);

        int selection=-1;

        while(selection==-1 || cSelect.estOccupe(selection)) {

            if(selection!=-1 && cSelect.estOccupe(selection))
            {
                pointsVoisin[0]=0;
                pointsVoisin[1]=0;
                pointsVoisin[2]=0;
                pointsVoisin[3]=0;
                selection++;
                if(selection>4){selection=1;}
            }

            if (pointsVoisin[0] > pointsVoisin[1] && pointsVoisin[0] > pointsVoisin[2] && pointsVoisin[0] > pointsVoisin[3]) {
                selection=1;
            } else if (pointsVoisin[1] > pointsVoisin[0] && pointsVoisin[1] > pointsVoisin[2] && pointsVoisin[1] > pointsVoisin[3]) {
                selection=2;
            } else if (pointsVoisin[2] > pointsVoisin[1] && pointsVoisin[2] > pointsVoisin[0] && pointsVoisin[2] > pointsVoisin[3]) {
                selection=3;
            } else {
                selection=4;
            }
        }

        return selection;
    }

}
