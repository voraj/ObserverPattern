package com.runner;

import javax.swing.JFrame;

import com.components.Arena;
import com.components.GameConstants;

public class GameRunner extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GameRunner() {
		startGame();
	}

	public static void main(String[] args) {
		new GameRunner();
	}

	public void startGame() {
		// load Game screen
		add(new Arena());
		setTitle(GameConstants.GAME_WINDOW_TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(GameConstants.WINDOW_WIDTH, GameConstants.WINDOW_HEIGHT);
		setResizable(false);
		setVisible(true);
	}
}