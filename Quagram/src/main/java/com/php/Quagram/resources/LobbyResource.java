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

import com.php.Quagram.database.DatabaseQuagramUsers;
import com.php.Quagram.model.User;
import com.php.Quagram.service.ErrorService;
import com.php.Quagram.service.GameplayService;
import com.php.Quagram.service.LobbyService;

@Path("/lobby")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LobbyResource {
	LobbyService lobbyService = new LobbyService();
	ErrorService errorService = new ErrorService();
	
	@GET
	@Path("/{sessionID}")
	public List<User> getUsersInLobby(@PathParam("sessionID") String sessionID) {
		errorService.isSessionIDValid(sessionID);
		errorService.isUserInLobby(sessionID);
		return lobbyService.getAllLobbyUsers();
	}
	
	@POST
	@Path("/{sessionID}")
	public List<User> addUserToLobby(@PathParam("sessionID") String sessionID) {
		errorService.isSessionIDValid(sessionID);
		lobbyService.addUserToLobby(sessionID);
		return lobbyService.getAllLobbyUsers();
	}
	
	@DELETE
	@Path("/{sessionID}")
	public String deleteUserFromLobby(@PathParam("sessionID") String sessionID) {
		errorService.isSessionIDValid(sessionID);
		lobbyService.removeUserFromLobby(sessionID);
		return "Successfully deleted";
	}
	
	
	// TODO: DELETE nur für Debugging
	@GET
	@Path("dummy")
	public String dummy() {
		ErrorService errorService = new ErrorService();
		
		String sessionIDKrazycOde = "24104284-bd6a-4471-9148-ebe1ddae575b";
		String sessionIDCrazyc0de = "250e93be-3dae-46ee-834d-15914d3e69e5";
		String userToInviteInstagramID = "5894207441";
		String matchSessionID = "3e5ec942-452f-4f56-88b4-64db05b4ae23";
		String pictureID = "20481889_271943753284770_7151381797815713792_n.jpg";
		//sessionID = "heeelooo";
		
		
		errorService.isInvitationDeclined(matchSessionID);
		return "Dummy erfolgreich ausgeführt";
	}
}






