package com.php.Quagram.service;

import java.util.ArrayList;

import com.php.Quagram.database.DatabaseQuagramCards;
import com.php.Quagram.database.DatabaseQuagramGamePlay;
import com.php.Quagram.database.DatabaseQuagramMatchSessionCard;
import com.php.Quagram.database.DatabaseQuagramUsers;
import com.php.Quagram.model.Card;
import com.php.Quagram.model.Gameplay;
import com.php.Quagram.model.MatchSessionCard;
import com.php.Quagram.model.User;

public class GameplayService {
	DatabaseQuagramUsers dbUsers = new DatabaseQuagramUsers();
	DatabaseQuagramCards dbCards = new DatabaseQuagramCards();
	DatabaseQuagramMatchSessionCard dbMatchSessionCard = new DatabaseQuagramMatchSessionCard();
	
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
 }
