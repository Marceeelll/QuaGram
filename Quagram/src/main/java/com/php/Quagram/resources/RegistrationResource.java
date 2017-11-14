package com.php.Quagram.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import com.php.Quagram.model.User;
import com.php.Quagram.service.UserService;

@Path("/registration")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RegistrationResource {
	UserService userService = new UserService();
	
//	@GET
//	public List<User> getUsers() {
//		return userService.getAllUsers();
//	}

	@GET
	@Path("/instagram")
	public String instagramRegistration() {
		String clientID = "334bddc7b37f437dbb709f44661dc458";
		String redirectURI = "http://localhost:8080/Quagram/webapi/registration";
		
		// https://api.instagram.com/oauth/authorize/?client_id=CLIENT-ID&redirect_uri=REDIRECT-URI&response_type=code
		String registrationURI = "https://api.instagram.com/oauth/authorize/?client_id=" + clientID + "&redirect_uri=" + redirectURI + "&response_type=code";
		return registrationURI;
	}
	
	@GET
	public String addUser(@QueryParam("code") String accessToken) {
		System.out.println(accessToken);
		return "Erfolgreich-AccessToken: " + accessToken;
	}
}
