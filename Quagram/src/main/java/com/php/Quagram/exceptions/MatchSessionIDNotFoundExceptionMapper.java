package com.php.Quagram.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.php.Quagram.model.ErrorMessage;

@Provider
public class MatchSessionIDNotFoundExceptionMapper implements ExceptionMapper<MatchSessionIDNotFoundException> {

	@Override
	public Response toResponse(MatchSessionIDNotFoundException exception) {
		ErrorMessage errorMessage = new ErrorMessage(430, "MatchSession mit der ID " + exception.getMessage() + " wurde nicht gefunden."); 
		return Response.status(errorMessage.getErrorCode())
				.entity(errorMessage)
				.build();
	}
	
}