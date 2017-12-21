package com.php.Quagram.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.php.Quagram.model.Invitation;
import com.php.Quagram.model.User;

public class DatabaseQuagramRound {
	private DatabaseQuagramSingleton databaseConnection = DatabaseQuagramSingleton.sharedInstance;

	public ArrayList<String> getWinnersForGameplay(String gameplayID) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			
			String sql;
			sql = "select * from turn where gameplay_id='" + gameplayID + "'";
			ResultSet result = databaseConnection.statement.executeQuery(sql);
			
			ArrayList<String> winners = new ArrayList<>();
			
			while (result.next()) {

				String winnerID = result.getString("winner_id");
				winners.add(winnerID);
			}
			return winners;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
	
	public void addTurnForGameplay(User winnerID, String gameplayID) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			String sql;
			
			sql = "insert into turn values (";
			sql += "'" + gameplayID + "',";
			sql += "'" + winnerID + "'";
			sql += ")";
			
			int result = databaseConnection.statement.executeUpdate(sql);
			System.out.println("Inserted Turn (addTurnForGameplay): " +result);
		} catch (SQLException e) {
			// if contains id already -> make update instead of insert 
			e.printStackTrace();
		}
	}
	
	public void deleteTurnsForGameplay(String gameplayID) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			String sql;
			
			sql = "delete from turn where ";
			sql += "gameplay_id='" + gameplayID + "'";
			sql += ")";
			
			int result = databaseConnection.statement.executeUpdate(sql);
			System.out.println("Delete Turns (deleteTurnsForGameplay): " + result);
		} catch (SQLException e) {
			// if contains id already -> make update instead of insert 
			e.printStackTrace();
		}
	}
}
