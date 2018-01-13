package com.php.Quagram.exceptions;

public class InvitationDoesNotExistException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvitationDoesNotExistException(String matchSessionID) {
		super(matchSessionID);
	}
}
