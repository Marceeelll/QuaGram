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
		errorMessage.setErrorCode(401);
		if (exception.getMessage() == null) {
			errorMessage.setErrorMessage("Nutzer ist nicht eingeloggt.");
		}
		return Response.status(errorMessage.getErrorCode())
				.entity(errorMessage)
				.build();
	}
}
