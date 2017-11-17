package com.php.Quagram.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Card {
	private int id;
	private String pictureURL;
	private int likes;
	private int comments;
	private double temperature;
	private double heightMeter;
	private Location location;
	
	public Card() {
	}
	
	public Card(int id, String pictureURL, int likes, int comments, double temperature, double heightMeter,
			Location location) {
		super();
		this.id = id;
		this.pictureURL = pictureURL;
		this.likes = likes;
		this.comments = comments;
		this.temperature = temperature;
		this.heightMeter = heightMeter;
		this.location = location;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPictureURL() {
		return pictureURL;
	}

	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public int getComments() {
		return comments;
	}

	public void setComments(int comments) {
		this.comments = comments;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public double getHeightMeter() {
		return heightMeter;
	}

	public void setHeightMeter(double heightMeter) {
		this.heightMeter = heightMeter;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}
