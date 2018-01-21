package com.php.Quagram.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class Invitation {
	private Date created;
	private String hostUserID;
	private String matchSessionID;
	
	//@XmlElement(name = "link-templates")
	private ArrayList<Link> links = new ArrayList<>();
	
	@XmlTransient
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getCreatedDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return dateFormat.format(getCreated());
	}
	public void setCreatedDate(String dateString) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = dateFormat.parse(dateString);
			setCreated(date);
		} catch (Exception e) {
			e.printStackTrace();
			setCreated(new Date());
		}
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
	
	public void setCreatedFromString(String dateString) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = dateFormatter.parse(dateString);
			setCreated(date);
		} catch (ParseException e) {
			setCreated(new Date());
		}
	}
	
	public String getCreatedFormated() {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormatter.format(created);
	}
	public ArrayList<Link> getLinks() {
		return links;
	}
	
	public void addLink(String url, String rel, String type) {
		Link link = new Link();
		link.setLink(url);
		link.setRel(rel);
		link.setType(type);
		links.add(link);
	}
	
}
