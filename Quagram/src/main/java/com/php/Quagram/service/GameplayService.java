package com.php.Quagram.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import com.php.Quagram.database.DatabaseQuagramCards;
import com.php.Quagram.database.DatabaseQuagramGamePlay;
import com.php.Quagram.database.DatabaseQuagramMatchSessionCard;
import com.php.Quagram.database.DatabaseQuagramRound;
import com.php.Quagram.database.DatabaseQuagramUsers;
import com.php.Quagram.model.Card;
import com.php.Quagram.model.Gameplay;
import com.php.Quagram.model.MatchSessionCard;
import com.php.Quagram.model.User;

public class GameplayService {
	DatabaseQuagramUsers dbUsers = new DatabaseQuagramUsers();
	DatabaseQuagramCards dbCards = new DatabaseQuagramCards();
	DatabaseQuagramMatchSessionCard dbMatchSessionCard = new DatabaseQuagramMatchSessionCard();
	DatabaseQuagramGamePlay dbGameplay = new DatabaseQuagramGamePlay();
	DatabaseQuagramRound dbRound = new DatabaseQuagramRound();
	
	public String getGameplay(String sessionID, String matchSessionID) {
		makeMatchIDToGameplayID(matchSessionID);
		createGameplayIfNecessaryForMatchSessionID(matchSessionID, sessionID);
		
		return null;
	}
	
	private void makeMatchIDToGameplayID(String matchSessionID) {
		ArrayList<User> usersInMatchSession = dbUsers.getUserForMatchSessionID(matchSessionID);
		for(User user: usersInMatchSession) {
			// TODO: alte gameplays löschen !!!
			asd
			dbUsers.addGameplayIDToUser(user.getInstagramID(), matchSessionID);
		}
	}
	
	private void createGameplayIfNecessaryForMatchSessionID(String matchSessionID, String sessionID) {
		if (!dbMatchSessionCard.alreadyContainsCardsForMatchSessionID(matchSessionID)) {
			ArrayList<User> usersInMatchSession = dbUsers.getUserForMatchSessionID(matchSessionID);
			int maxRounds = numberOfMaximalRoundsCanBePlayed(usersInMatchSession);
			if (maxRounds > 15) {
				maxRounds = 15;
			}
			
			// Spieler Karten für die verschiedenen Runden aufteilen
			ArrayList<Integer> randomNumbers = createRandomNumberArrayWithMaxNumber(maxRounds);
			for(User user: usersInMatchSession) {
				ArrayList<Card> userCards = dbCards.getCardsForUserInstagramID(user.getInstagramID());
				System.out.println("\tUsername: " + user.getUsername() + "\t " + dbCards.getCardsForUserInstagramID(user.getInstagramID()).size());
				for(int i = 0; i < randomNumbers.size(); i++) {
					// Karte in MatchSessionCard eintragen
					int currentTurn = randomNumbers.get(i);
					String ownerID = user.getInstagramID();
					String cardPicID = userCards.get(currentTurn).getId();
					MatchSessionCard matchSessionCard = new MatchSessionCard(matchSessionID, currentTurn, ownerID, cardPicID);
					dbMatchSessionCard.addMatchSessionCard(matchSessionCard);
					
					// Spieler in turn eintragen
					dbRound.addTurnForGameplay(ownerID, matchSessionID);
				}
			}
			
			User userToStartWith = dbUsers.getUserForSessionID(sessionID);
			createGamePlayInDB(matchSessionID, userToStartWith.getInstagramID(), maxRounds);
		}
	}
	
	private int numberOfMaximalRoundsCanBePlayed(ArrayList<User> usersInMatchSession) {
		int min = -1;
		
		for(User user: usersInMatchSession) {
			System.out.println("Username: " + user.getUsername() + " --> " + user.getInstagramID());
			int numberOfCards = dbCards.numberOfCardsFromUser(user.getInstagramID());
			if (min < 0) {
				min = numberOfCards;
			}
			if (numberOfCards < min) {
				min = numberOfCards;
			}
		}
		
		return min;
	}
	
