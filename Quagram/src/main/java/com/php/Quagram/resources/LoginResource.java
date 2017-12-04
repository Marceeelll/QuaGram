package com.php.Quagram.resources;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.php.Quagram.model.User;
import com.php.Quagram.service.JSONService;
import com.php.Quagram.service.LoginService;

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
	
	@GET
	@Path("/debuggAllUser")
	public ArrayList<User> debuggAllUser() {
		return loginService.debuggAllUser();
	}
	
}
