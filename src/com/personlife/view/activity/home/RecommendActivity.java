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
import com.personlife.net.JSONArrayHttpResponseHandler;
import com.personlife.net.JSONObjectHttpResponseHandler;
import com.personlife.utils.Constants;
import com.personlife.widget.ClearEditText;
import com.personlife.widget.MyListView;

public class RecommendActivity extends Activity implements OnClickListener {
	MyListView lvApps;
	Button mBack;
	TextView mTitle;
	String kind;
	List<App> apps;
	AppsAdapter appsadapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recommend);
		lvApps = (MyListView) findViewById(R.id.lv_tuijian_apps);
		mBack = (Button) findViewById(R.id.txt_left);
		mTitle = (TextView) findViewById(R.id.txt_title);
		mTitle.setText("推荐列表");
		mBack.setVisibility(View.VISIBLE);
		mBack.setOnClickListener(this);
		initData();
	}

	private void initData() {
		apps = new ArrayList<App>();
		kind = getIntent().getStringExtra("kind");

		RequestParams params = new RequestParams();
		params.add("tag", kind);
		BaseAsyncHttp.postReq(getApplicationContext(), "/myapp/tag", params,
				new JSONArrayHttpResponseHandler() {

					@Override
					public void jsonSuccess(JSONArray resp) {
						// TODO Auto-generated method stub
						try {
							for (int i = 0; i < resp.length(); i++) {
								App app = new App();
								JSONObject jsonapp = resp.getJSONObject(i);
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
								app.setStars((float)jsonapp.optDouble("stars"));
								app.setPackageName(jsonapp.optString("package"));
								apps.add(app);
							}
							updateView();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void jsonFail(JSONArray resp) {
						// TODO Auto-generated method stub

					}
				});
	}

	public void updateView() {
		Log.i("recommend kind",
				"apps size is " + apps.size() + " :"
						+ String.valueOf(apps.toString()));
		appsadapter = new AppsAdapter(getApplicationContext(), apps);
		lvApps.setAdapter(appsadapter);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txt_left:
			finish();
			break;
		}
	}

}
