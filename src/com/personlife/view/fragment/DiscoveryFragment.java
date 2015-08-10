package com.personlife.view.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
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

import com.example.personlifep.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.personlife.utils.Utils;
import com.personlife.view.activity.circle.CircleActivity;
import com.personlife.view.activity.discovery.GuessActivity;
import com.personlife.widget.JazzyViewPager;
import com.personlife.widget.JazzyViewPager.TransitionEffect;
import com.personlife.widget.MyListView;
import com.personlife.widget.OutlineContainer;

public class DiscoveryFragment extends Fragment implements OnClickListener {
	private Activity ctx;
	private View layout;
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
	private static final int MSG_CHANGE_PHOTO = 1;
	/** 图片自动切换时间 */
	private static final int PHOTO_CHANGE_TIME = 3000;
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
	private void initData(){
		mImageUrl = "drawable://" + R.drawable.back1;
		mImageUrls.add(mImageUrl);
		mImageUrl = "drawable://" + R.drawable.back3;
		mImageUrls.add(mImageUrl);
		mImageUrl = "drawable://" + R.drawable.back3;
		mImageUrls.add(mImageUrl);

	}
	private void initView() {
		gridview = (GridView) layout.findViewById(R.id.gridview);
		lvRecommend = (MyListView) layout
				.findViewById(R.id.lv_dicovery_recommend);
		mViewPager = (JazzyViewPager) layout.findViewById(R.id.index_product_images_container);
		mIndicator = (LinearLayout) layout.findViewById(R.id.index_product_images_indicator);
		// 生成动态数组，并且转入数据
		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("iv_grid_staricon", R.drawable.star1);// 添加图像资源的ID
			map.put("tv_grid_starname", "李宇春的APP");// 按序号做ItemText
			map.put("tv_grid_counts", String.valueOf(1211));// 按序号做ItemText
			lstImageItem.add(map);
			map = new HashMap<String, Object>();
			map.put("iv_grid_staricon", R.drawable.star2);// 添加图像资源的ID
			map.put("tv_grid_starname", "李宇春的APP");// 按序号做ItemText
			map.put("tv_grid_counts", String.valueOf(1211));// 按序号做ItemText
			lstImageItem.add(map);
			map = new HashMap<String, Object>();
			map.put("iv_grid_staricon", R.drawable.star3);// 添加图像资源的ID
			map.put("tv_grid_starname", "李宇春的APP");// 按序号做ItemText
			map.put("tv_grid_counts", String.valueOf(1211));// 按序号做ItemText
			lstImageItem.add(map);
			map = new HashMap<String, Object>();
			map.put("iv_grid_staricon", R.drawable.star4);// 添加图像资源的ID
			map.put("tv_grid_starname", "李宇春的APP");// 按序号做ItemText
			map.put("tv_grid_counts", String.valueOf(1211));// 按序号做ItemText
			lstImageItem.add(map);
			map = new HashMap<String, Object>();
			map.put("iv_grid_staricon", R.drawable.star5);// 添加图像资源的ID
			map.put("tv_grid_starname", "李宇春的APP");// 按序号做ItemText
			map.put("tv_grid_counts", String.valueOf(1211));// 按序号做ItemText
			lstImageItem.add(map);
			map = new HashMap<String, Object>();
			map.put("iv_grid_staricon", R.drawable.star6);// 添加图像资源的ID
			map.put("tv_grid_starname", "李宇春的APP");// 按序号做ItemText
			map.put("tv_grid_counts", String.valueOf(1211));// 按序号做ItemText
			lstImageItem.add(map);
		// 生成适配器的ImageItem <====> 动态数组的元素，两者一一对应
		SimpleAdapter saImageItems = new SimpleAdapter(getActivity(), // 没什么解释
				lstImageItem,// 数据来源
				R.layout.layout_grid_star,// night_item的XML实现

				// 动态数组与ImageItem对应的子项
				new String[] { "iv_grid_staricon", "tv_grid_starname",
						"tv_grid_counts" },

				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
				new int[] { R.id.iv_grid_staricon, R.id.tv_grid_starname,
						R.id.tv_grid_counts });
		// 添加并且显示
		gridview.setAdapter(saImageItems);
		// 添加消息处理
		gridview.setOnItemClickListener(new ItemClickListener());

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
				new String[] { "iv_recommend_icon", "tv_recommend_content" ,"tv_recommend_content1"},

				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
				new int[] { R.id.iv_recommend_icon, R.id.tv_recommend_content, R.id.tv_recommend_content1});
		lvRecommend.setAdapter(saRecommend);
		lvRecommend.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
//				HashMap<String, Object> item = (HashMap<String, Object>) parent
//						.getItemAtPosition(position);
//				Utils.showShortToast(getActivity(), (String)item.get("tv_recommend_content"));
				BasicNameValuePair name = new BasicNameValuePair("kind", String.valueOf(position));
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

	}

	// 当AdapterView被单击(触摸屏或者键盘)，则返回的Item单击事件
	class ItemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> arg0,// The AdapterView where the
													// click happened
				View arg1,// The view within the AdapterView that was clicked
				int arg2,// The position of the view in the adapter
				long arg3// The row id of the item that was clicked
		) {
			// 在本例中arg2=arg3
			HashMap<String, Object> item = (HashMap<String, Object>) arg0
					.getItemAtPosition(arg2);
			Utils.start_Activity(getActivity(), CircleActivity.class, null);
			// 显示所选Item的ItemText
			// setTitle((String)item.get("ItemText"));
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
