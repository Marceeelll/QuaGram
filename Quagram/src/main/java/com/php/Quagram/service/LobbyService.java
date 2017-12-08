package com.php.Quagram.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.php.Quagram.database.DatabaseClass;
import com.php.Quagram.model.Invitation;
import com.php.Quagram.model.User;

public class LobbyService {
	private Map<String, User> lobbyUsers = DatabaseClass.getLobbyUsers();
	private Map<String, ArrayList<Invitation>> invitationDB = DatabaseClass.getInvitations();
	
	DBManagerService dbManager = new DBManagerService();
	
	public LobbyService() {
	}
	
	public void addUserToLobby(String sessionID) {
		User user = dbManager.getUserForSessionID(sessionID);
		lobbyUsers.put(user.getInstagramID(), user);
	}
	
	public List<User> getAllLobbyUsers() {
		return new ArrayList<User>(lobbyUsers.values());
	}
	
	public User removeUserFromLobby(String sessionID) {
		User userToRemove = dbManager.getUserForSessionID(sessionID);
		return lobbyUsers.remove(userToRemove.getInstagramID());
	}
	
	
	
	
	
	
	
	public ArrayList<Invitation> getInvitationsForSessionID(String sessionID) {
		if(!dbManager.isSessionIDValid(sessionID)) {
			System.out.println("TODO--throw-Error- getInvitationsForSessionID");
			return null;
		}
		User user = dbManager.getUserForSessionID(sessionID);
		ArrayList<Invitation> invitations = invitationDB.get(user.getInstagramID());
		return invitations;
	}
	
	public Invitation sendInvitaitonToInstagramID(String instagramIDToInvite, String hostSessionID) {
		if(!dbManager.isSessionIDValid(hostSessionID)) {
			System.out.println("TODO--throw-Error- sendInvitaitonToInstagramID-1");
			return null;
		}
		User userToInvite = dbManager.getLobbyUserForInstagramID(instagramIDToInvite);
		if(userToInvite == null) {
			System.out.println("TODO--throw-Error- sendInvitaitonToInstagramID-2");
			return null;
		}
		
		User hostUser = dbManager.getUserForSessionID(hostSessionID);
		String matchSessionID = UUID.randomUUID().toString();
		
		Invitation invitation = new Invitation();
		invitation.setCreated(new Date());
		invitation.setHostUserID(hostUser.getInstagramID());
		invitation.setMatchSessionID(matchSessionID);
		
		appendInvitationToUser(userToInvite, invitation);
		
		return invitation;
	}
	
	public void appendInvitationToUser(User userWhoGotInvitation, Invitation invitation) {
		ArrayList<Invitation> invitations = invitationDB.get(userWhoGotInvitation.getInstagramID());
		invitations.add(invitation);
		invitationDB.put(userWhoGotInvitation.getInstagramID(), invitations);
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
