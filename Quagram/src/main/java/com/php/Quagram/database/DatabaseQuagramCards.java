package com.php.Quagram.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.php.Quagram.model.Card;
import com.php.Quagram.model.Location;

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
			
			int result = databaseConnection.statement.executeUpdate(sql);
			
			System.out.println("Card hinzugefügt: " + result);
		} catch (SQLException e) {
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
	
	public int numberOfCardsFromUser(String userInstagramID) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			
			String sql = "select * from card where owner_id='" + userInstagramID + "'";
			ResultSet rs = databaseConnection.statement.executeQuery(sql);
			
			int numberOfCards = 0;
			while(rs.next()) {
				numberOfCards += 1;
			}
			
			return numberOfCards;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public ArrayList<Card> getCardsForUserInstagramID(String userInstagramID) {
		try {
			databaseConnection.statement = databaseConnection.connection.createStatement();
			
			String sql = "select * from card where owner_id='" + userInstagramID + "';";
			ResultSet rs = databaseConnection.statement.executeQuery(sql);
			
			ArrayList<Card> userCards = new ArrayList<>();
			
			while (rs.next()) {
				String id = rs.getString("card_pic_id");
				int number_of_comments = rs.getInt("number_of_comments");
				int number_of_likes = rs.getInt("number_of_likes");
				Double longitude = rs.getDouble("longitude");
				Double latitude = rs.getDouble("latitude");
				String place_name = rs.getString("place_name");
				int elevation = rs.getInt("elevation");
				Double temperature = rs.getDouble("temperature");
				int humidity = rs.getInt("humidity");
				int engagement = rs.getInt("engagement");
				int windspeed = rs.getInt("windspeed");
				
				Location location = new Location();
				location.setLatitude(latitude);
				location.setLongitude(longitude);
				location.setName(place_name);
				
				Card card = new Card(id, "", number_of_likes, number_of_comments, temperature, elevation, location, humidity, engagement, windspeed);
				userCards.add(card);
			}
			
			return userCards;
		} catch(NullPointerException e) {
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

