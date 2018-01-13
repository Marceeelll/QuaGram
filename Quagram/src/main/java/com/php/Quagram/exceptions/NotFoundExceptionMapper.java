package com.php.Quagram.exceptions;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.php.Quagram.model.ErrorMessage;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<ClientErrorException> {

	@Override
	public Response toResponse(ClientErrorException exception) {
		ErrorMessage errorMessage = new ErrorMessage(404, "Seite nicht gefunden."); 
		return Response.status(errorMessage.getErrorCode())
				.entity(errorMessage)
				.type(MediaType.APPLICATION_JSON)
				.build();
	}
	
}