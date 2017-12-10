package com.php.Quagram.database;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.php.Quagram.model.Invitation;
import com.php.Quagram.model.User;

public class DatabaseClass {
	private static Map<String, User> users = new HashMap<>(); // registrierte Nutzer in unserem Dienst
	private static Map<String, String> sessionUser = new HashMap<>(); // Nutzer die gerade eine Sitzung laufen haben (eingeloggt) sind 
	private static Map<String, User> lobbyUsers = new HashMap<>();
	
	private static Map<String, ArrayList<Invitation>> invitations = new HashMap<>();
	
	public DatabaseClass() {
	}
	
	public static Map<String, User> getUsers() {
		return users;
	}
	
	public static Map<String, User> getLobbyUsers() {
		return lobbyUsers;
	}
	
	public static Map<String, String> getSessionUser() {
		return sessionUser;
	}
	
	public static Map<String, ArrayList<Invitation>> getInvitations() {
		return invitations;
	}
	
	public static void appendDummyDBContent() {
		
		User userRealData = new User();
		userRealData.setAccessToken("5894207441.334bddc.563b44fb33f047f4a39525f67713f8f3");
		userRealData.setUsername("crazyc0de");
		userRealData.setGamesWin(21);
		userRealData.setGamesLost(4);
		userRealData.setInstagramID("5894207441");
		userRealData.setSessionID("a7d15657-acc1-48cd-8c3a-b674fdccbd8c");
		
		users.put(userRealData.getInstagramID(), userRealData);
		sessionUser.put(userRealData.getSessionID(), userRealData.getInstagramID());
		lobbyUsers.put(userRealData.getInstagramID(), userRealData);
		
		
		
		User user1 = new User();
		user1.setAccessToken("accessToken1");
		user1.setUsername("username1");
		user1.setGamesWin(21);
		user1.setGamesLost(4);
		user1.setInstagramID("id1");
		user1.setSessionID("sessionID1");
		
		users.put(user1.getInstagramID(), user1);
		sessionUser.put(user1.getSessionID(), user1.getInstagramID());
		lobbyUsers.put(user1.getInstagramID(), user1);
		
		Invitation invi1 = new Invitation();
		invi1.setCreated(new Date());
		invi1.setHostUserID("123-Marcel-ID-456");
		invi1.setMatchSessionID("123-Match-Session-ID-456");
		//user1.appendInvitation(invi1);
		
		
		User user2 = new User();
		user2.setAccessToken("accessToken2");
		user2.setUsername("Marceeelll");
		user2.setGamesWin(721);
		user2.setGamesLost(2);
		user2.setInstagramID("id2");
		user2.setSessionID("sessionID2");
		
		users.put(user2.getInstagramID(), user2);
		sessionUser.put(user2.getSessionID(), user2.getInstagramID());
		//lobbyUsers.put(user2.getInstagramID(), user2);
		
		
		User user3 = new User();
		user3.setAccessToken("accessToken3");
		user3.setUsername("username3");
		user3.setGamesWin(7);
		user3.setGamesLost(7);
		user3.setInstagramID("id3");
		user3.setSessionID("sessionID3");
		
		users.put(user3.getInstagramID(), user3);
		sessionUser.put(user3.getSessionID(), user3.getInstagramID());
		//lobbyUsers.put(user3.getInstagramID(), user3);
		
	}
}
