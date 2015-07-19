package com.personlife.view.activity.home;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.personlifep.R;
import com.personlife.adapter.home.AppListAdapter;
import com.personlife.bean.App;
import com.personlife.widget.MyListView;



public class AppListActivity extends Activity {
	private MyListView mLvApplist;
	private AppListAdapter mAdapter;
	private List<App> apps = new ArrayList<App>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_list);
		mLvApplist = (MyListView) findViewById(R.id.lv_app_list);
		apps.add(new App("淘宝",5,"我好想买",1000));
		apps.add(new App("天猫",5,"就是这个",9999));
		apps.add(new App("天猫",5,"就是这个",9999));
		apps.add(new App("天猫",5,"就是这个",9999));
		apps.add(new App("天猫",5,"就是这个",9999));
		apps.add(new App("天猫",5,"就是这个",9999));
		Log.i("Example", "apps size is "+apps.size());
		mAdapter = new AppListAdapter(this, apps);
		mLvApplist.setAdapter(mAdapter);
//		ListViewUtils.setListViewHeightBasedOnChildren(mLvApplist);
//		SystemUtils.getAllAppsNoSystem(this);
	}
}
