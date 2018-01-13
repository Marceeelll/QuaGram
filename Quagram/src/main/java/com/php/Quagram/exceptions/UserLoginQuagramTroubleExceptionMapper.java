package com.php.Quagram.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.php.Quagram.model.ErrorMessage;

@Provider
public class UserLoginQuagramTroubleExceptionMapper implements ExceptionMapper<UserLoginQuagramTroubleException> {

	@Override
	public Response toResponse(UserLoginQuagramTroubleException exception) {
		ErrorMessage errorMessage = new ErrorMessage(406, "Einloggen nicht möglich. Quagram kann Sie derzeit nicht anmelden."); 
		return Response.status(errorMessage.getErrorCode())
				.entity(errorMessage)
				.build();
	}
	
}