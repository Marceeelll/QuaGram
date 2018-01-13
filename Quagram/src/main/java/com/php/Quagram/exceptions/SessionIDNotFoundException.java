package com.php.Quagram.exceptions;

public class SessionIDNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SessionIDNotFoundException(String sessionID) {
		super(sessionID);
	}
}
