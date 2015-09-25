/**
 * 
 */
package com.components;

import java.awt.Point;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

/**
 * @author hp
 *
 */
public class Paddle extends ComponentDimensions {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int xDx;

	public Paddle() {

		ImageIcon imageIcon = new ImageIcon(this.getClass().getClassLoader()
				.getResource(GameConstants.PADDLE_IMAGE_PATH));
		image = imageIcon.getImage();

		width = image.getWidth(null);
		heigth = image.getHeight(null);
		Point startPoint = getStartPostion();
		setBounds((int) startPoint.getX(), (int) startPoint.getY(), getWidth(),
				getHeight());
	}

	public void move() {
		setBounds(getX() + xDx, getY(), getWidth(), getHeight());
		if (getX() < 0) {
			setBounds(0, getY(), getWidth(), getHeight());
		}

		if (getBounds().getMaxX() > GameConstants.WINDOW_WIDTH) {
			setBounds(GameConstants.WINDOW_WIDTH - getWidth(), getY(),
					getWidth(), getHeight());
		}
	}

	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();

		if (keyCode == KeyEvent.VK_LEFT) {
			xDx = -1;
		}

		if (keyCode == KeyEvent.VK_RIGHT) {
			xDx = 1;
		}
	}

	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();

		if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT) {
			xDx = 0;
		}
	}
}
