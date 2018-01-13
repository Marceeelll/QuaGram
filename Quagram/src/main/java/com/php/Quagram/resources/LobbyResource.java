package com.php.Quagram.resources;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;

import com.php.Quagram.model.User;
import com.php.Quagram.service.ErrorService;
import com.php.Quagram.service.JSONClientOutput;
import com.php.Quagram.service.LobbyService;

@Path("/lobby")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LobbyResource {
	LobbyService lobbyService = new LobbyService();
	ErrorService errorService = new ErrorService();
	
	@GET
	@Path("/{sessionID}")
	public ArrayList<User> getUsersInLobby(@PathParam("sessionID") String sessionID) {
		errorService.isSessionIDValid(sessionID);
		errorService.isUserInLobby(sessionID);
		
		ArrayList<User> users = lobbyService.getAllLobbyUsers();
		return users;
	}
	
	@PUT
	@Path("/{sessionID}")
	public ArrayList<User> addUserToLobby(@PathParam("sessionID") String sessionID) {
		errorService.isSessionIDValid(sessionID);
		lobbyService.addUserToLobby(sessionID);
		
		ArrayList<User> users = lobbyService.getAllLobbyUsers();
		return users;
	}
	
	@DELETE
	@Path("/{sessionID}")
	public Response deleteUserFromLobby(@PathParam("sessionID") String sessionID) {
		errorService.isSessionIDValid(sessionID);
		errorService.isUserInLobby(sessionID);
		lobbyService.removeUserFromLobby(sessionID);
		
		return Response.status(Status.NO_CONTENT).build();
	}
	
	
	// TODO: DELETE nur für Debugging
	@GET
	@Path("dummy")
	public String dummy() {
		return "Dummy erfolgreich ausgeführt";
	}
}


