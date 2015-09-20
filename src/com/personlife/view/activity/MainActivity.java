package com.personlife.view.activity;

import java.util.HashSet;
import java.util.Set;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.example.personlifep.R;
import com.loopj.android.http.RequestParams;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.DownloadTaskManager;
import com.personlife.net.JSONObjectHttpResponseHandler;
import com.personlife.utils.ActivityCollector;
import com.personlife.utils.Constants;
import com.personlife.utils.DownloadHeadImg;
import com.personlife.utils.ImageLoaderUtils;
import com.personlife.utils.PersonInfoLocal;
import com.personlife.utils.Utils;
import com.personlife.view.activity.circle.SharePlusActivity;
import com.personlife.view.activity.home.AllDownloadActivity;
import com.personlife.view.activity.home.AppSearchActivity;
import com.personlife.view.fragment.DiscoveryFragment;
import com.personlife.view.fragment.HomeFragment;
import com.personlife.view.fragment.NewCircleFragment;
import com.personlife.view.fragment.PersonalCenter;
import com.umeng.analytics.MobclickAgent;

public class MainActivity extends FragmentActivity implements OnClickListener {

	private int index;
	private TextView title;// 当前页标题
	private ImageView[] tabButtons;// 下方四个tab
	private TextView[] tabText;
	private int currentTabIndex = 0;// 当前tab
	private Button downloadButton;// 一键下载 按钮
	private ImageButton txtSearch;// 推荐界面的搜索按钮
	private ImageButton ibSharePlus;// 朋友圈的发表分享

	private Fragment[] fragments;
	private PersonalCenter personalCenter;// 个人中心界面
	private HomeFragment homefragment;
	private DiscoveryFragment discoveryfragment;
	private String telphone;
	private NewCircleFragment circlefragment;

	private String headkey;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ImageLoaderUtils.InitConfig(getApplicationContext());
		ActivityCollector.addActivity(this);
		PushManager.startWork(getApplicationContext(),
                PushConstants.LOGIN_TYPE_API_KEY,"0q0u5yGZiI7GaQWF5T68CsmS");
		Intent intent = getIntent();
		telphone = intent.getStringExtra("telphone");
		if (PersonInfoLocal.getPersonPassword(this, telphone).length() != 0) {
			initdataWithPassword();
		} else {
			initdataWithNoPassword();
		}

