package com.personlife.view.activity.personcenter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.example.personlifep.R;
import com.loopj.android.http.RequestParams;
import com.personlife.adapter.home.ContactAdapter;
import com.personlife.adapter.home.UserAdapter;
import com.personlife.bean.User;
import com.personlife.bean.UserFriend;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONObjectHttpResponseHandler;
import com.personlife.utils.ActivityCollector;
import com.personlife.utils.FriendsUtils;
import com.personlife.view.activity.personinfo.UserDetail;
import com.personlife.widget.ClearEditText;
import com.personlife.widget.MyListView;

/**
 * 
 * @author liugang
 * @date 2015年7月19日
 */

// 搜索
public class SearchUser extends Activity implements OnClickListener {
	private Button cancel;
	private TextView title;
	private ClearEditText search;
	private UserAdapter userAdapter;
	private MyListView mListView;
	private List<User> users;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_user);
		ActivityCollector.addActivity(this);
		
		initView();
	}

	private void initView() {
		mListView = (MyListView) findViewById(R.id.result_search_list);
		users = new ArrayList<User>();
		title=(TextView) findViewById(R.id.txt_title);
		title.setText("添加好友");
		cancel = (Button) findViewById(R.id.txt_left);
		cancel.setVisibility(View.VISIBLE);
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		search = (ClearEditText) findViewById(R.id.et_search_user);
		search.requestFocus();
		search.setInputType(InputType.TYPE_CLASS_NUMBER);
		search.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// TODO Auto-generated method stub

				switch (actionId) {
				case EditorInfo.IME_ACTION_NEXT:
				case EditorInfo.IME_ACTION_DONE:
					users.clear();
					mListView.setAdapter(new UserAdapter(SearchUser.this, users));
					// 添加搜索
					RequestParams params = new RequestParams();
					params.put("phone", v.getText().toString());
					
					BaseAsyncHttp.postReq(getApplicationContext(),"/users/getinfo", params,
							new JSONObjectHttpResponseHandler() {

								@Override
								public void jsonSuccess(JSONObject resp) {
									// TODO Auto-generated method stub
									User user = new User();
									user.setId(resp.optString("id"));
									user.setUserName(resp.optString("nickname"));
									user.setHeadUrl(resp.optString("thumb"));
									user.setTelephone(resp.optString("phone"));
									user.setSex(resp.optString("gender"));
									user.setLocation(resp.optString("area"));
									user.setJob(resp.optString("job"));
									user.setHobby(resp.optString("hobby"));
									user.setSignature(resp.optString("signature"));
									if(user.getUserName().equals("")){
										user.setHeadUrl("");
										user.setUserName("所搜索用户不存在");
										users.add(user);
										mListView.setAdapter(new UserAdapter(SearchUser.this, users));
									}else{
										users.add(user);
										mListView.setAdapter(new UserAdapter(SearchUser.this, users));
									}
								}

								@Override
								public void jsonFail(JSONObject resp) {
									// TODO Auto-generated method stub
									
								}
							});

					break;
				}
				return false;
			}
		});
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				UserFriend user = FriendsUtils.userFriends.get(position);
				if (user != null) {
					 Intent intent = new Intent(SearchUser.this,UserDetail.class);
					 intent.putExtra("phone", user.getPhone());
					 startActivity(intent);
//					 getActivity().overridePendingTransition(R.anim.push_left_in,
//					 R.anim.push_left_out);
				}
			}
		});
	}



	// private void initData() {
	// // TODO Auto-generated method stub
	// users.add(new UserFriend("123", "", "heh"));
	// userAdapter = new ContactAdapter(SearchUser.this, users);
	// mListView.setAdapter(userAdapter);
	// }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
//		case R.id.txt_left:
//			this.finish();
//			break;
		default:
			break;
		}
	}
}
