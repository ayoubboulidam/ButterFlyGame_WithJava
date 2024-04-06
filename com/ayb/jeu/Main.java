package com.ayb.jeu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// This class represents the main entry point of the game
public class Main {

	public static JFrame fenetre;
	public static Scene scene;

	public static void main(String[] args) {
		initializeGame();
	}

	// Method to initialize the game
	public static void initializeGame() {
		fenetre = new JFrame("Butterfly");
		scene = new Scene();

		// Create restart button
		JButton restartButton = new JButton("Restart");
		restartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Restart the game
				restartGame();
			}
		});

		// Panel to hold the restart button
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(restartButton);

		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setSize(300, 475);
		fenetre.setLocationRelativeTo(null);
		fenetre.setResizable(false);
		fenetre.setAlwaysOnTop(true);

		fenetre.setLayout(new BorderLayout());
		fenetre.add(scene, BorderLayout.CENTER);
		fenetre.add(buttonPanel, BorderLayout.SOUTH);

		fenetre.setVisible(true);

		// Attach key listener to the scene
		scene.setFocusable(true);
		scene.requestFocusInWindow();
		scene.addKeyListener(new Clavier());
	}

	// Method to restart the game
	public static void restartGame() {
		// Dispose of current frame
		fenetre.dispose();
		// Reinitialize the game
		initializeGame();
	}
}
