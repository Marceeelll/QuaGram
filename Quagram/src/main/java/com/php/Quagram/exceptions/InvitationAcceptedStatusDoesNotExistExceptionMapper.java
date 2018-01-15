package com.php.Quagram.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.php.Quagram.model.ErrorMessage;

@Provider
public class InvitationAcceptedStatusDoesNotExistExceptionMapper implements ExceptionMapper<InvitationAcceptedStatusDoesNotExistException> {
	
	@Override
	public Response toResponse(InvitationAcceptedStatusDoesNotExistException exception) {
		String clientTriedAcceptedStatus = exception.getMessage();
		ErrorMessage errorMessage = new ErrorMessage(404, "Der Parameter (" + clientTriedAcceptedStatus + ") ist ungültig. Folgende Parameter sind gültig: 0 (Einladung ablehnen) oder 1 (Einladung akzeptieren).");
		return Response.status(errorMessage.getErrorCode())
				.entity(errorMessage)
				.build();
	}
}