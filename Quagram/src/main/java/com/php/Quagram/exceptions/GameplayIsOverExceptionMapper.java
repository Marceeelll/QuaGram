package com.php.Quagram.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.php.Quagram.model.ErrorMessage;

@Provider
public class GameplayIsOverExceptionMapper implements ExceptionMapper<GameplayIsOverException> {
	
	@Override
	public Response toResponse(GameplayIsOverException exception) {
		String gameplayID = exception.getMessage();
		ErrorMessage errorMessage = new ErrorMessage(409, "Der Gameplay mit der ID (" + gameplayID + ") ist zu Ende.");
		return Response.status(errorMessage.getErrorCode())
				.entity(errorMessage)
				.build();
	}
}