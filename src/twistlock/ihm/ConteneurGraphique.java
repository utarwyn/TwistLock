package twistlock.ihm;

import twistlock.metier.Conteneur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class ConteneurGraphique extends JButton implements ActionListener {

	private IHM ihm;

	private Conteneur conteneur;

	ConteneurGraphique(IHM ihm, Conteneur conteneur) {
		this.ihm = ihm;
		this.conteneur = conteneur;

		this.setBorder(BorderFactory.createEmptyBorder());
		this.addActionListener(this);
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Dimension dim = this.getSize();

		int SIZE = 30;

		Area rect = new Area(new Rectangle(3, 3, (int) dim.getWidth() - 6, (int) dim.getHeight() - 6));

		Area c1 = new Area(new Ellipse2D.Double(                -SIZE/2,                  -SIZE/2, SIZE, SIZE));
		Area c2 = new Area(new Ellipse2D.Double(dim.getWidth() - SIZE/2,                  -SIZE/2, SIZE, SIZE));
		Area c3 = new Area(new Ellipse2D.Double(                -SIZE/2, dim.getHeight() - SIZE/2, SIZE, SIZE));
		Area c4 = new Area(new Ellipse2D.Double(dim.getWidth() - SIZE/2, dim.getHeight() - SIZE/2, SIZE, SIZE));

		rect.subtract(c1);
		rect.subtract(c2);
		rect.subtract(c3);
		rect.subtract(c4);

		// Dessin
		g2.setColor(Color.WHITE);
		g2.fill(rect);
		g2.setColor(Color.BLACK);
		g2.draw(rect);

		// Texte
		int FONT_SIZE = 24;
		String text = String.valueOf(this.conteneur.getValeur());
		Font font = g2.getFont().deriveFont(Font.BOLD, FONT_SIZE);

		int width = g2.getFontMetrics(font).stringWidth(text);

		g2.setFont(font);
		g2.drawString(text, (float) dim.getWidth()/2 - width/2, (float) (dim.getHeight() - 6)/2 + FONT_SIZE/2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String[] coin = new String[] { "1", "2", "3", "4" };

		int rang = JOptionPane.showOptionDialog(
				null,
				"Choisissez le coin où placer le twistlock",
				"Choix du coin",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null, coin, null
		);

		this.ihm.choixTwistlock(this.conteneur, rang+1);
	}

}
