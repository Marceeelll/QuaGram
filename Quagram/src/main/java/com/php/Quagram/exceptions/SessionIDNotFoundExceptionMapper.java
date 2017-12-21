package com.php.Quagram.exceptions;

import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.Response;
import com.php.Quagram.model.ErrorMessage;

@Provider
public class SessionIDNotFoundExceptionMapper implements ExceptionMapper<SessionIDNotFoundException> {

	@Override
	public Response toResponse(SessionIDNotFoundException exception) {
		ErrorMessage errorMessage = new ErrorMessage(424, "Session with id " + exception.getMessage() + " not found"); 
		return Response.status(424)
				.entity(errorMessage)
				.build();
	}
	
}