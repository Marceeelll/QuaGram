package com.php.Quagram.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class User {
	private String instagramID;
	private String accessToken;
	
	private String sessionID;
	private String username;
	private int gamesLost;
	private int gamesWin;
	
	public User() {
	}
	
	public User(String instagramID, String accessToken) {
		super();
		this.instagramID = instagramID;
		this.accessToken = accessToken;
	}

	public String getInstagramID() {
		return instagramID;
	}

	public void setInstagramID(String instagramID) {
		this.instagramID = instagramID;
	}

	@XmlTransient
	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	
	@XmlTransient
	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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

	@Override
	public String toString() {
		return username;
	}
	
}
