package com.php.Quagram.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.php.Quagram.model.ErrorMessage;

@Provider
public class InvitationCantBeSendToOneselfExceptionMapper implements ExceptionMapper<InvitationCantBeSendToOneselfException> {
	
	@Override
	public Response toResponse(InvitationCantBeSendToOneselfException exception) {
		ErrorMessage errorMessage = new ErrorMessage(409, "Einladung kann nicht an sich selbst versendet werden.");
		return Response.status(errorMessage.getErrorCode())
				.entity(errorMessage)
				.build();
	}
}