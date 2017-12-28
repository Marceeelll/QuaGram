package com.php.Quagram.service;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

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
		
		for (Invitation invitation: invitations) {
			JSONObject invitationObject = new JSONObject();
			invitationObject.put("createdDate", invitation.getCreated());
			invitationObject.put("hostUserID", invitation.getHostUserID());
			invitationObject.put("matchSessionID", invitation.getMatchSessionID());
		}
		
		return invitationArray;
	}
}
