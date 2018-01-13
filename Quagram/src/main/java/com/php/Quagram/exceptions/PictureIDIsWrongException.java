package com.php.Quagram.exceptions;

public class PictureIDIsWrongException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4239291693491547773L;

	public PictureIDIsWrongException(String pictureID) {
		super(pictureID);
	}
}
