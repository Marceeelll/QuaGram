package com.php.Quagram.service;

import java.util.ArrayList;

import com.php.Quagram.database.DatabaseQuagramInvitations;
import com.php.Quagram.database.DatabaseQuagramUsers;
import com.php.Quagram.model.User;

public class LobbyService {
	DatabaseQuagramUsers dbUsers = new DatabaseQuagramUsers();
	DatabaseQuagramInvitations dbInvitations = new DatabaseQuagramInvitations();
	
	public LobbyService() {
	}
	
	public void addUserToLobby(String sessionID) {
		dbUsers.addUserToLobby(sessionID);
	}
	
	public ArrayList<User> getAllLobbyUsersWithoutSessionID(String withoutSessionID) {
		return dbUsers.getLobbyUsersWithoutSessionID(withoutSessionID);
	}
	
	public void removeUserFromLobby(String sessionID) {
		dbUsers.removeUserFromLobby(sessionID);
	}
	
}
