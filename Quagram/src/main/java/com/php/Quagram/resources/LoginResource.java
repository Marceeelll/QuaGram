package com.php.Quagram.resources;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.php.Quagram.model.User;
import com.php.Quagram.service.LoginService;

import others.JSONService;

@Path("/registration")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource {
	LoginService loginService = new LoginService();
	JSONService jsonService = new JSONService();

	@GET
	@Path("/login")
	public String instagramRegistration() {
		String registrationURI = loginService.getRegistrationURI();
		return registrationURI;
	}
	
	@GET
	public User userDidAllowedPermissions(@QueryParam("code") String code) {
		String currentUserJSONRespond = loginService.requestAccessToken(code);
		User instagramJSONRespondUser = jsonService.parseUserAfterLogin(currentUserJSONRespond);
		User user = loginService.loginUser(instagramJSONRespondUser);
		System.out.println("accessToken: " + user.getAccessToken());
		return user;
	}
	
	@PUT
	@Path("logout/{sessionID}")
	public String loggout(@PathParam("sessionID") String sessionID) {
		int result = loginService.logoutUser(sessionID);
		if (result == 1) {
			return "Succesfully logged out!";
		} else {
			return "Could not logg out!";
		}
	}
	
}
