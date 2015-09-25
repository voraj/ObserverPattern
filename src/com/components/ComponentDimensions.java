/**
 * 
 */
package com.components;

import java.awt.Image;
import java.awt.Point;

import javax.swing.JComponent;

/**
 * @author hp
 *
 */
public class ComponentDimensions extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected int width;
	protected int heigth;
	protected transient Image image;

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return heigth;
	}

	public Image getImage() {
		return image;
	}

	/**
	 * Method to calculate initial position of components on game screen.
	 * 
	 * @return
	 */
	protected Point getStartPostion() {
		return new Point(
				(GameConstants.WINDOW_WIDTH - width) / 2,
				(int) (GameConstants.WINDOW_HEIGHT * GameConstants.PADDLE_DISTANCE_FROM_BOTTOM));
	}
}
