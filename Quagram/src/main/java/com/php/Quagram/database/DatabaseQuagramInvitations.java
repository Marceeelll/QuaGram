package com.php.Quagram.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.php.Quagram.model.Invitation;
import com.php.Quagram.model.User;

public class DatabaseQuagramInvitations {
	private DatabaseQuagramSingleton databaseConnection = DatabaseQuagramSingleton.sharedInstance;

	public ArrayList<Invitation> getInvitationsForInstagramID(String instagramID) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			
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
	
	public void appendInvitationToUser(User userWhoGotInvitation, Invitation invitation) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			String sql = "insert into invitation values ('" + invitation.getCreated() + userWhoGotInvitation.getInstagramID() +"', '" + invitation.getHostUserID() + "', '" + invitation.getMatchSessionID() + "');";
			int result = databaseConnection.statement.executeUpdate(sql);
			System.out.println("Inserted Invitation (appendInvitationToUser): " +result);
		} catch (SQLException e) {
			// if contains id already -> make update instead of insert 
			e.printStackTrace();
		}
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
