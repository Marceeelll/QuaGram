package com.php.Quagram.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.php.Quagram.model.ErrorMessage;

@Provider
public class GameplayUserIsNotInTurnExceptionMapper implements ExceptionMapper<GameplayUserIsNotInTurnException> {
	
	@Override
	public Response toResponse(GameplayUserIsNotInTurnException exception) {
		ErrorMessage errorMessage = new ErrorMessage(440, "Nutzer ist nicht an der Reihe, ein Attribut auszuwählen.");
		return Response.status(errorMessage.getErrorCode())
				.entity(errorMessage)
				.build();
	}
}