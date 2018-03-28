package twistlock.ihm;

import twistlock.Controleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Formulaire pour donner : - le nombre de conteneurs (lignes et colonnes) - le nombre de twistlocks de chaque joueurs au départ - le nom de chaque
 * joueurs
 */
public class FormJoueur extends JFrame {

	private Controleur controleur;

	private JLabel jLabelTitre;
	private JLabel jLabelNbLignes;
	private JLabel jLabelNbColonnes;
	private JLabel jLabelNbTL;
	private JLabel jLabelJ1;
	private JLabel jLabelJ2;
	private JLabel jLabelJ3;
	private JLabel jLabelJ4;
	private JTextField textFieldNbLignes;
	private JTextField textFieldNbColonnes;
	private JTextField textFieldNbTL;
	private JTextField textFieldJ1;
	private JTextField textFieldJ2;
	private JTextField textFieldJ3;
	private JTextField textFieldJ4;
	private JButton buttonQuitter, buttonValider;
	private int nbJoueurs;
	private int lignes;
	private int colonnes;
	private int nbTwistlocks;
	private ArrayList<String> nomJoueurs;

	/**
	 * Classe Formulaire de joueur
	 *
	 * @param controleur de l'application
	 */
	FormJoueur( Controleur controleur ) {
		this.controleur = controleur;

		this.setTitle("Jeu des Twistlocks - Ajouter des Joueurs");
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setSize(1200, 800);

		//prépare les éléments graphique
		preparer();

		//ajoute les élments graphiques
		remplir();

		this.setLocationRelativeTo(null);

		this.setVisible(true);
	}

