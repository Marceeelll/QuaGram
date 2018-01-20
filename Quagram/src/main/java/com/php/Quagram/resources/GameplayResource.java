package com.php.Quagram.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import com.php.Quagram.database.DatabaseQuagramUsers;
import com.php.Quagram.model.User;
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
		errorService.isSessionIDValid(sessionID);
		errorService.isMatchSessionValid(matchSessionID);
		errorService.isGameplayOver(sessionID, matchSessionID);
		User userWhoWantsToSayTheAttribute = new DatabaseQuagramUsers().getUserForSessionID(sessionID);
		errorService.isUserInTurnForGameplay(matchSessionID, userWhoWantsToSayTheAttribute.getInstagramID());
		errorService.isGameplayCardAttributeValid(card_attribute);
		
		gameplayService.postSelectedGameplayAttribute(card_attribute, matchSessionID, sessionID);
		
		JSONObject gameplayJSON = gameplayService.getGameplay(sessionID, matchSessionID);
		return gameplayJSON.toString();
	}
	
	// TODO: Delete methode implementieren, um Gameplay zu beenden!
	// TODO: Keine Nutzer in Gameplay Exception werfen!
}


















