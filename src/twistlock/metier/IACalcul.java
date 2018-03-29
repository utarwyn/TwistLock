package twistlock.metier;

/**
 * Classe AI permettant d'instancier une IA
 */

public class IACalcul {

    private Conteneur[][] jeu;
    private int nextI;
    private int nextJ;
    private int nextIAdversaire;
    private int nextJAdversaire;
    private int lastCaseId;
    private Conteneur[] lastCases;

    /**
     * Constructeur de la classe AIGestion
     * @param jeu tableau de conteneurs symbolisant le plateau de jeu
     * @param departX position X de départ de l'IA
     * @param departY position Y de départ de l'IA
     * @param positionAdversaireX position X actuelle de l'adversaire
     * @param positionAdversaireY position Y actuelle de l'adversaire
     */

    IACalcul(Conteneur[][] jeu, int departX, int departY, int positionAdversaireX, int positionAdversaireY)
    {
        this.jeu = jeu;
        this.lastCases = new Conteneur[6];
        this.lastCaseId = 0;
        this.nextI = departX;
        this.nextJ = departY;

        // position de l'adversaire
        this.nextIAdversaire = positionAdversaireX;
        this.nextJAdversaire = positionAdversaireY;

        for(int i=0;i<this.lastCases.length;i++)
        {
            this.lastCases[i]=null;
        }
    }

    /**
     * Obtenir la position X actuelle de l'IA
     * @return Position X de l'IA
     */
    int getX(){ if(this.nextI>=jeu.length){this.nextI=jeu.length-1;} return this.nextI; }

    /**
     * Obtenir la position Y actuelle de l'IA
     * @return Position Y de l'IA
     */
    int getY(){ if(this.nextJ>=jeu.length){this.nextJ=jeu.length-1;} return this.nextJ; }


    /**
     * Obtenir la position X actuelle de l'adversaire
     * @return Position X de l'adversaire
     */
    public int getXAdverse(){ return this.nextIAdversaire; }

    /**
     * Obtenir la position Y actuelle de l'adversaire
     * @return Position Y de l'adversaire
     */
    public int getYAdverse(){ return this.nextJAdversaire; }


