package com.php.Quagram.resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import com.php.Quagram.database.DatabaseClass;
import com.php.Quagram.model.User;
import com.php.Quagram.service.JSONService;
import com.php.Quagram.service.LoginService;
import com.php.Quagram.service.UserService;

@Path("/registration")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RegistrationResource {
	UserService userService = new UserService();
	LoginService loginService = new LoginService();
	JSONService jsonService = new JSONService();
	
//	@GET
//	public List<User> getUsers() {
//		return userService.getAllUsers();
//	}

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
		return user;
	}
	
	@GET
	@Path("/debuggAllUser")
	public ArrayList<User> debuggAllUser() {
		return loginService.debuggAllUser();
	}
	
}
