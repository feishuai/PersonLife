package com.personlife.view.activity.discovery;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.personlifep.R;
import com.loopj.android.http.RequestParams;
import com.personlife.adapter.AppListAdapter;
import com.personlife.bean.App;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONArrayHttpResponseHandler;
import com.personlife.utils.ComplexPreferences;
import com.personlife.utils.Constants;
import com.personlife.utils.Utils;
import com.personlife.view.activity.home.AllDownloadActivity;
import com.personlife.widget.MyListView;

public class GuessActivity extends Activity implements OnClickListener {
	private MyListView lv;
	private ImageView back1, back2;
	private TextView tvContent, mTitle;
	private Button mBack;
	private String[] titles = { "猜您喜欢", "办公必备", "您的朋友喜欢" };
	private String[] contents = { "根据您的爱好生成，实时更新", "根据您的职业给您推荐相关APP",
			"好朋友的喜好全部都在这儿呢" };
	private Integer[] urls = { R.drawable.back1, R.drawable.back2,
			R.drawable.back3 };
	private Integer[] icons = { R.drawable.caininxihuan,
			R.drawable.bangongbibei, R.drawable.nindepengyouxihuan };

	private AppListAdapter aApps;
	private List<App> mList = new ArrayList<App>();
	private Button downloadButton;// 一键下载 按钮
	private ImageButton btnShare;// 分享按钮

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guess);
		lv = (MyListView) findViewById(R.id.lv_guess_app);
		back1 = (ImageView) findViewById(R.id.iv_guess_back);
		back2 = (ImageView) findViewById(R.id.iv_guess_back1);
		tvContent = (TextView) findViewById(R.id.tv_guess_content);
		mBack = (Button) findViewById(R.id.txt_left);
		mBack.setVisibility(View.VISIBLE);
		mBack.setOnClickListener(this);
		mTitle = (TextView) findViewById(R.id.txt_title);
		btnShare = (ImageButton)findViewById(R.id.imgbtn_share);
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
		downloadButton.setTextColor(getResources().getColorStateList(R.color.bg));
		initData();
	}

	private void initData() {
		int kind = Integer.parseInt(getIntent().getStringExtra("kind"));
		mTitle.setText(titles[kind]);
		back1.setBackground(getResources().getDrawable(urls[kind]));
		back2.setBackground(getResources().getDrawable(icons[kind]));
		tvContent.setText(contents[kind]);
		switch (kind) {
		case 0:
		case 1:
		case 2:
			RequestParams request = new RequestParams();
			BaseAsyncHttp.postReq(this, "/app/guess", request,
					new JSONArrayHttpResponseHandler() {

						@Override
						public void jsonSuccess(JSONArray resp) {
							// TODO Auto-generated method stub
							for (int i = 0; i < resp.length(); i++) {
								App appInfo = new App();
								appInfo.setId(resp.optJSONObject(i)
										.optInt("id"));
								appInfo.setName(resp.optJSONObject(i)
										.optString("name"));
								appInfo.setVersion(resp.optJSONObject(i)
										.optString("version"));
								appInfo.setDownloadUrl(resp.optJSONObject(i)
										.optString("android_url"));
								appInfo.setStars(resp.optJSONObject(i).optInt(
										"stars"));
								appInfo.setDowloadcount(resp.optJSONObject(i)
										.optInt("downloadcount"));
								appInfo.setIntrodution(resp.optJSONObject(i)
										.optString("introduction"));
								appInfo.setUpdateDate(resp.optJSONObject(i)
										.optLong("updated_at"));
								appInfo.setSize(resp.optJSONObject(i)
										.optString("size"));
								appInfo.setIcon(resp.optJSONObject(i)
										.optString("icon"));
								appInfo.setUpdateLog(resp.optJSONObject(i)
										.optString("updated_log"));
								appInfo.setProfile(resp.optJSONObject(i)
										.optString("profile"));
								appInfo.setDownloadPath(Constants.DownloadPath
										+ appInfo.getName() + ".apk");
								mList.add(appInfo);
								aApps = new AppListAdapter(
										getApplicationContext(), mList);
								lv.setAdapter(aApps);
							}
						}

						@Override
						public void jsonFail(JSONArray resp) {
							// TODO Auto-generated method stub

						}
					});
			break;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txt_left:
			finish();
			break;
		case R.id.txt_download:
			ComplexPreferences.putObject(getApplicationContext(),
					Constants.ShareAllDownloadApps, mList);
			Utils.start_Activity(GuessActivity.this, AllDownloadActivity.class,
					new BasicNameValuePair("key",
							Constants.ShareAllDownloadApps));
			break;
		case R.id.imgbtn_share:
			break;
		}
	}

}
