package com.php.Quagram.service;

import java.util.ArrayList;

import com.php.Quagram.database.DatabaseQuagramInvitations;
import com.php.Quagram.database.DatabaseQuagramMatchSessionCard;
import com.php.Quagram.database.DatabaseQuagramUsers;
import com.php.Quagram.model.User;

public class MatchSessionService {
	DatabaseQuagramUsers dbUsers = new DatabaseQuagramUsers();
	DatabaseQuagramMatchSessionCard dbMatchSessionCards = new DatabaseQuagramMatchSessionCard();
	DatabaseQuagramInvitations dbInvitations = new DatabaseQuagramInvitations();
	
	public ArrayList<User> getMatchSession(String matchSessionID) {
		ArrayList<User> matchSessionUsers = dbUsers.getUserForMatchSessionID(matchSessionID);
		return matchSessionUsers;
	}
	
	public void leaveMatchSession(String sessionID, String matchSessionID) {
		dbUsers.leaveMatchSession(sessionID, matchSessionID);
		dbMatchSessionCards.deleteMatchSessionCards(matchSessionID);
		System.out.println("###### USER has LEFT the GAME!!!!! #######");
	}
	
	public void answerToMatchSessionInvitation(String sessionID, String matchSessionID, String accepted_status) {
		if ("1".equals(accepted_status)) {
			dbUsers.addMatchSessionIDToUser(sessionID, matchSessionID);
			dbInvitations.deleteInvitation(matchSessionID);
		} else if ("0".equals(accepted_status)) {
			dbInvitations.deleteInvitation(matchSessionID);
		}
	}
}
