package twistlock.metier;

import twistlock.Controleur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe métier qui gère le coeur du jeu.
 */
public class Metier {

	private int nbLig, nbCol;

	private ArrayList<Joueur> joueurs;

	private Joueur joueurCourant;

	private Conteneur[][] conteneurs;

	private Controleur controleur;

	public Metier(Controleur controleur, int nbLig, int nbCol) {
		this.controleur = controleur;

		this.nbLig = nbLig;
		this.nbCol = nbCol;

		this.conteneurs = new Conteneur[this.nbLig][this.nbCol];
		this.joueurs = new ArrayList<>();

		this.initialiser();
	}

	/**
	 * Initialise la grille de jeu avec des conteneurs à valeur aléatoire
	 */
	private void initialiser() {
		// Création des conteneurs
		for (int lig = 0; lig < this.nbLig; lig++)
			for (char col = 0; col < this.nbCol; col++) {
				this.conteneurs[lig][col] = new Conteneur(lig + 1, (char) ('A' + col), (int) (Math.random() * (54 - 5) + 5));
			}
	}

	/**
	 * Retourne le nombre de lignes de la grille
	 *
	 * @return Nombre de lignes
	 */
	public int getNbLig() {
		return this.nbLig;
	}

	/**
	 * Retourne le nombre de colonnes de la grille
	 *
	 * @return Nombre de colonnes
	 */
	public int getNbCol() {
		return this.nbCol;
	}

	/**
	 * Retourne un conteneur à une position précise dans la grille
	 *
	 * @param lig Ligne où chercher le conteneur
	 * @param col Colonne où chercher le conteneur
	 * @return Le conteneur trouvé, null si aucun conteneur à la position
	 */
	public Conteneur getConteneur(int lig, int col) {
		if (lig < 0 || col < 0 || lig >= this.nbLig || col >= this.nbCol) return null;

		return this.conteneurs[lig][col];
	}

	/**
	 * Retourne les joueurs qui participent au jeu
	 *
	 * @return Liste des joueurs participants
	 */
	public ArrayList<Joueur> getJoueurs() {
		return this.joueurs;
	}

	/**
	 * Retourne le joueur courant
	 *
	 * @return Joueur jourant, null si aucun joueur n'est en train de jouer.
	 */
	public Joueur getJoueurCourant() {
		return this.joueurCourant;
	}

	/**
	 * Recalcule le score de tous les joueurs en fonction de leur possession de conteneurs dans la grille de jeu.
	 */
	private void recalculerScores() {
		// On réinitialise d'abord le score de tous les joueurs
		for (Joueur joueur : this.joueurs)
			joueur.resetScore();

		Conteneur conteneur;
		Joueur proprietaire;

		for (int lig = 0; lig < this.nbLig; lig++) {
			for (int col = 0; col < this.nbCol; col++) {
				conteneur = this.getConteneur(lig, col);
				proprietaire = conteneur.getProprietaire();

				if (proprietaire != null) {
					for (Joueur joueur : this.joueurs)
						if (joueur == proprietaire) {
							joueur.addScore(conteneur.getValeur());
							break;
						}
				}
			}
		}
	}

	/**
	 * Ajoute un joueur lié au jeu avec avec nom
	 *
	 * @param nom Nom du joueur
	 */
	public Joueur ajouterJoueur(String nom, int tL) {
		Joueur joueur = new Joueur(this.joueurs.size() + 1, nom, tL);

		this.joueurs.add(joueur);
		if (this.joueurCourant == null)
			this.joueurCourant = joueur;

		return joueur;
	}

	/**
	 * Ajoute un joueur lié au jeu avec avec nom
	 *
	 * @param nom Nom du joueur
	 */
	public Joueur ajouterAI(String nom, int tL) {
		//tL=1;
		Joueur joueur = new IA(this, this.joueurs.size() + 1, nom, tL);

		this.joueurs.add(joueur);
		if (this.joueurCourant == null)
			this.joueurCourant = joueur;

		return joueur;
	}

	/**
	 * Lance la partie
	 */
	public void lancerPartie() {
		// On test de faire jouer le premier joueur (IA)
		this.faireJouerIA();
	}

	/**
	 * Passe le tour au joueur suivant
	 *
	 * @return Vrai si le tour a pu être passé, faux si la partie est terminée.
	 */
	public boolean nouveauTour() {
		this.recalculerScores();

		// Suppression d'un twistlock pour le joueur courant
		if (this.joueurCourant != null) this.joueurCourant.retirerTwistlock();

		/* Vérifications de fin de partie:
		 *   - Aucun emplacement de Twistlock disponible
		 *   - Plus aucun joueur n'a de twistlock         */
		boolean fin1 = true, fin2 = true;
		Conteneur[][] conteneurs = this.conteneurs;

		for (Conteneur[] conteneur : conteneurs)
			for (Conteneur aConteneur : conteneur) if (!aConteneur.estEntoure()) fin1 = false;

		for (Joueur joueur : this.joueurs)
			if (joueur.peutJouer())
				fin2 = false;

		if (fin1 || fin2) {
			this.joueurCourant = null;
			return false;
		}

		// Détermination du prochain joueur (il doit avoir des twistlocks)
		do {

			this.prochainJoueur();

		} while (!this.joueurCourant.peutJouer());

		this.faireJouerIA();

		return true;
	}

