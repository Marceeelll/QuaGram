package com.php.Quagram.resources;

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
import com.php.Quagram.service.GameplayService;
import com.php.Quagram.service.LobbyService;

@Path("/lobby")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LobbyResource {
	LobbyService lobbyService = new LobbyService();
	
	@GET
	@Path("/{sessionID}")
	public List<User> getUsersInLobby(@PathParam("sessionID") String sessionID) {
		lobbyService.isSessionIDValid(sessionID);
		return lobbyService.getAllLobbyUsers();
	}
	
	@POST
	@Path("/{sessionID}")
	public List<User> addUserToLobby(@PathParam("sessionID") String sessionID) {
		lobbyService.isSessionIDValid(sessionID);
		lobbyService.addUserToLobby(sessionID);
		return lobbyService.getAllLobbyUsers();
	}
	
	@DELETE
	@Path("/{sessionID}")
	public String deleteUserFromLobby(@PathParam("sessionID") String sessionID) {
		lobbyService.removeUserFromLobby(sessionID);
		return "Successfully deleted";
	}
	
	
	// TODO: DELETE nur für Debugging
	@GET
	@Path("dummy")
	public String dummy() {
		GameplayService service = new GameplayService();
		service.finishGameplay("4757108a-cbfa-4212-a391-b19f56843a72");
		
		//JSONObject object = service.getGameplayRoundJSON("23a80f93-ac97-4a30-bdba-54b6b8fae225", "e076c7a3-3965-4e0b-b3b1-89365c097793");
		
		//new DatabaseQuagramUsers().incrementGameWonFromUser("c85105a4-003f-4196-a928-bce873574d76");
		//new DatabaseQuagramUsers().incrementGameLostFromUser("c85105a4-003f-4196-a928-bce873574d76");
		
		return "Dummy erfolgreich ausgeführt - "; // - " + object;
	}
}






