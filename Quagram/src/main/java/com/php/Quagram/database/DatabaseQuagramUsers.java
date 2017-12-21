package com.php.Quagram.database;

import java.io.File;
import java.net.URL;
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
				String sql = "insert into user values ('" + user.getInstagramID()+ "', '"  + user.getAccessToken() +"', '" + user.getSessionID() + "', " + 0 + ", '" + "NULL" + "', '"  + user.getUsername() + "', 0, 0," + user.getProfilePic() +", NULL);";      
				int result = databaseConnection.statement.executeUpdate(sql);
				System.out.println("Inserted User (addUser): " +result);
			} else {
				String sql = "update user set access_token='" + user.getAccessToken() + "', session_id='" + user.getSessionID() + "', profile_pic='" + user.getProfilePic() + "' where instagram_id='" + user.getInstagramID() + "'";
				System.out.println("sql: " + sql);
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
			sql = "select * from user where session_id='"+ sessionID + "'";
			ResultSet rs = databaseConnection.statement.executeQuery(sql);
			
			if (rs.next()) {
				String id = rs.getString("instagram_id");
				String accessToken = rs.getString("access_token");
				String username = rs.getString("username");
				int gamesLost = rs.getInt("games_lost");
				int gamesWon = rs.getInt("games_won");
				
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
			sql = "select * from user where instagram_id='"+ instagramID + "'";
			ResultSet rs = databaseConnection.statement.executeQuery(sql);
			
			if (rs.next()) {
				String id = rs.getString("instagram_id");
				String accessToken = rs.getString("access_token");
				String sessionID = rs.getString("session_id");
				String username = rs.getString("username");
				int gamesLost = rs.getInt("games_lost");
				int gamesWon = rs.getInt("games_won");
				
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
			sql = "select * from user where is_in_lobby=1";
			ResultSet rs = databaseConnection.statement.executeQuery(sql);
			
			ArrayList<User> lobbyUser = new ArrayList<>();
			
			while (rs.next()) {
				String id = rs.getString("instagram_id");
				String accessToken = rs.getString("access_token");
				String sessionID = rs.getString("session_id");
				String username = rs.getString("username");
				int gamesLost = rs.getInt("games_lost");
				int gamesWon = rs.getInt("games_won");
				
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
			sql = "update user set is_in_lobby=0 where session_id='" + sessionID +"'";
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
			sql = "update user set is_in_lobby=1 where session_id='" + sessionID +"'";
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
			String sql = "select * from user where session_id='" + sessionID + "'";
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
			String sql = "update user set session_id=NULL where session_id='" + sessionID + "'";
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
			sql = "select * from user where instagram_id='"+ instagramID + "' and is_in_lobby=1";
			ResultSet rs = databaseConnection.statement.executeQuery(sql);
			
			if (rs.next()) {
				String id = rs.getString("instagram_id");
				String accessToken = rs.getString("access_token");
				String sessionID = rs.getString("session_id");
				String username = rs.getString("username");
				int gamesLost = rs.getInt("games_lost");
				int gamesWon = rs.getInt("games_won");
				
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
	
	public String getInstagramIDForSessionID(String sessionID) {
		
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			
			String sql;
			sql = "select instagram_id from user where session_id='" + sessionID + "'";
			ResultSet rs = databaseConnection.statement.executeQuery(sql);
			
			if (rs.next()) {
				String id = rs.getString("instagram_id");
				
				return id;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void addMatchSessionIDToUser(String sessionID, String matchSessionID) {
		
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			
			//TODO: vorher muss eine MatchSessionID generiert werden
			
			String sql;
			sql = "update user set match_session_id='" + matchSessionID + "' where session_id='" + sessionID + "'";
			int result = databaseConnection.statement.executeUpdate(sql);
			System.out.println("Match SessionID zu user hinzugefügt: " + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addGameplayIDToUser(String instagramID, String gameplayID) {
		
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			
			//TODO: vorher muss neues Gameplay erstellt werden -> Dann die gameplayID hier zum User hinzufügen
			
			String sql;
			sql = "update user set gameplay_id='" + gameplayID + "' where instagram_id='" + instagramID + "'";
			int result = databaseConnection.statement.executeUpdate(sql);
			System.out.println("GameplayID zu user hinzugefügt: " + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
