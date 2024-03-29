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

import org.json.JSONObject;

import com.php.Quagram.database.DatabaseQuagramUsers;
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
		String jsonURLEntityString = new JSONClientOutput().getURLJSON(registrationURIResponse).toString();
		return Response.status(Status.OK)
				.entity(jsonURLEntityString)
				.build();
	}
	
	@GET
	@Path("/registration")
	public String userDidAllowedPermissions(@QueryParam("code") String code, @QueryParam("error") String error, @QueryParam("error_reason") String error_reason, @QueryParam("error_description") String error_description, @QueryParam("type") String type) {
		System.out.println("--userDidAllowedPermissions");
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
			throw new UserLogoutException();
		}
	}
	
	
	/*
	 * Die nachfolgende Resource ist nur für das iPad implementiert, auf dem die Studienarbeit
	 * abgegeben wird, da auf diesem Gerät OAuth nicht richtig funktioniert und von Instagram aus schwirigkeiten bereitet.
	 * 
	 * */
	@GET
	@Path("/iPadProfileInformation/{sessionID}")
	public Response getPlayerInformationForIPadThatCantUseOAuth(@PathParam("sessionID") String sessionID) {
		User user = new DatabaseQuagramUsers().getUserForSessionID(sessionID);
		JSONObject jsonRespond = new JSONClientOutput().parseLoginUserWithSessionID(user);
		System.out.println(jsonRespond);
		return Response.status(Status.OK).entity(jsonRespond.toString()).build();
	}
	
}
