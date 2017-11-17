package com.php.Quagram.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Player {
	private String name;
	private int gamesLost;
	private int gamesWin;
	
	public Player() {
	}
	
	public Player(String name, int gamesLost, int gamesWin) {
		super();
		this.name = name;
		this.gamesLost = gamesLost;
		this.gamesWin = gamesWin;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getGamesLost() {
		return gamesLost;
	}
	public void setGamesLost(int gamesLost) {
		this.gamesLost = gamesLost;
	}
	public int getGamesWin() {
		return gamesWin;
	}
	public void setGamesWin(int gamesWin) {
		this.gamesWin = gamesWin;
	}
}
