package com.php.Quagram.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.php.Quagram.model.MatchSessionCard;

public class DatabaseQuagramMatchSessionCard {
	
	private DatabaseQuagramSingleton databaseConnection = DatabaseQuagramSingleton.sharedInstance;

	public MatchSessionCard getMatchSessionCard(String matchSessionID, int currentTurn, String instagram_id) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			
			String sql;
			sql = "select * from match_session_card where match_session_id='" + matchSessionID + "'";
			sql += " and turn=" + currentTurn + "";
			sql += " and instagram_id='" + instagram_id + "'";
			
			ResultSet result = databaseConnection.statement.executeQuery(sql);
			
			MatchSessionCard matchSessionCard = new MatchSessionCard();
			
			while (result.next()) {
				String matchSession = result.getString("match_session_id");
				int turn = result.getInt("turn");
				String instagramIDOwner = result.getString("instagram_id");
				String cardPicID = result.getString("card_pic_id");
				
				matchSessionCard.setMatchSessionID(matchSession);
				matchSessionCard.setCurrentTurn(turn);
				matchSessionCard.setOwnerID(instagramIDOwner);
				matchSessionCard.setCardPicID(cardPicID);
			}
			return matchSessionCard;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void addMatchSessionCard(MatchSessionCard matchSessionCard) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			String sql;
			
			sql = "insert into match_session_card values (";
			sql += "'" + matchSessionCard.getMatchSessionID() + "',";
			sql += "" + matchSessionCard.getCurrentTurn() + ",";
			sql += "'" + matchSessionCard.getOwnerID() + "',";
			sql += "'" + matchSessionCard.getCardPicID() + "'";
			sql += ")";
			
			int result = databaseConnection.statement.executeUpdate(sql);
			System.out.println("Inserted MatchSessionCard (addMatchSessionCard): " +result);
		} catch (SQLException e) {
			// if contains id already -> make update instead of insert 
			e.printStackTrace();
		}
	}
	
	public void deleteMatchSessionCards(String matchSessionID) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			
			String sql;
			sql = "delete from match_session_card where match_session_id='" + matchSessionID + "'";
			int result = databaseConnection.statement.executeUpdate(sql);
			System.out.println("MatchSessionCards gelöscht: " + result + "--- " + sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Boolean alreadyContainsCardsForMatchSessionID(String matchSessionID) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			
			String sql = "select * from match_session_card where match_session_id='" + matchSessionID + "'";
			ResultSet result = databaseConnection.statement.executeQuery(sql);
			Boolean contains = result.next();
			System.out.println("------- " + contains);
			return contains;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
