package com.php.Quagram.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import com.php.Quagram.database.DatabaseQuagramGamePlay;
import com.php.Quagram.database.DatabaseQuagramInvitations;
import com.php.Quagram.database.DatabaseQuagramMatchSessionCard;
import com.php.Quagram.database.DatabaseQuagramRound;
import com.php.Quagram.database.DatabaseQuagramUsers;
import com.php.Quagram.model.User;

public class LoginService {
	DatabaseQuagramUsers dbUsers = new DatabaseQuagramUsers();
	DatabaseQuagramInvitations dbInvitations = new DatabaseQuagramInvitations();
	DatabaseQuagramMatchSessionCard dbMatchSessionCards = new DatabaseQuagramMatchSessionCard();
	DatabaseQuagramRound dbRound = new DatabaseQuagramRound();
	DatabaseQuagramMatchSessionCard dbMatchSessionCard = new DatabaseQuagramMatchSessionCard();
	DatabaseQuagramGamePlay dbGameplay = new DatabaseQuagramGamePlay();
	
	private String clientID = "334bddc7b37f437dbb709f44661dc458";
	private String redirectURI = "http://localhost:8080/Quagram/webapi/registration";
	private String clientSecret = "7c832fa066254e368309f1905ea720d3";
	
	
	public String getClientID() {
		return clientID;
	}

	public String getRedirectURI() {
		return redirectURI;
	}

	public String getClientSecret() {
		return clientSecret;
	}
	
	public String getRegistrationURI() {
		String registrationURI = "https://api.instagram.com/oauth/authorize/?client_id=" + clientID + "&redirect_uri="+ redirectURI + "&response_type=code";
		return registrationURI;
	}

	public String requestAccessToken(String code) {
	     try {
	         HttpClient httpclient = HttpClients.createDefault();
	         HttpPost httppost = new 
	         HttpPost("https://api.instagram.com/oauth/access_token");

	         // Request parameters and other properties.
	         List<NameValuePair> params = new ArrayList<NameValuePair>(2);
	         params.add(new BasicNameValuePair("client_id", "334bddc7b37f437dbb709f44661dc458"));
	         params.add(new BasicNameValuePair("client_secret", "7c832fa066254e368309f1905ea720d3"));
	         params.add(new BasicNameValuePair("grant_type", "authorization_code"));
	         params.add(new BasicNameValuePair("redirect_uri",  "http://localhost:8080/Quagram/webapi/registration"));
	         params.add(new BasicNameValuePair("code",  code));


	         httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

	         //Execute and get the response.
	         HttpResponse response = httpclient.execute(httppost);
	         HttpEntity entity = response.getEntity();
	         System.out.println("entity "+ entity.getContent());

	         if (entity != null) {
	             InputStream instream = entity.getContent();
	             try {
	            	 String result = new BufferedReader(new InputStreamReader(instream)).lines()
	            			   .parallel().collect(Collectors.joining("\n"));
	            	 	return result;
	                 // do something useful
	             } finally {
	                 instream.close();
	             }
	         }
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    } catch (ClientProtocolException e) {
	        e.printStackTrace();
	    } catch (UnsupportedOperationException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return "PMP";
	 }
	
	public User loginUser(User user) {
		User userLoggedIn = dbUsers.addUser(user);
		return userLoggedIn;
	}
	
	public int logoutUser(String sessionID) {
		int result = dbUsers.logoutUser(sessionID);
		if (result == 1) {
			String userID = dbUsers.getInstagramIDForSessionID(sessionID);
			dbInvitations.deleteAllInvitationsFromUserID(userID);
			dbUsers.removeUserFromLobby(sessionID);
			
			String gameplayID = dbUsers.getMatchSessionIDFromUserSessionID(sessionID);
			
			if (gameplayID != null) {
				// gameSession & gameID von Nutzern austragen
				cleanUpGameplayUserParticipants(gameplayID);
				
				// turn db einträge leeren zu der gameplayID
				cleanUpTurnDB(gameplayID);
				
				// match_session_card db einträge leeren zu der gameplayID
				cleanUpMatchSessionCard(gameplayID);
				
				// gameplay aus db löschen
				dbGameplay.deleteGameplayForGameplay(gameplayID);
			}
		}
		return result;
	}
	
	private void cleanUpGameplayUserParticipants(String gameplayID) {
		// match_session_id & game_id -- in der Nutzer DB entfernen
		ArrayList<User> gameplayUsers = dbUsers.getUserForMatchSessionID(gameplayID);
		
		for (User user: gameplayUsers) {
			dbUsers.setUserAttribetuToNull("match_session_id", user.getSessionID());
			dbUsers.setUserAttribetuToNull("game_id", user.getSessionID());
		}
	}
	
	private void cleanUpTurnDB(String gameplayID) {
		dbRound.deleteTurnsForGameplay(gameplayID);
	}
	
	private void cleanUpMatchSessionCard(String gameplayID) {
		dbMatchSessionCard.deleteMatchSessionCards(gameplayID);
	}

}
