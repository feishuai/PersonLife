package com.personlife.bean;

import java.util.List;

public class Shuoshuo {
	private String name;
	private String status;
	private String content;
	private List<App>apps;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Shuoshuo() {
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<App> getApps() {
		return apps;
	}
	public void setApps(List<App> apps) {
		this.apps = apps;
	}
}
