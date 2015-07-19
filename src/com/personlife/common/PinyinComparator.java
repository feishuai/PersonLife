package com.personlife.common;

import java.util.Comparator;

import com.personlife.bean.User;
import com.personlife.bean.UserFriend;


public class PinyinComparator implements Comparator {

	@Override
	public int compare(Object arg0, Object arg1) {
		// String str1 = PingYinUtil.getPingYin((String) o1);
		// String str2 = PingYinUtil.getPingYin((String) o2);
		// return str1.compareTo(str2);

		UserFriend user0 = (UserFriend) arg0;
		UserFriend user1 = (UserFriend) arg1;
		// 按照名字排序
		String catalog0 = PingYinUtil
				.converterToFirstSpell(user0.getNickname()).substring(0, 1);
		String catalog1 = PingYinUtil
				.converterToFirstSpell(user1.getNickname()).substring(0, 1);
		int flag = catalog0.compareTo(catalog1);
		return flag;

	}

}
