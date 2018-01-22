package com.php.Quagram.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.php.Quagram.model.Invitation;
import com.php.Quagram.model.User;

public class DatabaseQuagramInvitations {
	private DatabaseQuagramSingleton databaseConnection = DatabaseQuagramSingleton.sharedInstance;

	public ArrayList<Invitation> getInvitationsForUser(String sessionID) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			
			String instagramID = new DatabaseQuagramUsers().getInstagramIDForSessionID(sessionID);
			if(instagramID == null) {
				return new ArrayList<>();
			}
			
			String sql;
			sql = "select * from invitation where receiver_id='" + instagramID + "'";
			ResultSet result = databaseConnection.statement.executeQuery(sql);
			
			ArrayList<Invitation> invitations = new ArrayList<>();
			
			while (result.next()) {
				Date created = result.getDate("created_on");
				String hostUserID = result.getString("host_user_id");
				String matchSessionID = result.getString("match_session_id");
				
				Invitation invitation = new Invitation();
				invitation.setHostUserID(hostUserID);
				invitation.setMatchSessionID(matchSessionID);
				invitation.setCreated(created);
				
				invitations.add(invitation);
			}
			return invitations;

		} catch(SQLException e) {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
	
	public ArrayList<Invitation> getInvitationsForInstagramID(String instagramID) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			
			if(instagramID == null) {
				return new ArrayList<>();
			}
			
			String sql;
			sql = "select * from invitation where receiver_id='" + instagramID + "'";
			ResultSet result = databaseConnection.statement.executeQuery(sql);
			
			ArrayList<Invitation> invitations = new ArrayList<>();
			
			while (result.next()) {
				Date created = result.getDate("created_on");
				String hostUserID = result.getString("host_user_id");
				String matchSessionID = result.getString("match_session_id");
				
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
	
	
	public void addInvitation(User receiver, Invitation invitation) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			String sql;
			
			sql = "insert into invitation values (";
			sql += "'" + invitation.getCreatedFormated() + "',";
			sql += "'" + receiver.getInstagramID() + "',";
			sql += "'" + invitation.getHostUserID() + "',";
			sql += "'" + invitation.getMatchSessionID() + "'";
			sql += ")";
			
			int result = databaseConnection.statement.executeUpdate(sql);
			System.out.println("Inserted Invitation (addInvitation): " +result);
		} catch (SQLException e) { 
			e.printStackTrace();
		}
	}
	
	public void deleteInvitation(User host, User receiver) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			String sql;
			
			sql = "delete from invitation where ";
			sql += "host_user_id='" + host.getInstagramID() + "' and ";
			sql += "receiver_id='" + receiver.getInstagramID() + "'";
			sql += ")";
			
			int result = databaseConnection.statement.executeUpdate(sql);
			System.out.println("Delete Invitation (deleteInvitation): " + result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteInvitation(String matchSessionID) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			String sql;
			
			sql = "delete from invitation where ";
			sql += "match_session_id='" + matchSessionID + "'";
			
			int result = databaseConnection.statement.executeUpdate(sql);
			System.out.println("Delete Invitation (deleteInvitation): " + result);
		} catch (SQLException e) { 
			e.printStackTrace();
		}
	}
	
	public void deleteAllInvitationsFromUserID(String userID) {
		deleteInvitationWithHostID(userID);
		deleteInvitationWithReceiverID(userID);
	}
	
	public void deleteInvitationWithReceiverID(String receiverID) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			String sql;
			
			sql = "delete from invitation where ";
			sql += "receiver_id='" + receiverID + "'";
			
			int result = databaseConnection.statement.executeUpdate(sql);
			System.out.println("Delete Invitation (deleteInvitation): " + result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteInvitationWithHostID(String hostID) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			String sql;
			
			sql = "delete from invitation where ";
			sql += "host_user_id='" + hostID + "'";
			
			int result = databaseConnection.statement.executeUpdate(sql);
			System.out.println("Delete Invitation (deleteInvitation): " + result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean doesInvitationExist(String matchSessionID) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			
			String sql = "select * from invitation where match_session_id='" + matchSessionID +"'";
			ResultSet rs = databaseConnection.statement.executeQuery(sql);
			
			return rs.next();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean doesInvitationExist(String hostID, String receiverID) {
		
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			
			String sql;
			sql = "select * from invitation where host_user_id='"+ hostID + "' and receiver_id='" + receiverID + "'";
			ResultSet rs = databaseConnection.statement.executeQuery(sql);
			
			return rs.next();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
