package com.php.Quagram.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.php.Quagram.model.ErrorMessage;

@Provider
public class InvitationDoesNotExistExceptionMapper implements ExceptionMapper<InvitationDoesNotExistException> {
	
	@Override
	public Response toResponse(InvitationDoesNotExistException exception) {
		ErrorMessage errorMessage = new ErrorMessage(404, "Die Einladung mit der ID " + exception.getMessage() +" ist nicht mehr verfügbar.");
		return Response.status(errorMessage.getErrorCode())
				.entity(errorMessage)
				.build();
	}
}