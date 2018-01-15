package com.php.Quagram.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.php.Quagram.database.DatabaseQuagramUsers;
import com.php.Quagram.model.Card;
import com.php.Quagram.model.Invitation;
import com.php.Quagram.model.User;

public class JSONClientOutput {
	public JSONObject parseLoginUserWithSessionID(User user) {
		JSONObject result = new JSONObject();
		
		result.put("gamesLost", user.getGamesLost());
		result.put("gamesWin", user.getGamesWin());
		result.put("instagramID", user.getInstagramID());
		result.put("username", user.getUsername());
		result.put("sessionID", user.getSessionID());
		result.put("profile_pic", user.getProfilePic());
		
		return result;
	}
	
	public JSONArray parseUserArrayListToJSON(ArrayList<User> users, String withoutSessionID) {
		JSONArray userArray = new JSONArray();
		
		DatabaseQuagramUsers userDB = new DatabaseQuagramUsers();
		String withoutInstagramID = userDB.getUsernameForSessionID(withoutSessionID);
		
		for (User user: users) {
			if (!user.getUsername().equals(withoutInstagramID)) {
				JSONObject userObject = new JSONObject();
				userObject.put("gamesLost", user.getGamesLost());
				userObject.put("gamesWin", user.getGamesWin());
				userObject.put("username", user.getUsername());
				userArray.put(userObject);
			}
		}
		
		return userArray;
	}
	
	public JSONArray parseInvitationListToJSON(ArrayList<Invitation> invitations) {
		JSONArray invitationArray = new JSONArray();
		
		DatabaseQuagramUsers userDB = new DatabaseQuagramUsers();
		
		for (Invitation invitation: invitations) {
			JSONObject invitationObject = new JSONObject();
			invitationObject.put("createdDate", invitation.getCreatedDate());
			User hostUser = userDB.getUserForInstagramID(invitation.getHostUserID());
			invitationObject.put("hostUsername", hostUser.getUsername());
			invitationObject.put("matchSessionID", invitation.getMatchSessionID());
			invitationArray.put(invitationObject);
		}
		
		return invitationArray;
	}
	
	public JSONObject parseCardToJSON(Card card) {
		if (card != null) {
			JSONObject cardObject = new JSONObject();
			
			cardObject.put("id", card.getId());
			cardObject.put("likes", card.getLikes());
			cardObject.put("comments", card.getComments());
			cardObject.put("temperature", card.getTemperature());
			cardObject.put("height_meter", card.getHeightMeter());
			
			JSONObject locationObject = new JSONObject();
			locationObject.put("name", card.getLocation().getName());
			locationObject.put("latitude", card.getLocation().getLatitude());
			locationObject.put("longitude", card.getLocation().getLongitude());
			
			cardObject.put("location", locationObject);
			return cardObject;
		} else {
			return null;
		}
		
	}
	
	public JSONObject createGameplayJSON(String usernamePlayerInTurn,
									Card cardToPlay,
									HashMap<String, Integer> usersInGame,
									int numberOfMaxRounds,
									int currentRound) {
		JSONObject gameplayObject = new JSONObject();
		
		gameplayObject.put("username_of_player_in_turn", usernamePlayerInTurn);
		gameplayObject.put("number_of_max_rounds", numberOfMaxRounds);
		gameplayObject.put("current_round", currentRound + 1);
		if (cardToPlay == null) {
			gameplayObject.put("card", JSONObject.NULL);
		} else {
			gameplayObject.put("card", parseCardToJSON(cardToPlay));
		}
		
		JSONArray userGameplayInfos = new JSONArray();
		for (String username: usersInGame.keySet()) {
			// -1 weil jeder beim erstellen der GameplaySession einmal in die Turn spalte eingetragen wird!
			int wonRounds = usersInGame.get(username) - 1;
			JSONObject userGameplayInfo = new JSONObject();
			userGameplayInfo.put("username", username);
			userGameplayInfo.put("won_rounds", wonRounds);
			userGameplayInfos.put(userGameplayInfo);
		}
		
		gameplayObject.put("userGameplayInfos", userGameplayInfos);
		
		return gameplayObject;
	}
	
	public JSONObject createLobbyJSON(ArrayList<User> lobbyUsers) {
		
		return null;
	}
}










