package com.php.Quagram.resources;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.php.Quagram.database.DatabaseQuagramUsers;
import com.php.Quagram.exceptions.InvitationWasSendToNonExistentUserException;
import com.php.Quagram.model.Invitation;
import com.php.Quagram.model.User;
import com.php.Quagram.service.ErrorService;
import com.php.Quagram.service.InvitationService;

@Path("/lobby")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InvitationRessource {
	InvitationService invitationService = new InvitationService();
	ErrorService errorService = new ErrorService();
	
	@GET
	@Path("{sessionID}/invitations")
	public ArrayList<Invitation> getInvitations(@PathParam("sessionID") String sessionID) {
		errorService.isSessionIDValid(sessionID);
		errorService.isUserInLobby(sessionID);
		
		return invitationService.getInvitationsForSessionID(sessionID);
	}
	
	@PUT
	@Path("{sessionID}/invite/{instagramUsername}")
	public Invitation sendInvitation(@PathParam("sessionID") String sessionID, @PathParam("instagramUsername") String instagramUsername) {
		errorService.isSessionIDValid(sessionID);
		errorService.isUserInLobby(sessionID);
		
		User instagramUserToInvite = new DatabaseQuagramUsers().getLobbyUserForInstagramUsername(instagramUsername);
		if (instagramUserToInvite == null) {
			throw new InvitationWasSendToNonExistentUserException(instagramUsername);
		}
		
		errorService.isUserAlreadyInvited(instagramUserToInvite.getInstagramID(), sessionID);
		errorService.isSendingInvitationToYourself(sessionID, instagramUsername);
		
		Invitation invitation = invitationService.sendInvitaitonToInstagramUsername(instagramUserToInvite.getUsername(), sessionID);
		
		return invitation;
	}
}
