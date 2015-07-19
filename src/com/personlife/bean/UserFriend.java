package com.personlife.bean;
/**  
 *   
 * @author liugang  
 * @date 2015年7月11日   
 */
public class UserFriend {

	private String friendid;
	private String thumb;
	private String nickname;
	public UserFriend(){
		
	}
	public UserFriend(String f,String t,String n){
		this.friendid=f;
		this.thumb=t;
		this.nickname=n;
	}
	public String getFriendid() {
		return friendid;
	}
	public void setFriendid(String friendid) {
		this.friendid = friendid;
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
	
}
