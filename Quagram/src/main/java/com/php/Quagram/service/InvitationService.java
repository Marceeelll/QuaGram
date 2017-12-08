package com.php.Quagram.service;

import java.util.Map;

import com.php.Quagram.database.DatabaseClass;
import com.php.Quagram.model.User;

public class InvitationService {
	
	private Map<String, String> sessionUser = DatabaseClass.getSessionUser();
	private Map<String, User> users = DatabaseClass.getUsers();
	
	private User getUserForSessionID(String sessionID) {
		String userID = sessionUser.get(sessionID);
		if (userID == null) {
			System.out.println("TODO--throw-Error- getUserForSessionID");
			return null;
		}
		User user = users.get(userID);
		return user;
	}
	
}
