package com.personlife.adapter;

import java.util.Collections;
import java.util.List;

import com.example.personlifep.R;
import com.personlife.bean.User;
import com.personlife.bean.UserFriend;
import com.personlife.common.PingYinUtil;
import com.personlife.common.PinyinComparator;
import com.personlife.common.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;


public class ContactAdapter extends BaseAdapter implements SectionIndexer {
	private Context mContext;
	private List<UserFriend> UserFriendInfos;// 好友信息

	public ContactAdapter(Context mContext, List<UserFriend> UserInfos) {
		this.mContext = mContext;
		this.UserFriendInfos = UserInfos;
		// 排序(实现了中英文混排)
		Collections.sort(UserInfos, new PinyinComparator());
	}

	@Override
	public int getCount() {
		return UserFriendInfos.size();
	}

	@Override
	public Object getItem(int position) {
		return UserFriendInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		UserFriend user = UserFriendInfos.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.contact_item, null);

		}
		ImageView ivAvatar = ViewHolder.get(convertView,
				R.id.contactitem_avatar_iv);
		TextView tvCatalog = ViewHolder.get(convertView,
				R.id.contactitem_catalog);
		TextView tvNick = ViewHolder.get(convertView, R.id.contactitem_nick);
		String catalog = PingYinUtil.converterToFirstSpell(user.getNickname())
				.substring(0, 1);
		if (position == 0) {
			tvCatalog.setVisibility(View.VISIBLE);
			tvCatalog.setText(catalog);
		} else {
			UserFriend Nextuser = UserFriendInfos.get(position - 1);
			String lastCatalog = PingYinUtil.converterToFirstSpell(
					Nextuser.getNickname()).substring(0, 1);
			if (catalog.equals(lastCatalog)) {
				tvCatalog.setVisibility(View.GONE);
			} else {
				tvCatalog.setVisibility(View.VISIBLE);
				tvCatalog.setText(catalog);
			}
		}

		ivAvatar.setImageResource(R.drawable.head);
		tvNick.setText(user.getNickname());
		return convertView;
	}

	@Override
	public int getPositionForSection(int section) {
		for (int i = 0; i < UserFriendInfos.size(); i++) {
			UserFriend user = UserFriendInfos.get(i);
			String l = PingYinUtil.converterToFirstSpell(user.getNickname())
					.substring(0, 1);
			char firstChar = l.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		return 0;
	}

	@Override
	public int getSectionForPosition(int position) {
		return 0;
	}

	@Override
	public Object[] getSections() {
		return null;
	}
}
