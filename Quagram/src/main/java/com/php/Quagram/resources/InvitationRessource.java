package com.php.Quagram.resources;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.php.Quagram.model.Invitation;
import com.php.Quagram.service.InvitationService;

@Path("/lobby")
public class InvitationRessource {
	InvitationService invitationService = new InvitationService();
	
	@GET
	@Path("{sessionID}/invitations")
	public ArrayList<Invitation> getInvitations(@PathParam("sessionID") String sessionID) {
		return invitationService.getInvitationsForSessionID(sessionID);
	}
	
	@PUT
	@Path("{sessionID}/invite/{instagramID}")
	public Invitation sendInvitation(@PathParam("sessionID") String sessionID, @PathParam("instagramID") String instagramIDToInvite) {
		Invitation invitation = invitationService.sendInvitaitonToInstagramID(instagramIDToInvite, sessionID);
		return invitation;
	}
}
