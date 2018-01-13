package com.php.Quagram.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.php.Quagram.model.ErrorMessage;

@Provider
public class UserLoginInstagramTroubleExceptionMapper implements ExceptionMapper<UserLoginInstagramTroubleException> {

	@Override
	public Response toResponse(UserLoginInstagramTroubleException exception) {
		ErrorMessage errorMessage = new ErrorMessage(405, "Einloggen nicht möglich. Instagram Fehlermeldung: " + exception.getMessage()); 
		return Response.status(errorMessage.getErrorCode())
				.entity(errorMessage)
				.build();
	}
	
}