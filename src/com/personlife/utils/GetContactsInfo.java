package com.personlife.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.personlife.bean.UserFriend;

/**
 * 
 * @author liugang
 * @date 2015年8月29日
 */
public class GetContactsInfo {
	List<UserFriend> localList;
	List<UserFriend> SIMList;
	Context context;
	UserFriend userFriend;
	UserFriend SIMUserFriend;

	public GetContactsInfo(Context context) {
		localList = new ArrayList<UserFriend>();
		SIMList = new ArrayList<UserFriend>();
		this.context = context;

	}

	// ----------------得到本地联系人信息-------------------------------------
	public List<UserFriend> getLocalUserFriends() {
		ContentResolver cr = context.getContentResolver();
		String str[] = { Phone.CONTACT_ID, Phone.DISPLAY_NAME, Phone.NUMBER,
				Phone.PHOTO_ID };
		Cursor cur = cr.query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, str, null,
				null, null);

		if (cur != null) {
			while (cur.moveToNext()) {
				userFriend = new UserFriend();
				userFriend.setPhone(cur.getString(cur
						.getColumnIndex(Phone.NUMBER)));// 得到手机号码

				userFriend.setNickname(cur.getString(cur
						.getColumnIndex(Phone.DISPLAY_NAME)));
				Log.i("phone", userFriend.getPhone() + userFriend.getNickname());
				localList.add(userFriend);
			}
		}
		cur.close();
		return localList;

	}

	public List<UserFriend> getSIMUserFriends() {
		TelephonyManager mTelephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		ContentResolver cr = context.getContentResolver();
		final String SIM_URI_ADN = "content://icc/adn";// SIM卡

		Uri uri = Uri.parse(SIM_URI_ADN);
		Cursor cursor = cr.query(uri, null, null, null, null);
		while (cursor.moveToFirst()) {
			SIMUserFriend = new UserFriend();
			SIMUserFriend.setNickname(cursor.getString(cursor
					.getColumnIndex("name")));
			SIMUserFriend.setPhone(cursor.getString(cursor
					.getColumnIndex("number")));
			SIMList.add(SIMUserFriend);
		}
		cursor.close();
		return SIMList;
	}

}
