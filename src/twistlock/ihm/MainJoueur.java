package twistlock.ihm;

import twistlock.metier.Joueur;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Représente la main du joueur en IHM.
 * Il peut y en avoir jusqu'à 4 sur le terrain de jeu.
 */
class MainJoueur extends JPanel {

	private Joueur joueur;

	private JLabel nom;

	private JLabel locks;

	MainJoueur(Joueur joueur) {
		this.joueur = joueur;
		this.preparer();
	}

	/**
	 * Retourne le joueur à qui appartient la main
	 *
	 * @return Joueur concerné
	 */
	public Joueur getJoueur() {
		return this.joueur;
	}

	/**
	 * Prépare le panel IHM
	 */
	private void preparer() {
		JLabel image = new JLabel();
		this.nom = new JLabel();
		this.locks = new JLabel();

		ImageIcon icon;

		try {
			icon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/twistlock/ihm/img/bouee_" + this.joueur.getId() + ".png")));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		if (this.joueur.getId() > 2)
			icon = new ImageIcon(
					icon.getImage().getScaledInstance(65, 158, Image.SCALE_SMOOTH)
			);

		this.nom.setBorder(BorderFactory.createEmptyBorder(10, 8, 10, 10));
		this.nom.setFont(this.nom.getFont().deriveFont(Font.BOLD, 18));
		this.nom.setHorizontalAlignment(JLabel.CENTER);
		this.nom.setForeground(IHM.COULEURS[this.joueur.getId() - 1]);

		this.locks.setBorder(this.nom.getBorder());
		this.locks.setFont(this.nom.getFont());
		this.locks.setHorizontalAlignment(JLabel.CENTER);
		this.locks.setForeground(this.nom.getForeground());

		image.setIcon(icon);
		image.setHorizontalAlignment(JLabel.CENTER);

		this.setLayout(new BorderLayout());

		this.add(image, "North");
		this.add(this.nom, "Center");
		this.add(this.locks, "South");

		this.miseAJour(this.joueur.getId() == 1);
	}

	/**
	 * Mets à jour la main du joueur
	 *
	 * @param monTour Vrai si c'est au tour du joueur
	 */
	void miseAJour(boolean monTour) {
		if (monTour) {
			this.setBackground(new Color(99, 205, 218));
			this.setOpaque(true);
		} else {
			this.setBackground(null);
			this.setOpaque(false);
		}

		this.nom.setText(this.joueur.getNom() + "   " + this.joueur.getScore());
		this.locks.setText(this.joueur.getNbTwistlock() + " twistlocks");
	}

}
