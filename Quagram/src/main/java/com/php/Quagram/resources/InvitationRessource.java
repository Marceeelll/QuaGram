package com.php.Quagram.resources;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.json.JSONObject;

import com.php.Quagram.database.DatabaseQuagramUsers;
import com.php.Quagram.exceptions.InvitationWasSendToNonExistentUserException;
import com.php.Quagram.model.Invitation;
import com.php.Quagram.model.User;
import com.php.Quagram.service.ErrorService;
import com.php.Quagram.service.InvitationService;
import com.php.Quagram.service.JSONClientOutput;

@Path("/lobby")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InvitationRessource {
	InvitationService invitationService = new InvitationService();
	ErrorService errorService = new ErrorService();
	
	@GET
	@Path("{sessionID}/invitation/{hostUsername}")
	public String getInvitation(@PathParam("sessionID") String sessionID, @PathParam("hostUsername") String hostUsername, @Context UriInfo uriInfo) {
		errorService.isSessionIDValid(sessionID);
		errorService.isUserInLobby(sessionID);
		
		JSONObject invitationJSON = invitationService.getInvitationForSessionIDAndHostName(sessionID, hostUsername, uriInfo);
		
		return invitationJSON.toString();
	}
	
	@GET
	@Path("{sessionID}/invitations")
	public String getInvitations(@PathParam("sessionID") String sessionID, @Context UriInfo uriInfo) {
		errorService.isSessionIDValid(sessionID);
		errorService.isUserInLobby(sessionID);
		
		ArrayList<Invitation> invitations = invitationService.getInvitationsForSessionID(sessionID);
		invitationService.appendHypermediaToInvitations(invitations, uriInfo, sessionID);
		
		return new JSONClientOutput().parseInvitationListToJSON(invitations).toString();
	}
	
	@PUT
	@Path("{sessionID}/invite/{instagramUsername}")
	public String sendInvitation(@PathParam("sessionID") String sessionID, @PathParam("instagramUsername") String instagramUsername, @Context UriInfo uriInfo) {
		errorService.isSessionIDValid(sessionID);
		errorService.isUserInLobby(sessionID);
		
		User instagramUserToInvite = new DatabaseQuagramUsers().getLobbyUserForInstagramUsername(instagramUsername);
		if (instagramUserToInvite == null) {
			throw new InvitationWasSendToNonExistentUserException(instagramUsername);
		}
		
		errorService.isUserAlreadyInvited(instagramUserToInvite.getInstagramID(), sessionID);
		errorService.isSendingInvitationToYourself(sessionID, instagramUsername);
		
		Invitation invitation = invitationService.sendInvitaitonToInstagramUsername(instagramUserToInvite.getUsername(), sessionID);
		invitationService.appendHypermediaToInvitation(invitation, uriInfo, sessionID);
		
		return new JSONClientOutput().parseInvitationToJSON(invitation).toString();
	}
}