	/**
	 * On teste de faire jouer le joueur courant si c'est une IA
	 */
	public void faireJouerIA() {
		// Tour de jeu pour un IA (si tel est le cas)
		if (this.getJoueurCourant() instanceof IA && this.controleur != null) {
			((IA) this.getJoueurCourant()).Jouer();
			this.controleur.miseAJourIHM();

			new Thread(() -> {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (!this.nouveauTour())
					this.controleur.fermerIHM();
			}).start();
		}
	}

	/**
	 * Passe la main au prochain joueur
	 */
	private void prochainJoueur() {
		this.joueurCourant = this.joueurs.get(this.joueurCourant.getId() % this.joueurs.size());
	}

	/**
	 * Joue un twistlock pour un joueur à une position donnée
	 *
	 * @param conteneur Conteneur lié au twistlock à poser
	 * @param coin      Coin dans lequel poser le twistlock
	 * @return Vrai si le twistlock à pu être posé.
	 */
	public boolean jouerTwistlock(Conteneur conteneur, int coin) {
		Map<Conteneur, Integer> conteneurs = new HashMap<>();
		Joueur joueur = this.joueurCourant;

		// Il y a déjà un twistlock où le joueur souhaite jouer
		if (conteneur.estOccupe(coin)) {
			joueur.retirerTwistlock();
			return false;
		}

		// Création du twistlock
		TwistLock twistlock = new TwistLock(joueur);

		conteneurs.put(conteneur, coin);
		conteneurs.putAll(this.getVoisins(conteneur, coin));

		for (Map.Entry<Conteneur, Integer> conteneurEntry : conteneurs.entrySet())
			conteneurEntry.getKey().getCoins()[conteneurEntry.getValue() - 1] = twistlock;

		return true;
	}

	/**
	 * Récupère le joueur qui possède le plus de score
	 *
	 * @return Joueur gagnant
	 */
	public Joueur getGagnant() {
		int max = 0;
		Joueur joueur = null;

		for (Joueur j : this.joueurs)
			if (j.getScore() > max) {
				max = j.getScore();
				joueur = j;
			}

		return joueur;
	}

	/**
	 * Calculer les scores des joueurs dans l'ordre
	 *
	 * @return Classement des scores des joueurs
	 */
	public String getClassement() {
		ArrayList<Joueur> arrayList = new ArrayList<>(this.joueurs);
		arrayList.sort((o1, o2) -> o2.getScore() - o1.getScore());

		StringBuilder scores = new StringBuilder("Classement :\n\n ");

		for (int i = 0; i < arrayList.size(); i++) {
			Joueur joueur = arrayList.get(i);
			scores.append(i + 1).append(" - ")
					.append(joueur.getNom()).append(" : ")
					.append(joueur.getScore()).append("\n");
		}

		return scores.toString();
	}

	/**
	 * Retourne la présentation en chaîne du plateau
	 *
	 * @return Plateau en chaîne
	 */
	public String getRepresentationPlateau() {
		StringBuilder sb = new StringBuilder();

		sb.append("MAP=");

		for (Conteneur[] conteneur : this.conteneurs) {
			for (Conteneur aConteneur : conteneur) sb.append(String.format("%02d", aConteneur.getValeur())).append(":");

			sb.deleteCharAt(sb.length() - 1);

			sb.append("|");
		}

		return sb.toString();
	}

	/**
	 * Retourne les voisins d'un conteneur lié par un coin commun
	 *
	 * @param origine Conteneur d'origine pour récupérer les voisins
	 * @param coin    Coin de liaison
	 * @return Tableau associatif des voisins (avec les coins de liaison)
	 */
	public HashMap<Conteneur, Integer> getVoisins(Conteneur origine, int coin) {
		HashMap<Conteneur, Integer> voisins = new HashMap<>();
		Conteneur voisin;

		int lig = origine.getLigne() - 1;
		int col = origine.getColonne() - 'A';

		switch (coin) {
			case 1:
				if ((voisin = this.getConteneur(lig - 1, col)) != null)
					voisins.put(voisin, 4);
				if ((voisin = this.getConteneur(lig, col - 1)) != null)
					voisins.put(voisin, 2);
				if ((voisin = this.getConteneur(lig - 1, col - 1)) != null)
					voisins.put(voisin, 3);

				break;
			case 2:
				if ((voisin = this.getConteneur(lig - 1, col)) != null)
					voisins.put(voisin, 3);
				if ((voisin = this.getConteneur(lig, col + 1)) != null)
					voisins.put(voisin, 1);
				if ((voisin = this.getConteneur(lig - 1, col + 1)) != null)
					voisins.put(voisin, 4);

				break;
			case 3:
				if ((voisin = this.getConteneur(lig + 1, col)) != null)
					voisins.put(voisin, 2);
				if ((voisin = this.getConteneur(lig, col + 1)) != null)
					voisins.put(voisin, 4);
				if ((voisin = this.getConteneur(lig + 1, col + 1)) != null)
					voisins.put(voisin, 1);

				break;
			case 4:
				if ((voisin = this.getConteneur(lig + 1, col)) != null)
					voisins.put(voisin, 1);
				if ((voisin = this.getConteneur(lig, col - 1)) != null)
					voisins.put(voisin, 3);
				if ((voisin = this.getConteneur(lig + 1, col - 1)) != null)
					voisins.put(voisin, 2);

				break;
		}

		return voisins;
	}

	/**
	 * Retourne la liste des conteneurs
	 *
	 * @return La liste des conteneurs
	 */
	public Conteneur[][] getConteneurs() {
		return conteneurs;
	}
	
}
