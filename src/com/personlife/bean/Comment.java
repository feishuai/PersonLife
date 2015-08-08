package com.personlife.bean;

import java.util.Date;

public class Comment {
	private String title;
	private int commentstars;
	private String usernickname;
	private String comments;
	private String userthumb;
	private String created_at;
	
	public Comment(){
		
	}
	public Comment(String title, int stars, String username, String content,
			String createdDate) {
		super();
		this.title = title;
		this.commentstars = stars;
		this.usernickname = username;
		this.comments = content;
		this.created_at = createdDate;
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

	public String getCreated_at() {
		return created_at;
	}

	public String getUserthumb() {
		return userthumb;
	}

	public void setUserthumb(String userthumb) {
		this.userthumb = userthumb;
	}

	public void setCreated_at(String createdDate) {
		this.created_at = createdDate;
	}
}
