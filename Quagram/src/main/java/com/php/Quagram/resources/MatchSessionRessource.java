package com.php.Quagram.resources;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.php.Quagram.model.User;
import com.php.Quagram.service.ErrorService;
import com.php.Quagram.service.MatchSessionService;

@Path("/matchSession")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MatchSessionRessource {
	MatchSessionService matchSessionService = new MatchSessionService();
	ErrorService errorService = new ErrorService();
	
	@GET
	@Path("/{matchSessionID}")
	public ArrayList<User> getMatchSession(@PathParam("matchSessionID") String matchSessionID) {
		errorService.isMatchSessionValid(matchSessionID);
		
		return matchSessionService.getMatchSession(matchSessionID);
	}
	
	@DELETE
	@Path("/{sessionID}/{matchSessionID}")
	public Response deleteMatchSession(@PathParam("sessionID") String sessionID, @PathParam("matchSessionID") String matchSessionID) {
		errorService.isSessionIDValid(sessionID);
		errorService.isMatchSessionValid(matchSessionID);
		
		matchSessionService.leaveMatchSession(sessionID, matchSessionID);
		return Response.status(Status.NO_CONTENT).build();
	}
	
	@PUT
	@Path("/{sessionID}/{matchSessionID}")
	public Response answerToMatchSessionInvitation(@PathParam("sessionID") String sessionID, @PathParam("matchSessionID") String matchSessionID, @QueryParam("accepted") String accepted_status) {
		errorService.isSessionIDValid(sessionID);
		errorService.isMatchSessionValid(matchSessionID);
		errorService.isInvitationAcceptedStatusExisting(accepted_status);
		
		matchSessionService.answerToMatchSessionInvitation(sessionID, matchSessionID, accepted_status);		
		return Response.status(Status.NO_CONTENT).build();
	}
}
