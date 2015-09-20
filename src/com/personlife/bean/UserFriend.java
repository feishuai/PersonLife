package com.personlife.bean;

/**
 * 
 * @author liugang
 * @date 2015年7月11日
 */
public class UserFriend {

	private String phone;
	private String thumb;
	private String nickname;
	private int isRegister;

	public UserFriend() {

	}

	public UserFriend(String f, String t, String n) {
		this.phone = f;
		this.thumb = t;
		this.nickname = n;
	}

	public int getIsRegister() {
		return isRegister;
	}

	public void setIsRegister(int isRegister) {
		this.isRegister = isRegister;
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if (!(o instanceof UserFriend)) {
			return false;
		}
		UserFriend userFriend = (UserFriend) o;
		return userFriend.getPhone().equals(this.phone);
	}

}
