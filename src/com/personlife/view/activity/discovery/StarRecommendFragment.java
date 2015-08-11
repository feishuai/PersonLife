package com.personlife.view.activity.discovery;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.personlifep.R;
import com.personlife.bean.User;
import com.personlife.widget.MyListView;

public class StarRecommendFragment extends Fragment {
	private View layout;
	private MyListView lv;
	List<User> mList;
	UsersAdapter starsAdapter;
	
	public StarRecommendFragment(List<User> mList) {
		// TODO Auto-generated constructor stub
		this.mList = mList;
	}
	public List<User> getAppsList() {
		return mList;
	}
	public void setAppsList(List<User> mList) {
		this.mList = mList;
	}
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		layout = inflater.inflate(R.layout.fragment_starrecommend, container,
				false);
		initView();
		return layout;
	}
	
	
	public void initView() {
		lv = (MyListView) layout.findViewById(R.id.lv_starrecommend_stars);
		starsAdapter = new UsersAdapter(getActivity());
		lv.setAdapter(starsAdapter);
	}

	class UsersAdapter extends BaseAdapter {

		private Context context;

		public UsersAdapter(Context context) {
			this.context = context;
		}

		@Override
		public int getCount() {
			return mList.size();
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
						.inflate(R.layout.layout_item_starrecommend, null);
				holder = new ViewHolder();
				holder.name = (TextView) convertView
						.findViewById(R.id.tv_starrecommend_name);
				holder.fanscounts = (TextView) convertView
						.findViewById(R.id.tv_starrecommend_fanscounts);
				holder.icon = (ImageView) convertView
						.findViewById(R.id.iv_starrecommend_icon);
				holder.home = (ImageButton) convertView
						.findViewById(R.id.ib_starrecommend_home);
				holder.lastesttime = (TextView) convertView
						.findViewById(R.id.tv_starrecommend_lastesttime);
				holder.downloadcounts = (TextView) convertView
						.findViewById(R.id.tv_starrecommend_downloadcounts);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			// ImageLoaderUtils.displayAppIcon("https://ss0.bdstatic.com/-0U0bnSm1A5BphGlnYG/tam-ogel/5136becf77e9cfc440849e0b694fdd6e_121_121.jpg",
			// holder.icon);
//			holder.name.setText(mList.get(position).getName());
			
			return convertView;
		}

		class ViewHolder {
			ImageView icon;
			TextView downloadcounts;
			TextView name;
			TextView fanscounts;
			TextView lastesttime;
			ImageButton home;
		}
	}
	
}
