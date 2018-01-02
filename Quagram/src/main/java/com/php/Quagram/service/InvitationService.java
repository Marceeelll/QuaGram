package com.php.Quagram.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import com.php.Quagram.database.DatabaseQuagramInvitations;
import com.php.Quagram.database.DatabaseQuagramUsers;
import com.php.Quagram.exceptions.SessionIDNotFoundException;
import com.php.Quagram.model.Invitation;
import com.php.Quagram.model.User;

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
	
	public Invitation sendInvitaitonToInstagramUsername(String instagramUsername, String hostSessionID) {
		if (!dbUsers.isSessionIDValid(hostSessionID)) {
			throw new SessionIDNotFoundException(hostSessionID);
		}
		
		
		User userToInvite = dbUsers.getLobbyUserForInstagramUsername(instagramUsername);
		if(userToInvite == null) {
			System.out.println("TODO--throw-Error- sendInvitaitonToInstagramID-2");
			return null;
		}
		
		User hostUser = dbUsers.getUserForSessionID(hostSessionID);
		String matchSessionID = UUID.randomUUID().toString();
		
		Invitation invitation = new Invitation();
		invitation.setCreated(new Date());
		invitation.setHostUserID(hostUser.getInstagramID());
		invitation.setMatchSessionID(matchSessionID);
		
		appendInvitationToUser(userToInvite, invitation);
		
		return invitation;
	}
	
	private void appendInvitationToUser(User userWhoGotInvitation, Invitation invitation) {
		dbInvitations.addInvitation(userWhoGotInvitation, invitation);
	}
	
}
