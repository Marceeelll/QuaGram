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
			
			//Get InstagramID for SessionID
			String instagramID = new DatabaseQuagramUsers().getInstagramIDForSessionID(sessionID);
			System.out.println(instagramID);
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
	//TODO: Wenn Spieler schon eingeladen wurde, darf die Einladung nicht noch einmal eingetragen werden
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
			// if contains id already -> make update instead of insert 
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
			// if contains id already -> make update instead of insert 
			e.printStackTrace();
		}
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
}
