package com.personlife.common;

import com.alibaba.fastjson.JSON;
import com.easemob.chat.EMChatManager;
import com.personlife.bean.User;
import com.personlife.utils.Constants;

import android.content.Context;
import android.text.TextUtils;



public class UserUtils {
	/**
	 * 获取用户信息
	 * 
	 * @param context
	 * @return
	 */
	public static User getUserModel(Context context) {
		User user = null;
		String jsondata = Utils.getValue(context, Constants.UserInfo);
		// Log.e("", jsondata);
		if (!TextUtils.isEmpty(jsondata))
			user = JSON.parseObject(jsondata, User.class);
		return user;
	}

	/**
	 * 获取用户ID
	 * 
	 * @param context
	 * @return
	 */
	public static String getUserID(Context context) {
		User user = getUserModel(context);
		if (user != null)
			return user.getId();
		else
			return "";
	}

	/**
	 * 获取用户名字
	 * 
	 * @param context
	 * @return
	 */
	public static String getUserName(Context context) {
		User user = getUserModel(context);
		if (user != null)
			return user.getUserName();
		else
			return "";
	}

	/**
	 * 获取用户
	 * 
	 * @param context
	 * @return
	 */
	public static String getUserPwd(Context context) {
		User user = getUserModel(context);
		if (user != null)
			return user.getPassword();
		else
			return "";
	}

}
