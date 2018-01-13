package com.php.Quagram.exceptions;

public class InvitationAcceptedStatusDoesNotExistException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3055927459211107203L;

	public InvitationAcceptedStatusDoesNotExistException(String acceptedStatus) {
		super(acceptedStatus);
	}
}
