package com.personlife.view.activity.home;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.example.personlifep.R;
import com.example.personlifep.R.id;
import com.example.personlifep.R.layout;
import com.personlife.adapter.AppListAdapter;
import com.personlife.bean.App;
import com.personlife.widget.ClearEditText;
import com.personlife.widget.MyListView;

public class AppSearchActivity extends Activity implements OnClickListener{

	private Button cancel;
	private ClearEditText search;
	private AppListAdapter mAdapter;
	private MyListView mListView;
	private List<App> apps;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_search);
		initView();
		initData();
	}

	private void initView() {
		cancel=(Button)findViewById(R.id.btn_search_concel);		
		cancel.setOnClickListener(this);
		search=(ClearEditText)findViewById(R.id.et_search_search);
		search.requestFocus();
		search.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO Auto-generated method stub
				Log.i("AppSearchActivity actionId is ", String.valueOf(actionId));
				switch(actionId){
				case EditorInfo.IME_ACTION_NEXT:
				case EditorInfo.IME_ACTION_DONE:
					//添加搜索
					Toast.makeText(AppSearchActivity.this, search.getText().toString().trim(), Toast.LENGTH_SHORT).show();
					break;
				}
				return false;
			}
		});
		mListView=(MyListView)findViewById(R.id.lv_search_list);
		apps = new ArrayList<App>();
	}
	
	private void initData() {
		// TODO Auto-generated method stub
		apps.add(new App("淘宝",5,"很好",1000));
		apps.add(new App("天猫",5,"很好",9999));
		apps.add(new App("搜狐",1,"一般",10));
		mAdapter = new AppListAdapter(AppSearchActivity.this, apps);
		mListView.setAdapter(mAdapter);
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
