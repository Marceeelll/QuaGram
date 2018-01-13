package com.php.Quagram.exceptions;

public class MatchSessionIDNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5530112612089698467L;

	public MatchSessionIDNotFoundException(String matchSessionID) {
		super(matchSessionID);
	}
}
