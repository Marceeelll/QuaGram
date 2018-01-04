package com.php.Quagram.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.activation.DataContentHandler;

import com.php.Quagram.model.Card;
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
			
			while (result.next()) {
				String gameplayID = result.getString("gameplay_id");
				String turnInstagramID = result.getString("turn_instagram_id");
				int totalNumberOfTurns = result.getInt("total_number_of_turns");
				int currentTurnNumber = result.getInt("current_turn_number");
				
				gameplay.setGameplayID(gameplayID);
				gameplay.setTurnInstagramID(turnInstagramID);
				gameplay.setTotalNumberOfTurns(totalNumberOfTurns);
				gameplay.setCurrentTurnNumber(currentTurnNumber);
			}
			return gameplay;

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
	
	/*
	public int currentRoundInGameplayID(String gameplayID) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			
			String sql = "select * from turn where gameplay_id='" + gameplayID + "'";
			
			ResultSet result = databaseConnection.statement.executeQuery(sql);
			
			int currentRound = 0;
			
			while (result.next()) {
				currentRound += 1;
			}
			
			return currentRound;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public void addWinnerForRoundInGameplayID(String gameplayID, String winnerUserID) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			
			String sql = "insert into turn values (";
			sql += "'" + gameplayID + "',";
			sql += "'" + winnerUserID + "'";
			sql += ")";
			
			int result = databaseConnection.statement.executeUpdate(sql);
			System.out.println("Inserted Winner Status: " + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
	
	/*
	public void deleteGameplay(String gameplayID) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			String sql;
			
			//GameID aus User löschen
			
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
	}*/
}
