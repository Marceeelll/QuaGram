package com.php.Quagram.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.php.Quagram.model.ErrorMessage;

@Provider
public class UserHasDeclinedInvitationExceptionMapper implements ExceptionMapper<UserHasDeclinedInvitationException> {

	@Override
	public Response toResponse(UserHasDeclinedInvitationException exception) {
		ErrorMessage errorMessage = new ErrorMessage(431, "Der eingeladene Nutzer hat die Einladung abgelehnt. Die MatchSession wird deshalb beendet!"); 
		return Response.status(errorMessage.getErrorCode())
				.entity(errorMessage)
				.build();
	}
	
}