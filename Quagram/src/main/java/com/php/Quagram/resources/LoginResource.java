package com.php.Quagram.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.php.Quagram.exceptions.UserLogoutException;
import com.php.Quagram.model.User;
import com.php.Quagram.service.ErrorService;
import com.php.Quagram.service.JSONClientOutput;
import com.php.Quagram.service.LoginService;

import others.CardDownloadController;
import others.JSONService;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource {
	LoginService loginService = new LoginService();
	JSONService jsonService = new JSONService();

	@GET
	@Path("/registration/login")
	public Response instagramRegistration() {
		String registrationURIResponse = loginService.getRegistrationURI();
		return Response.status(Status.OK)
				.entity(registrationURIResponse)
				.build();
	}
	
	@GET
	@Path("/registration")
	public String userDidAllowedPermissions(@QueryParam("code") String code) {
		String currentUserJSONRespond = loginService.requestAccessToken(code);
		ErrorService errorService = new ErrorService();
		errorService.isInstagramRegistrationSuccessfully(currentUserJSONRespond);
		User instagramJSONRespondUser = jsonService.parseUserAfterLogin(currentUserJSONRespond);
		User user = loginService.loginUser(instagramJSONRespondUser);
		System.out.println("UUUUUSER-PROFILE-PIC: " + user.getProfilePic());
		CardDownloadController cardDownloadController = new CardDownloadController();
		System.out.println("Accesssssss Token: " + user.getAccessToken());
		cardDownloadController.downloadCardsForUserAndSafeToDB(user.getAccessToken(), user.getInstagramID());
		return "" + new JSONClientOutput().parseLoginUserWithSessionID(user);
	}
	
	@PUT
	@Path("/logout/{sessionID}")
	public Response loggout(@PathParam("sessionID") String sessionID) {
		int result = loginService.logoutUser(sessionID);
		System.out.println("Logout result: " +result);
		if (result == 1) {
			return Response.status(Status.NO_CONTENT).build();
		} else {
//			throw new PictureIDIsWrongException("hello");
			throw new UserLogoutException();
		}
	}
	
}
