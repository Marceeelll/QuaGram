package com.php.Quagram.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.php.Quagram.model.ErrorMessage;

@Provider
public class UserIsNotInLobbyExceptionMapper implements ExceptionMapper<UserIsNotLoggedInException> {

	@Override
	public Response toResponse(UserIsNotLoggedInException exception) {
		ErrorMessage errorMessage = new ErrorMessage(410, "Der Nutzer befindet sich nicht in der Lobby."); 
		return Response.status(errorMessage.getErrorCode())
				.entity(errorMessage)
				.build();
	}
	
}