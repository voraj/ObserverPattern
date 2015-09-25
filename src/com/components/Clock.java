package com.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Clock implements ActionListener {

	private static Timer timer;
	private static int gameTimer = GameConstants.GAME_TIMER_DURATION_MINUTES * 60;
	private boolean isTimerZero = false;

	public Clock() {
	}

	/**
	 * Method to fetch the timer value in String format
	 * 
	 * @return String
	 */
	public String getTime() {
		int minutes, seconds;
		minutes = gameTimer / 60;
		seconds = gameTimer % 60;

		return minutes + ":" + seconds;
	}

	/**
	 * Action listener to update the clock.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (gameTimer > 0) {
			gameTimer--;
		} else {
			isTimerZero = true;
		}
	}

	/**
	 * Method to tick the timer.
	 */
	public void startClock() {
		if (timer == null) {
			timer = new Timer(1000, this);
		}
		timer.setInitialDelay(0);
		timer.start();
	}

	public void stopClock() {

		if (timer != null) {
			timer.stop();
			gameTimer = GameConstants.GAME_TIMER_DURATION_MINUTES * 60;
		}
	}

	/**
	 * @return the isTimerZero
	 */
	public boolean isTimerZero() {
		return isTimerZero;
	}

	/**
	 * REsets the clock to full timer.
	 */
	public void resetClock() {
		isTimerZero = false;
		startClock();
	}

}
