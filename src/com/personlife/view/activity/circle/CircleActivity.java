package com.personlife.view.activity.circle;

import java.util.List;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
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
import android.widget.TextView;

import com.example.personlifep.R;
import com.personlife.adapter.ViewPagerTabAdapter;
import com.personlife.utils.ImageLoaderUtils;
import com.personlife.widget.CircleImageView;
import com.personlife.widget.PagerSlidingTabStrip;

public class CircleActivity extends FragmentActivity implements OnClickListener {
	private TextView mTitle;
	private Button mBack;
	ImageView staricon;
	TextView starname, signature, tabviews[],follows;
	ViewPager pager;
	ViewPagerTabAdapter adapter;
	PagerSlidingTabStrip tabs;
	LinearLayout view;
	CharSequence Titles[] = { "分享", "我的APP" };
	Drawable drawableFenxiang[];
	Drawable drawableWodeApp[];
	ColorStateList colors[];
	Fragment fragments[];
	Fragment friendsfragment,appsfragment;
	private String starphone,starnickname,starthumb,starfollower;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_circle);
		Intent intent=getIntent();
		starphone=intent.getStringExtra("starphone");
		starnickname=intent.getStringExtra("starnickname");
		starthumb=intent.getStringExtra("starthumb");
		starfollower=intent.getStringExtra("starfollowers");
		mBack = (Button) findViewById(R.id.txt_left);
		mBack.setVisibility(View.VISIBLE);
		mBack.setOnClickListener(this);
		mTitle = (TextView) findViewById(R.id.txt_title);
		mTitle.setText(starnickname+"的圈子");

		staricon = (ImageView) findViewById(R.id.iv_circle_staricon);
		ImageLoaderUtils.displayAppIcon(starthumb, staricon);
		starname = (TextView) findViewById(R.id.tv_circle_starname);
		starname.setText(starnickname);
		signature = (TextView) findViewById(R.id.tv_circle_signature);
		follows=(TextView) findViewById(R.id.tv_circle_dianzancounts);
		follows.setText(starfollower);
		
		friendsfragment = new CircleFriendsFragment();
		appsfragment = new CircleAppsFragment();
		fragments= new Fragment[]{friendsfragment,appsfragment};

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
				} else {
					tabviews[0].setCompoundDrawables(drawableFenxiang[1], null,
							null, null);
					tabviews[1].setCompoundDrawables(drawableWodeApp[0], null,
							null, null);
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
		}
	}

}