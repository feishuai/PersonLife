package com.personlife.view.activity.personcenter;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalDb;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personlifep.R;
import com.loopj.android.http.RequestParams;
import com.personlife.adapter.ContactAdapter;
import com.personlife.adapter.NewFriendsAdapter;
import com.personlife.bean.User;
import com.personlife.bean.UserFriend;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONArrayHttpResponseHandler;
import com.personlife.net.JSONObjectHttpResponseHandler;
import com.personlife.utils.FriendsUtils;
import com.personlife.utils.GetContactsInfo;
import com.personlife.utils.PersonInfoLocal;
import com.personlife.utils.SideBar;
import com.personlife.utils.Utils;
import com.personlife.view.activity.personinfo.UserDetail;

//通讯录

public class Fragment_Friends extends Fragment implements OnClickListener,
		OnItemClickListener {
	private Activity ctx;
	private View layout, layout_head;
	private ListView lvContact;
	private SideBar indexBar;
	private TextView mDialogText;
	private WindowManager mWindowManager;
	private ImageView redhot;
	private String telphone;

	public Fragment_Friends(String tel) {
		// TODO Auto-generated constructor stub
		super();
		telphone = tel;
	}

	public Fragment_Friends() {
		// TODO Auto-generated constructor stub
	}

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
			initRedHot();
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

		FriendsUtils.userFriends = new ArrayList<UserFriend>();
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
		redhot = (ImageView) layout_head.findViewById(R.id.redHot);
		lvContact.addHeaderView(layout_head);
		lvContact.setAdapter(new ContactAdapter(getActivity(),
				FriendsUtils.userFriends));
	}
	
	public void initRedHot(){
		RequestParams request = new RequestParams();
		request.put("phone", PersonInfoLocal.getPhone(getActivity()));
		BaseAsyncHttp.postReq(getActivity(), "/friend/listenadd", request,
				new JSONArrayHttpResponseHandler() {

					@Override
					public void jsonSuccess(JSONArray resp) {
						// TODO Auto-generated method stub
						if (resp.length() > 0)
							redhot.setVisibility(View.VISIBLE);
						else
							redhot.setVisibility(View.GONE);
					}

					@Override
					public void jsonFail(JSONArray resp) {
						// TODO Auto-generated method stub

					}
				});
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
		initRedHot();
	}

	private void initData() {

		RequestParams params = new RequestParams();
		params.put("phone", telphone);

		BaseAsyncHttp.postReq(getActivity().getApplicationContext(),
				"/friend/getall", params, new JSONObjectHttpResponseHandler() {

					@Override
					public void jsonSuccess(JSONObject resp) {
						// TODO Auto-generated method stub
						JSONArray jsons = resp.optJSONArray("items");
						for (int i = 0; i < jsons.length(); i++) {
							UserFriend userFriend = new UserFriend();
							userFriend.setPhone(jsons.optJSONObject(i)
									.optString("phone"));
							userFriend.setNickname(jsons.optJSONObject(i)
									.optString("nickname"));
							if (userFriend.getNickname().equals("")) {
								userFriend.setNickname(jsons.optJSONObject(i)
										.optString("phone"));

							}
							userFriend.setThumb(jsons.optJSONObject(i)
									.optString("thumb"));
							FriendsUtils.userFriends.add(userFriend);

						}
						lvContact.setAdapter(new ContactAdapter(getActivity(),
								FriendsUtils.userFriends));
					}

					@Override
					public void jsonFail(JSONObject resp) {
						// TODO Auto-generated method stub

					}
				});

	}

	private void setOnListener() {
		lvContact.setOnItemClickListener(this);
		layout.findViewById(R.id.layout_addfriend).setOnClickListener(this);
		layout.findViewById(R.id.layout_search).setOnClickListener(this);
		layout.findViewById(R.id.layout_phonecontact).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_search:// 搜索好友
			Utils.start_Activity(getActivity(), SearchFriendActivity.class);
			break;
		case R.id.layout_addfriend:// 好友申请
			Intent intent = new Intent(getActivity(),
					NewFriendsListActivity.class);
			intent.putExtra("telphone", telphone);
			startActivityForResult(intent, 1);
			break;
		case R.id.layout_phonecontact:
			Intent intent1 = new Intent(getActivity(), LocalPhoneContact.class);
			intent1.putExtra("telphone", telphone);
			startActivity(intent1);
			break;
		default:
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		FriendsUtils.userFriends.clear();
		refresh();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		UserFriend user = FriendsUtils.userFriends.get(arg2 - 1);
		if (user != null) {
			Intent intent = new Intent(getActivity(), UserDetail.class);
			intent.putExtra("fromwhere", "friend");
			intent.putExtra("phone", user.getPhone());
			intent.putExtra("mytelphone",
					PersonInfoLocal.getPhone(getActivity()));
			startActivityForResult(intent, 2);
		}
	}
}
