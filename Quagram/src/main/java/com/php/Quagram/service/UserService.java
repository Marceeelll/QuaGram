package com.php.Quagram.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.php.Quagram.database.DatabaseClass;
import com.php.Quagram.model.User;

public class UserService {
	private Map<String, User> users = DatabaseClass.getUsers();
	
	public UserService() {
		addUser("aiuzghj_hhj123jhk");
	}
	
	public User addUser(String accessToken) {
		User newUser = new User();
		newUser.setAccessToken(accessToken);
		users.put(accessToken, newUser);
		return newUser;
	}
	
	public List<User> getAllUsers() {
		return new ArrayList<User>(users.values());
	}
	
	public Boolean contains(String userID) {
		users.containsKey(userID);
		return true;
	}
	
	public User getUser(String instagramID) {
		return users.get(instagramID);
	}
}
