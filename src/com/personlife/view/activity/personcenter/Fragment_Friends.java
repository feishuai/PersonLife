package com.personlife.view.activity.personcenter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.example.personlifep.R;
import com.loopj.android.http.RequestParams;
import com.personlife.adapter.home.ContactAdapter;
import com.personlife.bean.UserFriend;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONObjectHttpResponseHandler;
import com.personlife.utils.SideBar;
import com.personlife.utils.Utils;

//通讯录

public class Fragment_Friends extends Fragment implements OnClickListener,
		OnItemClickListener {
	private Activity ctx;
	private View layout,layout_head;
	private ListView lvContact;
	private SideBar indexBar;
	private TextView mDialogText;
	private WindowManager mWindowManager;
	private List<UserFriend> userFriends;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (layout == null) {
			ctx = this.getActivity();
			layout = ctx.getLayoutInflater().inflate(R.layout.fragment_friends,
					null);
			mWindowManager = (WindowManager) ctx
					.getSystemService(Context.WINDOW_SERVICE);
			initViews();
			initData();
			setOnListener();
		} else {
			ViewGroup parent = (ViewGroup) layout.getParent();
			if (parent != null) {
				parent.removeView(layout);
			}
		}
		return layout;
	}

	private void initViews() {

		userFriends = new ArrayList<UserFriend>();
		lvContact = (ListView) layout.findViewById(R.id.lvContact);

		mDialogText = (TextView) LayoutInflater.from(getActivity()).inflate(
				R.layout.list_position, null);
		mDialogText.setVisibility(View.INVISIBLE);
		indexBar = (SideBar) layout.findViewById(R.id.sideBar);
		indexBar.setListView(lvContact);
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_APPLICATION,
				WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
						| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				PixelFormat.TRANSLUCENT);
		mWindowManager.addView(mDialogText, lp);
		indexBar.setTextView(mDialogText);
		layout_head = ctx.getLayoutInflater().inflate(
				R.layout.layout_head_friend, null);
		lvContact.addHeaderView(layout_head);
		lvContact.setAdapter(new ContactAdapter(getActivity(),
				userFriends));
	}

	@Override
	public void onDestroy() {
		mWindowManager.removeView(mDialogText);
		super.onDestroy();
	}

	/**
	 * 刷新页面
	 */
	public void refresh() {
		initData();
	}

	private void initData() {

		// if (GloableParams.UserFriendInfos != null) {
		// lvContact.setAdapter(new ContactAdapter(getActivity(),
		// GloableParams.UserFriendInfos));
		// } else {
		// FinalDb db = FinalDb
		// .create(getActivity(), Constants.DB_NAME, false);
		// GloableParams.UserInfos = db.findAllByWhere(User.class, "type='N'");
		
		RequestParams params = new RequestParams();
		params.put("myid", "1");
		
		Log.i("personlife", "start post");
		BaseAsyncHttp.postReq(getActivity().getApplicationContext(),"/friend/getall", params,
				new JSONObjectHttpResponseHandler() {

					@Override
					public void jsonSuccess(JSONObject resp) {
						// TODO Auto-generated method stub
						JSONArray jsons = resp.optJSONArray("items");
						for (int i = 0; i < jsons.length(); i++) {
							UserFriend userFriend = new UserFriend();
							userFriend.setFriendid(jsons.optJSONObject(i)
									.optString("friendid"));
							userFriend.setNickname(jsons.optJSONObject(i)
									.optString("nickname"));
							userFriend.setThumb(jsons.optJSONObject(i)
									.optString("thumb"));
							userFriends.add(userFriend);
						}
						lvContact.setAdapter(new ContactAdapter(getActivity(),
								userFriends));
					}

					@Override
					public void jsonFail(JSONObject resp) {
						// TODO Auto-generated method stub

					}
				});

		// Intent intent = new Intent(getActivity(), UpdateService.class);
		// getActivity().startService(intent);
		// }
	}

	private void setOnListener() {
		lvContact.setOnItemClickListener(this);
		layout.findViewById(R.id.layout_addfriend)
				.setOnClickListener(this);
		layout.findViewById(R.id.layout_search).setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_search:// 搜索好友及公众号
			Utils.start_Activity(getActivity(), SearchFriendActivity.class);
			break;
		case R.id.layout_addfriend:// 添加好友
			// Utils.start_Activity(getActivity(),
			// NewFriendsListActivity.class);
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		UserFriend user = userFriends.get(arg2 - 1);
		if (user != null) {
			// Intent intent = new Intent(getActivity(),
			// FriendMsgActivity.class);
			// intent.putExtra(Constants.NAME, user.getUserName());
			// intent.putExtra(Constants.TYPE, ChatActivity.CHATTYPE_SINGLE);
			// intent.putExtra(Constants.User_ID, user.getTelephone());
			// getActivity().startActivity(intent);
			// getActivity().overridePendingTransition(R.anim.push_left_in,
			// R.anim.push_left_out);
		}

	}
}
