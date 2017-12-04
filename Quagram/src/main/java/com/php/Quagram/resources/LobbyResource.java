package com.php.Quagram.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.php.Quagram.database.DatabaseClass;
import com.php.Quagram.model.Invitation;
import com.php.Quagram.model.User;
import com.php.Quagram.service.GeonamesRequestService;
import com.php.Quagram.service.InstagramRequestService;
import com.php.Quagram.service.LobbyService;
import com.php.Quagram.servlets.LobbyTestServlet;

@Path("/lobby")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LobbyResource {
	LobbyService lobbyService = new LobbyService();
	
	// TODO: Diese Funktion löschen (IST NUR FÜR DEBUGGING), steht nicht in der Spezifikation
	@GET
	@Path("/{sessionID}")
	public List<User> getUsersInLobby(@PathParam("sessionID") String sessionID) {
		if (!lobbyService.isSessionIDValid(sessionID)) {
			// TODO: gebe Fehler-Status zurück
			System.out.println("TODO-Error-addUserToLobby()  (GET: /{sessionID})");
			return null;
		}
		
		return lobbyService.getAllLobbyUsers();
	}
	
	@PUT
	@Path("/{sessionID}")
	public List<User> addUserToLobby(@PathParam("sessionID") String sessionID) {
		if (!lobbyService.isSessionIDValid(sessionID)) {
			// TODO: gebe Fehler-Status zurück
			System.out.println("TODO-Error-addUserToLobby()  (PUT: /{sessionID})");
			return null;
		}
		
		lobbyService.addUserToLobby(sessionID);
		return lobbyService.getAllLobbyUsers();
	}
	
	@DELETE
	@Path("/{sessionID}")
	public User deleteUserFromLobby(@PathParam("sessionID") String sessionID) {
		return lobbyService.removeUserFromLobby(sessionID);
	}
	
	@GET
	@Path("{sessionID}/invitations")
	public ArrayList<Invitation> getInvitations(@PathParam("sessionID") String sessionID) {
		return lobbyService.getInvitationsForSessionID(sessionID);
	}
	
	@PUT
	@Path("{sessionID}/invite/{instagramID}")
	public Invitation sendInvitation(@PathParam("sessionID") String sessionID, @PathParam("instagramID") String instagramIDToInvite) {
		Invitation invitation = lobbyService.sendInvitaitonToInstagramID(instagramIDToInvite, sessionID);
		return invitation;
	}
	
	/*
	 * TODO: erst implementieren, nachdem wir Hernn Bachman gefragt haben
	@PUT
	@Path("{sessionID}/invite/{instagramID}/{matchSessionID}")
	public Invitation sendInvitation(@PathParam("sessionID") String sessionID, @PathParam("instagramID") String instagramIDToInvite, @PathParam("matchSessionID") String matchSessionID) {
		Invitation invitation =
		return null;
	}
	*/
	
	// TODO: DELETE nur für Debugging
	@GET
	@Path("dummy")
	public String dummy() {
		DatabaseClass.appendDummyDBContent();
		//GeonamesRequestService geo = new GeonamesRequestService();
		//geo.getLocationData(50.325238, 11.941379);
		
		InstagramRequestService ig = new InstagramRequestService();
		ig.getAllUserPictures("5894207441.334bddc.563b44fb33f047f4a39525f67713f8f3");
		//ig.getUserProfile("5894207441.334bddc.563b44fb33f047f4a39525f67713f8f3");
		
		return "Dummy Content erfolgreich hinzufügefügt";
	}
}






