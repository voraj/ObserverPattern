/**
 * 
 */
package com.components;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

/**
 * @author hp
 *
 */
public class Ball extends ComponentDimensions implements Observable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Observer> observers = new ArrayList<Observer>();
	private int xDx = 1;
	private int yDy = -1;

	public Ball() {

		ImageIcon imageIcon = new ImageIcon(this.getClass().getClassLoader()
				.getResource(GameConstants.BALL_IMAGE_PATH));
		image = imageIcon.getImage();

		width = image.getWidth(null);
		heigth = image.getHeight(null);
		Point startPoint = getStartPostion();
		setBounds((int) startPoint.getX(), (int) startPoint.getY()
				- getHeight(), getWidth(), getHeight());
	}

	public void move() {

		setBounds(getX() + xDx, getY() + yDy, getWidth(), getHeight());
		if (getX() == 0) {
			xDx = 1;
		}

		if (getX() == (GameConstants.WINDOW_WIDTH - getWidth())) {
			xDx = -1;
		}

		if (getY() == 0) {
			yDy = 1;
		}
		// Add 1ms delay to control ball speed
		try {
			Thread.sleep(GameConstants.BALL_SPEED_DELAY);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// Notify observer of the change in ball position.
		notifyObserver();
	}

	/**
	 * @return the xDx
	 */
	public int getxDx() {
		return xDx;
	}

	/**
	 * @param xDx
	 *            the xDx to set
	 */
	public void setxDx(int xDx) {
		this.xDx = xDx;
	}

	/**
	 * @return the yDy
	 */
	public int getyDy() {
		return yDy;
	}

	/**
	 * @param yDy
	 *            the yDy to set
	 */
	public void setyDy(int yDy) {
		this.yDy = yDy;
	}

	@Override
	public void registerObserver(Observer observer) {
		observers.add(observer);
	}

	@Override
	public void removeObserver(Observer observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObserver() {
		for (Observer observer : observers) {
			observer.update(this);
		}
	}
}
