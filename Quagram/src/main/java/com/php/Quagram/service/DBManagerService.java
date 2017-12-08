package com.php.Quagram.service;

import java.util.Map;

import com.php.Quagram.database.DatabaseClass;
import com.php.Quagram.model.User;

public class DBManagerService {
	private Map<String, User> lobbyUsers = DatabaseClass.getLobbyUsers();
	private Map<String, User> users = DatabaseClass.getUsers();
	private Map<String, String> sessionUser = DatabaseClass.getSessionUser();
	
	public Boolean isSessionIDValid(String sessionID) {
		return sessionUser.containsKey(sessionID);
	}
	
	public User getUserForSessionID(String sessionID) {
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
	
	public User getLobbyUserForInstagramID(String instagramID) {
		for(User user: lobbyUsers.values()) {
			if (user.getInstagramID().equals(instagramID)) {
				return user;
			}
		}
		return null;
	}
}
