package com.php.Quagram.model;

public class MatchSessionCard {
	
	private String matchSessionID;
	private int currentTurn;
	private String ownerID;
	private String cardPicID;
	
	public MatchSessionCard() {
		
	}

	public MatchSessionCard(String matchSessionID, int currentTurn, String ownerID, String cardPicID) {
		super();
		this.matchSessionID = matchSessionID;
		this.currentTurn = currentTurn;
		this.ownerID = ownerID;
		this.cardPicID = cardPicID;
	}

	public String getMatchSessionID() {
		return matchSessionID;
	}

	public void setMatchSessionID(String matchSessionID) {
		this.matchSessionID = matchSessionID;
	}

	public int getCurrentTurn() {
		return currentTurn;
	}

	public void setCurrentTurn(int currentTurn) {
		this.currentTurn = currentTurn;
	}

	public String getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(String ownerID) {
		this.ownerID = ownerID;
	}

	public String getCardPicID() {
		return cardPicID;
	}

	public void setCardPicID(String cardPicID) {
		this.cardPicID = cardPicID;
	}
}
