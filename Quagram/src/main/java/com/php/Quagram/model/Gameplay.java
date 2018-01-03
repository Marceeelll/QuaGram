package com.php.Quagram.model;

public class Gameplay {
	
	private String gameplayID;
	private String turnInstagramUsername;
	private int totalNumberOfTurns;
	private int currentTurnNumber;
	
	public Gameplay() {
		
	}
	
	public Gameplay(String gameplayID, String instagramUsername, int totalNumberOfTurns, int currentTurnNumber) {
		super();
		this.gameplayID = gameplayID;
		this.turnInstagramUsername = instagramUsername;
		this.totalNumberOfTurns = totalNumberOfTurns;
		this.currentTurnNumber = currentTurnNumber;
	}

	public String getGameplayID() {
		return gameplayID;
	}

	public void setGameplayID(String gameplayID) {
		this.gameplayID = gameplayID;
	}

	public String getTurnInstagramID() {
		return turnInstagramUsername;
	}

	public void setTurnInstagramID(String turnInstagramID) {
		this.turnInstagramUsername = turnInstagramID;
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
