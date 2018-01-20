package com.php.Quagram.service;

import java.io.File;
import java.util.ArrayList;

import org.json.JSONObject;

import com.php.Quagram.database.DatabaseQuagramGamePlay;
import com.php.Quagram.database.DatabaseQuagramInvitations;
import com.php.Quagram.database.DatabaseQuagramUsers;
import com.php.Quagram.exceptions.SessionIDNotFoundException;
import com.php.Quagram.exceptions.UserHasDeclinedInvitationException;
import com.php.Quagram.exceptions.UserIsNotLoggedInException;
import com.php.Quagram.exceptions.UserLoginInstagramTroubleException;
import com.php.Quagram.exceptions.GameplayAttributeDoesNotExistException;
import com.php.Quagram.exceptions.GameplayIsOverException;
import com.php.Quagram.exceptions.GameplayUserIsNotInTurnException;
import com.php.Quagram.exceptions.InvitationAcceptedStatusDoesNotExistException;
import com.php.Quagram.exceptions.InvitationAlreadySentToUserException;
import com.php.Quagram.exceptions.InvitationCantBeSendToOneselfException;
import com.php.Quagram.exceptions.InvitationDoesNotExistException;
import com.php.Quagram.exceptions.LobbyDoesNotIncludeUserException;
import com.php.Quagram.exceptions.MatchSessionIDNotFoundException;
import com.php.Quagram.exceptions.PictureIDIsWrongException;
import com.php.Quagram.model.Card;
import com.php.Quagram.model.Gameplay;
import com.php.Quagram.model.Invitation;
import com.php.Quagram.model.User;
import com.php.Quagram.resources.PictureDownloadResource;

public class ErrorService {
	DatabaseQuagramUsers dbUsers = new DatabaseQuagramUsers();
	DatabaseQuagramInvitations dbInvitations = new DatabaseQuagramInvitations();
	DatabaseQuagramGamePlay dbGameplay = new DatabaseQuagramGamePlay();
	
	/*
	 * 
	 * Allgemeine Fehler
	 * 
	 * */
	public void isSessionIDValid(String sessionID) {
		if (!dbUsers.isSessionIDValid(sessionID)) {
			throw new SessionIDNotFoundException(sessionID);
		}
	}
	
	public void isUserLoggedIn(String sessionID) {
		String instagramID = dbUsers.getInstagramIDForSessionID(sessionID);
		Boolean isUserLoggedIn = dbUsers.isInstagramIDLoggedIn(instagramID);
		if (!isUserLoggedIn) {
			throw new UserIsNotLoggedInException();
		}
	}
	
	public void isInstagramRegistrationSuccessfully(String jsonRespond) {
		JSONObject object = new JSONObject(jsonRespond);
		if (!object.has("access_token")) {
			String errorType = object.getString("error_type");
			throw new UserLoginInstagramTroubleException(errorType);
		}
	}
	
	
	/*
	 * 
	 * Lobby Fehler
	 * 
	 * */
	public void isUserInLobby(String sessionID) {
		Boolean isInLobby = false;
		for (User user: dbUsers.getLobbyUsers()) {
			if (user.getSessionID().equals(sessionID)) {
				isInLobby = true;
				break;
			}
		}
		if (!isInLobby) {
			throw new LobbyDoesNotIncludeUserException();			
		}
	}
	
	
	
