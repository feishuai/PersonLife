package com.personlife.bean;

import java.io.Serializable;

import android.media.Image;

/**  
 *   
 * @author liugang  
 * @date 2015年6月26日   
 */
public class UserInfo implements Serializable{

	private Image head;
	private int ID;
	private String nickname;
	private String sex;
	private String area;
	private String profession;
	private String interests;
	private String sign;
	
	public Image getHead() {
		return head;
	}
	public void setHead(Image head) {
		this.head = head;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public String getInterests() {
		return interests;
	}
	public void setInterests(String interests) {
		this.interests = interests;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
}