		init();

	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	public void initdataWithPassword() {

		RequestParams request = new RequestParams();
		request.put("phone", telphone);
		BaseAsyncHttp.postReq(getApplicationContext(), "/users/getinfo",
				request, new JSONObjectHttpResponseHandler() {

					@Override
					public void jsonSuccess(JSONObject resp) {
						try {
							headkey = resp.get("thumb").toString();
							String[] temp = resp.get("hobby").toString()
									.split(" ");
							Set<String> set = new HashSet<String>();
							for (int i = 0; i < temp.length; i++) {
								set.add(temp[i]);
							}
							PersonInfoLocal.storeMainPersonInfo(
									MainActivity.this, telphone,
									resp.get("nickname").toString(),
									resp.get("thumb").toString(),
									resp.get("signature").toString(),
									resp.get("gender").toString(),
									resp.get("area").toString(), resp
											.get("job").toString(), set);
							try {
								DownloadHeadImg.downloadFile(headkey, telphone);
								PersonInfoLocal.storeMainHeadUri(
										MainActivity.this, telphone,
										Environment
												.getExternalStorageDirectory()
												.getPath()
												+ "/" + telphone + ".jpg");
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
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
		// 从七牛存储中下载头像

	}

	public void initdataWithNoPassword() {

	}

	public void init() {
		DownloadTaskManager.getDownloadTaskManager(getApplicationContext());
		title = (TextView) findViewById(R.id.txt_title);
		title.setText("我的APP");
		tabButtons = new ImageView[4];
		tabButtons[0] = (ImageView) findViewById(R.id.ib_home);
		tabButtons[1] = (ImageView) findViewById(R.id.ib_interact);
		tabButtons[2] = (ImageView) findViewById(R.id.ib_recom);
		tabButtons[3] = (ImageView) findViewById(R.id.ib_personal);
		tabButtons[0].setSelected(true);// 默认第一个tab被选中

		tabText = new TextView[4];
		tabText[0] = (TextView) findViewById(R.id.tv_home);
		tabText[1] = (TextView) findViewById(R.id.tv_interact);
		tabText[2] = (TextView) findViewById(R.id.tv_recom);
		tabText[3] = (TextView) findViewById(R.id.tv_personal);
		tabText[0].setTextColor(Color.GREEN);

		downloadButton = (Button) findViewById(R.id.txt_download);
		downloadButton.setVisibility(View.VISIBLE);// 主页的一键下载按钮显示
		downloadButton.setOnClickListener(this);
		Drawable xiazai = getResources().getDrawable(R.drawable.xiazai);
		// / 这一步必须要做,否则不会显示.
		xiazai.setBounds(0, 0, xiazai.getMinimumWidth(),
				xiazai.getMinimumHeight());
		downloadButton.setCompoundDrawables(xiazai, null, null, null);

		txtSearch = (ImageButton) findViewById(R.id.img_right);// 推荐里的搜索按钮
		txtSearch.setOnClickListener(this);
		ibSharePlus = (ImageButton) findViewById(R.id.imgbtn_plus);
		ibSharePlus.setOnClickListener(this);

		personalCenter = new PersonalCenter(telphone);

		homefragment = new HomeFragment();
		discoveryfragment = new DiscoveryFragment(telphone);
		circlefragment = new NewCircleFragment();
		fragments = new Fragment[] { homefragment, circlefragment,
				discoveryfragment, personalCenter };
		// 添加显示第一个fragment
		getSupportFragmentManager().beginTransaction()
				.add(R.id.fragment_container, personalCenter)
				.add(R.id.fragment_container, homefragment)
				.add(R.id.fragment_container, discoveryfragment)
				.add(R.id.fragment_container, circlefragment)
				.hide(personalCenter).hide(discoveryfragment)
				.hide(circlefragment).show(homefragment).commit();

	}

	public void onTabClicked(View view) {
		downloadButton.setVisibility(View.GONE);
		txtSearch.setVisibility(View.GONE);
		ibSharePlus.setVisibility(View.GONE);
		switch (view.getId()) {
		case R.id.re_home:
			title.setText("我的APP");
			downloadButton.setVisibility(View.VISIBLE);
			index = 0;
			break;
		case R.id.re_interact:
			index = 1;
			title.setText("朋友圈");
			ibSharePlus.setVisibility(View.VISIBLE);
			break;
		case R.id.re_recommend:
			index = 2;
			title.setText("发现");
			txtSearch.setVisibility(View.VISIBLE);
			break;
		case R.id.re_personal:
			index = 3;
			title.setText("个人中心");
			break;
		}
		if (currentTabIndex != index) {
			FragmentTransaction trx = getSupportFragmentManager()
					.beginTransaction();

			if (!fragments[index].isAdded()) {
				trx.add(R.id.fragment_container, fragments[index]);
			}

			trx.hide(fragments[currentTabIndex]).show(fragments[index])
					.commit();
		}
		tabButtons[currentTabIndex].setSelected(false);
		tabText[currentTabIndex].setTextColor(Color.BLACK);
		tabButtons[index].setSelected(true);// 把当前tab设为选中状态
		tabText[index].setTextColor(getResources()
				.getColorStateList(R.color.bg));
		currentTabIndex = index;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txt_download:
			Utils.start_Activity(
					MainActivity.this,
					AllDownloadActivity.class,
					new BasicNameValuePair("key", Constants.HomeAllDownloadApps));
			break;
		case R.id.imgbtn_plus:
			Utils.start_Activity(MainActivity.this, SharePlusActivity.class,
					null);
			break;
		case R.id.img_right:
			Utils.start_Activity(MainActivity.this, AppSearchActivity.class,
					null);
			break;
		}
	}

}