	/**
	 * Prépare les éléments graphiques
	 */
	private void preparer() {

		jLabelTitre = new JLabel("Configurez votre partie");

		jLabelNbLignes = new JLabel("Nombre de lignes : ");
		jLabelNbColonnes = new JLabel("Nombre de colonnes : ");
		jLabelNbTL = new JLabel("Nombre de twistlocks : ");

		jLabelJ1 = new JLabel("Nom du joueur 1 : ");
		jLabelJ2 = new JLabel("Nom du joueur 2 : ");
		jLabelJ3 = new JLabel("Nom du joueur 3 : ");
		jLabelJ4 = new JLabel("Nom du joueur 4 : ");

		textFieldNbLignes = new JTextField();
		textFieldNbLignes.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char caracter = e.getKeyChar();
				if (((caracter < '1') || (caracter > '9')) && (caracter != '\b') || textFieldNbLignes.getText().length() > 0) {
					e.consume();
				}
			}
		});

		textFieldNbColonnes = new JTextField();
		textFieldNbColonnes.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char caracter = e.getKeyChar();
				if (((caracter < '1') || (caracter > '9')) && (caracter != '\b') || textFieldNbColonnes.getText().length() > 0) {
					e.consume();
				}
			}
		});

		textFieldNbTL = new JTextField();
		textFieldNbTL.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char caracter = e.getKeyChar();
				if (((caracter < '0') || (caracter > '9')) && (caracter != '\b')) {
					e.consume();
				}
			}
		});

		textFieldJ1 = new JTextField();
		textFieldJ2 = new JTextField();
		textFieldJ3 = new JTextField();
		textFieldJ4 = new JTextField();

		textFieldNbLignes.setColumns(20);
		textFieldNbColonnes.setColumns(20);
		textFieldNbTL.setColumns(20);
		textFieldJ1.setColumns(20);
		textFieldJ2.setColumns(20);
		textFieldJ3.setColumns(20);
		textFieldJ4.setColumns(20);

		//pour quitter
		buttonQuitter = new JButton("Quitter");
		buttonQuitter.addActionListener(e -> System.exit(0));

		//pour passer au jeu
		buttonValider = new JButton("Valider");
		buttonValider.addActionListener(e -> {
					if (verification()) {

						nomJoueurs = new ArrayList<>();

						nbJoueurs = 2;
						nomJoueurs.add(textFieldJ1.getText());
						nomJoueurs.add(textFieldJ2.getText());

						if (!textFieldJ3.getText().equals("")) {
							nomJoueurs.add(textFieldJ3.getText());
							nbJoueurs = 3;
						}

						if (!textFieldJ4.getText().equals("")) {
							nbJoueurs = 4;
							nomJoueurs.add(textFieldJ4.getText());
						}

						lignes = Integer.parseInt(textFieldNbLignes.getText());
						colonnes = Integer.parseInt(textFieldNbColonnes.getText());
						nbTwistlocks = Integer.parseInt(textFieldNbTL.getText());

						//lancer la nouvelle fanêtre avec le plateau de jeu
						lancer();
					}
				}
		);
	}

	/**
	 * Remplie l'interface graphique et met en forme les éléments
	 */
	private void remplir() {
		this.setLayout(new GridBagLayout());

		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		gridBagConstraints.insets = new Insets(10, 0, 10, 0);
		gridBagConstraints.anchor = GridBagConstraints.CENTER;

		gridBagConstraints.gridwidth = 2;
		this.add(jLabelTitre, gridBagConstraints);

		gridBagConstraints.gridwidth = 1;

		gridBagConstraints.anchor = GridBagConstraints.LINE_END;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridx = 0;
		this.add(jLabelNbLignes, gridBagConstraints);

		gridBagConstraints.anchor = GridBagConstraints.LINE_START;
		gridBagConstraints.gridx = 1;
		this.add(textFieldNbLignes, gridBagConstraints);

		gridBagConstraints.anchor = GridBagConstraints.LINE_END;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridx = 0;
		this.add(jLabelNbColonnes, gridBagConstraints);

		gridBagConstraints.anchor = GridBagConstraints.LINE_START;
		gridBagConstraints.gridx = 1;
		this.add(textFieldNbColonnes, gridBagConstraints);

		gridBagConstraints.anchor = GridBagConstraints.LINE_END;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridx = 0;
		this.add(jLabelNbTL, gridBagConstraints);

		gridBagConstraints.anchor = GridBagConstraints.LINE_START;
		gridBagConstraints.gridx = 1;
		this.add(textFieldNbTL, gridBagConstraints);

		gridBagConstraints.anchor = GridBagConstraints.LINE_END;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.gridx = 0;
		this.add(jLabelJ1, gridBagConstraints);

		gridBagConstraints.anchor = GridBagConstraints.LINE_START;
		gridBagConstraints.gridx = 1;
		this.add(textFieldJ1, gridBagConstraints);

		gridBagConstraints.anchor = GridBagConstraints.LINE_END;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.gridx = 0;
		this.add(jLabelJ2, gridBagConstraints);

		gridBagConstraints.anchor = GridBagConstraints.LINE_START;
		gridBagConstraints.gridx = 1;
		this.add(textFieldJ2, gridBagConstraints);

		gridBagConstraints.anchor = GridBagConstraints.LINE_END;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.gridx = 0;
		this.add(jLabelJ3, gridBagConstraints);

		gridBagConstraints.anchor = GridBagConstraints.LINE_START;
		gridBagConstraints.gridx = 1;
		this.add(textFieldJ3, gridBagConstraints);

		gridBagConstraints.anchor = GridBagConstraints.LINE_END;
		gridBagConstraints.gridy = 7;
		gridBagConstraints.gridx = 0;
		this.add(jLabelJ4, gridBagConstraints);

		gridBagConstraints.anchor = GridBagConstraints.LINE_START;
		gridBagConstraints.gridx = 1;
		this.add(textFieldJ4, gridBagConstraints);

		gridBagConstraints.anchor = GridBagConstraints.CENTER;
		gridBagConstraints.gridy = 8;
		gridBagConstraints.gridx = 0;
		this.add(buttonQuitter, gridBagConstraints);

		gridBagConstraints.gridx = 1;
		this.add(buttonValider, gridBagConstraints);
	}

	/**
	 * Lance le jeu et ouvre la fenêtre avec le plateau du jeu
	 * Ajoute les joueurs
	 */
	private void lancer() {
		controleur.chargerMetier(lignes, colonnes);
		for (int i = 0; i < nbJoueurs; i++) {
			controleur.ajouterJoueur(nomJoueurs.get(i), nbTwistlocks);
		}
		controleur.chargerIHM();
		this.dispose();
	}

	/**
	 * Vérifie les donées saisies et affiche des messages d'erreur en cas d'erreur
	 *
	 * @return si les données sont valides
	 */
	private boolean verification() {
		boolean bOk = true;

		//nombre de lignes
		if (textFieldNbLignes.getText().equals("")) {
			bOk = false;
			erreur("Saississez un nombre de lignes entre 1 et 9");
		} else

			try {
				Integer.parseInt(textFieldNbLignes.getText());
			} catch (Exception e) {
				bOk = false;
				erreur("Saississez un nombre de lignes entre 1 et 9");
			}

		//nombre de colonnes
		if (textFieldNbColonnes.getText().equals("")) {
			bOk = false;
			erreur("Saississez un nombre de colonnes entre 1 et 9");
		} else

			try {
				Integer.parseInt(textFieldNbColonnes.getText());
			} catch (Exception e) {
				bOk = false;
				erreur("Saississez un nombre de colonnes entre 1 et 9");
			}

		//nombre de twistlocks
		if (textFieldNbTL.getText().equals("")) {
			bOk = false;
			erreur("Saississez un nombre de Twistlocks");
		} else

			try {
				Integer.parseInt(textFieldNbTL.getText());
			} catch (Exception e) {
				bOk = false;
				erreur("Saississez un nombre de Twistlocks");
			}

		//nom joueur 1 vide
		if (textFieldJ1.getText().equals("")) {
			bOk = false;
			erreur("Saississez le nom du joueur 1");
		}

		//nom joueur 2 vide
		if (textFieldJ2.getText().equals("")) {
			bOk = false;
			erreur("Saississez le nom du joueur 2");
		}

		//nom joueur 4 remplie mais pas le joueur 3
		if (!textFieldJ4.getText().equals("") && textFieldJ3.getText().equals("")) {
			bOk = false;
			erreur("Saisissez le nom du joueur 3");
		}

		//noms de joueurs égaux
		if (textFieldJ1.getText().equals(textFieldJ2.getText()) || textFieldJ1.getText().equals(textFieldJ3.getText()) ||
				textFieldJ1.getText().equals(textFieldJ4.getText()) || textFieldJ2.getText().equals(textFieldJ3.getText()) ||
				textFieldJ2.getText().equals(textFieldJ4.getText()) ||
				!textFieldJ3.getText().equals("") && textFieldJ3.getText().equals(textFieldJ4.getText())) {
			bOk = false;
			erreur("Noms de joueurs identiques");
		}

		return bOk;
	}

	/**
	 * fenêtre d'erreur
	 *
	 * @param message a afficher dans la fenêtre d'erreur
	 */
	private void erreur(String message) {
		JOptionPane.showMessageDialog(null, message);
	}
}
