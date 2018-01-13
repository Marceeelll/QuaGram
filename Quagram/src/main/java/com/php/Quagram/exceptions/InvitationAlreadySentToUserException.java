package com.php.Quagram.exceptions;

public class InvitationAlreadySentToUserException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9132611559491854133L;

	public InvitationAlreadySentToUserException(String usernameToInvite) {
		super(usernameToInvite);
	}
}
