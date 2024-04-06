package com.ayb.jeu;

import java.awt.*;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.ayb.objet.Tuyau;
import com.ayb.personnages.Butterfly;

// This class represents the game scene where gameplay elements are displayed
public class Scene extends JPanel {

	private ImageIcon icoBandeFond;
	private Image imgBandeFond;

	public Tuyau tuyauHaut1;
	public Tuyau tuyauBas1;
	public Tuyau tuyauHaut2;
	public Tuyau tuyauBas2;
	public Tuyau tuyauHaut3;
	public Tuyau tuyauBas3;

	public Butterfly butterfly;
	private int score;
	private Font police;

	private final int LARGEUR_BANDE_FOND = 140;
	private final int DISTANCE_TUYAUX = 250;
	private final int ECART_TUYAUX = 120;

	public int xFond;
	private int xTuyaux;
	public boolean finDuJeu;

	private Random hasard;

	// Constructor to initialize the scene
	public Scene() {
		super();
		this.icoBandeFond = new ImageIcon(getClass().getResource("/images/bandeFondEcran.png"));
		this.imgBandeFond = this.icoBandeFond.getImage();

		this.xFond = 0;
		this.xTuyaux = 400;
		this.finDuJeu = false;

		this.tuyauHaut1 = new Tuyau(this.xTuyaux, -150, "/images/tuyauHaut.png");
		this.tuyauBas1 = new Tuyau(this.xTuyaux, 250, "/images/tuyauBas.png");
		this.tuyauHaut2 = new Tuyau(this.xTuyaux + this.DISTANCE_TUYAUX, -100, "/images/tuyauHaut.png");
		this.tuyauBas2 = new Tuyau(this.xTuyaux + this.DISTANCE_TUYAUX, 300, "/images/tuyauBas.png");
		this.tuyauHaut3 = new Tuyau(this.xTuyaux + this.DISTANCE_TUYAUX * 2, -120, "/images/tuyauHaut.png");
		this.tuyauBas3 = new Tuyau(this.xTuyaux + this.DISTANCE_TUYAUX * 2, 280, "/images/tuyauBas.png");

		this.butterfly = new Butterfly(100, 150, "/images/oiseau1.png");

		hasard = new Random();

		this.setFocusable(true);
		this.requestFocusInWindow();
		this.addKeyListener(new Clavier());

		this.score = 0;
		this.police = new Font("Arial", Font.PLAIN, 40);

		Thread chronoEcran = new Thread(new Chrono());
		chronoEcran.start();
	}

	// Method to move the background
	private void deplacementFond(Graphics g) {
		if (this.xFond == -this.LARGEUR_BANDE_FOND) {
			this.xFond = 0;
		}
		g.drawImage(this.imgBandeFond, this.xFond, 0, null);
		g.drawImage(this.imgBandeFond, this.xFond + this.LARGEUR_BANDE_FOND, 0, null);
		g.drawImage(this.imgBandeFond, this.xFond + this.LARGEUR_BANDE_FOND * 2, 0, null);
		g.drawImage(this.imgBandeFond, this.xFond + this.LARGEUR_BANDE_FOND * 3, 0, null);
	}

	// Method to move the pipes
	private void deplacementTuyaux(Graphics g) {
		// Tuyau 1
		this.tuyauHaut1.setX(this.tuyauHaut1.getX() - 1);
		this.tuyauBas1.setX(this.tuyauHaut1.getX());

		if (this.tuyauHaut1.getX() == -100) {
			this.tuyauHaut1.setX(this.tuyauHaut3.getX() + this.DISTANCE_TUYAUX);
			this.tuyauHaut1.setY(-100 - 10 * this.hasard.nextInt(18));
			this.tuyauBas1.setY(this.tuyauHaut1.getY() + this.tuyauHaut1.getHauteur() + this.ECART_TUYAUX);
		}
		g.drawImage(this.tuyauHaut1.getImgTuyau(), this.tuyauHaut1.getX(), this.tuyauHaut1.getY(), null);
		g.drawImage(this.tuyauBas1.getImgTuyau(), this.tuyauBas1.getX(), this.tuyauBas1.getY(), null);

		// Tuyau 2
		this.tuyauHaut2.setX(this.tuyauHaut2.getX() - 1);
		this.tuyauBas2.setX(this.tuyauHaut2.getX());

		if (this.tuyauHaut2.getX() == -100) {
			this.tuyauHaut2.setX(this.tuyauHaut1.getX() + this.DISTANCE_TUYAUX);
			this.tuyauHaut2.setY(-100 - 10 * this.hasard.nextInt(18));
			this.tuyauBas2.setY(this.tuyauHaut2.getY() + this.tuyauHaut2.getHauteur() + this.ECART_TUYAUX);
		}
		g.drawImage(this.tuyauHaut2.getImgTuyau(), this.tuyauHaut2.getX(), this.tuyauHaut2.getY(), null);
		g.drawImage(this.tuyauBas2.getImgTuyau(), this.tuyauBas2.getX(), this.tuyauBas2.getY(), null);

		// Tuyau 3
		this.tuyauHaut3.setX(this.tuyauHaut3.getX() - 1);
		this.tuyauBas3.setX(this.tuyauHaut3.getX());

		if (this.tuyauHaut3.getX() == -100) {
			this.tuyauHaut3.setX(this.tuyauHaut2.getX() + this.DISTANCE_TUYAUX);
			this.tuyauHaut3.setY(-100 - 10 * this.hasard.nextInt(18));
			this.tuyauBas3.setY(this.tuyauHaut3.getY() + this.tuyauHaut3.getHauteur() + this.ECART_TUYAUX);
		}
		g.drawImage(this.tuyauHaut3.getImgTuyau(), this.tuyauHaut3.getX(), this.tuyauHaut3.getY(), null);
		g.drawImage(this.tuyauBas3.getImgTuyau(), this.tuyauBas3.getX(), this.tuyauBas3.getY(), null);
	}

