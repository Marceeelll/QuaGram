package com.php.Quagram.database;

import java.util.HashMap;
import java.util.Map;

import com.php.Quagram.model.User;

public class DatabaseClass {
	private static Map<String, User> users = new HashMap<>();
	
	public static Map<String, User> getUsers() {
		return users;
	}
}
