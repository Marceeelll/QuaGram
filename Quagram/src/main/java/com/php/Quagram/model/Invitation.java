package com.php.Quagram.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Invitation {
	private Date created;
	private String hostUserID;
	private String matchSessionID;
	
	
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getHostUserID() {
		return hostUserID;
	}
	public void setHostUserID(String hostUserID) {
		this.hostUserID = hostUserID;
	}
	public String getMatchSessionID() {
		return matchSessionID;
	}
	public void setMatchSessionID(String matchSessionID) {
		this.matchSessionID = matchSessionID;
	}
	
	
}
