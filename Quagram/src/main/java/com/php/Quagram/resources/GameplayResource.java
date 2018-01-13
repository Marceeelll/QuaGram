package com.php.Quagram.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import com.php.Quagram.service.ErrorService;
import com.php.Quagram.service.GameplayService;

@Path("/gameplay")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GameplayResource {
	GameplayService gameplayService = new GameplayService();
	ErrorService errorService = new ErrorService();
	
	@GET
	@Path("/{sessionID}/matchSession/{matchSessionID}")
	public String getGamplay(@PathParam("sessionID") String sessionID, @PathParam("matchSessionID") String matchSessionID) {
		errorService.isSessionIDValid(sessionID);
		errorService.isMatchSessionValid(matchSessionID);
		
		JSONObject gameplayJSON = gameplayService.getGameplay(sessionID, matchSessionID);
		
		return gameplayJSON.toString();
	}
	
	@POST
	@Path("/{sessionID}/matchSession/{matchSessionID}/card/{card_attribute}")
	public String postGameplayAttribute(@PathParam("sessionID") String sessionID, @PathParam("matchSessionID") String matchSessionID, @PathParam("card_attribute") String card_attribute) {
		return gameplayService.postSelectedGameplayAttribute(card_attribute, matchSessionID, sessionID);
		//return "PUT\nSessionID: " + sessionID + "\nMatchSession: " + matchSessionID + "\nCard_attribute: " + card_attribute;
	}
	
	@PUT
	@Path("/{sessionID}/matchSession/{matchSessionID}")
	public String putGameplayIDtoMatchSessionID(@PathParam("sessionID") String sessionID, @PathParam("matchSessionID") String matchSessionID) {
		gameplayService.putMatchIDtoGameplayID(sessionID, matchSessionID);
		return "Erfolgreich die matchID zur GameID gemacht";
	}
	
	// TODO: Delete methode implementieren, um Gameplay zu beenden!
}


















