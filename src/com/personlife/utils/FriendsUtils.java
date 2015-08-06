package com.personlife.utils;

import java.util.ArrayList;
import java.util.List;

import com.personlife.bean.UserFriend;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**  
 *   
 * @author liugang  
 * @date 2015年8月6日   
 */
public class FriendsUtils extends SQLiteOpenHelper{
	public static List<UserFriend> userFriends = new ArrayList<UserFriend>();// 好友信息
	public static final String CREATE_FRIENDS="create table friendslist ("
			+"friendid text primary key,"
			+"nickname text,"
			+"thum text)";
	private Context mcontext;
	public FriendsUtils(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
		mcontext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_FRIENDS);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
