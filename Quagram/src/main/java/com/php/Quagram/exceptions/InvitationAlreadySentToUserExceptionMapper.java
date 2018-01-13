package com.php.Quagram.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.php.Quagram.model.ErrorMessage;

@Provider
public class InvitationAlreadySentToUserExceptionMapper implements ExceptionMapper<InvitationAlreadySentToUserException> {
	
	@Override
	public Response toResponse(InvitationAlreadySentToUserException exception) {
		ErrorMessage errorMessage = new ErrorMessage(420, "Der Nutzer (" + exception.getMessage() + ") wurde bereits eingeladen.");
		return Response.status(errorMessage.getErrorCode())
				.entity(errorMessage)
				.build();
	}
}