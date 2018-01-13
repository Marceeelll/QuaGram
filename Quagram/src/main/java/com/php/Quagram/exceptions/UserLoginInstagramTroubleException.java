package com.php.Quagram.exceptions;

public class UserLoginInstagramTroubleException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UserLoginInstagramTroubleException(String instagramErrorMessage) {
		super(instagramErrorMessage);
	}
}
