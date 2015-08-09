package com.personlife.view.activity.home;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.personlifep.R;
import com.personlife.adapter.AppsAdapter;
import com.personlife.bean.App;
import com.personlife.widget.ClearEditText;
import com.personlife.widget.MyListView;

public class RecommendActivity extends Activity implements OnClickListener {
	private MyListView lvApps;
	Button mBack;
	TextView mTitle;
	ClearEditText search;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recommend);
		lvApps = (MyListView) findViewById(R.id.lv_tuijian_apps);
		search = (ClearEditText) findViewById(R.id.et_tuijian_search);
		mBack = (Button) findViewById(R.id.txt_left);
		mTitle = (TextView)findViewById(R.id.txt_title);
		mTitle.setText("推荐列表");
		mBack.setVisibility(View.VISIBLE);
		mBack.setOnClickListener(this);
		search.setOnClickListener(this);
		initData();
	}
	private void initData(){
		List<App> apps = new ArrayList<App>();
		apps.add(new App());
		apps.add(new App());
		apps.add(new App());
		apps.add(new App());
		apps.add(new App());
		lvApps.setAdapter(new AppsAdapter(getApplicationContext(), apps));
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txt_left:
			finish();
			break;
		case R.id.et_tuijian_search:
			break;
		}
	}

}
