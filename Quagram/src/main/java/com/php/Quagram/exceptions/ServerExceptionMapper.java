package com.php.Quagram.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.php.Quagram.model.ErrorMessage;


@Provider
public class ServerExceptionMapper implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable exception) {
		ErrorMessage errorMessage = new ErrorMessage(500, "HTTP Error 500 Internal server error"); 
		return Response.status(errorMessage.getErrorCode())
				.entity(errorMessage)
				.build();
	}
	
}