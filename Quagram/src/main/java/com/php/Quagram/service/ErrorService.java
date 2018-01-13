package com.php.Quagram.service;

import java.io.File;
import java.util.ArrayList;

import com.php.Quagram.database.DatabaseQuagramGamePlay;
import com.php.Quagram.database.DatabaseQuagramInvitations;
import com.php.Quagram.database.DatabaseQuagramUsers;
import com.php.Quagram.exceptions.SessionIDNotFoundException;
import com.php.Quagram.exceptions.UserHasDeclinedInvitationException;
import com.php.Quagram.exceptions.UserIsNotLoggedInException;
import com.php.Quagram.exceptions.GameplayUserIsNotInTurnException;
import com.php.Quagram.exceptions.InvitationAlreadySentToUserException;
import com.php.Quagram.exceptions.InvitationCantBeSendToOneselfException;
import com.php.Quagram.exceptions.InvitationDoesNotExistException;
import com.php.Quagram.exceptions.LobbyDoesNotIncludeUserException;
import com.php.Quagram.exceptions.MatchSessionIDNotFoundException;
import com.php.Quagram.exceptions.PictureIDIsWrongException;
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
	 * Allgemeine Fehler - 400...409
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
	
	
	/*
	 * 
	 * Lobby Fehler - 410...419
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
	 * Invitation Fehler - 420...429
	 * 
	 * */
	public void isUserAlreadyInvited(String userToInviteID, String matchSessionID) {
		ArrayList<Invitation> invitations = dbInvitations.getInvitationsForInstagramID(userToInviteID);
		for (Invitation invitation: invitations) {
			if (invitation.getMatchSessionID().equals(matchSessionID)) {
				throw new InvitationAlreadySentToUserException("crazyc0de");
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
		String hostID = dbUsers.getInstagramIDForSessionID(sessionID);
		if (userIDToInvite.equals(hostID)) {
			throw new InvitationCantBeSendToOneselfException();
		}
	}
	
	/*
	 * 
	 * MatchSession Fehler - 430...439
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
	
	
	/*
	 * 
	 * Gameplay Fehler - 440...449
	 * 
	 * */
	public void isUserInTurnForGameplay(String gameplayID, String userID) {
		Gameplay gameplay = dbGameplay.getGameplay(gameplayID);
		if(gameplay == null || !gameplay.getTurnInstagramID().equals(userID)) {
			throw new GameplayUserIsNotInTurnException();
		}
	}
	
	
	/*
	 * 
	 * Picture Fehler - 450...459
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






