package com.personlife.view.activity.circle;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personlifep.R;
import com.personlife.bean.App;
import com.personlife.view.activity.personcenter.TabTaskList.TabTaskAdapter;
import com.personlife.widget.MyListView;

public class CircleAppsFragment extends Fragment {
	private View layout;
	private MyListView lv;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		layout = inflater.inflate(R.layout.fragment_circle_apps, container,
				false);
		initView();
		return layout;
	}
	
	public void initView(){
		lv = (MyListView)layout.findViewById(R.id.lv_circle_apps);
		List<App>mList = new ArrayList<App>();
		mList.add(new App());
		mList.add(new App());
		mList.add(new App());
		lv.setAdapter( new AppsAdapter(getActivity(), mList));
		
	}

	class AppsAdapter extends BaseAdapter {

		private Context context;
		private List<App> mlist;

		public AppsAdapter(Context context, List<App> mlist) {
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
			Log.i("adapter", "mlist size is " + mlist.size());
			if (convertView == null) {
				convertView = ((LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
						.inflate(R.layout.layout_item_download, null);
				holder = new ViewHolder();
				holder.appname = (TextView) convertView
						.findViewById(R.id.tv_download_name);
				holder.status = (TextView) convertView
						.findViewById(R.id.tv_download_status);
				holder.icon = (ImageView) convertView
						.findViewById(R.id.iv_download_icon);
				holder.download = (Button) convertView
						.findViewById(R.id.btn_download_download);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			// 设置控件属性
			// holder.download.setText("打开");
			// holder.download.setBackgroundColor(R.color.gray1);

			holder.download.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(context, "downloading", Toast.LENGTH_SHORT)
							.show();
					;
				}
			});
			// ImageLoader.getInstance().displayImage(mlist.get(position).getBitmap(),holder.icon);
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

				}
			});
			return convertView;
		}

		public void setData(List<App> list) {
			mlist = list;
		}

		private class ViewHolder {
			ImageView icon;
			TextView appname;
			TextView status;
			Button download;
		}
	}
}
