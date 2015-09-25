/**
 * 
 */
package com.components;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.runner.GameRunner;

/**
 * @author hp
 *
 */
public class Arena extends JPanel implements Observer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient Image image;
	private transient ImageIcon imageIcon;
	private transient Clock clock;
	private transient Ball ball;
	private transient Paddle paddle;
	private transient Brick bricks[][];
	private JButton newGameButton;
	private JButton startButton;
	private JButton quitButton;
	JPanel buttonPanel;

	private int score;
	private boolean isMoreBricks = false;
	private boolean isBallFallen = false;

	public Arena() {
		// Assigns control to the game window
		setFocusable(true);
		// Initialize Game components
		createComponents();
		// Create a listener to capture keyboard actions.
		addKeyListener(new KeyboardListner());
	}

	private void createComponents() {

		bricks = new Brick[GameConstants.BRICK_ROWS][GameConstants.BRICKS_PER_ROW];
		ball = new Ball();
		paddle = new Paddle();
		clock = new Clock();

		for (int i = 0; i < GameConstants.BRICK_ROWS; i++) {
			for (int j = 0; j < GameConstants.BRICKS_PER_ROW; j++) {
				bricks[i][j] = new Brick(j, i);
			}
		}
		// Register itself as observer
		ball.registerObserver(this);
		// create game option buttons
		initButtons();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		if (startButton.isVisible()) {
			buttonPanel.setVisible(true);
		} else if (!(clock.isTimerZero() || isBallFallen)) {
			g.setFont(new Font(GameConstants.SCORE_TITLE_FONT, Font.PLAIN,
					GameConstants.TITLE_FONT_SIZE));

			isMoreBricks = false;
			for (int i = 0; i < GameConstants.BRICK_ROWS; i++) {
				for (int j = 0; j < GameConstants.BRICKS_PER_ROW; j++) {
					if (!bricks[i][j].isDestroyed()) {
						isMoreBricks = true;
						g.drawImage(bricks[i][j].getImage(),
								j * bricks[i][j].getWidth(),
								i * bricks[i][j].getHeight()
										+ GameConstants.TIMER_PANEL,
								bricks[i][j].getWidth(),
								bricks[i][j].getHeight(), this);
					}
				}
			}

			if (!isMoreBricks) {
				ball.setxDx(0);
				ball.setyDy(0);
				endGame(g, false);
			}

			g.drawImage(ball.getImage(), ball.getX(), ball.getY(),
					ball.getWidth(), ball.getHeight(), this);
			g.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(),
					paddle.getWidth(), paddle.getHeight(), this);

			g.drawString("Time Left : " + clock.getTime(), 10, 25);
			g.drawString("Score : " + score, 350, 25);
			disposeGraphic(g);
			refreshCanvas();
		} else {
			endGame(g, true);
		}
	}

	private void refreshCanvas() {
		ball.move();
		paddle.move();
		deflectBall();
	}

	private void endGame(Graphics g, boolean status) {
		if (status) {
			image = new ImageIcon(this.getClass().getClassLoader()
					.getResource("images/lose.jpg")).getImage();
		} else {
			image = new ImageIcon(this.getClass().getClassLoader()
					.getResource("images/win.jpg")).getImage();
			imageIcon = new ImageIcon(image.getScaledInstance(
					(int) (0.8 * image.getWidth(null)),
					(int) (0.8 * image.getHeight(null)), Image.SCALE_SMOOTH));
			image = imageIcon.getImage();
		}

		g.drawImage(image, (getWidth() - image.getWidth(null)) / 2,
				(getHeight() - image.getHeight(null)) / 3,
				image.getWidth(null), image.getHeight(null), this);
		clock.stopClock();
		displayGameOptionButtons();
		disposeGraphic(g);
	}

	private void disposeGraphic(Graphics g) {
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	private void deflectBall() {

		/*
		 * Deflection from Paddle
		 */
		Rectangle leftPaddle = new Rectangle(
				(int) paddle.getBounds().getMinX(), (int) paddle.getBounds()
						.getMinY(), paddle.getWidth() / 2, paddle.getHeight());

		if (ball.getBounds().intersects(paddle.getBounds())) {
			ball.setyDy(-1);
			ball.setxDx(1);
			if (ball.getBounds().intersects(leftPaddle.getBounds())
					&& (leftPaddle.getX() + leftPaddle.getWidth() - ball.getX()) > (ball
							.getWidth() / 2)) {
				ball.setxDx(-1);
			}
		}

		/**
		 * Game over when ball hits bottom
		 */
		if ((ball.getY() + ball.getHeight()) >= getHeight()) {
			isBallFallen = true;
		}
		/*
		 * Deflection from Bricks
		 */
		int ballMinX = (int) ball.getBounds().getMinX();
		int ballMinY = (int) ball.getBounds().getMinY();

		Point top = new Point(ballMinX, ballMinY - 1);
		Point bottom = new Point(ballMinX, ballMinY + ball.getHeight() + 1);
		Point left = new Point(ballMinX - 1, ballMinY);
		Point right = new Point(ballMinX + ball.getWidth() + 1, ballMinY);

		for (Brick[] brickRow : bricks) {
			for (Brick brickk : brickRow) {
				if (!brickk.isDestroyed()
						&& ball.getBounds().intersects(brickk.getBounds())) {

					// Destroy hit brick
					brickk.setDestroyed(true);
					score++;

					if (brickk.getBounds().contains(top)) {
						ball.setyDy(1);
					} else if (brickk.getBounds().contains(bottom)) {
						ball.setyDy(-1);
					} else if (brickk.getBounds().contains(left)) {
						ball.setxDx(1);
					} else if (brickk.getBounds().contains(right)) {
						ball.setxDx(-1);
					}
				}
			}
		}
	}

	private void displayGameOptionButtons() {

		buttonPanel.setLocation((getWidth() - buttonPanel.getWidth()) / 2,
				(int) (getHeight() * 0.8));
		buttonPanel.setVisible(true);
	}

	private void initButtons() {

		buttonPanel = new JPanel();
		imageIcon = new ImageIcon(new ImageIcon(this.getClass()
				.getClassLoader().getResource("images/start.png")).getImage()
				.getScaledInstance(GameConstants.GAME_OPTION_BUTTONS_WIDTH,
						GameConstants.GAME_OPTION_BUTTONS_HEIGHT,
						Image.SCALE_SMOOTH));

		startButton = new JButton(imageIcon);
		startButton.setBorder(BorderFactory.createEmptyBorder());
		startButton.setActionCommand("start");
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				startButton.setVisible(false);
				newGameButton.setVisible(true);
				quitButton.setVisible(true);
				buttonPanel.setVisible(false);
				clock.resetClock();
			}
		});
		buttonPanel.add(startButton);

		imageIcon = new ImageIcon(new ImageIcon(this.getClass()
				.getClassLoader().getResource("images/newGame.png")).getImage()
				.getScaledInstance(GameConstants.GAME_OPTION_BUTTONS_WIDTH,
						GameConstants.GAME_OPTION_BUTTONS_HEIGHT,
						Image.SCALE_SMOOTH));

		newGameButton = new JButton(imageIcon);
		newGameButton.setBorder(BorderFactory.createEmptyBorder());
		newGameButton.setActionCommand("newgame");
		newGameButton.setVisible(false);
		newGameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Release all game resources
				((GameRunner) SwingUtilities.getRootPane(
						buttonPanel.getParent()).getParent()).dispose();
				// Relaunch the game
				new GameRunner();
			}
		});
		buttonPanel.add(newGameButton);

		imageIcon = new ImageIcon(new ImageIcon(this.getClass()
				.getClassLoader().getResource("images/quit.png")).getImage()
				.getScaledInstance(GameConstants.GAME_OPTION_BUTTONS_WIDTH,
						GameConstants.GAME_OPTION_BUTTONS_HEIGHT,
						Image.SCALE_SMOOTH));

		quitButton = new JButton(imageIcon);
		quitButton.setBorder(BorderFactory.createEmptyBorder());
		quitButton.setActionCommand("quit");
		quitButton.setVisible(false);
		quitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Stop the JVM
				System.exit(1);
			}
		});
		buttonPanel.add(quitButton);
		buttonPanel.setVisible(false);
		add(buttonPanel);
	}

	@Override
	public void update(Observable observable) {
		// Refresh canvas once the ball position is updated
		repaint();
	}

	/**
	 * Inner class to capture keyboard inputs and control the paddle movement.
	 * 
	 */
	class KeyboardListner extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			paddle.keyPressed(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			paddle.keyReleased(e);
		}
	}
}
