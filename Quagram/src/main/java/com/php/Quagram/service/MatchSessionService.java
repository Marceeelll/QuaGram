package com.php.Quagram.service;

import java.util.ArrayList;

import com.php.Quagram.database.DatabaseQuagramUsers;
import com.php.Quagram.model.User;

public class MatchSessionService {
	DatabaseQuagramUsers dbUsers = new DatabaseQuagramUsers();
	
	public ArrayList<User> getMatchSession(String matchSessionID) {
		ArrayList<User> matchSessionUsers = dbUsers.getUserForMatchSessionID(matchSessionID);
		return matchSessionUsers;
	}
	
	public void leaveMatchSession(String sessionID, String matchSessionID) {
		dbUsers.leaveMatchSession(sessionID, matchSessionID);
	}
	
	public void answerToMatchSessionInvitation(String sessionID, String matchSessionID, String accepted_status) {
		if (accepted_status.equals("1")) {
			dbUsers.addMatchSessionIDToUser(sessionID, matchSessionID);
		} else {
			// TODO: was machen wir, wenn der Nutzer die Einladung ablehnt?
		}
	}
}
