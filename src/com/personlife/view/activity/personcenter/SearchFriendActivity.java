package com.personlife.view.activity.personcenter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.example.personlifep.R;
import com.personlife.adapter.AppListAdapter;
import com.personlife.adapter.ContactAdapter;
import com.personlife.adapter.UserFriendAdapter;
import com.personlife.bean.App;
import com.personlife.bean.UserFriend;
import com.personlife.utils.ActivityCollector;
import com.personlife.utils.FriendsUtils;
import com.personlife.view.activity.home.AppSearchActivity;
import com.personlife.view.activity.personinfo.UserDetail;
import com.personlife.widget.ClearEditText;
import com.personlife.widget.MyListView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;


//搜索
public class SearchFriendActivity extends Activity implements OnClickListener {
	private Button cancel;
	private ClearEditText search;
	private UserFriendAdapter userAdapter;
	private MyListView mListView;
	private List<UserFriend> temList= new ArrayList<UserFriend>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searsh_friend);
		ActivityCollector.addActivity(this);
		initView();
		initData();
	}

	private void initView() {
		mListView=(MyListView)findViewById(R.id.lv_search_list);
		
		cancel=(Button)findViewById(R.id.btn_search_concel);		
		cancel.setOnClickListener(this);
		search=(ClearEditText)findViewById(R.id.et_search_search);
		search.requestFocus();
		search.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO Auto-generated method stub
				
				switch(actionId){
				case EditorInfo.IME_ACTION_NEXT:
				case EditorInfo.IME_ACTION_DONE:
					//添加搜索
					temList.clear();
					String temp = v.getText().toString();
					
					for(int i=0;i<FriendsUtils.userFriends.size();i++){
						String uf=FriendsUtils.userFriends.get(i).getNickname();
						if(uf.contains(temp)){
							temList.add(FriendsUtils.userFriends.get(i));
						}
					}
					HashSet h = new HashSet(temList); 
					temList.clear(); 
					temList.addAll(h); 
					userAdapter = new UserFriendAdapter(SearchFriendActivity.this, temList);
					mListView.setAdapter(userAdapter);
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
					 Intent intent = new Intent(SearchFriendActivity.this,UserDetail.class);
					 intent.putExtra("phone", user.getPhone());
					 startActivity(intent);
//					 getActivity().overridePendingTransition(R.anim.push_left_in,
//					 R.anim.push_left_out);
				}
			}
		});
	}
	private void initData() {
		// TODO Auto-generated method stub
			
//		userAdapter = new UserFriendAdapter(SearchFriendActivity.this, FriendsUtils.userFriends);
//		mListView.setAdapter(userAdapter);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_search_concel:
			this.finish();
			break;
		default:
			break;
		}
	}
}
