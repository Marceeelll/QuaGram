package com.php.Quagram.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import com.php.Quagram.database.DatabaseQuagramCards;
import com.php.Quagram.database.DatabaseQuagramGamePlay;
import com.php.Quagram.database.DatabaseQuagramMatchSessionCard;
import com.php.Quagram.database.DatabaseQuagramRound;
import com.php.Quagram.database.DatabaseQuagramSingleton;
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
	
	private void makeMatchIDToGameplayID(String matchSessionID) {
		ArrayList<User> usersInMatchSession = dbUsers.getUserForMatchSessionID(matchSessionID);
		
		if (usersInMatchSession.size() != 0 && dbGameplay.getGameplay(matchSessionID) == null) {
			dbGameplay.addGameplayWithID(matchSessionID, usersInMatchSession.get(0).getInstagramID());
		}
		
		for(User user: usersInMatchSession) {
			dbUsers.addGameplayIDToUser(user.getInstagramID(), matchSessionID);
		}
	}
	
	public JSONObject getGameplay(String sessionID, String matchSessionID) {
		makeMatchIDToGameplayID(matchSessionID);
		createGameplayIfNecessaryForMatchSessionID(matchSessionID, sessionID);

		JSONObject gameplayRoundJSON = getGameplayRoundJSON(sessionID, matchSessionID);
		return gameplayRoundJSON;
	}
	
	private void createGameplayIfNecessaryForMatchSessionID(String matchSessionID, String sessionID) {
		if (!dbMatchSessionCard.alreadyContainsCardsForMatchSessionID(matchSessionID)) {
			ArrayList<User> usersInMatchSession = dbUsers.getUserForMatchSessionID(matchSessionID);
			int maxRounds = numberOfMaximalRoundsCanBePlayed(usersInMatchSession);
			System.out.println("\t --- maxRounds:" + maxRounds);
			if (maxRounds > 15) {
				maxRounds = 15;
			}
			
			// Spieler Karten für die verschiedenen Runden aufteilen
			for(User user: usersInMatchSession) {
				ArrayList<Integer> randomNumbers = DatabaseQuagramSingleton.sharedInstance.createRandomNumberArrayWithMaxNumber(maxRounds);
				ArrayList<Card> userCards = dbCards.getCardsForUserInstagramID(user.getInstagramID());
				for(int i = 0; i < randomNumbers.size(); i++) {
					// Karte in MatchSessionCard eintragen
					int currentTurn = randomNumbers.get(i);
					String ownerID = user.getInstagramID();
					String cardPicID = userCards.get(i).getId();
					MatchSessionCard matchSessionCard = new MatchSessionCard(matchSessionID, currentTurn, ownerID, cardPicID);
					dbMatchSessionCard.addMatchSessionCard(matchSessionCard);
				}
				
				// Spieler in turn eintragen
				dbRound.addTurnForGameplay(user.getInstagramID(), matchSessionID);
			}
			
			User userToStartWith = dbUsers.getUserForSessionID(sessionID);
			createGamePlayInDB(matchSessionID, userToStartWith.getInstagramID(), maxRounds);
			System.out.println("Ist NICHT im else Block");
		} else {
			System.out.println("Ist im else Block");
		}
	}
	
	private int numberOfMaximalRoundsCanBePlayed(ArrayList<User> usersInMatchSession) {
		int min = -1;
		
		for(User user: usersInMatchSession) {
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
	
	private void createGamePlayInDB(String gameplayID, String hostID, int maxTurns) {
		Gameplay gameplay = new Gameplay();
		gameplay.setCurrentTurnNumber(0);
		gameplay.setTotalNumberOfTurns(maxTurns);
		gameplay.setTurnInstagramID(hostID);
		gameplay.setGameplayID(gameplayID);
		
		new DatabaseQuagramGamePlay().updateGameplay(gameplay);
	}
	
	
	
	public void postSelectedGameplayAttribute(String cardAttribute, String gameplayID, String sessionID) {
		String roundWinnerID = getWinnerIDForGameplayID(gameplayID, cardAttribute);
		
		if (roundWinnerID == null) {
			// unentschieden
			incrementRound(gameplayID);
		} else {
			// wenn es einen Gewinner gibt
			dbRound.addTurnForGameplay(roundWinnerID, gameplayID);
			dbGameplay.updateGameplayTurnInstagramID(roundWinnerID, gameplayID);
			incrementRound(gameplayID);
		}
		
		if (checkIfIsLastGameplayRound(gameplayID)) {
			System.out.println("GAME DID FINISHED");
			finishGameplay(gameplayID);
		}
	}
	
	private String getWinnerIDForGameplayID(String gameplayID, String selectedCardAttribute) {
		ArrayList<User> userInMatchSession = dbUsers.getUserForMatchSessionID(gameplayID);
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
					if (winnerCard.getLikes() == card.getLikes()) {
						return null;
					} else if(winnerCard.getLikes() < card.getLikes()) {
						winnerCard = card;
						winnerID = userID;
					}
					break;
				case "comments":
					if (winnerCard.getComments() == card.getComments()) {
						return null;
					} else if(winnerCard.getComments() < card.getComments()) {
						winnerCard = card;
						winnerID = userID;
					}
					break;
				case "temperature":
					if (Math.abs(winnerCard.getTemperature()) == Math.abs(card.getTemperature())) {
						return null;
					} else if (Math.abs(winnerCard.getTemperature()) < Math.abs(card.getTemperature())) {
						winnerCard = card;
						winnerID = userID;
					}
					break;
				case "height":
					if (winnerCard.getHeightMeter() == card.getHeightMeter()) {
						return null;
					} else if (winnerCard.getHeightMeter() < card.getHeightMeter()) {
						winnerCard = card;
						winnerID = userID;
					}
					break;
				default:
					break;
				}
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

		System.out.println("Card: " + userCard);
		
		Gameplay gameplay = dbGameplay.getGameplay(gameplayID);
		int numberOfMaxRounds = gameplay.getTotalNumberOfTurns();
		int currentRound = gameplay.getCurrentTurnNumber();
		
		if (userCard != null) {
			String usernameWhoHasNextTurn = getUsernameWhoHasNextTurn(gameplayID);
			HashMap<String, Integer> gameplayInfo = createGameplayInfos(gameplayID);
			
			JSONClientOutput jsonOutput = new JSONClientOutput();
			JSONObject gameplayJSON = jsonOutput.createGameplayJSON(usernameWhoHasNextTurn, userCard, gameplayInfo, numberOfMaxRounds, currentRound);
			
			return gameplayJSON;
		} else {
			System.out.println("Print gameende JSON");
			HashMap<String, Integer> gameplayInfo = createGameplayInfos(gameplayID);
			JSONClientOutput jsonOutput = new JSONClientOutput();
			JSONObject gameplayJSON = jsonOutput.createGameplayJSON(null, null, gameplayInfo, numberOfMaxRounds, currentRound);
			
			return gameplayJSON;
		}
	}
	
	private Boolean checkIfIsLastGameplayRound(String gameplayID) {
		Gameplay gameplay = dbGameplay.getGameplay(gameplayID);
		System.out.println("LAST GAMEPLAY-- " + gameplay.getCurrentTurnNumber() + "----  " + gameplay.getTotalNumberOfTurns());
		return gameplay.getCurrentTurnNumber() == gameplay.getTotalNumberOfTurns();
	}
	
	public void finishGameplay(String gameplayID) {
		System.out.println("\t\t\tpublic void finishGameplay(String gameplayID) { #######");
		// verlierer/gewinner attribut beim user erhöhen
		updateUsersLostWonNumbers(gameplayID);
	}
	
	public void updateUsersLostWonNumbers(String gameplayID) {
		ArrayList<String> roundWinners = dbRound.getWinnersForGameplay(gameplayID);
		HashMap<String, Integer> instagramIdAndWonRounds = new HashMap<>();
		for (String roundWinner: roundWinners) {
			if (instagramIdAndWonRounds.containsKey(roundWinner)) {
				int wonGames = instagramIdAndWonRounds.get(roundWinner);
				wonGames += 1;
				instagramIdAndWonRounds.put(roundWinner, wonGames);
			} else {
				instagramIdAndWonRounds.put(roundWinner, 0);
			}
		}
		
		// gewinner herausfinden
		String winnerInstagramID = "";
		int maxWons = -1;
		for (String instagramID: instagramIdAndWonRounds.keySet()) {
			int wons = instagramIdAndWonRounds.get(instagramID);
			if (wons > maxWons) {
				maxWons = wons;
				winnerInstagramID = instagramID;
			}
		}
		
		// unentschieden heraufsinden
		ArrayList<String> drawPlayers = new ArrayList<>();
		for (String instagramID: instagramIdAndWonRounds.keySet()) {
			int wons = instagramIdAndWonRounds.get(instagramID);
			if (wons == maxWons && !instagramID.equals(winnerInstagramID)) {
				drawPlayers.add(instagramID);
			}
		}
		
		
		if (drawPlayers.size() > 0) {
			System.out.println("ES FAND EIN UNENTSCHIEDEN STATT!");
			// es hat ein unentschieden stattgefunden
			dbUsers.incrementGameWonFromUser(winnerInstagramID);
			for(String drawPlayerID: drawPlayers) {
				dbUsers.incrementGameWonFromUser(drawPlayerID);
			}
			// hier durch den Rest durchaufen, falls später das Spiel auf mehr als
			// zwei Spieler erhöht wird und dementsprechend die Werte erhöhen.
		} else {
			System.out.println("ES FAND ---->KEIN<---- UNENTSCHIEDEN STATT!");
			// kein untentschieden
			// die Werte der Spieler erhöhen
			dbUsers.incrementGameWonFromUser(winnerInstagramID);
			for(String instagramIDWhoHasLost: instagramIdAndWonRounds.keySet()) {
				if (!winnerInstagramID.equals(instagramIDWhoHasLost)) {
					dbUsers.incrementGameLostFromUser(instagramIDWhoHasLost);
				}
			}
		}
	}
	
	private String getUsernameWhoHasNextTurn(String gameplayID) {
		String playerIdWhoHasNextTurn = getPlayerIdWhoHasNextTurn(gameplayID);
		return getUsernameForPlayerID(playerIdWhoHasNextTurn);
	}
	
	private String getPlayerIdWhoHasNextTurn(String gameplayID) {
		Gameplay gameplay = dbGameplay.getGameplay(gameplayID);
		String nextTurnID = gameplay.getTurnInstagramID();
		return nextTurnID;
	}
	
	private String getUsernameForPlayerID(String instagramID) {
		User user = dbUsers.getUserForInstagramID(instagramID);
		if (user != null) {
			String username = user.getUsername();
			return username;
		} 
		return null;
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
		if (gameplay != null) {
			int currentTurn = gameplay.getCurrentTurnNumber();
			String cardID = dbGameplay.getCardID(matchSessionID, instagramID, currentTurn);
			
			for(Card card: dbCards.getCardsForUserInstagramID(instagramID)) {
				if(card.getId().equals(cardID)) {
					return card;
				}
			}			
		}
		return null;
	}
	
 }

