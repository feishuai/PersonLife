package com.personlife.view.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.example.personlifep.R;
import com.personlife.utils.Utils;
import com.personlife.widget.MyListView;

public class DiscoveryFragment extends Fragment implements OnClickListener {
	private Activity ctx;
	private View layout;
	private MyListView lvRecommend;
	private GridView gridview;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (layout == null) {
			ctx = this.getActivity();
			layout = ctx.getLayoutInflater().inflate(
					R.layout.fragment_discovery, null);
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

	private void initView() {
		gridview = (GridView) layout.findViewById(R.id.gridview);
		lvRecommend = (MyListView) layout
				.findViewById(R.id.lv_dicovery_recommend);
		// 生成动态数组，并且转入数据
		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 6; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("iv_grid_staricon", R.drawable.default_backgroud);// 添加图像资源的ID
			map.put("tv_grid_starname", "NO." + String.valueOf(i));// 按序号做ItemText
			map.put("tv_grid_counts", String.valueOf(i));// 按序号做ItemText

			lstImageItem.add(map);
		}
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

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("iv_recommend_icon", R.drawable.default_app_icon);// 添加图像资源的ID
		map.put("tv_recommend_content", "猜您喜欢");
		lstRecommend.add(map);
		map = new HashMap<String, Object>();
		map.put("iv_recommend_icon", R.drawable.default_app_icon);// 添加图像资源的ID
		map.put("tv_recommend_content", "办公必备");
		lstRecommend.add(map);
		map = new HashMap<String, Object>();
		map.put("iv_recommend_icon", R.drawable.default_app_icon);// 添加图像资源的ID
		map.put("tv_recommend_content", "您的朋友喜欢");
		lstRecommend.add(map);
		SimpleAdapter saRecommend = new SimpleAdapter(getActivity(), // 没什么解释
				lstRecommend,// 数据来源
				R.layout.layout_item_recommend,// night_item的XML实现

				// 动态数组与ImageItem对应的子项
				new String[] { "iv_recommend_icon", "tv_recommend_content" },

				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
				new int[] { R.id.iv_recommend_icon, R.id.tv_recommend_content });
		lvRecommend.setAdapter(saRecommend);
		lvRecommend.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				HashMap<String, Object> item = (HashMap<String, Object>) parent
						.getItemAtPosition(position);
				Utils.showShortToast(getActivity(), (String)item.get("tv_recommend_content"));
			}
		});
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
			// 显示所选Item的ItemText
			// setTitle((String)item.get("ItemText"));
		}

	}
}
