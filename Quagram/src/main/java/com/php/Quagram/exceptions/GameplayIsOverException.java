package com.php.Quagram.exceptions;

public class GameplayIsOverException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3055927459211107203L;

	public GameplayIsOverException(String gameplayID) {
		super(gameplayID);
	}
}
