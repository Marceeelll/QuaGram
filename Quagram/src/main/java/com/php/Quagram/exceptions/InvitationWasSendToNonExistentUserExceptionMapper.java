package com.php.Quagram.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.php.Quagram.model.ErrorMessage;

@Provider
public class InvitationWasSendToNonExistentUserExceptionMapper implements ExceptionMapper<InvitationWasSendToNonExistentUserException> {
	
	@Override
	public Response toResponse(InvitationWasSendToNonExistentUserException exception) {
		ErrorMessage errorMessage = new ErrorMessage(423, "Der eingeladenen Nutzer mit dem Nutzername (" + exception.getMessage() + ") wurde nicht gefunden."); 
		return Response.status(errorMessage.getErrorCode())
				.entity(errorMessage)
				.build();
	}
}
