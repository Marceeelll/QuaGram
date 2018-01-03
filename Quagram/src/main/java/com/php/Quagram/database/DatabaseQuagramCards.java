package com.php.Quagram.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.php.Quagram.model.Card;

public class DatabaseQuagramCards {
	private DatabaseQuagramSingleton databaseConnection = DatabaseQuagramSingleton.sharedInstance;
	
	public Card addCard(Card card, String instagramID) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			String sql;
			if (doesCardExist(card)) {
				sql = "update card set ";
				sql += "card_pic_id='" + card.getId() + "',";
				sql += "owner_id='" + instagramID + "',";
				sql += "number_of_comments=" + card.getComments() + ",";
				sql += "number_of_likes=" + card.getLikes() + ",";
				sql += "longitude=" + card.getLocation().getLongitude() + ",";
				sql += "latitude=" + card.getLocation().getLatitude() + ",";
				sql += "place_name='" + card.getLocation().getName() + "',";
				sql += "elevation=" + card.getHeightMeter() + ",";
				sql += "temperature=" + card.getTemperature() + ",";
				sql += "humidity=" + card.getHumidity() + ",";
				sql += "engagement=" + card.getEngagement() + ",";
				sql += "windspeed=" + card.getWindspeed() + "";
				sql += " where card_pic_id='" + card.getId() + "'";
			} else {
				sql = "insert into card values (";
				sql += "'" + card.getId() + "',";
				sql += "'" + instagramID + "',";
				sql += "" + card.getComments() + ",";
				sql += "" + card.getLikes() + ",";
				sql += "" + card.getLocation().getLongitude() + ",";
				sql += "" + card.getLocation().getLatitude() + ",";
				sql += "'" + card.getLocation().getName() + "',";
				sql += "" + card.getHeightMeter() + ",";
				sql += "" + card.getTemperature() + ",";
				sql += "" + card.getHumidity() + ",";
				sql += "" + card.getEngagement() + ",";
				sql += "" + card.getWindspeed() + "";
				sql += ")";
			}
			
			System.out.println(sql);
			
			int result = databaseConnection.statement.executeUpdate(sql);
			
			System.out.println("Card hinzugefügt: " + result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Boolean doesCardExist(Card card) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			
			String sql;
			sql = "select * from card where card_pic_id='"+ card.getId() + "'";
			ResultSet rs = databaseConnection.statement.executeQuery(sql);
			
			return rs.next();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
