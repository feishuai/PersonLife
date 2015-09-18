package com.personlife.view.activity.personcenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.personlifep.R;
import com.fasterxml.jackson.core.type.TypeReference;
import com.personlife.bean.SystemNotification;
import com.personlife.utils.ComplexPreferences;
import com.personlife.utils.Constants;
import com.personlife.utils.Utils;

/**
 * 
 * @author liugang
 * @date 2015年6月22日
 */
public class TongzhiActivity extends Activity implements OnClickListener {
	Button mBtnBack;
	TextView mTitle;
	ListView listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview);
		mBtnBack = (Button) findViewById(R.id.txt_left);
		mBtnBack.setVisibility(View.VISIBLE);
		mBtnBack.setOnClickListener(this);
		mTitle = (TextView) findViewById(R.id.txt_title);
		mTitle.setText("系统通知");
		listview = (ListView) findViewById(R.id.listview);
		ArrayList<SystemNotification> notifications = ComplexPreferences
				.getObject(this, Constants.SYSTEMNOTIFICATION,
						new TypeReference<ArrayList<SystemNotification>>() {
						});
		if (notifications == null) {
			notifications = new ArrayList<SystemNotification>();
		}
		// Collections.sort(notifications, new Comparator<SystemNotification>()
		// {
		//
		// @Override
		// public int compare(SystemNotification lhs, SystemNotification rhs) {
		// // TODO Auto-generated method stub
		// return lhs.getTime() < rhs.getTime() ? 1 : 0;
		// }
		// });
		SystemNotificationAdapter notificationAdapter = new SystemNotificationAdapter(
				getApplicationContext(), notifications);
		listview.setAdapter(notificationAdapter);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txt_left:
			finish();
			break;
		}
	}

	class SystemNotificationAdapter extends BaseAdapter implements
			SectionIndexer {
		private Context mContext;
		private List<SystemNotification> notifications;

		public SystemNotificationAdapter(Context mContext,
				List<SystemNotification> notifications) {
			this.mContext = mContext;
			this.notifications = notifications;

		}

		@Override
		public int getCount() {
			return notifications.size();
		}

		@Override
		public Object getItem(int position) {
			return notifications.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.layout_item_systemnotification, null);

			}
			TextView title = (TextView) convertView.findViewById(R.id.title);
			TextView content = (TextView) convertView
					.findViewById(R.id.content);
			TextView time = (TextView) convertView.findViewById(R.id.time);
			SystemNotification notification = notifications.get(notifications
					.size() - position - 1);
			title.setText(notification.getTitle());
			content.setText(notification.getContent());
			time.setText(Utils.TimeStamp2SystemNotificationDate(notification
					.getTime()));
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
}
