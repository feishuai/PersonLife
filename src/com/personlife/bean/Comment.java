package com.personlife.bean;

import java.util.Date;

public class Comment {
	private String title;
	private int appid;
	private int commentstars;
	private String username;
	private String userthumb;
	private String usernickname;
	private String comments;
	private long created_at;

	public Comment() {

	}

	public Comment(String title, int stars, String username, String content,
			int createdDate) {
		super();
		this.title = title;
		this.commentstars = stars;
		this.usernickname = username;
		this.comments = content;
		this.created_at = createdDate;
	}

	public int getAppid() {
		return appid;
	}

	public void setAppid(int appid) {
		this.appid = appid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getCommentstars() {
		return commentstars;
	}

	public void setCommentstars(int stars) {
		this.commentstars = stars;
	}

	public String getUsernickname() {
		return usernickname;
	}

	public void setUsernickname(String username) {
		this.usernickname = username;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String content) {
		this.comments = content;
	}

	public long getCreated_at() {
		return created_at;
	}

	public String getUserthumb() {
		return userthumb;
	}

	public void setUserthumb(String userthumb) {
		this.userthumb = userthumb;
	}

	public void setCreated_at(long createdDate) {
		this.created_at = createdDate;
	}
}
