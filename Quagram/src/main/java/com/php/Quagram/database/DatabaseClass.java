package com.php.Quagram.database;

import java.util.HashMap;
import java.util.Map;

import com.php.Quagram.model.User;

public class DatabaseClass {
	private static Map<String, User> users = new HashMap<>(); // registrierte Nutzer in unserem Dienst
	private static Map<String, String> sessionUser = new HashMap<>(); // Nutzer die gerade eine Sitzung laufen haben (eingeloggt) sind 
	private static Map<String, User> lobbyUsers = new HashMap<>();
	
	public DatabaseClass() {
	}
	
	public static Map<String, User> getUsers() {
		appendDummyDBContent();
		return users;
	}
	
	public static Map<String, User> getLobbyUsers() {
		return lobbyUsers;
	}
	
	public static Map<String, String> getSessionUser() {
		return sessionUser;
	}
	
	public static void appendDummyDBContent() {
		User user1 = new User();
		user1.setAccessToken("5894207441.334bddc.563b44fb33f047f4a39525f67713f8f3");
		user1.setUsername("crazyc0de");
		user1.setGamesWin(21);
		user1.setGamesLost(4);
		user1.setInstagramID("5894207441");
		user1.setSessionID("a7d15657-acc1-48cd-8c3a-b674fdccbd8c");
		users.put(user1.getInstagramID(), user1);
	}
}
