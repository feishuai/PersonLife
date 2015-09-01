package com.personlife.view.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;

import com.example.personlifep.R;
import com.loopj.android.http.RequestParams;
import com.personlife.adapter.Register4_Adapter;
import com.personlife.adapter.StarRecomAdapter;
import com.personlife.bean.Star;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONArrayHttpResponseHandler;
import com.personlife.utils.ActivityCollector;
import com.personlife.utils.ImageLoaderUtils;
import com.personlife.utils.Utils;
import com.personlife.view.activity.circle.CircleActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 * @author liugang
 * @date 2015年8月12日
 */
public class RegisterActivity4 extends Activity implements OnClickListener {

	private String telphone;
	private Button back, finishstep;
	private TextView tv_title;
	private GridView star_gridview;
	private List<Star> liststar = new ArrayList<Star>();

	private SharedPreferences.Editor editor;
	private SharedPreferences pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register4);
		ImageLoaderUtils.InitConfig(getApplicationContext());
		ActivityCollector.addActivity(this);
		Intent intent = getIntent();
		telphone = intent.getStringExtra("telphone");
		initview();
		initdata();
	}

	public void initview() {
		back = (Button) findViewById(R.id.txt_left);
		back.setVisibility(View.VISIBLE);
		back.setOnClickListener(this);
		tv_title = (TextView) findViewById(R.id.txt_title);
		tv_title.setText("完善个人信息");
		star_gridview = (GridView) findViewById(R.id.star_gridview);
		finishstep = (Button) findViewById(R.id.register4_nextstep);
		finishstep.setOnClickListener(this);
	}

	public void initdata() {
		// 连网获取新星推荐
		RequestParams request = new RequestParams();
		BaseAsyncHttp.postReq(this, "/app/recommend", request,
				new JSONArrayHttpResponseHandler() {

					@Override
					public void jsonSuccess(JSONArray resp) {
						// TODO Auto-generated method stub
						for (int i = 0; i < resp.length(); i++) {
							Star star = new Star();
							star.setPhone(resp.optJSONObject(i).optString(
									"phone"));
							star.setNickname(resp.optJSONObject(i).optString(
									"nickname"));
							star.setThumb(resp.optJSONObject(i).optString(
									"thumb"));
							star.setFollower(resp.optJSONObject(i).optString(
									"follower"));
							star.setShared(resp.optJSONObject(i).optString(
									"shared"));
							liststar.add(star);
						}
						Register4_Adapter adapter = new Register4_Adapter(
								getApplicationContext(), liststar, telphone);
						star_gridview.setAdapter(adapter);
						star_gridview
								.setOnItemClickListener(new OnItemClickListener() {

									@Override
									public void onItemClick(
											AdapterView<?> parent, View view,
											int position, long id) {
										// TODO Auto-generated method stub
									}
								});
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
		switch (v.getId()) {
		case R.id.register4_nextstep:

			Intent intent = new Intent(RegisterActivity4.this,
					MainActivity.class);
			intent.putExtra("telphone", telphone);
			editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
			pref = PreferenceManager.getDefaultSharedPreferences(this);
			editor.putString("islogin", "1");
			editor.putString("telphone", telphone);
			editor.commit();
			startActivity(intent);
			ActivityCollector.finishAll();
			break;
		case R.id.txt_left:
			onBackPressed();
			break;
		}
	}
}
