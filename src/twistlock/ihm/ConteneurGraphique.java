package twistlock.ihm;

import twistlock.metier.Conteneur;
import twistlock.metier.Joueur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

/**
 * Représente un conteneur dans le plateau de jeu (Graphique)
 * Est en réalité un bouton cliquable redessiné.
 */
public class ConteneurGraphique extends JButton implements ActionListener {

	private IHM ihm;

	private Conteneur conteneur;

	ConteneurGraphique(IHM ihm, Conteneur conteneur, boolean focus) {
		this.ihm = ihm;
		this.conteneur = conteneur;

		this.setBorder(BorderFactory.createEmptyBorder());

		this.setOpaque(false);
		this.setFocusPainted(false);
		this.setBorderPainted(false);
		this.setContentAreaFilled(false);

		if( focus ) this.addActionListener(this);
	}

	/**
	 * Appelée lorsque l'on souhaite dessiner un conteneur graphique
	 *
	 * @param g Contexte graphique pour le dessin
	 */
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Dimension dim = this.getSize();

		int SIZE = 30;

		Area rect = new Area(new Rectangle(3, 3, (int) dim.getWidth() - 6, (int) dim.getHeight() - 6));

		Area c1 = new Area(new Ellipse2D.Double(-SIZE / 2, -SIZE / 2, SIZE, SIZE));
		Area c2 = new Area(new Ellipse2D.Double(dim.getWidth() - SIZE / 2, -SIZE / 2, SIZE, SIZE));
		Area c3 = new Area(new Ellipse2D.Double(-SIZE / 2, dim.getHeight() - SIZE / 2, SIZE, SIZE));
		Area c4 = new Area(new Ellipse2D.Double(dim.getWidth() - SIZE / 2, dim.getHeight() - SIZE / 2, SIZE, SIZE));

		rect.subtract(c1);
		rect.subtract(c2);
		rect.subtract(c3);
		rect.subtract(c4);

		// Dessin
		Color color = Color.WHITE;
		Joueur proprietaire = this.conteneur.getProprietaire();

		if (proprietaire != null)
			color = IHM.COULEURS[proprietaire.getId() - 1];

		g2.setColor(color);
		g2.fill(rect);
		g2.setColor(Color.BLACK);
		g2.draw(rect);

		// Texte Valeur
		int FONT_SIZE_VALEUR = 24;
		String text = String.valueOf(this.conteneur.getValeur());
		Font font = g2.getFont().deriveFont(Font.BOLD, FONT_SIZE_VALEUR);

		int width = g2.getFontMetrics(font).stringWidth(text);

		g2.setFont(font);
		g2.drawString(text, (float) dim.getWidth() / 2 - width / 2, (float) (dim.getHeight()) / 3 + FONT_SIZE_VALEUR / 2);
		
		// Texte Identifiant
		int FONT_SIZE_ID = 20;
		String textID = String.valueOf(this.conteneur.getLigne() + " " + this.conteneur.getColonne() );
		Font fontID = g2.getFont().deriveFont(Font.PLAIN, FONT_SIZE_ID);
		
		int widthID = g2.getFontMetrics(fontID).stringWidth(textID);
		
		g2.setFont(fontID);
		g2.drawString(textID, (float) dim.getWidth() / 2 - widthID / 2, (float) (dim.getHeight()) * 3 / 4 + FONT_SIZE_ID / 2);
		
		g2.dispose();
	}

	/**
	 * Méthode lancée dès lors du clic sur le bouton (conteneur)
	 *
	 * @param e Evenement de clic
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String[] coin = new String[]{"HAUT GAUCHE", "HAUT DROIT", "BAS DROIT", "BAS GAUCHE"};

		int rang = JOptionPane.showOptionDialog(
				this.ihm,
				"Choisissez le coin où placer le twistlock",
				"Choix du coin",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null, coin, null
		);

		if (rang > -1)
			this.ihm.choixTwistlock(this.conteneur, rang + 1);

	}

}
