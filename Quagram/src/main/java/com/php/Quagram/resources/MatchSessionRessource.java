package com.php.Quagram.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.php.Quagram.service.MatchSessionService;

@Path("/matchSession")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MatchSessionRessource {
	MatchSessionService matchSessionService = new MatchSessionService();
	
	@GET
	@Path("/{matchSessionID}")
	public String getMatchSession(@PathParam("matchSessionID") String matchSessionID) {
		return "GET the MatchSession - " + matchSessionID;
	}
	
	@DELETE
	@Path("/{matchSessionID}")
	public String deleteMatchSession(@PathParam("matchSessionID") String matchSessionID) {
		return "DELETE the MatchSession - " + matchSessionID;
	}
	
	@PUT
	@Path("/{sessionID}/{matchSessionID}/{accepted_status}")
	public String addUserToLobby(@PathParam("sessionID") String sessionID, @PathParam("matchSessionID") String matchSessionID, @PathParam("accepted_status") String accepted_status) {
		return "PUT\nSessionID: " + sessionID + "\nMatchSession: " + matchSessionID + "\nAccepted_Status: " + accepted_status;
	}
}
