package others;

import java.util.ArrayList;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import com.php.Quagram.model.Card;
import com.php.Quagram.model.Location;
import com.php.Quagram.model.User;

public class JSONService {
	/*
	 *
	 * Instagram API
	 *
	 * */
	public User parseUserAfterLogin(String respond) {
		// https://www.instagram.com/developer/authentication/
		JSONObject object = new JSONObject(respond);
		String accessToken = object.getString("access_token");
		
		JSONObject jsonUserObject = object.getJSONObject("user");
		String userInstagramID = jsonUserObject.getString("id");
		String username = jsonUserObject.getString("username");
		String profilePictureURL = jsonUserObject.getString("profile_picture");
		
		PictureController pictureController = new PictureController();
		String imageName = pictureController.saveImageToFilesystem(profilePictureURL);
		
		User user = new User();
		user.setAccessToken(accessToken);
		user.setUsername(username);
		user.setInstagramID(userInstagramID);
		user.setProfilePic(imageName);
		UUID sessionID = UUID.randomUUID();
		String shortSessionID = sessionID.toString().substring(0, 6); // TODO: nur für die Abgabe, später dann länger = sicherer
		user.setSessionID(shortSessionID);
		
		return user;
	}
	
	public void getInstagramUser(String respond) {
		JSONObject object = new JSONObject(respond).getJSONObject("data");
		
		String id = object.getString("id");
		String username = object.getString("username");
		String profilePicture = object.getString("profile_picture");
		String fullName = object.getString("full_name");
		String bio = object.getString("bio");
		String website = object.getString("website");
		
		object = object.getJSONObject("counts");
		int numberOfMedia = object.getInt("media");
		int numberOfFollows = object.getInt("follows");
		int numberOfFollowedBy = object.getInt("followed_by");
		
		
		System.out.println("\nProfil Info");
		System.out.println("id: " + id);
		System.out.println("username: " + username);
		System.out.println("profilePicture: " + profilePicture);
		System.out.println("fullName: " + fullName);
		System.out.println("bio: " + bio);
		System.out.println("website: " + website);
		System.out.println("numberOfMedia: " + numberOfMedia);
		System.out.println("numberOfFollows: " + numberOfFollows);
		System.out.println("numberOfFollowedBy: " + numberOfFollowedBy);
	}
	
	public ArrayList<Card> getInstagramCards(String respond) {
		JSONObject object = new JSONObject(respond);
		
		JSONArray array = object.getJSONArray("data");
		
		ArrayList<Card> instagramCards = new ArrayList<Card>();
		
		for(int i = 0; i < array.length(); i++) {
			object = array.getJSONObject(i);
			
			int imageComments = object.getJSONObject("comments").getInt("count");
			int imageLikes = object.getJSONObject("likes").getInt("count");
			double latitude = 0;
			double longitude = 0;
			String locationName = "";
			String imageURL = object.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
			
			try {
				latitude = object.getJSONObject("location").getInt("latitude");
				longitude = object.getJSONObject("location").getInt("longitude");
				locationName = object.getJSONObject("location").getString("name");
			} catch(Exception e) {
				continue;
			}
			
			PictureController pictureController = new PictureController();
			pictureController.saveImageToFilesystem(imageURL);
			
			Location location = new Location(locationName, latitude, longitude);
			
			Card card = new Card();
			card.setComments(imageComments);
			card.setLikes(imageLikes);
			card.setLocation(location);
			card.setLocation(location);
			card.setPictureURL(imageURL);
			String cardNameID = PictureController.createImageNameFromURL(imageURL);
			card.setId(cardNameID);
			
			instagramCards.add(card);
		}
		
		return instagramCards;
	}
	
	
	
	
	/*
	 *
	 * Geonames API
	 *
	 * */
	public Card getGeoNameCard(String respond) {
		JSONObject object = new JSONObject(respond).getJSONObject("weatherObservation");
		int elevation = object.getInt("elevation");
		String temperature = object.getString("temperature");
		int temperatureInt = Double.valueOf(temperature).intValue();
		int humidity = object.getInt("humidity");
		String windSpeed = object.getString("windSpeed");
		int windSpeedInt = Integer.valueOf(windSpeed);
		
		Card card = new Card();
		card.setHeightMeter(elevation);
		card.setTemperature(0.0 + temperatureInt);
		card.setWindspeed(windSpeedInt);
		card.setHumidity(humidity);
		
		return card;
	}
}
