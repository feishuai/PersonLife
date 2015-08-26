package com.personlife.view.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.personlifep.R;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.personlife.adapter.StarRecomAdapter;
import com.personlife.bean.Star;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONArrayHttpResponseHandler;
import com.personlife.net.JSONObjectHttpResponseHandler;
import com.personlife.utils.Utils;
import com.personlife.view.activity.circle.CircleActivity;
import com.personlife.view.activity.discovery.GuessActivity;
import com.personlife.view.activity.discovery.StarRecommendActivity;
import com.personlife.widget.JazzyViewPager;
import com.personlife.widget.JazzyViewPager.TransitionEffect;
import com.personlife.widget.MyListView;
import com.personlife.widget.OutlineContainer;

public class DiscoveryFragment extends Fragment implements OnClickListener {
	private Activity ctx;
	private View layout;
	private TextView starmore;
	private MyListView lvRecommend;
	private GridView gridview;
	protected Handler mHandler = null;
	private JazzyViewPager mViewPager = null;
	private LinearLayout mIndicator = null;
	/**
	 * 装指引的ImageView数组
	 */
	private ImageView[] mIndicators;

	/**
	 * 装ViewPager中ImageView的数组
	 */
	private ImageView[] mImageViews;
	private List<String> mImageUrls = new ArrayList<String>();
	private String mImageUrl;
	private List<Star> liststar=new ArrayList<Star>();
	private static final int MSG_CHANGE_PHOTO = 1;
	/** 图片自动切换时间 */
	private static final int PHOTO_CHANGE_TIME = 3000;
	private String telphone;
	
	public DiscoveryFragment(String tel){
		super();
		this.telphone=tel;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (layout == null) {
			ctx = this.getActivity();
			layout = ctx.getLayoutInflater().inflate(
					R.layout.fragment_discovery, null);
			initData();
			initView();
			setOnListener();
			updateContent();
		} else {
			ViewGroup parent = (ViewGroup) layout.getParent();
			if (parent != null) {
				parent.removeView(layout);
			}
		}
		return layout;
	}

	private void initData() {
		mImageUrl = "drawable://" + R.drawable.back1;
		mImageUrls.add(mImageUrl);
		mImageUrl = "drawable://" + R.drawable.back2;
		mImageUrls.add(mImageUrl);
		mImageUrl = "drawable://" + R.drawable.back3;
		mImageUrls.add(mImageUrl);

	}

	private void initView() {
		starmore = (TextView) layout.findViewById(R.id.tv_discovery_more);
		gridview = (GridView) layout.findViewById(R.id.gridview);
		lvRecommend = (MyListView) layout
				.findViewById(R.id.lv_dicovery_recommend);
		mViewPager = (JazzyViewPager) layout
				.findViewById(R.id.index_product_images_container);
		mIndicator = (LinearLayout) layout
				.findViewById(R.id.index_product_images_indicator);
		//连网获取新星推荐
		RequestParams request=new RequestParams();
		BaseAsyncHttp.postReq(ctx, "/app/recommend", request, new JSONArrayHttpResponseHandler() {
			
			@Override
			public void jsonSuccess(JSONArray resp) {
				// TODO Auto-generated method stub
				for(int i=0;i<resp.length();i++){
					Star star=new Star();
					star.setPhone(resp.optJSONObject(i).optString("phone"));
					star.setNickname(resp.optJSONObject(i).optString("nickname"));
					star.setThumb(resp.optJSONObject(i).optString("thumb"));
					star.setFollower(resp.optJSONObject(i).optString("follower"));
					star.setShared(resp.optJSONObject(i).optString("shared"));
					liststar.add(star);
				}
				StarRecomAdapter staradapter=new StarRecomAdapter(ctx, liststar);
				gridview.setAdapter(staradapter);
				gridview.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						Star star=(Star) parent.getItemAtPosition(position);
						
						Intent intent=new Intent(ctx, CircleActivity.class);
						intent.putExtra("starphone", star.getPhone());
						intent.putExtra("starnickname", star.getNickname());
						intent.putExtra("starthumb", star.getThumb());
						intent.putExtra("starfollowers", star.getFollower());
						startActivity(intent);
						
					}
				});
			}
			