	/*
	 * 
	 * Invitation Fehler
	 * 
	 * */
	public void isUserAlreadyInvited(String userToInviteID, String hostSessionID) {
		ArrayList<Invitation> invitations = dbInvitations.getInvitationsForInstagramID(userToInviteID);
		String hostInstagramID = dbUsers.getInstagramIDForSessionID(hostSessionID);
		
		for (Invitation invitation: invitations) {
			if (invitation.getHostUserID().equals(hostInstagramID)) {
				User userToInvite = dbUsers.getUserForInstagramID(userToInviteID);
				throw new InvitationAlreadySentToUserException(userToInvite.getUsername());
			}
		}
	}
	
	
	public void isInvitationAvailable(String sessionID, String matchSessionID) {
		ArrayList<Invitation> invitations = dbInvitations.getInvitationsForUser(sessionID);
		
		Boolean didFoundInvitation = false;
		for (Invitation invitation: invitations) {
			if (invitation.getMatchSessionID().equals(matchSessionID)) {
				didFoundInvitation = true;
				break;
			}
		}
		if (!didFoundInvitation) {
			throw new InvitationDoesNotExistException(matchSessionID);
		}
	}
	
	public void isSendingInvitationToYourself(String sessionID, String userIDToInvite) {
		User hostUser = dbUsers.getUserForSessionID(sessionID);
		if (userIDToInvite.equals(hostUser.getUsername())) {
			throw new InvitationCantBeSendToOneselfException();
		}
	}
	
	/*
	 * 
	 * MatchSession Fehler
	 * 
	 * */
	public void isMatchSessionValid(String matchSessionID) {
		// MatchSession ist nicht valid, wenn kein Nutzer die MatchSession bei sich eingetragen hat
		// deshalb nur in Methoden checken, die nach dem versendend er Einladung erfolgen!
		ArrayList<User> foundUsersToMatchSession = dbUsers.getUserForMatchSessionID(matchSessionID);
		if (foundUsersToMatchSession == null || foundUsersToMatchSession.size() == 0) {
			throw new MatchSessionIDNotFoundException(matchSessionID);
		}
	}
	
	public void isInvitationDeclined(String matchSessionID) {
		if(!dbInvitations.doesInvitationExist(matchSessionID)) {
			throw new UserHasDeclinedInvitationException();
		}
	}
	
	public void isInvitationAcceptedStatusExisting(String acceptedStatus) {		
		if (!("0".equals(acceptedStatus) || "1".equals(acceptedStatus))) {
			throw new InvitationAcceptedStatusDoesNotExistException(acceptedStatus);
		}
	}
	
	
	/*
	 * 
	 * Gameplay Fehler
	 * 
	 * */
	public void isUserInTurnForGameplay(String gameplayID, String userID) {
		Gameplay gameplay = dbGameplay.getGameplay(gameplayID);
		if(gameplay == null || !gameplay.getTurnInstagramID().equals(userID)) {
			throw new GameplayUserIsNotInTurnException();
		}
	}
	
	public void isGameplayCardAttributeValid(String cardAttribute) {
		ArrayList<String> validAttributeNames = new ArrayList<>();
		validAttributeNames.add("likes");
		validAttributeNames.add("comments");
		validAttributeNames.add("temperature");
		validAttributeNames.add("height");
		
		Boolean isValid = false;
		
		for (String attribute: validAttributeNames) {
			if(cardAttribute.equals(attribute)) {
				isValid = true;
			}
		}
		
		if (!isValid) {
			throw new GameplayAttributeDoesNotExistException(cardAttribute);
		}
	}
	
	public void isGameplayOver(String sessionID, String gameplayID) {
		String userID = dbUsers.getInstagramIDForSessionID(sessionID);
		Card userCard = new GameplayService().getCardToPlay(gameplayID, userID);
		System.out.println("#### isGameplayOver - userCard: " + userCard);
		if (userCard == null) {
			System.out.println("THROW isGameplayOver");
			throw new GameplayIsOverException(gameplayID);
		}
	}
	
	
	/*
	 * 
	 * Picture Fehler
	 * 
	 * */
	public void isPictureIDValid(String pictureID) {
		PictureDownloadResource pictureController = new PictureDownloadResource();
		String path = pictureController.getURLPathForPictureID(pictureID);
		File file = new File(path);
		if (!file.exists()) {
			throw new PictureIDIsWrongException(pictureID);
		}
	}
}






