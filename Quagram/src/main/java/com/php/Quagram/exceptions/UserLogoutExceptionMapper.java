package com.php.Quagram.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.php.Quagram.model.ErrorMessage;

@Provider
public class UserLogoutExceptionMapper implements ExceptionMapper<UserLogoutException> {

	@Override
	public Response toResponse(UserLogoutException exception) {
		ErrorMessage errorMessage = new ErrorMessage(409, "TEEEEST nicht möglich. Quagram kann Sie derzeit nicht anmelden."); 
		return Response.status(errorMessage.getErrorCode())
				.entity(errorMessage)
				.build();
	}
	
}