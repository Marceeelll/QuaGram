package com.php.Quagram.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.php.Quagram.service.GameplayService;

@Path("/gameplay")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GameplayResource {
	GameplayService gameplayService = new GameplayService();
	
	@GET
	@Path("/{sessionID}/matchSession/{matchSessionID}")
	public String getGamplay(@PathParam("sessionID") String sessionID, @PathParam("matchSessionID") String matchSessionID) {
		return gameplayService.getGameplay(sessionID, matchSessionID);
		//return "GET the Gameplay\nSessionID: " + sessionID +"\nMatchSessionID: " + matchSessionID;
	}
	
	@POST
	@Path("/{sessionID}/matchSession/{matchSessionID}/card/{card_attribute}")
	public String postGameplayAttribute(@PathParam("sessionID") String sessionID, @PathParam("matchSessionID") String matchSessionID, @PathParam("card_attribute") String card_attribute) {
		return gameplayService.postSelectedGameplayAttribute(card_attribute, matchSessionID, sessionID);
		//return "PUT\nSessionID: " + sessionID + "\nMatchSession: " + matchSessionID + "\nCard_attribute: " + card_attribute;
	}
}
