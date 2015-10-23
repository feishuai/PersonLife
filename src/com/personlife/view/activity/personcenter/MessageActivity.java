package com.personlife.view.activity.personcenter;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.personlifep.R;
import com.fasterxml.jackson.core.type.TypeReference;
import com.loopj.android.http.RequestParams;
import com.personlife.bean.Message;
import com.personlife.bean.SystemNotification;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONArrayHttpResponseHandler;
import com.personlife.utils.ComplexPreferences;
import com.personlife.utils.Constants;
import com.personlife.utils.ImageLoaderUtils;
import com.personlife.utils.PersonInfoLocal;
import com.personlife.utils.Utils;
import com.personlife.view.activity.circle.CircleActivity;

public class MessageActivity extends Activity implements OnClickListener {
	Button mBtnBack;
	TextView mTitle;
	ListView listview;
	List<Message> messages;
	MessageAdapter messageApdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview);
		mBtnBack = (Button) findViewById(R.id.txt_left);
		mBtnBack.setVisibility(View.VISIBLE);
		mBtnBack.setOnClickListener(this);
		mTitle = (TextView) findViewById(R.id.txt_title);
		mTitle.setText("消息列表");
		listview = (ListView) findViewById(R.id.listview);
		messages = new ArrayList<Message>();
		messageApdapter = new MessageAdapter(getApplicationContext(), messages);
		RequestParams request = new RequestParams();
		request.add("phone", PersonInfoLocal.getPhone(getApplicationContext()));
		BaseAsyncHttp.postReq(this, "/users/notify", request,
				new JSONArrayHttpResponseHandler() {
					@Override
					public void jsonSuccess(JSONArray resp) {
						// TODO Auto-generated method stub
						for (int i = 0; i < resp.length(); i++) {
							Message message = new Message();
							JSONObject messagejson = resp.optJSONObject(i);
							message.setKind(messagejson.optString("kind"));
							message.setContent(messagejson.optString("content"));
							message.setNickname(messagejson
									.optString("nickname"));
							message.setPhone(messagejson.optString("phone"));
							message.setTime(messagejson.optLong("created_at"));
							message.setThumb(messagejson.optString("thumb"));
							messages.add(message);
						}
						if (messages.size() == 0)
							messages = ComplexPreferences.getObject(
									getApplicationContext(), Constants.MESSAGE,
									new TypeReference<ArrayList<Message>>() {
									});
						if (messages != null)
							ComplexPreferences.putObject(
									getApplicationContext(), Constants.MESSAGE,
									messages);
						listview.setAdapter(messageApdapter);
					}

					@Override
					public void jsonFail(JSONArray resp) {
						// TODO Auto-generated method stub
						messages = ComplexPreferences.getObject(
								getApplicationContext(), Constants.MESSAGE,
								new TypeReference<ArrayList<Message>>() {
								});
						if (messages != null)
							listview.setAdapter(messageApdapter);
					}
				});
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

	class MessageAdapter extends BaseAdapter implements SectionIndexer {
		private Context mContext;
		private List<Message> messages;

		public MessageAdapter(Context mContext, List<Message> messages) {
			this.mContext = mContext;
			this.messages = messages;

		}

		@Override
		public int getCount() {
			return messages.size();
		}

		@Override
		public Object getItem(int position) {
			return messages.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.layout_item_message, null);
			}
			TextView nickname = (TextView) convertView
					.findViewById(R.id.nickname);
			TextView content = (TextView) convertView
					.findViewById(R.id.content);
			TextView time = (TextView) convertView.findViewById(R.id.time);
			TextView kind = (TextView) convertView.findViewById(R.id.kind);
			ImageView thumb = (ImageView) convertView.findViewById(R.id.thumb);
			final Message message = messages
					.get(messages.size() - position - 1);
			nickname.setText(message.getNickname());
			content.setText(message.getContent());
			time.setText(Utils.TimeStamp2SystemNotificationDate(message
					.getTime()));
			kind.setText(message.getKind());
			ImageLoaderUtils.displayImageView(message.getThumb(), thumb);
			nickname.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Utils.start_Activity(MessageActivity.this,
							CircleActivity.class, new BasicNameValuePair(
									"starphone", message.getPhone()));
				}
			});
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
