package com.personlife.view.activity.home;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.example.personlifep.R;
import com.personlife.adapter.home.AppListAdapter;
import com.personlife.bean.App;
import com.personlife.widget.MyListView;

public class HomeActivity extends Activity implements OnClickListener{
	private ListView mLvApplist1,mLvApplist2,mLvApplist3;
	private TextView mTvMore1;
	private AppListAdapter mAdapter;
	private List<App> apps = new ArrayList<App>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_home);
		mLvApplist1 =(MyListView)findViewById(R.id.lv_app_list1);
		mLvApplist2 =(MyListView)findViewById(R.id.lv_app_list2);
		mLvApplist3 =(MyListView)findViewById(R.id.lv_app_list3);
		mTvMore1 = (TextView)findViewById(R.id.tv_home_more1);
		apps.add(new App("淘宝",5,"很好",1000));
		apps.add(new App("天猫",5,"很好",9999));
		apps.add(new App("搜狐",1,"一般",10));
		apps.add(new App("奇艺",2,"一般",10));
		if(apps.size() > 3)
			mAdapter = new AppListAdapter(this, apps.subList(0, 2));
		else
			mAdapter = new AppListAdapter(this, apps);
		mLvApplist1.setAdapter(mAdapter);
		mLvApplist2.setAdapter(mAdapter);
		mLvApplist3.setAdapter(mAdapter);
		mTvMore1.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.tv_home_more1:
			System.out.println();
			mAdapter.setData(apps);
			mAdapter.notifyDataSetChanged();
			break;
		default:
			break;
		}
	}
}
