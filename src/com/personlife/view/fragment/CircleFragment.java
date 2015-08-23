package com.personlife.view.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.personlifep.R;
import com.personlife.adapter.ViewPagerTabAdapter;
import com.personlife.bean.App;
import com.personlife.bean.Reply;
import com.personlife.bean.Shuoshuo;
import com.personlife.bean.Star;
import com.personlife.utils.SystemUtils;
import com.personlife.view.activity.circle.CircleFriendsFragment;
import com.personlife.view.activity.circle.CircleMyAppsFragment;
import com.personlife.widget.ClearEditText;
import com.personlife.widget.PagerSlidingTabStrip;

public class CircleFragment extends Fragment implements OnClickListener {
	private ClearEditText search;
	private Button kind;
	private Activity ctx;
	private View layout;
	ImageView staricon;
	RelativeLayout rlBack;
	TextView starname, signature, tabviews[];
	ViewPager pager;
	ViewPagerTabAdapter adapter;
	PagerSlidingTabStrip tabs;
	LinearLayout view;
	CharSequence Titles[] = { "分享", "我的APP" };
	Drawable drawableFenxiang[];
	Drawable drawableWodeApp[];
	ColorStateList colors[];
	ImageView dianzan, fenxiang;
	TextView dianzancounts, fenxiangcounts;
	Button addattention;
	Fragment fragments[];
	CircleFriendsFragment friendsfragment;
	CircleMyAppsFragment appsfragment;
	public Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Log.i("listview getview", "activity main thread");
				LayoutParams params = pager.getLayoutParams();
				params.height = friendsfragment.getListViewLayoutParams();
				pager.setLayoutParams(params);
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (layout == null) {
			ctx = this.getActivity();
			layout = ctx.getLayoutInflater().inflate(R.layout.fragment_circle,
					null);
			initView();
			setOnListener();
			initData();
		} else {
			ViewGroup parent = (ViewGroup) layout.getParent();
			if (parent != null) {
				parent.removeView(layout);
			}
		}
		return layout;
	}

	public void initView() {
		staricon = (ImageView) layout.findViewById(R.id.iv_circle_staricon);
		starname = (TextView) layout.findViewById(R.id.tv_circle_starname);
		signature = (TextView) layout.findViewById(R.id.tv_circle_signature);
		addattention = (Button) layout
				.findViewById(R.id.btn_circle_addattention);
		fenxiang = (ImageView) layout.findViewById(R.id.iv_circle_fenxiang);
		dianzan = (ImageView) layout.findViewById(R.id.iv_circle_dianzan);
		fenxiangcounts = (TextView) layout
				.findViewById(R.id.tv_circle_fenxiangcounts);
		dianzancounts = (TextView) layout
				.findViewById(R.id.tv_circle_dianzancounts);
		rlBack = (RelativeLayout) layout.findViewById(R.id.rl_circle_back);

		rlBack.setBackgroundResource(R.color.transparent);

		addattention.setVisibility(View.GONE);
		fenxiang.setVisibility(View.VISIBLE);
		fenxiangcounts.setVisibility(View.VISIBLE);

		List<Shuoshuo> shuoshuos = new ArrayList<Shuoshuo>();
		Shuoshuo shuoshuo = new Shuoshuo();
		List<App> appsshuoshuo = new ArrayList<App>();
		appsshuoshuo.add(new App());
		shuoshuo.setApps(appsshuoshuo);
		List<Star> starsshuoshuo = new ArrayList<Star>();
		Star starshuoshuo = new Star();
		starshuoshuo.setPhone("111");
		starshuoshuo.setNickname("飞帅");
		starsshuoshuo.add(starshuoshuo);
		starsshuoshuo.add(starshuoshuo);
		shuoshuo.setStars(starsshuoshuo);
		List<Reply> repliesshuoshuo = new ArrayList<Reply>();
		Reply reply = new Reply();
		reply.setContent("什么东西");
		reply.setFromnickname("飞帅");
		reply.setTonickname("威少");
		repliesshuoshuo.add(reply);
		repliesshuoshuo.add(reply);
		repliesshuoshuo.add(reply);
		shuoshuo.setReplies(repliesshuoshuo);
		shuoshuos.add(shuoshuo);
		shuoshuos.add(shuoshuo);
		shuoshuos.add(shuoshuo);
		friendsfragment = new CircleFriendsFragment(shuoshuos);

		List<App> apps = new ArrayList<App>();
		apps = SystemUtils.getUserApps(getActivity());
		appsfragment = new CircleMyAppsFragment(apps);

		fragments = new Fragment[] { friendsfragment, appsfragment };

		adapter = new ViewPagerTabAdapter(getChildFragmentManager(), Titles,
				fragments);
		// Assigning ViewPager View and setting the adapter
		pager = (ViewPager) layout.findViewById(R.id.pager_circle);
		pager.setAdapter(adapter);

		// Assiging the Sliding Tab Layout View
		tabs = (PagerSlidingTabStrip) layout.findViewById(R.id.tabs_circle);

		// Setting the ViewPager For the SlidingTabsLayout
		tabs.setViewPager(pager);

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Log.i("listview getview", "activity update thread");
					while (!friendsfragment.getIsLoad()) {
						Thread.sleep(100);
					}
					// while (true) {
					// Thread.sleep(200);
					// Message message = new Message();
					// message.what = 1;
					// mHandler.sendMessage(message);
					// }
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
					params.height = friendsfragment.getListViewLayoutParams();
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
	}

	public void setOnListener() {
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
		// case R.id.et_home_search:
		// Intent intent = new Intent(getActivity(), AppSearchActivity.class);
		// startActivity(intent);
		// break;
		// case R.id.btn_home_class:
		// Utils.start_Activity(getActivity(), ClassificationActivity.class,
		// null);
		// // Utils.start_Activity(getActivity(), RecommendActivity.class,
		// // null);
		// break;
		default:
			break;
		}
	}
}
