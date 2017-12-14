package com.php.Quagram.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

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
	
	public void downloadImageFromURL(String imageURL) {		 
        InputStream inputStream = null;
        OutputStream outputStream = null;
        
        String path = "/Users/marcelhagmann/Desktop/DownloadFromInternet/";
        String imageName = createImageNameFromURL(imageURL);
        
        File file = new File(path + imageName);
 
        // only download file if it doesnt exist
        if (!file.exists()) {
        		try {
                URL url = new URL(imageURL);
                inputStream = url.openStream();
                outputStream = new FileOutputStream(path + imageName);
     
                byte[] buffer = new byte[2048];
                int length;
     
                while ((length = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, length);
                }
     
            } catch (MalformedURLException e) {
                System.out.println("MalformedURLException :- " + e.getMessage());
     
            } catch (FileNotFoundException e) {
                System.out.println("FileNotFoundException :- " + e.getMessage());
     
            } catch (IOException e) {
                System.out.println("IOException :- " + e.getMessage());
     
            } finally {
                try {
     
                    inputStream.close();
                    outputStream.close();
     
                } catch (IOException e) {
                    System.out.println("Finally IOException :- " + e.getMessage());
                }
            }
        } else {
        		System.out.println("Didnt Load Image! :)");
        }
	}
	
	private String createImageNameFromURL(String imageURL) {
		String[] urlComponents = imageURL.split("/");
		String imageName = urlComponents[urlComponents.length - 1];
		return imageName;
	}
	
	
}
