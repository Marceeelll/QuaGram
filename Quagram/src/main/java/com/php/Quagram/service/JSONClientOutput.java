package com.php.Quagram.service;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.php.Quagram.model.User;

public class JSONClientOutput {
	public JSONArray doIt(ArrayList<User> users) {
		JSONArray userArray = new JSONArray();
		JSONObject userObject = new JSONObject();
		
		for (User user: users) {
			userObject.put("gamesLost", user.getGamesLost());
			userObject.put("gamesWin", user.getGamesWin());
			userObject.put("username", user.getUsername());
			userArray.put(userObject);
		}
		
		return userArray;
	}
}
