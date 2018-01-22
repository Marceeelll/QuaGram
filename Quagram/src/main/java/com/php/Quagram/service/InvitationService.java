package com.php.Quagram.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import javax.ws.rs.core.UriInfo;

import org.json.JSONObject;

import com.php.Quagram.database.DatabaseQuagramInvitations;
import com.php.Quagram.database.DatabaseQuagramUsers;
import com.php.Quagram.exceptions.SessionIDNotFoundException;
import com.php.Quagram.model.Invitation;
import com.php.Quagram.model.User;
import com.php.Quagram.resources.InvitationRessource;
import com.php.Quagram.resources.MatchSessionRessource;

public class InvitationService {
	DatabaseQuagramUsers dbUsers = new DatabaseQuagramUsers();
	DatabaseQuagramInvitations dbInvitations = new DatabaseQuagramInvitations();
	
	public void isSessionIDValid(String sessionID) {
		if (!dbUsers.isSessionIDValid(sessionID)) {
			throw new SessionIDNotFoundException(sessionID);
		}
	}	
	
	public ArrayList<Invitation> getInvitationsForSessionID(String sessionID) {
		if (!dbUsers.isSessionIDValid(sessionID)) {
			throw new SessionIDNotFoundException(sessionID);
		}
		
		ArrayList<Invitation> invitations = dbInvitations.getInvitationsForUser(sessionID);
		
		return invitations;
	}
	
	public JSONObject getInvitationForSessionIDAndHostName(String sessionID, String hostUsername, UriInfo uriInfo) {
		ArrayList<Invitation> invitations = getInvitationsForSessionID(sessionID);
		
		for(Invitation invitation: invitations) {
			User user = dbUsers.getUserForInstagramID(invitation.getHostUserID());
			if (user.getUsername().equals(hostUsername)) {
				appendHypermediaToInvitation(invitation, uriInfo, sessionID);
				return new JSONClientOutput().parseInvitationToJSON(invitation);
			}
		}
		
		return null;
	}
	
	public Invitation sendInvitaitonToInstagramUsername(String instagramUsername, String hostSessionID) {
		if (!dbUsers.isSessionIDValid(hostSessionID)) {
			throw new SessionIDNotFoundException(hostSessionID);
		}
		
		
		User userToInvite = dbUsers.getLobbyUserForInstagramUsername(instagramUsername);
		if(userToInvite == null) {
			return null;
		}
		
		User hostUser = dbUsers.getUserForSessionID(hostSessionID);
		String matchSessionID = UUID.randomUUID().toString();
		
		Invitation invitation = new Invitation();
		invitation.setCreated(new Date());
		invitation.setHostUserID(hostUser.getInstagramID());
		invitation.setMatchSessionID(matchSessionID);
		
		dbUsers.addMatchSessionIDToUser(hostSessionID, matchSessionID);
		appendInvitationToUser(userToInvite, invitation);
		
		return invitation;
	}
	
	private void appendInvitationToUser(User userWhoGotInvitation, Invitation invitation) {
		dbInvitations.addInvitation(userWhoGotInvitation, invitation);
	}
	
	public void appendHypermediaToInvitations(ArrayList<Invitation> invitations, UriInfo uriInfo, String sessionID) {
		for(Invitation invitation: invitations) {
			appendHypermediaToInvitation(invitation, uriInfo, sessionID);
		}
	}
	
	public void appendHypermediaToInvitation(Invitation invitation, UriInfo uriInfo, String sessionID) {
		// self
		User hostUser = dbUsers.getUserForInstagramID(invitation.getHostUserID());
		String selfURI = uriInfo.getBaseUriBuilder().path(InvitationRessource.class).path(sessionID).path("invitation").path(hostUser.getUsername()).build().toString();
		invitation.addLink(selfURI, "self", "GET");
					
		// matchSession
		String matchSessionURI = uriInfo.getBaseUriBuilder().path(MatchSessionRessource.class).path(invitation.getMatchSessionID()).build().toString();
		invitation.addLink(matchSessionURI, "matchSession", "GET");
					
		// acceptInvitation
		String acceptInvitationURI = uriInfo.getBaseUriBuilder().path(MatchSessionRessource.class).path(sessionID).path(invitation.getMatchSessionID()).queryParam("accepted", "1").build().toString();
		invitation.addLink(acceptInvitationURI, "acceptInvitation", "PUT");
					
		// declinedInvitation
		String declinedInvitationURI = uriInfo.getBaseUriBuilder().path(MatchSessionRessource.class).path(sessionID).path(invitation.getMatchSessionID()).queryParam("accepted", "0").build().toString();
		invitation.addLink(declinedInvitationURI, "declineInvitation", "PUT");
	}
	
}
