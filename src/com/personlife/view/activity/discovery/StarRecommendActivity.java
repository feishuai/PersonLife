package com.personlife.view.activity.discovery;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.personlifep.R;
import com.loopj.android.http.RequestParams;
import com.personlife.adapter.ViewPagerTabAdapter;
import com.personlife.bean.App;
import com.personlife.bean.Star;
import com.personlife.bean.User;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONArrayHttpResponseHandler;
import com.personlife.widget.CircleImageView;
import com.personlife.widget.PagerSlidingTabStrip;

public class StarRecommendActivity extends FragmentActivity implements
		OnClickListener {
	private TextView mTitle;
	private Button mBack;
	TextView tabviews[];
	ViewPager pager;
	ViewPagerTabAdapter adapter;
	PagerSlidingTabStrip tabs;
	LinearLayout view;
	CharSequence Titles[] = { "全部", "最新", "最热" };
	ColorStateList colors[];
	Fragment fragments[];
	StarRecommendFragment allstarfragment;
	StarRecommendFragment lasteststarfragment;
	StarRecommendFragment hoteststarfragment;
	int lastTab = 0;
	int currentTab = 0;
	List<Star> mStars;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_starrecommend);
		mBack = (Button) findViewById(R.id.txt_left);
		mBack.setVisibility(View.VISIBLE);
		mBack.setOnClickListener(this);
		mTitle = (TextView) findViewById(R.id.txt_title);
		mTitle.setText("新星推荐");

		mStars = new ArrayList<Star>();
		mStars.add(new Star());
		mStars.add(new Star());
		mStars.add(new Star());
		mStars.add(new Star());
		allstarfragment = new StarRecommendFragment(mStars);
		lasteststarfragment = new StarRecommendFragment(mStars);
		hoteststarfragment = new StarRecommendFragment(mStars);
		RequestParams request = new RequestParams();
		BaseAsyncHttp.postReq(this, "/app/recommend-all", request,
				new JSONArrayHttpResponseHandler() {

					@Override
					public void jsonSuccess(JSONArray resp) {
						// TODO Auto-generated method stub
						List<Star> list = new ArrayList<Star>();
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
							list.add(star);

						}
						allstarfragment.setAppsList(list);
					}

					@Override
					public void jsonFail(JSONArray resp) {
						// TODO Auto-generated method stub

					}
				});
		BaseAsyncHttp.postReq(this, "/app/recommend-new", request,
				new JSONArrayHttpResponseHandler() {

					@Override
					public void jsonSuccess(JSONArray resp) {
						// TODO Auto-generated method stub
						List<Star> list = new ArrayList<Star>();
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
							list.add(star);

						}
						lasteststarfragment.setAppsList(list);
					}

					@Override
					public void jsonFail(JSONArray resp) {
						// TODO Auto-generated method stub

					}
				});
		BaseAsyncHttp.postReq(this, "/app/recommend-hot", request,
				new JSONArrayHttpResponseHandler() {

					@Override
					public void jsonSuccess(JSONArray resp) {
						// TODO Auto-generated method stub
						List<Star> list = new ArrayList<Star>();
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
							list.add(star);

						}
						hoteststarfragment.setAppsList(list);
					}

					@Override
					public void jsonFail(JSONArray resp) {
						// TODO Auto-generated method stub

					}
				});
		fragments = new Fragment[] { allstarfragment, lasteststarfragment,
				hoteststarfragment };
		// Creating The ViewPagerAdapter and Passing Fragment Manager, Titles
		// fot the Tabs and Number Of Tabs.
		adapter = new ViewPagerTabAdapter(getSupportFragmentManager(), Titles,
				fragments);
		// Assigning ViewPager View and setting the adapter
		pager = (ViewPager) findViewById(R.id.pager_starrecommend);
		pager.setAdapter(adapter);

		// Assiging the Sliding Tab Layout View
		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs_starrecommend);

		// Setting the ViewPager For the SlidingTabsLayout
		tabs.setViewPager(pager);

		tabs.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				Log.i("selected tab", "" + arg0);
				currentTab = arg0;
				switch (arg0) {
				case 0:
					tabviews[arg0].setTextColor(colors[0]);
					tabviews[2 - arg0].setTextColor(colors[1]);
					tabviews[1 - arg0].setTextColor(colors[1]);
					break;
				case 1:
					tabviews[arg0].setTextColor(colors[0]);
					tabviews[arg0 - 1].setTextColor(colors[1]);
					tabviews[arg0 + 1].setTextColor(colors[1]);
					break;
				case 2:
					tabviews[arg0].setTextColor(colors[0]);
					tabviews[arg0 - 1].setTextColor(colors[1]);
					tabviews[arg0 - 2].setTextColor(colors[1]);
					break;
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

	public void initData() {
		view = (LinearLayout) tabs.getChildAt(0);

		tabviews = new TextView[3];
		colors = new ColorStateList[2];

		colors[0] = getResources().getColorStateList(R.color.bg);
		colors[1] = getResources().getColorStateList(R.color.black);
		tabviews[0] = (TextView) view.getChildAt(0);
		tabviews[0].setTextColor(colors[0]);
		tabviews[1] = (TextView) view.getChildAt(1);
		tabviews[1].setTextColor(colors[1]);
		tabviews[2] = (TextView) view.getChildAt(2);
		tabviews[2].setTextColor(colors[1]);
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
