package twistlock.ihm;

import twistlock.Controleur;
import twistlock.metier.Conteneur;
import twistlock.metier.TwistLock;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Représente la grille de jeu dans l'IHM.
 */
class Plateau extends JPanel {

	private IHM ihm;

	private Controleur controleur;

	Plateau(IHM ihm, Controleur controleur) {
		super(true);

		this.ihm = ihm;
		this.controleur = controleur;
	}

	void preparer(boolean focus) {
		this.setOpaque(true);
		this.setLayout(new GridLayout(controleur.getNbLig(), controleur.getNbCol()));

		for (int lig = 0; lig < this.controleur.getNbLig(); lig++)
			for (int col = 0; col < this.controleur.getNbCol(); col++)
				this.add(new ConteneurGraphique(this.ihm, this.controleur.getConteneur(lig, col), focus));

		this.repaint();
	}

	void miseAJour() {
		this.repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		Dimension dim = this.getSize();
		Conteneur conteneur;
		TwistLock twistLock;
		double baseX, baseY;
		double contWidth, contHeight;
		double x, y;

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int SIZE = 20;

		contWidth = dim.getWidth() / this.controleur.getNbCol();
		contHeight = dim.getHeight() / this.controleur.getNbLig();

		for (int lig = 0; lig < this.controleur.getNbLig(); lig++)
			for (int col = 0; col < this.controleur.getNbCol(); col++) {
				conteneur = this.controleur.getConteneur(lig, col);

				baseX = col * contWidth;
				baseY = lig * contHeight;

				for (int i = 0; i < conteneur.getCoins().length; i++) {
					twistLock = conteneur.getCoins()[i];
					if (twistLock == null || twistLock.getJoueur() == null)
						continue;

					switch (i) {
						default:
						case 0:
							x = 0;
							y = 0;
							break;
						case 1:
							x = contWidth;
							y = 0;
							break;
						case 2:
							x = contWidth;
							y = contHeight;
							break;
						case 3:
							x = 0;
							y = contHeight;
							break;
					}

					g2.setColor(IHM.COULEURS[twistLock.getJoueur().getId() - 1]);

					Ellipse2D.Double c = new Ellipse2D.Double(baseX + x - SIZE / 2D, baseY + y - SIZE / 2D, SIZE, SIZE);
					g2.fill(c);
				}
			}
	}

}
