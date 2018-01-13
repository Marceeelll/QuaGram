package com.php.Quagram.exceptions;

public class InvitationWasSendToNonExistentUserException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvitationWasSendToNonExistentUserException(String username) {
		super(username);
	}
}
