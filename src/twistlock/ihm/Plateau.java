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
		double x, y;

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int SIZE = 20;

		Dimension conteneurSize = new Dimension();
		conteneurSize.setSize(
				dim.getWidth() / this.controleur.getNbCol() - 1,
				dim.getHeight() / this.controleur.getNbLig() - 1
		);

		for (int lig = 0; lig < this.controleur.getNbLig(); lig++)
			for (int col = 0; col < this.controleur.getNbCol(); col++) {
				conteneur = this.controleur.getConteneur(lig, col);

				baseX = col * conteneurSize.getWidth() + 1;
				baseY = lig * conteneurSize.getHeight() + 3;

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
							x = conteneurSize.getWidth();
							y = 0;
							break;
						case 2:
							x = conteneurSize.getWidth();
							y = conteneurSize.getHeight();
							break;
						case 3:
							x = 0;
							y = conteneurSize.getHeight();
							break;
					}

					g2.setColor(IHM.COULEURS[twistLock.getJoueur().getId() - 1]);

					Ellipse2D.Double c = new Ellipse2D.Double(baseX + x - SIZE / 2D, baseY + y - SIZE / 2D, SIZE, SIZE);
					g2.fill(c);
				}
			}
	}

}