			@Override
			public void jsonFail(JSONArray resp) {
				// TODO Auto-generated method stub
				
			}
		});
		//个性化推荐列表
		HashMap<String, Object> map = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> lstRecommend = new ArrayList<HashMap<String, Object>>();
		map = new HashMap<String, Object>();
		map.put("iv_recommend_icon", R.drawable.caininxihuan);// 添加图像资源的ID
		map.put("tv_recommend_content", "猜您喜欢");
		map.put("tv_recommend_content1", "根据您的爱好生成，实时更新");
		lstRecommend.add(map);
		map = new HashMap<String, Object>();
		map.put("iv_recommend_icon", R.drawable.bangongbibei);// 添加图像资源的ID
		map.put("tv_recommend_content", "办公必备");
		map.put("tv_recommend_content1", "根据您的职业给您推荐相关APP");
		lstRecommend.add(map);
		map = new HashMap<String, Object>();
		map.put("iv_recommend_icon", R.drawable.nindepengyouxihuan);// 添加图像资源的ID
		map.put("tv_recommend_content", "您的朋友喜欢");
		map.put("tv_recommend_content1", "好朋友的喜好全部都在这儿呢");
		lstRecommend.add(map);
		SimpleAdapter saRecommend = new SimpleAdapter(getActivity(), // 没什么解释
				lstRecommend,// 数据来源
				R.layout.layout_item_recommend,// night_item的XML实现
				// 动态数组与ImageItem对应的子项
				new String[] { "iv_recommend_icon", "tv_recommend_content",
						"tv_recommend_content1" },
				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
				new int[] { R.id.iv_recommend_icon, R.id.tv_recommend_content,
						R.id.tv_recommend_content1 });
		lvRecommend.setAdapter(saRecommend);
		lvRecommend.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// HashMap<String, Object> item = (HashMap<String, Object>)
				// parent
				// .getItemAtPosition(position);
				// Utils.showShortToast(getActivity(),
				// (String)item.get("tv_recommend_content"));
				BasicNameValuePair name = new BasicNameValuePair("kind", String
						.valueOf(position));
				Utils.start_Activity(getActivity(), GuessActivity.class, name);
			}
		});

		mHandler = new Handler(getActivity().getMainLooper()) {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
				case MSG_CHANGE_PHOTO:
					int index = mViewPager.getCurrentItem();
					if (index == mImageUrls.size() - 1) {
						index = -1;
					}
					mViewPager.setCurrentItem(index + 1);
					mHandler.sendEmptyMessageDelayed(MSG_CHANGE_PHOTO,
							PHOTO_CHANGE_TIME);
				}
			}

		};

		// ======= 初始化ViewPager ========
		mIndicators = new ImageView[mImageUrls.size()];

		for (int i = 0; i < mIndicators.length; i++) {
			ImageView imageView = new ImageView(getActivity());
			LayoutParams params = new LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 1.0f);
			if (i != 0) {
				params.leftMargin = 5;
			}
			imageView.setLayoutParams(params);
			mIndicators[i] = imageView;
			if (i == 0) {
				mIndicators[i]
						.setBackgroundResource(R.drawable.android_activities_cur);
			} else {
				mIndicators[i]
						.setBackgroundResource(R.drawable.android_activities_bg);
			}

			mIndicator.addView(imageView);
		}

		mImageViews = new ImageView[mImageUrls.size()];

		for (int i = 0; i < mImageViews.length; i++) {
			ImageView imageView = new ImageView(getActivity());
			imageView.setScaleType(ScaleType.CENTER_CROP);
			mImageViews[i] = imageView;
		}
		mViewPager.setTransitionEffect(TransitionEffect.Standard);
		mViewPager.setCurrentItem(0);
		mHandler.sendEmptyMessageDelayed(MSG_CHANGE_PHOTO, PHOTO_CHANGE_TIME);

		mViewPager.setAdapter(new MyAdapter());
		mViewPager.setOnPageChangeListener(new MyPageChangeListener());
		mViewPager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (mImageUrls.size() == 0 || mImageUrls.size() == 1)
					return true;
				else
					return false;
			}
		});
		// ======= 初始化ViewPager ========
	}

	private void setOnListener() {
		starmore.setOnClickListener(this);
	}

	private void updateContent() {

	}

	private static String[] urls = new String[] { "Text #1", "Text #2",
			"Text #3" };
	private BaseAdapter mRecommends = new BaseAdapter() {

		@Override
		public int getCount() {
			return urls.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View retval = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.layout_item_image, null);
			// TextView title = (TextView) retval.findViewById(R.id.title);
			// title.setText(dataObjects[position]);

			return retval;
		}

	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.tv_discovery_more:
			Utils.start_Activity(getActivity(), StarRecommendActivity.class, null);
			break;
		}

	}


	public class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mImageViews.length;
		}

		@Override
		public boolean isViewFromObject(View view, Object obj) {
			if (view instanceof OutlineContainer) {
				return ((OutlineContainer) view).getChildAt(0) == obj;
			} else {
				return view == obj;
			}
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView(mViewPager
					.findViewFromObject(position));
		}

		@Override
		public Object instantiateItem(View container, int position) {
			ImageLoader.getInstance().displayImage(mImageUrls.get(position),
					mImageViews[position]);
			((ViewPager) container).addView(mImageViews[position], 0);
			mViewPager.setObjectForPosition(mImageViews[position], position);
			return mImageViews[position];
		}

	}

	/**
	 * 当ViewPager中页面的状态发生改变时调用
	 * 
	 * @author Administrator
	 * 
	 */
	private class MyPageChangeListener implements OnPageChangeListener {

		/**
		 * This method will be invoked when a new page becomes selected.
		 * position: Position index of the new selected page.
		 */
		public void onPageSelected(int position) {
			setImageBackground(position);
		}

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}
	}

	/**
	 * 设置选中的tip的背景
	 * 
	 * @param selectItemsIndex
	 */
	private void setImageBackground(int selectItemsIndex) {
		for (int i = 0; i < mIndicators.length; i++) {
			if (i == selectItemsIndex) {
				mIndicators[i]
						.setBackgroundResource(R.drawable.android_activities_cur);
			} else {
				mIndicators[i]
						.setBackgroundResource(R.drawable.android_activities_bg);
			}
		}
	}

}
