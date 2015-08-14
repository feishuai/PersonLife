package com.personlife.view.activity.circle;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.personlifep.R;
import com.personlife.bean.Shuoshuo;
import com.personlife.utils.ListViewUtils;
import com.personlife.widget.HorizontialListView;

public class CircleFriendsFragment extends Fragment {
	View layout;
	ListView lv;
	List<Shuoshuo> mlist;
	ShuoshuoAdapter mAdapter;
	Boolean isLoad=false;
	public CircleFriendsFragment(List<Shuoshuo> list) {
		// TODO Auto-generated constructor stub
		this.mlist = list;
//		initView();
	}
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		layout = inflater.inflate(R.layout.fragment_circle_friends, container,
				false);
		mAdapter = new ShuoshuoAdapter(getActivity(), mlist);
		Log.i("listview getview", "circle firends frigment oncreateview");
		isLoad = true;
		initData();
		initView();
		return layout;
	}
	
	public Boolean getIsLoad(){
		return isLoad;
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		Log.i("listview getview", "circle firends frigment onstart");
		super.onStart();
	}
	
	public void updateData(List<Shuoshuo> list){
		mAdapter.setData(list);
		mAdapter.notifyDataSetChanged();
	}
	
	public int getListViewLayoutParams(){
		if(lv==null)
			return 0;
		int listViewHeight = ListViewUtils.setListViewHeightBasedOnChildren1(lv);
		Log.i("listview height", String.valueOf(listViewHeight));
		return listViewHeight;
	}
	public void initData() {

	}

	public void initView() {
		lv = (ListView) layout.findViewById(R.id.lv_circle_shuoshuo);
		lv.setAdapter(mAdapter);
	}

	class ShuoshuoAdapter extends BaseAdapter {

		private Context context;
		private List<Shuoshuo> mlist;

		public ShuoshuoAdapter(Context context, List<Shuoshuo> mlist) {
			this.context = context;
			this.mlist = mlist;
		}

		@Override
		public int getCount() {
			return mlist.size();
		}

		@Override
		public Object getItem(int arg0) {
			return arg0;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = ((LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
						.inflate(R.layout.layout_item_shuoshuo, null);
				holder = new ViewHolder();
				holder.name = (TextView) convertView
						.findViewById(R.id.tv_shuoshuo_name);
				holder.beforetime = (TextView) convertView
						.findViewById(R.id.tv_shuoshuo_beforetime);
				holder.status = (TextView) convertView
						.findViewById(R.id.tv_shuoshuo_status);
				holder.icon = (ImageView) convertView
						.findViewById(R.id.iv_shuoshuo_icon);
				holder.content = (TextView) convertView
						.findViewById(R.id.tv_shuoshuo_content);
				holder.apps = (HorizontialListView) convertView
						.findViewById(R.id.hlv_shuoshuo_apps);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.apps.setAdapter(mALikes);
			// ImageLoaderUtils.displayAppIcon("https://ss0.bdstatic.com/-0U0bnSm1A5BphGlnYG/tam-ogel/5136becf77e9cfc440849e0b694fdd6e_121_121.jpg",
			// holder.icon);
			Log.i("listview getview", "circle firends frigment"+String.valueOf(mlist.size()));
			return convertView;
		}

		public void setData(List<Shuoshuo> list) {
			mlist = list;
		}

		class ViewHolder {
			ImageView icon;
			TextView name;
			TextView beforetime;
			TextView status;
			TextView content;
			HorizontialListView apps;
		}
	}

	private static String[] urls = new String[] { "Text #1", "Text #1",
			"Text #1", "Text #1", "Text #1", "Text #1" };

	private BaseAdapter mALikes = new BaseAdapter() {

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
					R.layout.layout_item_like, null);
			ImageView appicon = (ImageView)retval.findViewById(R.id.iv_item_icon);
			TextView appname = (TextView) retval
					.findViewById(R.id.tv_item_name);
			appname.setVisibility(View.GONE);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(appicon.getLayoutParams());  
			lp.setMargins(0, 20, 0, 0);  
			appicon.setLayoutParams(lp);  
			return retval;
		}
	};
}
