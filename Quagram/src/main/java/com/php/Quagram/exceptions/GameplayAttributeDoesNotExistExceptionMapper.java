package com.php.Quagram.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.php.Quagram.model.ErrorMessage;

@Provider
public class GameplayAttributeDoesNotExistExceptionMapper implements ExceptionMapper<GameplayAttributeDoesNotExistException> {
	
	@Override
	public Response toResponse(GameplayAttributeDoesNotExistException exception) {
		String clientTriedAcceptedStatus = exception.getMessage();
		ErrorMessage errorMessage = new ErrorMessage(404, "Der Parameter (" + clientTriedAcceptedStatus + ") ist ungültig. Folgende Parameter sind gültig: likes/comments/temperature/height.");
		return Response.status(errorMessage.getErrorCode())
				.entity(errorMessage)
				.build();
	}
}