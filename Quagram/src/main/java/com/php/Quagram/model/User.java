package com.php.Quagram.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {
	private String instagramID;
	private String accessToken;
	
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

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	
}
