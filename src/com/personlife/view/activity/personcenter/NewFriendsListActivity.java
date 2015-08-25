package com.personlife.view.activity.personcenter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.personlifep.R;
import com.loopj.android.http.RequestParams;
import com.personlife.adapter.ContactAdapter;
import com.personlife.adapter.NewFriendsAdapter;
import com.personlife.adapter.UserAdapter;
import com.personlife.bean.Star;
import com.personlife.bean.User;
import com.personlife.bean.UserFriend;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONArrayHttpResponseHandler;
import com.personlife.net.JSONObjectHttpResponseHandler;
import com.personlife.utils.FriendsUtils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


//新朋友
public class NewFriendsListActivity extends Activity implements OnClickListener {
	private TextView txt_title;
	private Button back;
	private ListView mlistview;
	private String telphone;
	private List<UserFriend> mList;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview);		
		telphone=getIntent().getStringExtra("telphone");
		initControl();
	}

	public void initControl() {
		txt_title = (TextView) findViewById(R.id.txt_title);
		txt_title.setText("新的朋友");
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
		mlistview = (ListView) findViewById(R.id.listview);
		RequestParams request=new RequestParams();
		request.put("phone", telphone);
		BaseAsyncHttp.postReq(this, "/friend/listenadd", request,
				new JSONArrayHttpResponseHandler() {

					@Override
					public void jsonSuccess(JSONArray resp) {
						// TODO Auto-generated method stub
						mList = new ArrayList<UserFriend>();
						for (int i = 0; i < resp.length(); i++) {
							UserFriend friend = new UserFriend();
							friend.setPhone(resp.optJSONObject(i).optString(
									"phone"));
							friend.setNickname(resp.optJSONObject(i).optString(
									"nickname"));
							friend.setThumb(resp.optJSONObject(i).optString(
									"thumb"));
							
							mList.add(friend);

						}
						mlistview.setAdapter(new NewFriendsAdapter(NewFriendsListActivity.this,mList,telphone));			
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

}
