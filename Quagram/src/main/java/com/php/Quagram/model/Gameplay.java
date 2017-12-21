package com.php.Quagram.model;

public class Gameplay {
	
	private String gameplayID;
	private String turnInstagramID;
	private int totalNumberOfTurns;
	private int currentTurnNumber;
	
	public Gameplay() {
		
	}
	
	public Gameplay(String gameplayID, String instagramID, int totalNumberOfTurns, int currentTurnNumber) {
		super();
		this.gameplayID = gameplayID;
		this.turnInstagramID = instagramID;
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
		return turnInstagramID;
	}

	public void setTurnInstagramID(String turnInstagramID) {
		this.turnInstagramID = turnInstagramID;
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
