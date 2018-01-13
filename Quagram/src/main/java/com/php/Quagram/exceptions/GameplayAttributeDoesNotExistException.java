package com.php.Quagram.exceptions;

public class GameplayAttributeDoesNotExistException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3055927459211107203L;

	public GameplayAttributeDoesNotExistException(String attributeToPlay) {
		super(attributeToPlay);
	}
}