    /**
     * Obtenir la plus grande valeur possible autour de la position actuelle
     * @param i position X actuelle de l'IA
     * @param j position Y actuelle de l'IA
     * @return plus grande valeur possible autour de la position actuelle
     */
    int getMax(int i, int j)
    {
        Conteneur[] val = new Conteneur[8];

        if(i+1<this.jeu.length){val[0] = this.jeu[i+1][j];} else{val[0]=null;}
        if(i-1>=0){val[1] = this.jeu[i-1][j];} else{val[1]=null;}
        if(j+1<this.jeu.length){val[2] = this.jeu[i][j+1];} else{val[2]=null;}
        if(j-1>=0){val[3] = this.jeu[i][j-1];} else{val[3]=null;}

        // diagonales
        if(i+1<this.jeu.length && j+1<this.jeu.length){val[4] = this.jeu[i+1][j+1];} else{val[4]=null;}
        if(i-1>=0 && j-1>=0){val[5] = this.jeu[i-1][j-1];} else{val[5]=null;}

        if(i+1<this.jeu.length && j-1>=0){val[6] = this.jeu[i+1][j-1];} else{val[6]=null;}
        if(i-1>=0 && j+1<this.jeu.length){val[7] = this.jeu[i-1][j+1];} else{val[7]=null;}

        int min=-1;

        while(min==-1)
        {
            if(cVal(val[0])>cVal(val[1]) && cVal(val[0])>=cVal(val[2]) && cVal(val[0])>=cVal(val[3]) && cVal(val[0])>=cVal(val[4]) && cVal(val[0])>=cVal(val[5]) && cVal(val[0])>=cVal(val[6]) && cVal(val[0])>cVal(val[7]) && !dejaUtilise(val[0])) { min=0; }
            else if(cVal(val[1])>cVal(val[0]) && cVal(val[1])>=cVal(val[2]) && cVal(val[1])>=cVal(val[3]) && cVal(val[1])>=cVal(val[5]) && cVal(val[1])>=cVal(val[6]) && cVal(val[1])>cVal(val[7]) && !dejaUtilise(val[1])) { min=1; }
            else if(cVal(val[2])>cVal(val[1]) && cVal(val[2])>=cVal(val[0]) && cVal(val[2])>=cVal(val[3]) && cVal(val[2])>=cVal(val[5]) && cVal(val[2])>=cVal(val[6]) && cVal(val[2])>cVal(val[7]) && !dejaUtilise(val[2])) { min=2; }
            else if(cVal(val[3])>cVal(val[1]) && cVal(val[3])>=cVal(val[2]) && cVal(val[3])>=cVal(val[0]) && cVal(val[3])>=cVal(val[5]) && cVal(val[3])>=cVal(val[6]) && cVal(val[3])>cVal(val[7]) && !dejaUtilise(val[3])) { min=3; }
            // diagonales
            else if(cVal(val[4])>cVal(val[1]) && cVal(val[4])>=cVal(val[2]) && cVal(val[4])>=cVal(val[3]) && cVal(val[4])>=cVal(val[0]) && cVal(val[4])>=cVal(val[5]) && cVal(val[4])>=cVal(val[6]) && cVal(val[4])>=cVal(val[7]) && !dejaUtilise(val[4])) { min=4; }
            else if(cVal(val[5])>cVal(val[1]) && cVal(val[5])>=cVal(val[2]) && cVal(val[5])>=cVal(val[3]) && cVal(val[5])>=cVal(val[4]) && cVal(val[5])>=cVal(val[0]) && cVal(val[5])>=cVal(val[6]) && cVal(val[5])>=cVal(val[7]) && !dejaUtilise(val[5])) { min=5; }
            else if(cVal(val[6])>cVal(val[1]) && cVal(val[6])>=cVal(val[2]) && cVal(val[6])>=cVal(val[3]) && cVal(val[6])>=cVal(val[4]) && cVal(val[6])>=cVal(val[5]) && cVal(val[6])>=cVal(val[0]) && cVal(val[6])>=cVal(val[7]) && !dejaUtilise(val[6])) { min=6; }
            else if(cVal(val[7])>cVal(val[1]) && cVal(val[7])>=cVal(val[2]) && cVal(val[7])>=cVal(val[3]) && cVal(val[7])>=cVal(val[4]) && cVal(val[7])>=cVal(val[5]) && cVal(val[7])>=cVal(val[6]) && cVal(val[7])>=cVal(val[0]) && !dejaUtilise(val[7])) { min=7; }

            if(min==-1)
            {
                for(int adv=0;adv<val.length;adv++)
                {
                    if(dejaUtilise(val[adv])){val[adv]=null;}
                }

                min = getIndexMin(i,j,val);
            }

        }

        if(min==0){this.nextI = i+1; this.nextJ = j;}
        else if(min==1){this.nextI = i-1; this.nextJ = j;}
        else if(min==2){this.nextI = i; this.nextJ = j+1;}
        else if(min==3){this.nextI = i; this.nextJ = j-1;}
        else if(min==4){this.nextI = i+1; this.nextJ = j+1;}
        else if(min==5){this.nextI = i-1; this.nextJ = j-1;}
        else if(min==6){this.nextI = i+1; this.nextJ = j-1;}
        else if(min==7){this.nextI = i-1; this.nextJ = j+1;}

        this.lastCases[this.lastCaseId]=jeu[i][j];
        this.lastCaseId++;
        if(this.lastCaseId>=this.lastCases.length){this.lastCaseId=0;}

        if(val[min]==null)
        {
            for(int v=0;v<val.length;v++)
            {
                if(val[v]!=null){min=v; break;}
            }
        }

        if(val[min]==null)
        {return -1;}

        return val[min].getValeur();
    }

    /**
     * Permet d'actualiser la position de l'IA
     * @param x Coordonnée X de l'IA
     * @param y Coordonnée Y de l'IA
     */

    void setPosition(int x, int y)
    {
        this.nextI=x;
        this.nextJ=y;
    }

