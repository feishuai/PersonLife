package com.personlife.utils;

import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;

/**  
 *   
 * @author liugang  
 * @date 2015年8月9日   
 */
public class PersonInfoLocal {
	private static String phone;
	private static String nickname;
	public static String getPhone(){
		if(phone!=null)
			return phone;
		return "18268028693";
	}
	public static String getNickname(){
		if(nickname!=null)
			return nickname;
		return "小明";
	}
	public static void storeRegisterTel(Context ctx,String phone){
		SharedPreferences.Editor editor = ctx.getSharedPreferences(phone, ctx.MODE_PRIVATE).edit();
		editor.putString("telephone", phone);
		editor.putString("password", "");
		editor.commit();
	}
	public static void storeRegisterNickName(Context ctx,String phone,String nickname,String headUri){
		SharedPreferences.Editor editor = ctx.getSharedPreferences(phone, ctx.MODE_PRIVATE).edit();
		editor.putString("nickname", nickname);
		editor.putString("headuri", headUri);
		editor.commit();
	}
	public static void storeRegisterHobbys(Context ctx,String phone ,Set<String> hobby){
		SharedPreferences.Editor editor = ctx.getSharedPreferences(phone, ctx.MODE_PRIVATE).edit();
		editor.putStringSet("hobbys", hobby);
		editor.commit();
	}
	public static String getPersonPassword(Context ctx,String phone){
		SharedPreferences pref=ctx.getSharedPreferences(phone, ctx.MODE_PRIVATE);
		return pref.getString("password", "");		
	}
	public static void storeLoginTelAndPass(Context ctx,String phone,String password){
		SharedPreferences.Editor editor = ctx.getSharedPreferences(phone, ctx.MODE_PRIVATE).edit();
		editor.putString("telephone", phone);
		editor.putString("password", password);
		editor.commit();
		phone = phone;
	}
	public static void storeMainPersonInfo(Context ctx,String phone,
			String nickname,String headkey,String signature,String sex,String location,
			String job,Set<String> hobbys)
	{
		SharedPreferences.Editor editor = ctx.getSharedPreferences(phone, ctx.MODE_PRIVATE).edit();
		editor.putString("nickname", nickname);
		editor.putString("headkey", headkey);
		editor.putString("signature", signature);
		editor.putString("sex", sex);
		editor.putString("location",location);
		editor.putString("job", job);
		editor.putStringSet("hobbys", hobbys);
		editor.commit();
	}
	public static void storeMainHeadUri(Context ctx,String phone,String headuri){
		SharedPreferences.Editor editor = ctx.getSharedPreferences(phone, ctx.MODE_PRIVATE).edit();
		editor.putString("headuri", headuri);
		editor.commit();
	}
	public static String getNcikName(Context ctx,String phone){
		SharedPreferences pref=ctx.getSharedPreferences(phone, ctx.MODE_PRIVATE);
		return pref.getString("nickname", "");	
	}
	public static String getSex(Context ctx,String phone){
		SharedPreferences pref=ctx.getSharedPreferences(phone, ctx.MODE_PRIVATE);
		return pref.getString("sex", "男");	
	}
	public static String getSignature(Context ctx,String phone){
		SharedPreferences pref=ctx.getSharedPreferences(phone, ctx.MODE_PRIVATE);
		return pref.getString("signature", "");	
	}
	public static String getHeadKey(Context ctx,String phone){
		SharedPreferences pref=ctx.getSharedPreferences(phone, ctx.MODE_PRIVATE);
		return pref.getString("headkey", "");	
	}
	public static String getHeadUri(Context ctx,String phone){
		SharedPreferences pref=ctx.getSharedPreferences(phone, ctx.MODE_PRIVATE);
		return pref.getString("headuri", "");	
	}
	public static String getLocation(Context ctx,String phone){
		SharedPreferences pref=ctx.getSharedPreferences(phone, ctx.MODE_PRIVATE);
		return pref.getString("location", "");
	}
	public static String getJob(Context ctx,String phone){
		SharedPreferences pref=ctx.getSharedPreferences(phone, ctx.MODE_PRIVATE);
		return pref.getString("job", "");
	}
	public static Set<String> getHobbys(Context ctx,String phone){
		SharedPreferences pref=ctx.getSharedPreferences(phone, ctx.MODE_PRIVATE);
		return pref.getStringSet("hobbys", null);
	}
	public static void storeHeadkey(Context ctx,String phone,String headkey){
		SharedPreferences.Editor editor = ctx.getSharedPreferences(phone, ctx.MODE_PRIVATE).edit();
		editor.putString("headkey", headkey);
		editor.commit();
	}
	public static void storeNickname(Context ctx,String phone,String nickname){
		SharedPreferences.Editor editor = ctx.getSharedPreferences(phone, ctx.MODE_PRIVATE).edit();
		editor.putString("nickname", nickname);
		editor.commit();
	}
	public static void storeSex(Context ctx,String phone,String sex){
		SharedPreferences.Editor editor = ctx.getSharedPreferences(phone, ctx.MODE_PRIVATE).edit();
		editor.putString("sex", sex);
		editor.commit();
	}
	public static void storeLocation(Context ctx,String phone,String location){
		SharedPreferences.Editor editor = ctx.getSharedPreferences(phone, ctx.MODE_PRIVATE).edit();
		editor.putString("location", location);
		editor.commit();
	}
	public static void storeJob(Context ctx,String phone,String job){
		SharedPreferences.Editor editor = ctx.getSharedPreferences(phone, ctx.MODE_PRIVATE).edit();
		editor.putString("job", job);
		editor.commit();
	}
	public static void storeSignature(Context ctx,String phone,String signature){
		SharedPreferences.Editor editor = ctx.getSharedPreferences(phone, ctx.MODE_PRIVATE).edit();
		editor.putString("signature", signature);
		editor.commit();
	}
}
