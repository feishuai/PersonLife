package com.personlife.view.activity.personcenter;

import java.util.ArrayList;
import java.util.List;

import com.example.personlifep.R;
import com.personlife.adapter.home.AppListAdapter;
import com.personlife.adapter.home.ContactAdapter;
import com.personlife.bean.App;
import com.personlife.bean.UserFriend;
import com.personlife.view.activity.home.AppSearchActivity;
import com.personlife.widget.ClearEditText;
import com.personlife.widget.MyListView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;


//搜索
public class SearchFriendActivity extends Activity implements OnClickListener {
	private Button cancel;
	private ClearEditText search;
	private ContactAdapter userAdapter;
	private MyListView mListView;
	private List<UserFriend> users;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searsh_friend);
		initView();
		initData();
	}

	private void initView() {
		mListView=(MyListView)findViewById(R.id.lv_search_list);
		users = new ArrayList<UserFriend>();
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
					Toast.makeText(SearchFriendActivity.this, search.getText().toString().trim(), Toast.LENGTH_SHORT).show();
					
					break;
				}
				return false;
			}
		});
		
	}
	private void initData() {
		// TODO Auto-generated method stub
		users.add(new UserFriend("123","","heh"));		
		userAdapter = new ContactAdapter(SearchFriendActivity.this, users);
		mListView.setAdapter(userAdapter);
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
