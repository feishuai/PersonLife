package com.personlife.view.activity.circle;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.personlifep.R;
import com.fasterxml.jackson.core.type.TypeReference;
import com.loopj.android.http.RequestParams;
import com.personlife.adapter.ViewPagerTabAdapter;
import com.personlife.bean.App;
import com.personlife.bean.Reply;
import com.personlife.bean.Shuoshuo;
import com.personlife.bean.Star;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONArrayHttpResponseHandler;
import com.personlife.net.JSONObjectHttpResponseHandler;
import com.personlife.utils.ComplexPreferences;
import com.personlife.utils.Constants;
import com.personlife.utils.ImageLoaderUtils;
import com.personlife.utils.PersonInfoLocal;
import com.personlife.utils.Utils;
import com.personlife.view.activity.home.AllDownloadActivity;
import com.personlife.widget.PagerSlidingTabStrip;

public class CircleActivity extends FragmentActivity implements OnClickListener {
	private TextView mTitle;
	private Button mBack, addfriend;
	ImageView staricon, praise;
	TextView starname, signature, tabviews[], follows;
	ViewPager pager;
	ViewPagerTabAdapter adapter;
	PagerSlidingTabStrip tabs;
	LinearLayout view;
	CharSequence Titles[] = { "分享", "我的APP" };
	Drawable drawableFenxiang[];
	Drawable drawableWodeApp[];
	ColorStateList colors[];
	Fragment fragments[];
	String phone;
	Star star;
	Boolean isPraised = false;
	Boolean isAdded = false;
	CircleFriendsFragment friendsfragment;
	CircleOtherAppsFragment appsfragment;
	private Button downloadButton;// 一键下载 按钮
	private ImageButton btnShare;// 分享按钮
	public Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				LayoutParams params = pager.getLayoutParams();
				params.height = friendsfragment.getListViewLayoutParams() + 160;
				pager.setLayoutParams(params);
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_circle);
		phone = getIntent().getStringExtra("starphone");
		mBack = (Button) findViewById(R.id.txt_left);
		mBack.setVisibility(View.VISIBLE);
		mBack.setOnClickListener(this);
		mTitle = (TextView) findViewById(R.id.txt_title);
		staricon = (ImageView) findViewById(R.id.iv_circle_staricon);
		starname = (TextView) findViewById(R.id.tv_circle_starname);
		signature = (TextView) findViewById(R.id.tv_circle_signature);
		follows = (TextView) findViewById(R.id.tv_circle_dianzancounts);
		addfriend = (Button) findViewById(R.id.btn_circle_addattention);
		addfriend.setOnClickListener(this);
		praise = (ImageView) findViewById(R.id.iv_circle_dianzan);
		praise.setOnClickListener(this);
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

		star = new Star();
		star.setPhone(phone);
		final List<Shuoshuo> shuoshuos = new ArrayList<Shuoshuo>();
		friendsfragment = new CircleFriendsFragment(shuoshuos,
				(Star) ComplexPreferences.getObject(getApplicationContext(),
						"user", new TypeReference<Star>() {
						}));
		RequestParams request = new RequestParams();
		request.add("phone", star.getPhone());
		BaseAsyncHttp.postReq(getApplicationContext(), "/users/getinfo",
				request, new JSONObjectHttpResponseHandler() {

					@Override
					public void jsonSuccess(JSONObject resp) {
						// TODO Auto-generated method stub
						star.setPhone(resp.optString("phone"));
						star.setNickname(resp.optString("nickname"));
						star.setThumb(resp.optString("thumb"));
						star.setFollower(resp.optString("follower"));
						star.setShared(resp.optString("shared"));
						star.setFamous(resp.optInt("famous"));
						star.setSignature(resp.optString("signature"));
						star.setFavour(resp.optInt("favour"));
						star.setFamous(resp.optInt("famous"));
						ComplexPreferences.putObject(getApplicationContext(),
								"star", star);
						updateFriendsCircle();
						updateProfile();
					}

					@Override
					public void jsonFail(JSONObject resp) {
						// TODO Auto-generated method stub

					}
				});

		final List<App> apps = new ArrayList<App>();
		appsfragment = new CircleOtherAppsFragment(apps);
		star.setApps(apps);
		BaseAsyncHttp.postReq(getApplicationContext(), "/myapp/get", request,
				new JSONArrayHttpResponseHandler() {

					@Override
					public void jsonSuccess(JSONArray resp) {
						// TODO Auto-generated method stub
						for (int i = 0; i < resp.length(); i++) {
							App appInfo = new App();
							appInfo.setId(resp.optJSONObject(i).optInt("id"));
							appInfo.setName(resp.optJSONObject(i).optString(
									"name"));
							appInfo.setVersion(resp.optJSONObject(i).optString(
									"version"));
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
							appInfo.setSize(resp.optJSONObject(i).optString(
									"size"));
							appInfo.setIcon(resp.optJSONObject(i).optString(
									"icon"));
							appInfo.setUpdateLog(resp.optJSONObject(i)
									.optString("updated_log"));
							appInfo.setProfile(resp.optJSONObject(i).optString(
									"profile"));
							appInfo.setDownloadPath(Constants.DownloadPath
									+ appInfo.getName() + ".apk");
							// apps.add(appInfo);
							// apps.add(appInfo);
							// apps.add(appInfo);
							// apps.add(appInfo);
							// apps.add(appInfo);
							// apps.add(appInfo);
							// apps.add(appInfo);
						}
						appsfragment.setData(apps);
					}

					@Override
					public void jsonFail(JSONArray resp) {
						// TODO Auto-generated method stub
					}
				});

		fragments = new Fragment[] { friendsfragment, appsfragment };

		// Creating The ViewPagerAdapter and Passing Fragment Manager, Titles
		// fot the Tabs and Number Of Tabs.
		adapter = new ViewPagerTabAdapter(getSupportFragmentManager(), Titles,
				fragments);
		// Assigning ViewPager View and setting the adapter
		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(adapter);

		// Assiging the Sliding Tab Layout View
		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);

		// Setting the ViewPager For the SlidingTabsLayout
		tabs.setViewPager(pager);

		LayoutParams params = pager.getLayoutParams();
		params.height = friendsfragment.getListViewLayoutParams();
		pager.setLayoutParams(params);

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Log.i("listview getview", "activity update thread");
					while (!friendsfragment.getIsLoaded()) {
						Thread.sleep(400);
					}
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Message message = new Message();
				message.what = 1;
				mHandler.sendMessage(message);
			}
		});
		thread.start();

		tabs.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				Log.i("selected tab", "" + arg0);
				tabviews[arg0].setTextColor(colors[0]);
				tabviews[1 - arg0].setTextColor(colors[1]);
				if (arg0 == 1) {
					tabviews[1].setCompoundDrawables(drawableWodeApp[1], null,
							null, null);
					tabviews[0].setCompoundDrawables(drawableFenxiang[0], null,
							null, null);
					LayoutParams params = pager.getLayoutParams();
					params.height = appsfragment.getListViewLayoutParams();
					pager.setLayoutParams(params);

				} else {
					tabviews[0].setCompoundDrawables(drawableFenxiang[1], null,
							null, null);
					tabviews[1].setCompoundDrawables(drawableWodeApp[0], null,
							null, null);
					LayoutParams params = pager.getLayoutParams();
					params.height = friendsfragment.getListViewLayoutParams() + 160;
					pager.setLayoutParams(params);
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

		initData();
	}

	protected void updateFriendsCircle() {
		// TODO Auto-generated method stub
		final List<Shuoshuo> shuoshuos = new ArrayList<Shuoshuo>();
		RequestParams requestShuoshuo = new RequestParams();
		requestShuoshuo.add("phone", star.getPhone());
		BaseAsyncHttp.postReq(this, "/users/getmsg", requestShuoshuo,
				new JSONObjectHttpResponseHandler() {
					@Override
					public void jsonSuccess(JSONObject resp) {
						// TODO Auto-generated method stub
						try {
							JSONArray jsonshuoshuos = resp.getJSONArray("item");
							for (int i = 0; i < jsonshuoshuos.length(); i++) {
								Shuoshuo shuoshuo = new Shuoshuo();
								JSONObject jsonshuoshuo = jsonshuoshuos
										.getJSONObject(i);
								JSONObject jsonbasic = jsonshuoshuo
										.getJSONObject("basic");
								shuoshuo.setContent(jsonbasic
										.getString("content"));
								shuoshuo.setCreatedtime(jsonbasic
										.getInt("created_at"));
								shuoshuo.setArea(jsonbasic.getString("area"));
								shuoshuo.setMsgid(jsonbasic.getInt("id"));
								shuoshuo.setNickname(star.getNickname());
								shuoshuo.setThumb(star.getThumb());
								shuoshuo.setScore(jsonshuoshuo
										.optInt("appstars"));
								shuoshuo.setLabels(jsonshuoshuo
										.optString("appkinds"));
								JSONArray jsonapps = jsonshuoshuo
										.getJSONArray("apps");
								List<App> shuoshuoapps = new ArrayList<App>();
								for (int j = 0; j < jsonapps.length(); j++) {
									App app = new App();
									JSONObject jsonapp = jsonapps
											.getJSONObject(j);
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
									shuoshuoapps.add(app);
								}
								shuoshuo.setApps(shuoshuoapps);
								JSONArray jsonreplies = jsonshuoshuo
										.getJSONArray("replys");
								List<Reply> shuoshuoreplies = new ArrayList<Reply>();
								for (int k = 0; k < jsonreplies.length(); k++) {
									Reply reply = new Reply();
									JSONObject jsonreply = jsonreplies
											.getJSONObject(k);
									reply.setFromphone(jsonreply
											.getString("fromphone"));
									reply.setFromnickname(jsonreply
											.getString("fromnickname"));
									reply.setTophone(jsonreply
											.getString("tophone"));
									reply.setTonickname(jsonreply
											.getString("tonickname"));
									reply.setContent(jsonreply
											.getString("content"));
									shuoshuoreplies.add(reply);
								}
								shuoshuo.setReplies(shuoshuoreplies);
								List<Star> shuoshuozans = new ArrayList<Star>();
								JSONArray jsonzans = jsonshuoshuo
										.getJSONArray("zan");
								for (int l = 0; l < jsonzans.length(); l++) {
									Star zan = new Star();
									JSONObject jsonzan = jsonzans
											.getJSONObject(l);
									zan.setPhone(jsonzan.getString("phone"));
									zan.setNickname(jsonzan
											.getString("nickname"));
									shuoshuozans.add(zan);
								}
								shuoshuo.setStars(shuoshuozans);
								shuoshuos.add(shuoshuo);
							}
							friendsfragment.updateData(shuoshuos);
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

	protected void updateProfile() {
		// TODO Auto-generated method stub
		mTitle.setText(star.getNickname() + "的圈子");
		ImageLoaderUtils.displayImageView(star.getThumb(), staricon);
		starname.setText(star.getNickname());
		follows.setText("(" + star.getFavour() + ")");
		signature.setText(star.getSignature());
		if (star.getFamous() == 0)
			addfriend.setText("添加好友");
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		Log.i("listview getview", "activity onstart");
		super.onStart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Log.i("listview getview", "activity onresume");
		super.onResume();
		LayoutParams params = pager.getLayoutParams();
		params.height = friendsfragment.getListViewLayoutParams();
		pager.setLayoutParams(params);
	}

	public void initData() {
		view = (LinearLayout) tabs.getChildAt(0);

		tabviews = new TextView[2];
		drawableFenxiang = new Drawable[2];
		drawableWodeApp = new Drawable[2];
		colors = new ColorStateList[2];

		colors[0] = getResources().getColorStateList(R.color.bg);
		colors[1] = getResources().getColorStateList(R.color.black);
		tabviews[0] = (TextView) view.getChildAt(0);
		tabviews[0].setTextColor(colors[0]);
		tabviews[1] = (TextView) view.getChildAt(1);
		tabviews[1].setTextColor(colors[1]);

		drawableFenxiang[0] = getResources()
				.getDrawable(R.drawable.fenxiangbg1);
		drawableFenxiang[1] = getResources()
				.getDrawable(R.drawable.fenxiangbg2);
		// / 这一步必须要做,否则不会显示.
		drawableFenxiang[0].setBounds(0, 0,
				drawableFenxiang[0].getMinimumWidth(),
				drawableFenxiang[0].getMinimumHeight());
		drawableFenxiang[1].setBounds(0, 0,
				drawableFenxiang[1].getMinimumWidth(),
				drawableFenxiang[1].getMinimumHeight());
		tabviews[0].setCompoundDrawables(drawableFenxiang[1], null, null, null);

		drawableWodeApp[0] = getResources().getDrawable(R.drawable.wodeapp1);
		drawableWodeApp[1] = getResources().getDrawable(R.drawable.wodeapp2);
		// / 这一步必须要做,否则不会显示.
		drawableWodeApp[0].setBounds(0, 0,
				drawableWodeApp[0].getMinimumWidth(),
				drawableWodeApp[0].getMinimumHeight());
		drawableWodeApp[1].setBounds(0, 0,
				drawableWodeApp[1].getMinimumWidth(),
				drawableWodeApp[1].getMinimumHeight());
		tabviews[1].setCompoundDrawables(drawableWodeApp[0], null, null, null);
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
					Constants.ShareAllDownloadApps, star.getApps());
			Utils.start_Activity(CircleActivity.this,
					AllDownloadActivity.class, new BasicNameValuePair("key",
							Constants.ShareAllDownloadApps));
			break;
		case R.id.imgbtn_share:
			Dialog dialog = new ShareDialog(CircleActivity.this);
			Window dialogWindow = dialog.getWindow();
			WindowManager.LayoutParams lp = dialogWindow.getAttributes();
			dialogWindow.setGravity(Gravity.RIGHT | Gravity.TOP);
			lp.x = 5; // 新位置X坐标
			lp.y = 90; // 新位置Y坐标
			lp.alpha = 0.7f; // 透明度
			dialogWindow.setAttributes(lp);
			dialog.show();
			break;
		case R.id.btn_circle_addattention:
			RequestParams request = new RequestParams();
			request.add("myphone",
					PersonInfoLocal.getPhone(getApplicationContext()));
			request.add("fphone", star.getPhone());
			if (star.getFamous() == 1)
				if (!isAdded)
					BaseAsyncHttp.postReq(this, "/follow/set", request,
							new JSONObjectHttpResponseHandler() {
								@Override
								public void jsonSuccess(JSONObject resp) {
									// TODO Auto-generated method stub
									int flag = resp.optInt("flag");
									if (flag == 1)
										Utils.showShortToast(
												getApplicationContext(), "关注成功");
									else
										Utils.showShortToast(
												getApplicationContext(), "已关注");
									addfriend.setText("已关注");
									addfriend.setGravity(Gravity.CENTER);
									isAdded = true;
								}

								@Override
								public void jsonFail(JSONObject resp) {
									// TODO Auto-generated method stub
								}
							});
				else
					BaseAsyncHttp.postReq(this, "/follow/cancel", request,
							new JSONObjectHttpResponseHandler() {

								@Override
								public void jsonSuccess(JSONObject resp) {
									// TODO Auto-generated method stub
									Utils.showShortToast(
											getApplicationContext(), "取消关注");
									addfriend.setText("添加关注");
									isAdded = false;
								}

								@Override
								public void jsonFail(JSONObject resp) {
									// TODO Auto-generated method stub

								}
							});
			RequestParams addrequest = new RequestParams();
			addrequest.add("myid",
					PersonInfoLocal.getPhone(getApplicationContext()));
			addrequest.add("friendid", star.getPhone());
			if (star.getFamous() == 0)
				if (!isAdded)
					BaseAsyncHttp.postReq(this, "/friend/requestadd", request,
							new JSONObjectHttpResponseHandler() {
								@Override
								public void jsonSuccess(JSONObject resp) {
									// TODO Auto-generated method stub
									int flag = resp.optInt("flag");
									if (flag == 0)
										Utils.showShortToast(
												getApplicationContext(),
												"已发送请求");
									else
										Utils.showShortToast(
												getApplicationContext(), "已是好友");
									addfriend.setText("已请求");
									addfriend.setGravity(Gravity.CENTER);
									isAdded = true;
								}

								@Override
								public void jsonFail(JSONObject resp) {
									// TODO Auto-generated method stub
								}
							});
				else
					Utils.showShortToast(getApplicationContext(), "已发送请求");
			break;
		case R.id.iv_circle_dianzan:
			if (!isPraised) {
				RequestParams request1 = new RequestParams();
				request1.add("phone", star.getPhone());
				BaseAsyncHttp.postReq(this, "/follow/zan", request1,
						new JSONObjectHttpResponseHandler() {

							@Override
							public void jsonSuccess(JSONObject resp) {
								// TODO Auto-generated method stub
								Utils.showShortToast(getApplicationContext(),
										"成功点赞");
							}

							@Override
							public void jsonFail(JSONObject resp) {
								// TODO Auto-generated method stub
							}
						});
				follows.setText("(" + String.valueOf(star.getFavour() + 1)
						+ ")");
				isPraised = true;
			} else {
				Utils.showShortToast(getApplicationContext(), "已点赞");
			}
			break;
		}
	}

}