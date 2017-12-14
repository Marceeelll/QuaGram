package com.php.Quagram.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.php.Quagram.model.User;

public class DatabaseQuagramUsers {
	private DatabaseQuagramSingleton databaseConnection = DatabaseQuagramSingleton.sharedInstance;
	
	public User addUser(User user) {
		addUser(user, true);
		return getUserForInstagramID(user.getInstagramID());
	}
	
	private void addUser(User user, Boolean shouldInsert) {
		System.out.println("Creating statement");
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			
			if (shouldInsert) {
				String sql = "insert into user values ('" + user.getInstagramID() + "', '" + user.getAccessToken() +"', '" + user.getSessionID() + "', '" + user.getUsername() + "', 0, 0, 0);";
				int result = databaseConnection.statement.executeUpdate(sql);
				System.out.println("Inserted User (addUser): " +result);
			} else {
				String sql = "update user set accessToken='" + user.getAccessToken() + "', sessionID='" + user.getSessionID() + "' where id='" + user.getInstagramID() + "'";
				int result = databaseConnection.statement.executeUpdate(sql);
				System.out.println("Updated User (addUser): " +result);
			}
		} catch (SQLException e) {
			// if contains id already -> make update instead of insert 
			addUser(user, false);
		}
	}
	
	public User getUserForSessionID(String sessionID) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			
			String sql;
			sql = "Select * from user where sessionID='"+ sessionID + "'";
			ResultSet rs = databaseConnection.statement.executeQuery(sql);
			
			if (rs.next()) {
				String id = rs.getString("id");
				String accessToken = rs.getString("accessToken");
				String username = rs.getString("username");
				int gamesLost = rs.getInt("gamesLost");
				int gamesWon = rs.getInt("gamesWon");
				
				User user = new User();
				user.setInstagramID(id);
				user.setAccessToken(accessToken);
				user.setSessionID(sessionID);
				user.setUsername(username);
				user.setGamesLost(gamesLost);
				user.setGamesWin(gamesWon);
				return user;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public User getUserForInstagramID(String instagramID) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			
			String sql;
			sql = "Select * from user where id='"+ instagramID + "'";
			ResultSet rs = databaseConnection.statement.executeQuery(sql);
			
			if (rs.next()) {
				String id = rs.getString("id");
				String accessToken = rs.getString("accessToken");
				String sessionID = rs.getString("sessionID");
				String username = rs.getString("username");
				int gamesLost = rs.getInt("gamesLost");
				int gamesWon = rs.getInt("gamesWon");
				
				User user = new User();
				user.setInstagramID(id);
				user.setAccessToken(accessToken);
				user.setSessionID(sessionID);
				user.setUsername(username);
				user.setGamesLost(gamesLost);
				user.setGamesWin(gamesWon);
				return user;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<User> getLobbyUsers() {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			
			String sql;
			sql = "select * from user where isInLobby=1";
			ResultSet rs = databaseConnection.statement.executeQuery(sql);
			
			ArrayList<User> lobbyUser = new ArrayList<>();
			
			while (rs.next()) {
				String id = rs.getString("id");
				String accessToken = rs.getString("accessToken");
				String sessionID = rs.getString("sessionID");
				String username = rs.getString("username");
				int gamesLost = rs.getInt("gamesLost");
				int gamesWon = rs.getInt("gamesWon");
				
				User user = new User();
				user.setSessionID(id);
				user.setAccessToken(accessToken);
				user.setSessionID(sessionID);
				user.setUsername(username);
				user.setGamesLost(gamesLost);
				user.setGamesWin(gamesWon);
				lobbyUser.add(user);
			}
			return lobbyUser;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
	
	public void removeUserFromLobby(String sessionID) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			
			String sql;
			sql = "update user set isInLobby=0 where sessionID='" + sessionID +"'";
			int result = databaseConnection.statement.executeUpdate(sql);
			System.out.println("Removed User to Lobby Successfully: " + result);
		} catch (Exception e) {
			System.out.println("ERROR Removing User from Lobby ");
			e.printStackTrace();
		}
	}
	
	public void addUserToLobby(String sessionID) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			
			String sql;
			sql = "update user set isInLobby=1 where sessionID='" + sessionID +"'";
			int result = databaseConnection.statement.executeUpdate(sql);
			System.out.println("Added User to Lobby Successfully: " + result);
		} catch (Exception e) {
			System.out.println("ERROR Added User to Lobby ");
			e.printStackTrace();
		}
	}
	
	public Boolean isSessionIDValid(String sessionID) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			String sql = "select * from user where sessionID='" + sessionID + "'";
			ResultSet result = databaseConnection.statement.executeQuery(sql);
			return result.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public int logoutUser(String sessionID) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			String sql = "update user set sessionID=NULL where sessionID='" + sessionID + "'";
			int result = databaseConnection.statement.executeUpdate(sql);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public User getLobbyUserForInstagramID(String instagramID) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			
			String sql;
			sql = "Select * from user where id='"+ instagramID + "' and isInLobby=1";
			ResultSet rs = databaseConnection.statement.executeQuery(sql);
			
			if (rs.next()) {
				String id = rs.getString("id");
				String accessToken = rs.getString("accessToken");
				String sessionID = rs.getString("sessionID");
				String username = rs.getString("username");
				int gamesLost = rs.getInt("gamesLost");
				int gamesWon = rs.getInt("gamesWon");
				
				User user = new User();
				user.setInstagramID(id);
				user.setAccessToken(accessToken);
				user.setSessionID(sessionID);
				user.setUsername(username);
				user.setGamesLost(gamesLost);
				user.setGamesWin(gamesWon);
				return user;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
