package com.php.Quagram.service;

import java.util.UUID;

import org.json.JSONObject;

import com.php.Quagram.model.User;

public class JSONService {
	public User parseUserAfterLogin(String respond) {
		JSONObject object = new JSONObject(respond);
		String accessToken = object.getString("access_token");
		
		JSONObject jsonUserObject = object.getJSONObject("user");
		String userInstagramID = jsonUserObject.getString("id");
		String username = jsonUserObject.getString("username");
		String profilePictureURL = jsonUserObject.getString("profile_picture");
		
		User user = new User();
		user.setAccessToken(accessToken);
		user.setUsername(username);
		user.setInstagramID(userInstagramID);
		UUID sessionID = UUID.randomUUID();
		user.setSessionID(sessionID.toString());
		// TODO: profilePictureURL wird noch nicht verwendet
		
		return user;
	}
}
