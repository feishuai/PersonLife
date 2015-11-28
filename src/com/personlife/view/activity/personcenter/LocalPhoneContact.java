package com.personlife.view.activity.personcenter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personlifep.R;
import com.loopj.android.http.RequestParams;
import com.personlife.bean.UserFriend;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONArrayHttpResponseHandler;
import com.personlife.net.JSONObjectHttpResponseHandler;
import com.personlife.utils.FriendsUtils;
import com.personlife.utils.GetContactsInfo;
import com.personlife.utils.PersonInfoLocal;

/**
 * 
 * @author liugang
 * @date 2015年9月1日
 */
public class LocalPhoneContact extends Activity implements OnClickListener {

	private TextView txt_title, tv1, tv2;
	private Button back;
	private ListView mlistview, mNolistview;
	private String telphone;
	private List<UserFriend> mList, mNoList;
	private LinearLayout ll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_localcontact);
		telphone = getIntent().getStringExtra("telphone");
		initView();
		initMessage();
	}

	private void initMessage() {
		// 处理返回的发送状态
		String SENT_SMS_ACTION = "SENT_SMS_ACTION";
		Intent sentIntent = new Intent(SENT_SMS_ACTION);
		PendingIntent sentPI = PendingIntent.getBroadcast(
				getApplicationContext(), 0, sentIntent, 0);
		// register the Broadcast Receivers
		getApplicationContext().registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context _context, Intent _intent) {
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					Toast.makeText(getApplicationContext(), "邀请短信发送成功",
							Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
				case SmsManager.RESULT_ERROR_RADIO_OFF:
				case SmsManager.RESULT_ERROR_NULL_PDU:
					Toast.makeText(getApplicationContext(), "邀请短信发送失败",
							Toast.LENGTH_SHORT).show();
					break;
				}
			}
		}, new IntentFilter(SENT_SMS_ACTION));

	}

	public void initView() {
		txt_title = (TextView) findViewById(R.id.txt_title);
		txt_title.setText("手机通讯录");
		tv1 = (TextView) findViewById(R.id.yizhuce);
		tv2 = (TextView) findViewById(R.id.yaoqingjiaru);
		back = (Button) findViewById(R.id.txt_left);
		back.setVisibility(View.VISIBLE);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
				finish();
			}
		});
		mlistview = (ListView) findViewById(R.id.localcontactlist);
		mNolistview = (ListView) findViewById(R.id.no_localcontactlist);
		ll = (LinearLayout) findViewById(R.id.ll);
		mList = new ArrayList<UserFriend>();
		mNoList = new ArrayList<UserFriend>();
		mNoList.addAll(new GetContactsInfo(this).getLocalUserFriends());
		mNoList.addAll(new GetContactsInfo(this).getSIMUserFriends());

		mNoList.removeAll(FriendsUtils.userFriends);
		RequestParams request = new RequestParams();
		for (int i = 0; i < mNoList.size(); i++) {
			request.put("phone[" + i + "]", mNoList.get(i).getPhone().trim().replace(" ", ""));
		}
		request.put("myphone", telphone);
		BaseAsyncHttp.postReq(this, "/friend/exist", request,
				new JSONArrayHttpResponseHandler() {

					@Override
					public void jsonSuccess(JSONArray resp) {
						// TODO Auto-generated method stub
						int i = 0;
						for (Iterator iterator = mNoList.iterator(); iterator
								.hasNext();) {
							UserFriend userFriend = (UserFriend) iterator
									.next();
							userFriend.setIsRegister(0);
							if (resp.optInt(i) == 1) {
								userFriend.setIsRegister(1);
								mList.add(userFriend);
							}
							if (resp.optInt(i) == 2) {
								iterator.remove();
							}
							i++;
						}
						mNoList.removeAll(mList);
						if (mNoList.size() == 0) {
							tv2.setVisibility(View.GONE);
						}
						if (mList.size() == 0) {
							tv1.setVisibility(View.GONE);
						}
						ll.setVisibility(View.VISIBLE);
						mlistview.setAdapter(new LocalContactAdapter(
								getApplicationContext(), mList, telphone));
						mNolistview.setAdapter(new LocalContactAdapter(
								getApplicationContext(), mNoList, telphone));
					}

					@Override
					public void jsonFail(JSONArray resp) {
						// TODO Auto-generated method stub

					}
				});

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	class LocalContactAdapter extends BaseAdapter {
		private Context context;
		private List<UserFriend> mList;
		private String mytelphone;

		public LocalContactAdapter(Context ctx, List<UserFriend> list, String s) {
			context = ctx;
			mList = list;
			mytelphone = s;
		}

		@Override
		public int getCount() {
			return mList.size();
		}

		@Override
		public Object getItem(int position) {
			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			final ViewHolder holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.layout_item_localphone, parent, false);
				holder = new ViewHolder();
				holder.photo = (ImageView) convertView
						.findViewById(R.id.img_photolocal);
				holder.nickname = (TextView) convertView
						.findViewById(R.id.txt_name_local);
				holder.yaoqing = (Button) convertView
						.findViewById(R.id.txt_yaoqing);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.nickname.setText(mList.get(position).getNickname());
			if (mList.get(position).getIsRegister() == 1) {
				holder.yaoqing.setText("添加");
			} else {
				holder.yaoqing.setText("邀请");
			}
			holder.yaoqing.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (mList.get(position).getIsRegister() == 1) {
						RequestParams request = new RequestParams();
						request.put("myphone", telphone);
						request.put("fphone", mList.get(position).getPhone());
						BaseAsyncHttp.postReq(getApplicationContext(),
								"/friend/requestadd", request,
								new JSONObjectHttpResponseHandler() {

									@Override
									public void jsonSuccess(JSONObject resp) {
										// TODO Auto-generated method stub
										Toast.makeText(getApplicationContext(),
												"发送好友请求成功", Toast.LENGTH_SHORT)
												.show();

									}

									@Override
									public void jsonFail(JSONObject resp) {
										// TODO Auto-generated method stub
										Toast.makeText(getApplicationContext(),
												"发送好友请求失败！再试一次吧",
												Toast.LENGTH_SHORT).show();
									}
								});
					} else {
						Intent intent = new Intent(Intent.ACTION_SENDTO, Uri
								.parse("smsto:"
										+ mList.get(position).getPhone()));
						intent.putExtra("sms_body", "邀请你加入定制生活大家庭");
						startActivity(intent);
					}

				}
			});
			return convertView;
		}

		class ViewHolder {
			ImageView photo;
			TextView nickname;
			Button yaoqing;
		}
	}

}
