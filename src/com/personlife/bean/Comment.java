package com.personlife.bean;

import java.util.Date;

public class Comment {
	private String title;
	private int stars;
	private String username;
	private String content;
	private Date createdDate;

	public Comment(String title, int stars, String username, String content,
			Date createdDate) {
		super();
		this.title = title;
		this.stars = stars;
		this.username = username;
		this.content = content;
		this.createdDate = createdDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
}
