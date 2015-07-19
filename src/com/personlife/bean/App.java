package com.personlife.bean;

import java.util.Date;
import java.util.List;

public class App {
	private int id;
	private String appname;
	private int stars;
	private int dowloadcounts;
	private String tag;
	private String icon;
	private String introdution;
	private String version;
	private Date createdDate;
	private Date updateDate;
	private String[] urls;
	private List<Comment> comments;
	private String downloadUrl;
	public App(String appname,int stars,String tag,int downloadcounts){
		this.appname=appname;
		this.stars = stars;
		this.tag = tag;
		this.dowloadcounts = downloadcounts;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAppname() {
		return appname;
	}
	public void setAppname(String appname) {
		this.appname = appname;
	}
	public int getStars() {
		return stars;
	}
	public void setStars(int stars) {
		this.stars = stars;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getIntrodution() {
		return introdution;
	}
	public void setIntrodution(String introdution) {
		this.introdution = introdution;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String[] getUrls() {
		return urls;
	}
	public void setUrls(String[] urls) {
		this.urls = urls;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public int getDowloadcounts() {
		return dowloadcounts;
	}
	public void setDowloadcounts(int dowloadcounts) {
		this.dowloadcounts = dowloadcounts;
	}
	
}
