package com.personlife.bean;

import java.util.List;

/**  
 *   
 * @author liugang  
 * @date 2015年8月13日   
 */
public class Star {
	private String phone;
	private String nickname;
	private String thumb;
	private String follower;
	private String shared;
	private String signature;
	private int famous;
	private int favour;
	private int sharecounts;
	private List<Shuoshuo> shuoshuos;
	private List<App> apps;
	public Star(){
	}
	
	public int getFamous() {
		return famous;
	}

	public void setFamous(int famous) {
		this.famous = famous;
	}

	public int getFavour() {
		return favour;
	}

	public void setFavour(int favour) {
		this.favour = favour;
	}

	public int getSharecounts() {
		return sharecounts;
	}

	public void setSharecounts(int sharecounts) {
		this.sharecounts = sharecounts;
	}

	public List<Shuoshuo> getShuoshuos() {
		return shuoshuos;
	}

	public void setShuoshuos(List<Shuoshuo> shuoshuos) {
		this.shuoshuos = shuoshuos;
	}

	public List<App> getApps() {
		return apps;
	}

	public void setApps(List<App> apps) {
		this.apps = apps;
	}

	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getThumb() {
		return thumb;
	}
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
	
	public String getFollower() {
		return follower;
	}
	public void setFollower(String follower) {
		this.follower = follower;
	}
	public String getShared() {
		return shared;
	}
	public void setShared(String shared) {
		this.shared = shared;
	}
	
}