	// Method to check for collision with the butterfly
	private boolean collisionButterfly() {
		boolean finDuJeu = false;
		if (this.butterfly.getX() + this.butterfly.getLargeur() > this.tuyauBas1.getX() - 20 &&
				this.butterfly.getX() < this.tuyauBas1.getX() + this.tuyauBas1.getLargeur() + 20) {
			finDuJeu = this.butterfly.collision(this.tuyauBas1);
			if (!finDuJeu) {
				finDuJeu = this.butterfly.collision(this.tuyauHaut1);
			}
		} else {
			if (this.butterfly.getX() + this.butterfly.getLargeur() > this.tuyauBas2.getX() - 20 && this.butterfly.getX() < this.tuyauBas2.getX() + this.tuyauBas2.getLargeur() + 20) {
				finDuJeu = this.butterfly.collision(this.tuyauBas2);
				if (!finDuJeu) {
					finDuJeu = this.butterfly.collision(this.tuyauHaut2);
				}
			} else {
				if (this.butterfly.getX() + this.butterfly.getLargeur() > this.tuyauBas3.getX() - 20 && this.butterfly.getX() < this.tuyauBas3.getX() + this.tuyauBas3.getLargeur() + 20) {
					finDuJeu = this.butterfly.collision(this.tuyauBas3);
					if (!finDuJeu) {
						finDuJeu = this.butterfly.collision(this.tuyauHaut3);
					}
				} else {
					if (this.butterfly.getY() < 0 || this.butterfly.getY() + this.butterfly.getHauteur() > 355) {
						finDuJeu = true;
					} else {
						finDuJeu = false;
					}
				}
			}
		}
		return finDuJeu;
	}

	// Method to update score
	private void updateScore() {
		if (this.tuyauBas1.getX() + this.tuyauBas1.getLargeur() == 95 || this.tuyauBas2.getX() + this.tuyauBas2.getLargeur() == 95 || this.tuyauBas3.getX() + this.tuyauBas3.getLargeur() == 95) {
			this.score++;
			Audio.playSound("/audio/sonnerie.wav");
		}
	}

	// Method to paint components on the scene
	public void paintComponent(Graphics g) {
		this.deplacementFond(g);
		this.deplacementTuyaux(g);
		this.updateScore();

		// Draw score box
		g.setColor(Color.WHITE); // Set color for the box
		int boxSize = 30; // Size of the square box
		int boxX = 120; // X-coordinate of the top-left corner of the box
		int boxY = 10; // Y-coordinate of the top-left corner of the box
		g.fillRect(boxX, boxY, boxSize, boxSize); // Draw square for the score box

		// Set font and color for the score
		int fontSize = 24; // Adjust font size here
		Font police = new Font("Arial", Font.PLAIN, fontSize); // Adjust font size here
		g.setFont(police);
		g.setColor(Color.BLACK); // Set color for the score text

		// Calculate coordinates to center the score text within the box
		FontMetrics fm = g.getFontMetrics(police);
		int textWidth = fm.stringWidth("" + score);
		int textHeight = fm.getAscent();
		int textX = boxX + (boxSize - textWidth) / 2;
		int textY = boxY + (boxSize + textHeight) / 2;

		g.drawString("" + score, textX, textY); // Draw score text

		this.finDuJeu = this.collisionButterfly();
		this.butterfly.setY(this.butterfly.getY() + 1);
		g.drawImage(this.butterfly.getImgOiseau(), this.butterfly.getX(), this.butterfly.getY(), null);

		if (this.finDuJeu) {
			Font font = new Font("Arial", Font.BOLD, 50);
			g.setFont(font);
			g.setColor(Color.RED);
			g.drawString("You lose", 30, 200);
			Audio.playSound("/audio/choc.wav");
		}
	}

	// Method to restart the game
	public void restartGame() {
		// Reset game state
		score = 0;
		butterfly.reset(); // Reset butterfly state
		finDuJeu = false;

		// Reset pipe positions
		tuyauHaut1.setX(xTuyaux);
		tuyauBas1.setX(xTuyaux);
		tuyauHaut2.setX(xTuyaux + DISTANCE_TUYAUX);
		tuyauBas2.setX(xTuyaux + DISTANCE_TUYAUX);
		tuyauHaut3.setX(xTuyaux + DISTANCE_TUYAUX * 2);
		tuyauBas3.setX(xTuyaux + DISTANCE_TUYAUX * 2);

		// Repaint the scene
		repaint();
	}

	// Method to check if the game is over
	public boolean isGameOver() {
		return finDuJeu;
	}
}