	private ArrayList<Integer> createRandomNumberArrayWithMaxNumber(int maxNumber) {
		ArrayList<Integer> randomNumbers = new ArrayList<>();
		
		while(randomNumbers.size() < maxNumber) {
			int randomNumber = (int)(Math.random() * maxNumber);
			if (!randomNumbers.contains(randomNumber)) {
				randomNumbers.add(randomNumber);
			}
		}
		
		return randomNumbers;
	}
	
	private void createGamePlayInDB(String gameplayID, String hostID, int maxTurns) {
		Gameplay gameplay = new Gameplay();
		gameplay.setCurrentTurnNumber(0);
		gameplay.setTotalNumberOfTurns(maxTurns);
		gameplay.setTurnInstagramID(hostID);
		gameplay.setGameplayID(gameplayID);
		
		new DatabaseQuagramGamePlay().addGameplay(gameplay);
	}
	
	
	
	public String postSelectedGameplayAttribute(String cardAttribute, String gameplayID, String sessionID) {
		// 1. checken ob Nutzer wirklich dran ist mit Attribut auswählen
		// 1.1 - Nein Nutzer ist nicht dran - Fehler werfen
		// 1.2 - Ja Nutzer ist dran
		//			Gewinner in turn Runde schreiben
		//			Gewinner und verlierer für die Runde zurück geben
		// 2. Aktuelle Runde um 1 erhöhen
		
		// TODO: check if sessionID ist valid
		
		System.out.println("GameplayID: " +gameplayID);
		String userIDWhoHasNextTurn = getPlayerIdWhoHasNextTurn(gameplayID);
		String userWhoTriesToPost = dbUsers.getInstagramIDForSessionID(sessionID);
		
		if (!userIDWhoHasNextTurn.equals(userWhoTriesToPost)) {
			// TODO: Fehler werfen
			System.out.println("Du bist nicht dran, das Attribut auszuwählen!");
		}
		
		if (!isCardAttributeValid(cardAttribute)) {
			// TODO: Fehler werfen
			System.out.println("Das ausgewählte Attribut ist nicht verfügar!");
		}
		
		// TODO: Fehler werfen, wenn das Spiel bereits vorbei ist und ein Attribut ausgewählt wird
		
		String roundWinner = getWinnerIDForGameplayID(gameplayID, cardAttribute);
		incrementRound(gameplayID);
		
		// TODO: hier nur Status zurück geben ob alles geklappt hat
		// 		 Der Client muss dann die Anzeige logik übernehmen
		return "Post :) - Der Gewinner ist: " + roundWinner;
	}
	
	private Boolean isCardAttributeValid(String cardAttribute) {
		ArrayList<String> validAttributeNames = new ArrayList<>();
		validAttributeNames.add("likes");
		validAttributeNames.add("comments");
		
		Boolean isValid = false;
		
		for (String attribute: validAttributeNames) {
			if(cardAttribute.equals(attribute)) {
				isValid = true;
			}
		}
		
		return isValid;
	}
	
	private String getWinnerIDForGameplayID(String gameplayID, String selectedCardAttribute) {
		ArrayList<User> userInMatchSession = dbUsers.getUserForMatchSessionID(gameplayID); // TODO: eigentlich matchSessionID nicht gameplayID!
		HashMap<String, Card> usersAndCards = new HashMap<>();
		
		for (User user: userInMatchSession) {
			Card userCard = getCardToPlay(gameplayID, user.getInstagramID());
			usersAndCards.put(user.getInstagramID(), userCard);
		}
		
		String winnerID = null;
		Card winnerCard = null;
		for(String userID: usersAndCards.keySet()) {
			Card card = usersAndCards.get(userID);
			if (winnerCard == null) {
				winnerCard = card;
				winnerID = userID;
			} else {
				switch (selectedCardAttribute) {
				case "likes":
					if(winnerCard.getLikes() < card.getLikes()) {
						winnerCard = card;
						winnerID = userID;
					}
					break;
				case "comments":
					if(winnerCard.getComments() < card.getComments()) {
						winnerCard = card;
						winnerID = userID;
					}
					break;
				default:
					break;
				}
				// TODO: alle Attribute implementieren !
			}
		}
		
		return winnerID;
	}
	
