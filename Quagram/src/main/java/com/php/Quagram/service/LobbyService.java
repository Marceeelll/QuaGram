package com.php.Quagram.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.php.Quagram.database.DatabaseQuagramSingleton;
import com.php.Quagram.model.Invitation;
import com.php.Quagram.model.User;

public class LobbyService {
	
	public LobbyService() {
	}
	
	
	public void addUserToLobby(String sessionID) {
		DatabaseQuagramSingleton.sharedInstance.addUserToLobby(sessionID);
	}
	
	public List<User> getAllLobbyUsers() {
		return DatabaseQuagramSingleton.sharedInstance.getLobbyUsers();
	}
	
	public void removeUserFromLobby(String sessionID) {
		DatabaseQuagramSingleton.sharedInstance.removeUserFromLobby(sessionID);
	}
	
	public Boolean isSessionIDValid(String sessionID) {
		return DatabaseQuagramSingleton.sharedInstance.isSessionIDValid(sessionID);
	}
	
	
	
	
	
	public ArrayList<Invitation> getInvitationsForSessionID(String sessionID) {
		if(!DatabaseQuagramSingleton.sharedInstance.isSessionIDValid(sessionID)) {
			System.out.println("TODO--throw-Error- getInvitationsForSessionID");
			return null;
		}
		
		User user = DatabaseQuagramSingleton.sharedInstance.getUserForSessionID(sessionID);
		ArrayList<Invitation> invitations = DatabaseQuagramSingleton.sharedInstance.getInvitationsForInstagramID(user.getInstagramID());
		return invitations;
	}
	
	public Invitation sendInvitaitonToInstagramID(String instagramIDToInvite, String hostSessionID) {
		
		if(!DatabaseQuagramSingleton.sharedInstance.isSessionIDValid(hostSessionID)) {
			System.out.println("TODO--throw-Error- sendInvitaitonToInstagramID-1");
			return null;
		}
		
		
		User userToInvite = DatabaseQuagramSingleton.sharedInstance.getLobbyUserForInstagramID(instagramIDToInvite);
		if(userToInvite == null) {
			System.out.println("TODO--throw-Error- sendInvitaitonToInstagramID-2");
			return null;
		}
		
		User hostUser = DatabaseQuagramSingleton.sharedInstance.getUserForSessionID(hostSessionID);
		String matchSessionID = UUID.randomUUID().toString();
		
		Invitation invitation = new Invitation();
		invitation.setCreated(new Date());
		invitation.setHostUserID(hostUser.getInstagramID());
		invitation.setMatchSessionID(matchSessionID);
		
		appendInvitationToUser(userToInvite, invitation);
		
		return invitation;
	}
	
	private void appendInvitationToUser(User userWhoGotInvitation, Invitation invitation) {
		//ArrayList<Invitation> invitations = invitationDB.get(userWhoGotInvitation.getInstagramID());
		//invitations.add(invitation);
		//invitationDB.put(userWhoGotInvitation.getInstagramID(), invitations);
		DatabaseQuagramSingleton.sharedInstance.appendInvitationToUser(userWhoGotInvitation, invitation);
	}
	
	/*
	 * Implementieren nachdem wir Herrn Bachman gefragt haben:
	 * 		Werden die Einladungen an den Nutzer gehängt
	 * 		Wird die matchSessionID (die jemand eröffnet hat) in eine neue ressource gespeichert oder kann man die zum Nutzer hinzufügen (Ein Nutzer kann maximal 1 matchSession gleichzeitig starten)
	public Invitation sendInvitationToInstagramID(String instagramIDToInvite, String hostSessionID, String matchSessionID) {
		if(!isSessionIDValid(hostSessionID)) {
			System.out.println("TODO--throw-Error- sendInvitaitonToInstagramID-1");
			return null;
		}
		User userToInvite = getLobbyUserForInstagramID(instagramIDToInvite);
		if(userToInvite == null) {
			System.out.println("TODO--throw-Error- sendInvitaitonToInstagramID-2");
			return null;
		}
		
		User hostUser = getUserForSessionID(hostSessionID);
		
		if (matchSessionID == null) {
			matchSessionID = UUID.randomUUID().toString();
			
			Invitation invitation = new Invitation();
			invitation.setCreated(new Date());
			invitation.setHostUserID(hostUser.getInstagramID());
			invitation.setMatchSessionID(matchSessionID);
			
			userToInvite.appendInvitation(invitation);
			return invitation;
		} else {
			userToInvite.appendInvitation(invitation);
		}
		
		return null;
	}
	*/
	
	
	
}
