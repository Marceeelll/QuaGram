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
		System.out.println("Creating statement - " + shouldInsert);
		
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			
			if (shouldInsert) {
				String sql = "insert into user values ('" + user.getInstagramID()+ "', '"  + user.getAccessToken() +"', '" + user.getSessionID() + "', " + 0 + ", '" + "NULL" + "', '"  + user.getUsername() + "', 0, 0, '" + user.getProfilePic() +"' , NULL);";
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
				
				rs.close();
				return user;
			}

		} catch(NullPointerException e) {
			
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
				String profilePictureID = rs.getString("profile_pic");
				
				User user = new User();
				user.setInstagramID(id);
				user.setAccessToken(accessToken);
				user.setSessionID(sessionID);
				user.setUsername(username);
				user.setGamesLost(gamesLost);
				user.setGamesWin(gamesWon);
				user.setProfilePic(profilePictureID);
				
				rs.close();
				return user;
			}

		} catch(NullPointerException e) {
			
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
			
			rs.close();
			return lobbyUser;

		} catch(NullPointerException e) {
			
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
			Boolean next = result.next();
			result.close();
			return next;
		} catch(NullPointerException e) {
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public Boolean isInstagramIDLoggedIn(String instagramID) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			
			String sql = "select session_id from user where instagram_id='" + instagramID + "'";
			ResultSet rs = databaseConnection.statement.executeQuery(sql);
			
			if(rs.next()) {
				String sessionID = rs.getString("session_id");
				if (sessionID == null) {
					return false;
				} else {
					return true;
				}
			}
			 
		} catch (Exception e) {
			
		}
		return false;
	}
	
	public int logoutUser(String sessionID) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			String sql = "update user set match_session_id=null, game_id=null, is_in_lobby=0, session_id=null where session_id='" + sessionID + "'";
			System.out.println(sql);
			int result = databaseConnection.statement.executeUpdate(sql);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public User getLobbyUserForInstagramUsername(String instagramUsername) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			
			String sql;
			sql = "select * from user where username='"+ instagramUsername + "' and is_in_lobby=1";
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
				
				rs.close();
				return user;
			}

		} catch(NullPointerException e) {
			
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
				
				rs.close();
				return id;
			}

		} catch(NullPointerException e) {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getUsernameForSessionID(String sessionID) {
		
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			
			String sql;
			sql = "select username from user where session_id='" + sessionID + "'";
			ResultSet rs = databaseConnection.statement.executeQuery(sql);
			
			if (rs.next()) {
				String id = rs.getString("username");
				
				rs.close();
				return id;
			}

		} catch(NullPointerException e) {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<User> getUserForMatchSessionID(String matchSessionID) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			
			String sql = "select * from user where match_session_id='" + matchSessionID + "';";
			ResultSet rs = databaseConnection.statement.executeQuery(sql);
			
			ArrayList<User> matchSessionUsers = new ArrayList<>();
			
			while (rs.next()) {
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
				matchSessionUsers.add(user);
			}
			
			rs.close();
			return matchSessionUsers;
			
		} catch(NullPointerException e) {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void addMatchSessionIDToUser(String sessionID, String matchSessionID) {
		
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			
			String sql;
			sql = "update user set match_session_id='" + matchSessionID + "' where session_id='" + sessionID + "'";
			int result = databaseConnection.statement.executeUpdate(sql);
			System.out.println("Match SessionID zu user hinzugefügt: " + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void leaveMatchSession(String sessionID, String matchSessionID) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			//TODO: ACHTUNG HIER WIRD NICHT AUF DIE MATCHSESSIONID EINGEGANGEN !!?!!
			String sql = "update user set match_session_id=null where session_id='" + sessionID +"'";
			int result = databaseConnection.statement.executeUpdate(sql);
			System.out.println("Match Session Leave-Status: " + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addGameplayIDToUser(String instagramID, String gameplayID) {
		
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			
			//TODO: vorher muss neues Gameplay erstellt werden -> Dann die gameplayID hier zum User hinzufügen
			
			String sql;
			sql = "update user set game_id='" + gameplayID + "' where instagram_id='" + instagramID + "'";
			System.out.println("VOR der EXCELPTION: sql: " +sql);
			int result = databaseConnection.statement.executeUpdate(sql);
			System.out.println("GameplayID zu user hinzugefügt: " + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void eraseGameplayIDFromUser(String instagramID) {
		
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			
			//TODO: vorher muss neues Gameplay erstellt werden -> Dann die gameplayID hier zum User hinzufügen
			
			String sql;
			sql = "update user set game_id='NULL' where instagram_id='" + instagramID + "'";
			int result = databaseConnection.statement.executeUpdate(sql);
			System.out.println("GameplayID wurd gelöscht: " + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void incrementGameLostFromUser(String sessionID) {
		int lostGames = getLostGamesFromUser(sessionID);
		if (lostGames != -1) {
			lostGames += 1;
			try {
				databaseConnection.statement = databaseConnection.connection.createStatement();
				
				String sql = "update user set games_lost=" + lostGames + " where session_id='" + sessionID + "';";
				int result = databaseConnection.statement.executeUpdate(sql);
				System.out.println("incrementGameLostFromUser: " + result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public int getLostGamesFromUser(String sessionID) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			
			String sql;
			sql = "select games_lost from user where session_id='" + sessionID + "'";
			ResultSet rs = databaseConnection.statement.executeQuery(sql);
			
			if (rs.next()) {
				int lostGames = rs.getInt("games_lost");
				
				rs.close();
				return lostGames;
			}

		} catch(NullPointerException e) {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public void incrementGameWonFromUser(String sessionID) {
		int wonGames = getWonGamesFromUser(sessionID);
		
		if (wonGames != -1) {
			wonGames += 1;
			try {
				databaseConnection.statement = databaseConnection.connection.createStatement();
				
				String sql = "update user set games_won=" + wonGames + " where session_id='" + sessionID + "';";
				int result = databaseConnection.statement.executeUpdate(sql);
				System.out.println("incrementGameLostFromUser: " + result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public int getWonGamesFromUser(String sessionID) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			
			String sql;
			sql = "select games_won from user where session_id='" + sessionID + "'";
			ResultSet rs = databaseConnection.statement.executeQuery(sql);
			
			if (rs.next()) {
				int wonGames = rs.getInt("games_won");
				
				rs.close();
				return wonGames;
			}

		} catch(NullPointerException e) {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public void setUserAttribetuToNull(String attribute, String sessionID) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			
			String sql = "update user set " + attribute + "=null where session_id='" + sessionID + "';";
			int result = databaseConnection.statement.executeUpdate(sql);
			System.out.println("incrementGameLostFromUser: " + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

