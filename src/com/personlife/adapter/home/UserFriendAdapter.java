package com.personlife.adapter.home;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.personlifep.R;
import com.personlife.bean.User;
import com.personlife.bean.UserFriend;
import com.personlife.common.ViewHolder;

/**  
 *   
 * @author liugang  
 * @date 2015年7月19日   
 */
public class UserFriendAdapter extends BaseAdapter implements SectionIndexer {
	private Context mContext;
	private List<UserFriend> UserInfos;// 好友信息

	public UserFriendAdapter(Context mContext, List<UserFriend> UserInfos) {
		this.mContext = mContext;
		this.UserInfos = UserInfos;
		
	}

	@Override
	public int getCount() {
		return UserInfos.size();
	}

	@Override
	public Object getItem(int position) {
		return UserInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		UserFriend user = UserInfos.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.contact_item, null);

		}
		ImageView ivAvatar = ViewHolder.get(convertView,
				R.id.contactitem_avatar_iv);
		
		TextView tvNick = ViewHolder.get(convertView, R.id.contactitem_nick);
		ivAvatar.setImageResource(R.drawable.head);
		tvNick.setText(user.getNickname());
		return convertView;
	}

	@Override
	public int getPositionForSection(int section) {
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