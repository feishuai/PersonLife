package com.personlife.bean;

import java.util.List;

public class Shuoshuo {
	private String nickname;
	private String area;
	private String content;
	private String phone;
	private String thumb;
	private String labels;
	private int collecttime;
	private int createdtime;
	private int msgid;
	private int score;
	private List<App> apps;
	private List<Reply> replies;
	private List<Star> stars;

	public Shuoshuo() {
	}

	public String getLabels() {
		return labels;
	}

	public void setLabels(String labels) {
		this.labels = labels;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getCreatedtime() {
		return createdtime;
	}

	public void setCreatedtime(int createdtime) {
		this.createdtime = createdtime;
	}

	public List<Star> getStars() {
		return stars;
	}

	public void setStars(List<Star> stars) {
		this.stars = stars;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public int getCollecttime() {
		return collecttime;
	}

	public void setCollecttime(int collecttime) {
		this.collecttime = collecttime;
	}

	public int getMsgid() {
		return msgid;
	}

	public void setMsgid(int msgid) {
		this.msgid = msgid;
	}

	public List<App> getApps() {
		return apps;
	}

	public void setApps(List<App> apps) {
		this.apps = apps;
	}

	public List<Reply> getReplies() {
		return replies;
	}

	public void setReplies(List<Reply> replies) {
		this.replies = replies;
	}

}
