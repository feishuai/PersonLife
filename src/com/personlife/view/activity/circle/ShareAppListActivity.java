package com.personlife.view.activity.circle;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.personlifep.R;
import com.example.personlifep.R.layout;
import com.fasterxml.jackson.core.type.TypeReference;
import com.loopj.android.http.RequestParams;
import com.personlife.adapter.AppsAdapter;
import com.personlife.bean.App;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONObjectHttpResponseHandler;
import com.personlife.utils.ComplexPreferences;
import com.personlife.utils.Constants;
import com.personlife.utils.PersonInfoLocal;
import com.personlife.utils.Utils;
import com.personlife.view.activity.discovery.GuessActivity;
import com.personlife.view.activity.home.AllDownloadActivity;
import com.personlife.widget.MyListView;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ShareAppListActivity extends Activity implements OnClickListener {
	private MyListView lv;
	private TextView mTitle;
	private Button mBack;
	private Button downloadButton;// 一键下载 按钮
	private ImageButton btnShare, btnCollect;// 分享按钮
	MyListView lvApps;
	List<App> apps;
	AppsAdapter appsadapter;
	int msgid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share_applist);
		mTitle = (TextView) findViewById(R.id.txt_title);
		mTitle.setVisibility(View.GONE);
		mBack = (Button) findViewById(R.id.txt_left);
		mBack.setVisibility(View.VISIBLE);
		mBack.setOnClickListener(this);
		btnCollect = (ImageButton) findViewById(R.id.imgbtn_collect);
		btnCollect.setVisibility(View.VISIBLE);
		btnCollect.setOnClickListener(this);
		btnShare = (ImageButton) findViewById(R.id.imgbtn_share);
		btnShare.setVisibility(View.VISIBLE);
		btnShare.setOnClickListener(this);
		downloadButton = (Button) findViewById(R.id.txt_download);
		downloadButton.setVisibility(View.VISIBLE);// 主页的一键下载按钮显示
		downloadButton.setOnClickListener(this);
		Drawable xiazai = getResources().getDrawable(R.drawable.yijianxiazai);
		// / 这一步必须要做,否则不会显示.
		xiazai.setBounds(0, 0, xiazai.getMinimumWidth(),
				xiazai.getMinimumHeight());
		downloadButton.setCompoundDrawables(xiazai, null, null, null);
		downloadButton.setTextColor(getResources()
				.getColorStateList(R.color.bg));

		msgid = getIntent().getIntExtra("msgid", 1);
		lvApps = (MyListView) findViewById(R.id.lv_tuijian_apps);
		apps = new ArrayList<App>();
		apps = ComplexPreferences.getObject(getApplicationContext(),
				Constants.ShareAllDownloadApps,
				new TypeReference<ArrayList<App>>() {
				});
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
		case R.id.txt_download:
			// ComplexPreferences.putObject(getApplicationContext(),
			// Constants.ShareAllDownloadApps, mList);
			Utils.start_Activity(ShareAppListActivity.this,
					AllDownloadActivity.class, new BasicNameValuePair("key",
							Constants.ShareAllDownloadApps));
			break;
		case R.id.imgbtn_share:
			break;
		case R.id.imgbtn_collect:
			RequestParams params = new RequestParams();
			params.add("phone", PersonInfoLocal.getPhone());
			params.add("msg", String.valueOf(msgid));
			BaseAsyncHttp.postReq(getApplicationContext(), "/collect/set-msg",
					params, new JSONObjectHttpResponseHandler() {

						@Override
						public void jsonSuccess(JSONObject resp) {
							// TODO Auto-generated method stub
							int flag = resp.optInt("flag");
							if (flag == 1)
								Utils.showShortToast(getApplicationContext(),
										"收藏成功");
							else
								Utils.showShortToast(getApplicationContext(),
										"已收藏");
						}

						@Override
						public void jsonFail(JSONObject resp) {
							// TODO Auto-generated method stub
							Utils.showShortToast(getApplicationContext(),
									Constants.OnFailure);
						}
					});
			break;
		}
	}
}
