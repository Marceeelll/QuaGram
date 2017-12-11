package com.php.Quagram.service;

import org.apache.http.client.utils.URIBuilder;

public class InstagramRequestService {
	
	public void getAllUserPictures(String instagramAccessToken) {
		// DOKU-LINK: https://www.instagram.com/developer/endpoints/users/#get_users_media_recent_self
		// https://api.instagram.com/v1/users/self/media/recent/?access_token=ACCESS-TOKEN
		
		URIBuilder builder = new URIBuilder()
				.setScheme("https")
				.setHost("api.instagram.com")
				.setPath("/v1/users/self/media/recent/")
				.setParameter("access_token", instagramAccessToken);
		
		System.out.println("\n\nAll Images URL: " + builder.toString() + "\n\n");
		
		try {
			String instagrammAllPictureResponse = URLConnectionReader.getText(builder.toString());
			System.out.println(instagrammAllPictureResponse);
			JSONService parser = new JSONService();
			parser.getTest(instagrammAllPictureResponse);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// lädt alle Bilder des Nutzers herunter und speichert diese in die DB
	}
	
	public void getUserProfile(String instagramAccessToken) {
		// DOKU-LINK: https://www.instagram.com/developer/endpoints/users/#get_users_self
		// https://api.instagram.com/v1/users/self/?access_token=ACCESS-TOKEN
		
		// data
		// 		id
		// 		username
		// 		full_name
		//		profile_picture
		//		bio
		//		website
		//		counts
		//			media
		//			follows
		//			followed_by
		
		URIBuilder builder = new URIBuilder()
				.setScheme("https")
				.setHost("api.instagram.com")
				.setPath("/v1/users/self/")
				.setParameter("access_token", instagramAccessToken);
		
		
		
		System.out.println("\nAll User Profile: " + builder.toString() + "\n");
		
		try {
			System.out.println("URL---->" +builder.toString());
			String instagramProfileResponse = URLConnectionReader.getText(builder.toString());
			System.out.println("DATA---->" +instagramProfileResponse);
			
			JSONService parser = new JSONService();
			parser.getInstagramUser(instagramProfileResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	// Alle Bilder
	//		--> Anzahl der Komentare
	//		--> Anzahl der Likes
	//		--> Anzahl der Personen die auf dem Bild verlinkt sind
	//		--> Location
	//			--> latitude
	//			--> longitude
	//			--> adress
	//			-->
}
