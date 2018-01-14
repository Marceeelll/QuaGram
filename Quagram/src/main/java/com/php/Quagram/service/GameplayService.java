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
				
				// Spieler in turn eintragen
				System.out.println("Trägt spieler in turn ein.");
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
		
		new DatabaseQuagramGamePlay().updateGameplay(gameplay);
	}
	
	
	
	public void postSelectedGameplayAttribute(String cardAttribute, String gameplayID, String sessionID) {
		String roundWinnerID = getWinnerIDForGameplayID(gameplayID, cardAttribute);
		incrementRound(gameplayID);
		dbRound.addTurnForGameplay(roundWinnerID, gameplayID);
		dbGameplay.updateGameplayTurnInstagramID(roundWinnerID, gameplayID);
		
		// TODO: hier nur Status zurück geben ob alles geklappt hat
		// 		 Der Client muss dann die Anzeige logik übernehmen
		
		if (checkIfIsLastGameplayRound(gameplayID)) {
			finishGameplay(gameplayID);
		}
		
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
				case "temperature":
					if (Math.abs(winnerCard.getTemperature()) < Math.abs(card.getTemperature())) {
						winnerCard = card;
						winnerID = userID;
					}
					break;
				case "height":
					if (winnerCard.getHeightMeter() < card.getHeightMeter()) {
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
		// TODO: 🚨⚠️ wenn das Gameplay zu Ende ist, steht in der nachfolgenden Zeile --> null
		String usernameWhoHasNextTurn = getUsernameWhoHasNextTurn(gameplayID);
		if (usernameWhoHasNextTurn != null) {
			HashMap<String, Integer> gameplayInfo = createGameplayInfos(gameplayID);
			Gameplay gameplay = dbGameplay.getGameplay(gameplayID);
			int numberOfMaxRounds = gameplay.getTotalNumberOfTurns();
			int currentRound = gameplay.getCurrentTurnNumber();
			
			JSONClientOutput jsonOutput = new JSONClientOutput();
			JSONObject gameplayJSON = jsonOutput.createGameplayJSON(usernameWhoHasNextTurn, userCard, gameplayInfo, numberOfMaxRounds, currentRound);
			
			return gameplayJSON;
		} else {
			return new JSONObject();
		}
	}
	
	private Boolean checkIfIsLastGameplayRound(String gameplayID) {
		Gameplay gameplay = dbGameplay.getGameplay(gameplayID);
		return gameplay.getCurrentTurnNumber() == gameplay.getTotalNumberOfTurns();
	}
	
	public void finishGameplay(String gameplayID) {
		System.out.println("\t\t\tpublic void finishGameplay(String gameplayID) { #######");
		// verlierer/gewinner attribut beim user erhöhen
		updateUsersLostWonNumbers(gameplayID);
		
		// gameSession & gameID von Nutzern austragen
		cleanUpGameplayUserParticipants(gameplayID);
		
		// turn db einträge leeren zu der gameplayID
		cleanUpTurnDB(gameplayID);
		
		// match_session_card db einträge leeren zu der gameplayID
		cleanUpMatchSessionCard(gameplayID);
		
		// gameplay aus db löschen
		dbGameplay.deleteGameplayForGameplay(gameplayID);
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
		System.out.println("WinnerID--- " +winnerInstagramID);
		
		dbUsers.incrementGameWonFromUser(winnerInstagramID);
		for(String instagramIDWhoHasLost: instagramIdAndWonRounds.keySet()) {
			if (!winnerInstagramID.equals(instagramIDWhoHasLost)) {
				dbUsers.incrementGameLostFromUser(instagramIDWhoHasLost);
				System.out.println("Looser-Wrote in hos profile--- " + instagramIDWhoHasLost);
			}
		}
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
	
	private String getUsernameWhoHasNextTurn(String gameplayID) {
		String playerIdWhoHasNextTurn = getPlayerIdWhoHasNextTurn(gameplayID);
		return getUsernameForPlayerID(playerIdWhoHasNextTurn);
	}
	
	private String getPlayerIdWhoHasNextTurn(String gameplayID) {
//		ArrayList<String> winnerInRounds = dbRound.getWinnersForGameplay(gameplayID);
//		System.out.println("--->Hello--->: " + winnerInRounds.size());
//		if (winnerInRounds.size() == 0) {
//			System.out.println("Werfe hier eine keine Nutzer exepetion!");
//			return "Werfe hier eine keine Nutzer exepetion!";
//		}
//		return winnerInRounds.get(winnerInRounds.size()-1);
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
					System.out.println("\tCARD ID: " + card.getId() + " turn: " + currentTurn + " likes: " + card.getLikes());
					return card;
				}
			}			
		}
		
		return null;
	}
	
 }







