package com.php.Quagram.service;

import java.util.UUID;

import org.json.JSONObject;

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
		
		User user = new User();
		user.setAccessToken(accessToken);
		user.setUsername(username);
		user.setInstagramID(userInstagramID);
		UUID sessionID = UUID.randomUUID();
		user.setSessionID(sessionID.toString());
		// TODO: profilePictureURL wird noch nicht verwendet
		
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
	
	
	
	
	/*
	 *
	 * Geonames API
	 *
	 * */
	public String getGeoNamesData(String respond) {
		JSONObject object = new JSONObject(respond).getJSONObject("weatherObservation");
		int elevation = object.getInt("elevation");
		String temperature = object.getString("temperature");
		int humidity = object.getInt("humidity");
		String windSpeed = object.getString("windSpeed");
		String countryCode = object.getString("countryCode");
		
		System.out.println("elevation: " + elevation);
		System.out.println("temperature: " + temperature);
		System.out.println("humidity: " + humidity);
		System.out.println("windSpeed: " + windSpeed);
		System.out.println("countryCode: " + countryCode);
		
		// Anmerkung: Diese Funktion liest alles richtig aus
		// --> muss nur noch in ein Model verpackt werden
		
		return null;
	}
}
