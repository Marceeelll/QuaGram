package com.php.Quagram.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.php.Quagram.model.ErrorMessage;

@Provider
public class LobbyDoesNotIncludeUserExceptionMapper implements ExceptionMapper<LobbyDoesNotIncludeUserException> {
	
	@Override
	public Response toResponse(LobbyDoesNotIncludeUserException exception) {
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setErrorCode(410);
		if (exception.getMessage() == null) {
			errorMessage.setErrorMessage("Nutzer befindet sich nicht in der Lobby.");
		}
		return Response.status(errorMessage.getErrorCode())
				.entity(errorMessage)
				.build();
	}
}
