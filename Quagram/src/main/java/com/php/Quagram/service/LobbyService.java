package com.php.Quagram.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.php.Quagram.database.DatabaseClass;
import com.php.Quagram.model.User;

public class LobbyService {
	private Map<String, User> lobbyUsers = DatabaseClass.getLobbyUsers(); // ID 		<--> 	User
																		// Username <--> 	User
	
	public LobbyService() {
		DatabaseClass dc = new DatabaseClass();
		Map<String, User> users = DatabaseClass.getUsers();
		User user = users.get("id_pa");
		System.out.println(users.toString());
		lobbyUsers.put(user.getInstagramID(), user);
	}
	
	public User addUserToLobby(User user) {
		lobbyUsers.put(user.getInstagramID(), user);
		return user;
	}
	
	public List<User> getAllLobbyUsers() {
		return new ArrayList<User>(lobbyUsers.values());
	}
	
	public User removeUserFromLobby(String instagramID) {
		return lobbyUsers.remove(instagramID);
	}
}
