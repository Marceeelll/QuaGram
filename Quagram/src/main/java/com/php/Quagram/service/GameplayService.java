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
		createGameplayIfNecessaryForMatchSessionID(matchSessionID, sessionID);
		
		return null;
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
			System.out.println("\tCARD ID: " + card.getId());
			if(card.getId().equals(cardID)) {
				return card;
			}
		}
		
		return null;
	}
	
	
	
 }







