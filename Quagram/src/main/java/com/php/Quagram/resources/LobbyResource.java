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
import com.php.Quagram.service.LobbyService;

import others.CardDownloadController;

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
		
		// GeonamesRequestService geo = new GeonamesRequestService();
		// geo.getLocationData(50.325238, 11.941379);
		
		//InstagramRequestService ig = new InstagramRequestService();
		//ig.getAllUserPictures("5894207441.334bddc.563b44fb33f047f4a39525f67713f8f3");
		
		CardDownloadController cardDownloadController = new CardDownloadController();
		cardDownloadController.downloadCardsForUserAndSafeToDB("5894207441.334bddc.563b44fb33f047f4a39525f67713f8f3", "5894207441");
		
		// IMAGE URL to download
		//String imageURL = "https://scontent.cdninstagram.com/t51.2885-15/s640x640/sh0.08/e35/25014974_528050640882518_3544772908960186368_n.jpg";
		//ig.downloadImageFromURL(imageURL);
		
//		Location location = new Location("Location Name", 1111.2222, 22222.3333);
//		Card card = new Card("123444", "www.pictureURL.de", 12212322, 2, 27.3, 112, location, 111, 555, 999);
//		
//		DatabaseQuagramCards dbCard = new DatabaseQuagramCards();
//		dbCard.addCard(card, "5894207441");
		
		return "Dummy erfolgreich ausgeführt";
	}
}






