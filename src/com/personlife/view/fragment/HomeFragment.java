package com.personlife.view.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.personlifep.R;
import com.personlife.adapter.AppListAdapter;
import com.personlife.adapter.AppsAdapter;
import com.personlife.bean.App;
import com.personlife.utils.Constants;
import com.personlife.utils.Utils;
import com.personlife.view.activity.home.AppDetailActivity;
import com.personlife.view.activity.home.AppSearchActivity;
import com.personlife.view.activity.home.ClassificationActivity;
import com.personlife.view.activity.home.RecommendActivity;
import com.personlife.widget.ClearEditText;
import com.personlife.widget.HorizontialListView;
import com.personlife.widget.MyListView;

public class HomeFragment extends Fragment implements OnClickListener {
	private ClearEditText search;
	private Button kind;
	private Activity ctx;
	private View layout;
	private MyListView mLvApps;
	private AppListAdapter mAdapter;
	private List<App> apps = new ArrayList<App>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (layout == null) {
			ctx = this.getActivity();
			layout = ctx.getLayoutInflater().inflate(R.layout.fragment_home,
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
		search = (ClearEditText) layout.findViewById(R.id.et_home_search);
		kind = (Button) layout.findViewById(R.id.btn_home_class);
		mLvApps = (MyListView) layout.findViewById(R.id.lv_home_kinds);
	}

	public void setOnListener() {
		search.setOnClickListener(this);
		kind.setOnClickListener(this);
	}

	public void initData() {
		List<App> apps = new ArrayList<App>();
		apps.add(new App());
		apps.add(new App());
		apps.add(new App());
		mLvApps.setAdapter(new KindsAdapter(getActivity(), apps));
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.et_home_search:
			Intent intent = new Intent(getActivity(), AppSearchActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_home_class:
			Utils.start_Activity(getActivity(), ClassificationActivity.class,
					null);
			// Utils.start_Activity(getActivity(), RecommendActivity.class,
			// null);
			break;
		default:
			break;
		}
	}

	class KindsAdapter extends BaseAdapter {

		private Context context;
		private List<App> mlist;

		public KindsAdapter(Context context, List<App> mlist) {
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
			final ViewHolder holder;
			if (convertView == null) {
				convertView = ((LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
						.inflate(R.layout.layout_item_home, null);
				holder = new ViewHolder();
				holder.tvkind = (TextView) convertView
						.findViewById(R.id.tv_home_kind);
				holder.counts = (TextView) convertView
						.findViewById(R.id.tv_home_counts);
				holder.more = (Button) convertView
						.findViewById(R.id.btn_home_more);
				holder.hlvMyapps = (HorizontialListView) convertView
						.findViewById(R.id.hlv_home_apps);
				holder.lvapps = (MyListView) convertView
						.findViewById(R.id.lv_home_apps);				
				List<App> apps = new ArrayList<App>();
				apps.add(new App());
				apps.add(new App());
				apps.add(new App());
				holder.lvapps.setAdapter(new AppsAdapter(context, apps));

				holder.hlvMyapps.setAdapter(mALikes);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			// ImageLoaderUtils.displayAppIcon("https://ss0.bdstatic.com/-0U0bnSm1A5BphGlnYG/tam-ogel/5136becf77e9cfc440849e0b694fdd6e_121_121.jpg",
			// holder.icon);
//			holder.lvapps.setOnItemClickListener(new OnItemClickListener() {
//
//				@Override
//				public void onItemClick(AdapterView<?> parent, View view,
//						int position, long id) {
//					// TODO Auto-generated method stub
//					Intent intent = new Intent(context, AppDetailActivity.class);
//					intent.putExtra(Constants.AppId, mlist.get(position)
//							.getId());
//					context.startActivity(intent);
//
//				}
//
//			});

			holder.more.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					context.startActivity(new Intent(context,
							RecommendActivity.class));
				}
			});
			// Intent intent = new Intent(context, AppDetailActivity.class);
			// intent.putExtra(Constants.AppId, mlist.get(position)
			// .getId());
			// context.startActivity(intent);
			// }
			// });
			return convertView;
		}

		public void setData(List<App> list) {
			mlist = list;
		}

		class ViewHolder {
			TextView tvkind;
			TextView counts;
			Button more;
			HorizontialListView hlvMyapps;
			MyListView lvapps;
		}
	}

	private static String[] urls = new String[] { "Text #1", "Text #1",
			"Text #1", "Text #1" };
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
			ImageView appicon = (ImageView) retval
					.findViewById(R.id.iv_item_icon);
			TextView appname = (TextView) retval
					.findViewById(R.id.tv_item_name);
			appname.setVisibility(View.GONE);
			return retval;
		}

	};

}
