package com.php.Quagram.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.php.Quagram.model.Gameplay;

public class DatabaseQuagramGamePlay {
	private DatabaseQuagramSingleton databaseConnection = DatabaseQuagramSingleton.sharedInstance;

	public Gameplay getGameplay(String gameID) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			
			String sql;
			sql = "select * from gameplay where gameplay_id='" + gameID + "'";
			ResultSet result = databaseConnection.statement.executeQuery(sql);
			
			Gameplay gameplay = new Gameplay();
			
			if (result.next()) {
				String gameplayID = result.getString("gameplay_id");
				String turnInstagramID = result.getString("turn_instagram_id");
				int totalNumberOfTurns = result.getInt("total_number_of_turns");
				int currentTurnNumber = result.getInt("current_turn_number");
				
				gameplay.setGameplayID(gameplayID);
				gameplay.setTurnInstagramID(turnInstagramID);
				gameplay.setTotalNumberOfTurns(totalNumberOfTurns);
				gameplay.setCurrentTurnNumber(currentTurnNumber);
				return gameplay;
			}
			return null;

		} catch (NullPointerException e) {
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getCardID(String matchSessionID, String instagramID, int turn) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			
			String sql = "select * from match_session_card where ";
			sql += "match_session_id='" + matchSessionID + "' and ";
			sql += "turn='" + turn  + "' and ";
			sql += "instagram_id='" + instagramID + "'";
			ResultSet result = databaseConnection.statement.executeQuery(sql);
			
			if (result.next()) {
				String cardID = result.getString("card_pic_id");
				return cardID;
			}
			
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void addGameplay(Gameplay gameplay) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			String sql;
			
			sql = "insert into gameplay values (";
			sql += "'" + gameplay.getGameplayID() + "',";
			sql += "'" + gameplay.getTurnInstagramID() + "',";
			sql += "" + gameplay.getTotalNumberOfTurns() + ",";
			sql += "" + gameplay.getCurrentTurnNumber() + "";
			sql += ")";
			
			int result = databaseConnection.statement.executeUpdate(sql);
			System.out.println("Inserted Gameplay (addGameplay): " +result);
		} catch (SQLException e) {
			// if contains id already -> make update instead of insert 
			e.printStackTrace();
		}
	}
	
	public void updateGameplayCurrentTurn(String gameplayID, int currentTurnNumber) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			
			String sql;
			sql = "update gameplay set current_turn_number='" + currentTurnNumber + "' where gameplay_id='" + gameplayID + "'";
			int result = databaseConnection.statement.executeUpdate(sql);
			System.out.println("CurrentTurnNumber geupdated: " + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteGameplayForGameplay(String gameplayID) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			String sql;
			
			sql = "delete from gameplay where ";
			sql += "gameplay_id='" + gameplayID + "'";
			
			int result = databaseConnection.statement.executeUpdate(sql);
			System.out.println("Delete Turns (deleteTurnsForGameplay): " + result);
		} catch (SQLException e) { 
			e.printStackTrace();
		}
	}
	
}