    /**
     * Obtenir la valeur du conteneur en gérant la valeur null
     * @param c Conteneur
     * @return Valeur du conteneur
     */

    private int cVal(Conteneur c)
    {
        if(c==null){return -1;}
        return c.getValeur();
    }

    /**
     * Obtenir l'index du conteneur possédant la valeur la plus haute
     * @param i position X actuelle de l'IA
     * @param j position Y actuelle de l'IA
     * @param val conteneurs à comparer
     * @return l'index du conteneur possédant la valeur la plus haute
     */

    private int getIndexMin(int i, int j, Conteneur[] val)
    {
        int min=0;
        if(val[0]==null && val[1]==null && val[2]==null && val[3]==null && val[4]==null && val[5]==null && val[6]==null && val[7]==null)
        {
            if(j-1>=0){min=3;}
            else if(j+1<this.jeu.length){min=2;}
            else if(i-1>=0){min=1;}
        }
        else
        {
            if(cVal(val[0])>cVal(val[1]) && cVal(val[0])>cVal(val[2]) && cVal(val[0])>cVal(val[3]) && cVal(val[0])>cVal(val[4]) && cVal(val[0])>cVal(val[5]) && cVal(val[0])>cVal(val[6]) && cVal(val[0])>cVal(val[7])) { min=0; }
            else if(cVal(val[1])>cVal(val[0]) && cVal(val[1])>cVal(val[2]) && cVal(val[1])>cVal(val[3]) && cVal(val[1])>cVal(val[5]) && cVal(val[1])>cVal(val[6]) && cVal(val[1])>cVal(val[7])) { min=1; }
            else if(cVal(val[2])>cVal(val[1]) && cVal(val[2])>cVal(val[0]) && cVal(val[2])>cVal(val[3]) && cVal(val[2])>cVal(val[5]) && cVal(val[2])>cVal(val[6]) && cVal(val[2])>cVal(val[7])) { min=2; }
            else if(cVal(val[3])>cVal(val[1]) && cVal(val[3])>cVal(val[2]) && cVal(val[3])>cVal(val[0]) && cVal(val[3])>cVal(val[5]) && cVal(val[3])>cVal(val[6]) && cVal(val[3])>cVal(val[7])) { min=3; }
            // diagonales
            else if(cVal(val[4])>cVal(val[1]) && cVal(val[4])>cVal(val[2]) && cVal(val[4])>cVal(val[3]) && cVal(val[4])>cVal(val[0]) && cVal(val[4])>cVal(val[5]) && cVal(val[4])>cVal(val[6]) && cVal(val[4])>cVal(val[7])) { min=4; }
            else if(cVal(val[5])>cVal(val[1]) && cVal(val[5])>cVal(val[2]) && cVal(val[5])>cVal(val[3]) && cVal(val[5])>cVal(val[4]) && cVal(val[5])>cVal(val[0]) && cVal(val[5])>cVal(val[6]) && cVal(val[5])>cVal(val[7])) { min=5; }
            else if(cVal(val[6])>cVal(val[1]) && cVal(val[6])>cVal(val[2]) && cVal(val[6])>cVal(val[3]) && cVal(val[6])>cVal(val[4]) && cVal(val[6])>cVal(val[5]) && cVal(val[6])>cVal(val[0]) && cVal(val[6])>cVal(val[7])) { min=6; }
            else if(cVal(val[7])>cVal(val[1]) && cVal(val[7])>cVal(val[2]) && cVal(val[7])>cVal(val[3]) && cVal(val[7])>cVal(val[4]) && cVal(val[7])>cVal(val[5]) && cVal(val[7])>cVal(val[6]) && cVal(val[7])>cVal(val[0])) { min=7; }
        }
        return min;
    }

    /**
     * Vérifie si un conteneur a déjà été précédemment utilisé dans le chemin
     * pour éviter de repasser par ce conteneur
     * @param val conteneur
     * @return vrai si le conteneur a déjà été utilisé
     */


    private boolean dejaUtilise(Conteneur val)
    {
        for(Conteneur c : this.lastCases)
        {
            if(c!=null && c==val){return true;}
        }
        return false;
    }
}
