/**
 * 
 */
package com.components;

import javax.swing.ImageIcon;

/**
 * @author hp
 *
 */
public class Brick extends ComponentDimensions {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean isDestroyed;

	public Brick(int x, int y) {

		ImageIcon icon = new ImageIcon(this.getClass().getClassLoader()
				.getResource(GameConstants.BRICK_IMAGE_PATH));
		image = icon.getImage();
		width = image.getWidth(null);
		heigth = image.getHeight(null);
		isDestroyed = false;
		setBounds(x * width, y * heigth + GameConstants.TIMER_PANEL, width,
				heigth);
	}

	/**
	 * @return the isDestroyed
	 */
	public boolean isDestroyed() {
		return isDestroyed;
	}

	/**
	 * @param isDestroyed
	 *            the isDestroyed to set
	 */
	public void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

}
