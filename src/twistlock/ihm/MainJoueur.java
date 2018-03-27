package twistlock.ihm;

import twistlock.metier.Joueur;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

class MainJoueur extends JPanel {

	private Joueur joueur;

	MainJoueur(Joueur joueur) {
		this.joueur = joueur;
		this.preparer();
	}

	private void preparer() {
		JLabel image = new JLabel();
		JLabel nom = new JLabel();

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

		nom.setText(this.joueur.getNom() + "   " + this.joueur.getNbTwistlock());
		nom.setBorder(BorderFactory.createEmptyBorder(10, 8, 10, 10));
		nom.setFont(nom.getFont().deriveFont(Font.BOLD, 18));
		nom.setHorizontalAlignment(JLabel.CENTER);
		nom.setForeground(IHM.COULEURS[this.joueur.getId() - 1]);

		image.setIcon(icon);
		image.setHorizontalAlignment(JLabel.CENTER);

		this.setLayout(new BorderLayout());
		this.add(image, "North");
		this.add(nom, "Center");
	}

}
