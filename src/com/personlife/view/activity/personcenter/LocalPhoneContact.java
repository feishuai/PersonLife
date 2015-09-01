package com.personlife.view.activity.personcenter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.personlifep.R;
import com.loopj.android.http.RequestParams;
import com.personlife.bean.UserFriend;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONArrayHttpResponseHandler;
import com.personlife.net.JSONObjectHttpResponseHandler;
import com.personlife.utils.FriendsUtils;
import com.personlife.utils.GetContactsInfo;
import com.personlife.utils.ImageLoaderUtils;
import com.personlife.view.activity.personinfo.UserDetail;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_localcontact);
		telphone = getIntent().getStringExtra("telphone");
		initView();
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
		mList = new ArrayList<UserFriend>();
		mNoList = new ArrayList<UserFriend>();
		mNoList.addAll(new GetContactsInfo(this).getLocalUserFriends());
		mNoList.addAll(new GetContactsInfo(this).getSIMUserFriends());

		mNoList.removeAll(FriendsUtils.userFriends);
		RequestParams request = new RequestParams();
		for (int i = 0; i < mNoList.size(); i++) {
			request.put("phone[" + i + "]", mNoList.get(i).getPhone());
		}
		BaseAsyncHttp.postReq(this, "/friend/exist", request,
				new JSONArrayHttpResponseHandler() {

					@Override
					public void jsonSuccess(JSONArray resp) {
						// TODO Auto-generated method stub
						for (int i = 0; i < mNoList.size(); i++) {
							try {
								mNoList.get(i).setIsRegister(0);
								if (resp.getInt(i) == 1) {
									mNoList.get(i).setIsRegister(1);
									mList.add(mNoList.get(i));

								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						mNoList.removeAll(mList);
						if (mNoList.size() == 0) {
							tv2.setVisibility(View.GONE);
						}
						if (mList.size() == 0) {
							tv1.setVisibility(View.GONE);
						}
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
						// 直接调用短信接口发短信
						SmsManager smsManager = SmsManager.getDefault();
						smsManager.sendTextMessage(mList.get(position)
								.getPhone(), null, "邀请你加入定制生活大家庭", null, null);
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
