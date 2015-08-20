package com.personlife.view.activity.home;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.personlifep.R;
import com.loopj.android.http.RequestParams;
import com.personlife.adapter.AppsAdapter;
import com.personlife.bean.App;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONObjectHttpResponseHandler;
import com.personlife.utils.Constants;
import com.personlife.widget.ClearEditText;
import com.personlife.widget.MyListView;

public class RecommendActivity extends Activity implements OnClickListener {
	private MyListView lvApps;
	Button mBack;
	TextView mTitle;
	ClearEditText search;
	String kind;
	List<App> apps;
	AppsAdapter appsadapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recommend);
		lvApps = (MyListView) findViewById(R.id.lv_tuijian_apps);
		search = (ClearEditText) findViewById(R.id.et_tuijian_search);
		mBack = (Button) findViewById(R.id.txt_left);
		mTitle = (TextView) findViewById(R.id.txt_title);
		mTitle.setText("推荐列表");
		mBack.setVisibility(View.VISIBLE);
		mBack.setOnClickListener(this);
		search.setOnClickListener(this);
		initData();
	}

	private void initData() {
		apps = new ArrayList<App>();
		appsadapter = new AppsAdapter(getApplicationContext(), apps);
		apps.add(new App());
		apps.add(new App());
		apps.add(new App());
		apps.add(new App());
		apps.add(new App());
		lvApps.setAdapter(appsadapter);
		apps.clear();
		kind = getIntent().getStringExtra("kind");
		RequestParams params = new RequestParams();
		params.add("kind", kind);
		BaseAsyncHttp.postReq(getApplicationContext(), "/app/kind", params,
				new JSONObjectHttpResponseHandler() {

					@Override
					public void jsonSuccess(JSONObject resp) {
						// TODO Auto-generated method stub
						try {
							JSONArray jsonapps = resp.getJSONArray("item");
							for (int i = 0; i < jsonapps.length(); i++) {
								App app = new App();
								JSONObject jsonapp = jsonapps.getJSONObject(i);
								app.setIcon(jsonapp.getString("icon"));
								app.setSize(jsonapp.getString("size"));
								app.setDowloadcount(jsonapp
										.getInt("downloadcount"));
								app.setIntrodution(jsonapp
										.getString("introduction"));
								app.setName(jsonapp.getString("name"));
								app.setId(jsonapp.getInt("id"));
								app.setDownloadUrl(jsonapp
										.getString("android_url"));
								app.setProfile(jsonapp.getString("profile"));
								app.setDownloadPath(Constants.DownloadPath
										+ app.getName() + ".apk");
								apps.add(app);
							}
							updateView();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void jsonFail(JSONObject resp) {
						// TODO Auto-generated method stub

					}
				});
	}

	public void updateView() {
		Log.i("recommend kind",
				"apps size is " + apps.size() + " :"
						+ String.valueOf(apps.toString()));
		appsadapter.setData(apps);
		appsadapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txt_left:
			finish();
			break;
		case R.id.et_tuijian_search:
			Intent intent = new Intent(RecommendActivity.this, AppSearchActivity.class);
			startActivity(intent);
			break;
		}
	}

}
