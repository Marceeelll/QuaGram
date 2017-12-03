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
	private Map<String, User> users = DatabaseClass.getUsers();
	private Map<String, String> sessionUser = DatabaseClass.getSessionUser();
	
	public LobbyService() {
	}
	
	public void addUserToLobby(String sessionID) {
		User user = getUserForSessionID(sessionID);
		lobbyUsers.put(user.getInstagramID(), user);
	}
	
	public List<User> getAllLobbyUsers() {
		return new ArrayList<User>(lobbyUsers.values());
	}
	
	public User removeUserFromLobby(String sessionID) {
		User userToRemove = getUserForSessionID(sessionID);
		return lobbyUsers.remove(userToRemove.getInstagramID());
	}
	
	public Boolean isSessionIDValid(String sessionID) {
		return sessionUser.containsKey(sessionID);
	}
	
	private User getUserForSessionID(String sessionID) {
		String userID = sessionUser.get(sessionID);
		if (userID == null) {
			System.out.println("TODO--throw-Error- getUserForSessionID");
			return null;
		}
		User user = users.get(userID);
		return user;
	}
	
	public void removeUserForSessionID(String sessionID) {
		// TODO: implementieren
	}
	
	public ArrayList<Invitation> getInvitationsForSessionID(String sessionID) {
		if(!isSessionIDValid(sessionID)) {
			System.out.println("TODO--throw-Error- getInvitationsForSessionID");
			return null;
		}
		User user = getUserForSessionID(sessionID);
		return user.getInvitations();
	}
	
	public Invitation sendInvitaitonToInstagramID(String instagramIDToInvite, String hostSessionID) {
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
		String matchSessionID = UUID.randomUUID().toString();
		
		Invitation invitation = new Invitation();
		invitation.setCreated(new Date());
		invitation.setHostUserID(hostUser.getInstagramID());
		invitation.setMatchSessionID(matchSessionID);
		
		userToInvite.appendInvitation(invitation);
		
		return invitation;
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
	
	
	private User getLobbyUserForInstagramID(String instagramID) {
		for(User user: lobbyUsers.values()) {
			if (user.getInstagramID().equals(instagramID)) {
				return user;
			}
		}
		return null;
	}
}
