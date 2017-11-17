package com.php.Quagram.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.php.Quagram.model.User;
import com.php.Quagram.service.LobbyService;
import com.php.Quagram.service.UserService;

@Path("/lobby")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LobbyResource {
	
	UserService userService = new UserService();
	LobbyService lobbyService = new LobbyService();
	
	@GET
	@Path("/{instagramID}")
	public List<User> getUsersInLobby(@PathParam("instagramID") String instagramID) {
		if (!userService.contains(instagramID)) {
			// TODO: gebe Fehler-Status zurück
			return new ArrayList<User>();
		}
		
		return lobbyService.getAllLobbyUsers();
	}
	
	@POST
	@Path("/{instagramID}")
	public List<User> addUserToLobby(@PathParam("instagramID") String instagramID) {
		if (!userService.contains(instagramID)) {
			// TODO: gebe Fehler-Status zurück
			return new ArrayList<User>();
		}
		User userToAdd = userService.getUser(instagramID);
		lobbyService.addUserToLobby(userToAdd);
		return lobbyService.getAllLobbyUsers();
	}
	
	@DELETE
	@Path("/{instagramID}")
	public User deleteUserFromLobby(@PathParam("instagramID") String instagramID) {
		return lobbyService.removeUserFromLobby(instagramID);
	}
}
