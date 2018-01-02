package com.php.Quagram.service;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.php.Quagram.database.DatabaseQuagramUsers;
import com.php.Quagram.model.Invitation;
import com.php.Quagram.model.User;

public class JSONClientOutput {
	public JSONArray parseUserArrayListToJSON(ArrayList<User> users) {
		JSONArray userArray = new JSONArray();
		
		for (User user: users) {
			JSONObject userObject = new JSONObject();
			userObject.put("gamesLost", user.getGamesLost());
			userObject.put("gamesWin", user.getGamesWin());
			userObject.put("username", user.getUsername());
			userArray.put(userObject);
		}
		
		
		return userArray;
	}
	
	public JSONArray parseInvitationListToJSON(ArrayList<Invitation> invitations) {
		JSONArray invitationArray = new JSONArray();
		
		DatabaseQuagramUsers userDB = new DatabaseQuagramUsers();
		
		for (Invitation invitation: invitations) {
			JSONObject invitationObject = new JSONObject();
			invitationObject.put("createdDate", invitation.getCreatedDate());
			User hostUser = userDB.getUserForInstagramID(invitation.getHostUserID());
			invitationObject.put("hostUsername", hostUser.getUsername());
			invitationObject.put("matchSessionID", invitation.getMatchSessionID());
			invitationArray.put(invitationObject);
		}
		
		return invitationArray;
	}
}