	public void incrementRound(String matchSessionID) {
		Gameplay gameplay = dbGameplay.getGameplay(matchSessionID);
		int currentTurn = gameplay.getCurrentTurnNumber();
		int nextTurn = currentTurn + 1;
		dbGameplay.updateGameplayCurrentTurn(matchSessionID, nextTurn);
	}
	
	
	
	/*
	 *
	 *	Methoden für GameplayServlet
	 *
	 * */
	public JSONObject getGameplayRoundJSON(String sessionID, String gameplayID) {
		String userID = dbUsers.getInstagramIDForSessionID(sessionID);
		Card userCard = getCardToPlay(gameplayID, userID);
		String usernameWhoHasNextTurn = getUsernameWhoHasNextTurn(gameplayID);
		HashMap<String, Integer> gameplayInfo = createGameplayInfos(gameplayID);
		Gameplay gameplay = dbGameplay.getGameplay(gameplayID);
		int numberOfMaxRounds = gameplay.getTotalNumberOfTurns();
		int currentRound = gameplay.getCurrentTurnNumber();
		
		JSONClientOutput jsonOutput = new JSONClientOutput();
		JSONObject gameplayJSON = jsonOutput.createGameplayJSON(usernameWhoHasNextTurn, userCard, gameplayInfo, numberOfMaxRounds, currentRound);
		
		return gameplayJSON;
	}
	
	private Boolean hasGameplayFinished(String gameplayID) {
		Gameplay gameplay = dbGameplay.getGameplay(gameplayID);
		return gameplay.getCurrentTurnNumber() == gameplay.getTotalNumberOfTurns();
	}
	
	private String getUsernameWhoHasNextTurn(String gameplayID) {
		String playerIdWhoHasNextTurn = getPlayerIdWhoHasNextTurn(gameplayID);
		return getUsernameForPlayerID(playerIdWhoHasNextTurn);
	}
	
	private String getPlayerIdWhoHasNextTurn(String gameplayID) {
		ArrayList<String> winnerInRounds = dbRound.getWinnersForGameplay(gameplayID);
		return winnerInRounds.get(winnerInRounds.size()-1);
	}
	
	private String getUsernameForPlayerID(String instagramID) {
		return dbUsers.getUserForInstagramID(instagramID).getUsername();
	}
	
	private HashMap<String, Integer> createGameplayInfos(String gameplayID) {
		ArrayList<String> winnerInRounds = dbRound.getWinnersForGameplay(gameplayID);
		ArrayList<String> winnerUsernamesInRound = new ArrayList<>();
		
		for(String winner: winnerInRounds) {
			String username = getUsernameForPlayerID(winner);
			winnerUsernamesInRound.add(username);
		}
		
		HashMap<String, Integer> result = new HashMap<>();
		
		for(String winnerUsername: winnerUsernamesInRound) {
			if (result.containsKey(winnerUsername)) {
				int oldValue = result.get(winnerUsername);
				int newValue = oldValue + 1;
				result.put(winnerUsername, newValue);
			} else {
				result.put(winnerUsername, 1);
			}
		}
		
		return result;
	}
	
	public Card getCardToPlay(String matchSessionID, String instagramID) {
		Gameplay gameplay = dbGameplay.getGameplay(matchSessionID);
		int currentTurn = gameplay.getCurrentTurnNumber();
		String cardID = dbGameplay.getCardID(matchSessionID, instagramID, currentTurn);
		
		for(Card card: dbCards.getCardsForUserInstagramID(instagramID)) {
			if(card.getId().equals(cardID)) {
				System.out.println("\tCARD ID: " + card.getId() + " turn: " + currentTurn + " likes: " + card.getLikes());
				return card;
			}
		}
		
		return null;
	}
	
 }







