package com.php.Quagram.database;

import java.util.HashMap;
import java.util.Map;

import com.php.Quagram.model.User;

public class DatabaseClass {
	private static Map<String, User> users = new HashMap<>(); // registrierte Nutzer in unserem Dienst
	private static Map<String, User> lobbyUsers = new HashMap<>();
	
	public DatabaseClass() {
		User user1 = new User("id_ma", "xxaccessToookenxxx");
		user1.setName("Marcel");
		users.put(user1.getInstagramID(), user1);
		
		User user2 = new User("id_pa", "xxaccessToookenxxx2222");
		user2.setName("Patrick");
		users.put(user2.getInstagramID(), user2);
		
		User user3 = new User("id_pi", "xxaccessToookenxxx3333");
		user3.setName("Philipp");
		users.put(user3.getInstagramID(), user3);
		
		System.out.println("Hello: " +users.toString());
	}
	
	public static Map<String, User> getUsers() {
		return users;
	}
	
	public static Map<String, User> getLobbyUsers() {
		return lobbyUsers;
	}
}
