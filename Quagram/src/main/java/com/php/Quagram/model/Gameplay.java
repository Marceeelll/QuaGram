package com.php.Quagram.model;

public class Gameplay {
	
	private String gameplayID;
	private String instagramID;
	private int totalNumberOfTurns;
	private int currentTurnNumber;
	
	public Gameplay(String gameplayID, String instagramID, int totalNumberOfTurns, int currentTurnNumber) {
		super();
		this.gameplayID = gameplayID;
		this.instagramID = instagramID;
		this.totalNumberOfTurns = totalNumberOfTurns;
		this.currentTurnNumber = currentTurnNumber;
	}

	public String getGameplayID() {
		return gameplayID;
	}

	public void setGameplayID(String gameplayID) {
		this.gameplayID = gameplayID;
	}

	public String getInstagramID() {
		return instagramID;
	}

	public void setInstagramID(String instagramID) {
		this.instagramID = instagramID;
	}

	public int getTotalNumberOfTurns() {
		return totalNumberOfTurns;
	}

	public void setTotalNumberOfTurns(int totalNumberOfTurns) {
		this.totalNumberOfTurns = totalNumberOfTurns;
	}

	public int getCurrentTurnNumber() {
		return currentTurnNumber;
	}

	public void setCurrentTurnNumber(int currentTurnNumber) {
		this.currentTurnNumber = currentTurnNumber;
	}
}
