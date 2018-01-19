package com.php.Quagram.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Link {
	private String link;
	private String rel;
	private String type;
	private String title;
	
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getRel() {
		return rel;
	}
	public void setRel(String rel) {
		this.rel = rel;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
