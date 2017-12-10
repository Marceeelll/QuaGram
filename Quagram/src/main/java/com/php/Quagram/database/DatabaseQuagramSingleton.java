package com.php.Quagram.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.php.Quagram.model.Invitation;
import com.php.Quagram.model.User;

public class DatabaseQuagramSingleton {
	public static DatabaseQuagramSingleton sharedInstance = new DatabaseQuagramSingleton();
	private DatabaseQuagramSingleton() {
		startConnection();
	}
	
	// JDBC driver name and database url
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/Quagram";
		
	// Database credentials
	static final String USER = "root";
	static final String PASS = "pmp";	
	
	Connection conn = null;
	public Statement stmt = null;
	
	private void startConnection() {
		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// Execute a query
			System.out.println("Creating statement");
			stmt = conn.createStatement();
			
			System.out.println("Connected to Database");
		} catch (SQLException e) {
			// Handle errors for JDBC
			e.printStackTrace();
		} catch (Exception f) {
			// Handle errors for Class.ForName
			f.printStackTrace();
		}
	}
	
	public void closeConnection() {
		// Clean-up environment
		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public User addUser(User user) {
		addUser(user, true);
		return getUserForInstagramID(user.getInstagramID());
	}
	
	private void addUser(User user, Boolean shouldInsert) {
		System.out.println("Creating statement");
		try {
			stmt = conn.createStatement();
			
			if (shouldInsert) {
				String sql = "insert into user values ('" + user.getInstagramID() + "', '" + user.getAccessToken() +"', '" + user.getSessionID() + "', '" + user.getUsername() + "', 0, 0, 0);";
				int result = stmt.executeUpdate(sql);
				System.out.println("Inserted User (addUser): " +result);
			} else {
				String sql = "update user set accessToken='" + user.getAccessToken() + "', sessionID='" + user.getSessionID() + "' where id='" + user.getInstagramID() + "'";
				int result = stmt.executeUpdate(sql);
				System.out.println("Updated User (addUser): " +result);
			}
		} catch (SQLException e) {
			// if contains id already -> make update instead of insert 
			addUser(user, false);
		}
	}
	
	public User getUserForSessionID(String sessionID) {
		try {
			stmt = conn.createStatement();
			
			String sql;
			sql = "Select * from user where sessionID='"+ sessionID + "'";
			ResultSet rs = stmt.executeQuery(sql);
			
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
			stmt = conn.createStatement();
			
			String sql;
			sql = "Select * from user where id='"+ instagramID + "'";
			ResultSet rs = stmt.executeQuery(sql);
			
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
			stmt = conn.createStatement();
			
			String sql;
			sql = "select * from user where isInLobby=1";
			ResultSet rs = stmt.executeQuery(sql);
			
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
			stmt = conn.createStatement();
			
			String sql;
			sql = "update user set isInLobby=0 where sessionID='" + sessionID +"'";
			int result = stmt.executeUpdate(sql);
			System.out.println("Removed User to Lobby Successfully: " + result);
		} catch (Exception e) {
			System.out.println("ERROR Removing User from Lobby ");
			e.printStackTrace();
		}
	}
	
	public void addUserToLobby(String sessionID) {
		try {
			stmt = conn.createStatement();
			
			String sql;
			sql = "update user set isInLobby=1 where sessionID='" + sessionID +"'";
			int result = stmt.executeUpdate(sql);
			System.out.println("Added User to Lobby Successfully: " + result);
		} catch (Exception e) {
			System.out.println("ERROR Added User to Lobby ");
			e.printStackTrace();
		}
	}
	
	public Boolean isSessionIDValid(String sessionID) {
		try {
			stmt = conn.createStatement();
			String sql = "select * from user where sessionID='" + sessionID + "'";
			ResultSet result = stmt.executeQuery(sql);
			return result.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public int logoutUser(String sessionID) {
		try {
			stmt = conn.createStatement();
			String sql = "update user set sessionID=NULL where sessionID='" + sessionID + "'";
			int result = stmt.executeUpdate(sql);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/*
	 * TODO: implementieren, wenn mehrere Nutzer zu einer matchSessionID hinzugefügt werden sollen
	public Invitation getInvitationForMatchSessionID(String matchSessionID) {
		try {
			stmt = conn.createStatement();
			
			String sql;
			sql = "select * from invitation where matchSessionID='" + matchSessionID + "'";
			ResultSet result = stmt.executeQuery(sql);
			
			if (result.next()) {
				Date created = result.getDate("created");
				String hostUserID = result.getString("hostUserID");
				
				Invitation invitation = new Invitation();
				invitation.setHostUserID(hostUserID);
				invitation.setMatchSessionID(matchSessionID);
				invitation.setCreated(created);
				
				return invitation;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	*/
	
	public ArrayList<Invitation> getInvitationsForInstagramID(String instagramID) {
		try {
			stmt = conn.createStatement();
			
			String sql;
			sql = "select * from invitation where instagramID='" + instagramID + "'";
			ResultSet result = stmt.executeQuery(sql);
			
			ArrayList<Invitation> invitations = new ArrayList<>();
			
			while (result.next()) {
				Date created = result.getDate("created");
				String hostUserID = result.getString("hostUserID");
				String matchSessionID = result.getString("matchSessionID");
				
				Invitation invitation = new Invitation();
				invitation.setHostUserID(hostUserID);
				invitation.setMatchSessionID(matchSessionID);
				invitation.setCreated(created);
				
				invitations.add(invitation);
			}
			return invitations;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
	
	public User getLobbyUserForInstagramID(String instagramID) {
		try {
			stmt = conn.createStatement();
			
			String sql;
			sql = "Select * from user where id='"+ instagramID + "' and isInLobby=1";
			ResultSet rs = stmt.executeQuery(sql);
			
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
	
	public void appendInvitationToUser(User userWhoGotInvitation, Invitation invitation) {
		try {
			stmt = conn.createStatement();
			
			String sql = "insert into invitation values ('" + userWhoGotInvitation.getInstagramID() + "', '" + invitation.getCreatedFormated() +"', '" + invitation.getHostUserID() + "', '" + invitation.getMatchSessionID() + "');";
			int result = stmt.executeUpdate(sql);
			System.out.println("Inserted Invitation (appendInvitationToUser): " +result);
		} catch (SQLException e) {
			// if contains id already -> make update instead of insert 
			e.printStackTrace();
		}
	}
	
}










