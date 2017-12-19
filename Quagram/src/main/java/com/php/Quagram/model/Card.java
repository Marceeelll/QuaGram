package com.php.Quagram.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Card {
	private String id;
	private String pictureURL;
	private int likes;
	private int comments;
	private double temperature;
	private int heightMeter;
	private int humidity;
	private int engagement;
	private int windspeed;
	private Location location;
	
	public Card() {
	}
	
	public Card(String id, String pictureURL, int likes, int comments, double temperature, int heightMeter,
			Location location, int humidity, int engagement, int windspeed) {
		super();
		this.id = id;
		this.pictureURL = pictureURL;
		this.likes = likes;
		this.comments = comments;
		this.temperature = temperature;
		this.heightMeter = heightMeter;
		this.location = location;
		this.humidity = humidity;
		this.engagement = engagement;
		this.windspeed = windspeed;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public int getHeightMeter() {
		return heightMeter;
	}

	public void setHeightMeter(int heightMeter) {
		this.heightMeter = heightMeter;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public int getHumidity() {
		return humidity;
	}

	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

	public int getEngagement() {
		return engagement;
	}

	public void setEngagement(int engagement) {
		this.engagement = engagement;
	}

	public int getWindspeed() {
		return windspeed;
	}

	public void setWindspeed(int windspeed) {
		this.windspeed = windspeed;
	}
}
