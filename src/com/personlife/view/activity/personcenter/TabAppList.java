package com.personlife.view.activity.personcenter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.personlifep.R;
import com.personlife.bean.App;
import com.personlife.utils.SystemUtils;

/**
 * 
 * @author liugang
 * @date 2015年8月7日
 */
public class TabAppList extends Fragment implements OnClickListener {

	private Activity ctx;
	private View layout;
	private ListView listView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (layout == null) {
			ctx = this.getActivity();
			layout = ctx.getLayoutInflater().inflate(R.layout.fragment_task,
					null);
			initViews();
			initData();
			setOnListener();
		} else {
			ViewGroup parent = (ViewGroup) layout.getParent();
			if (parent != null) {
				parent.removeView(layout);
			}
		}
		return layout;
	}

	public void initViews() {
		listView = (ListView) layout.findViewById(R.id.listview_tasklist);
	}

	public void initData() {
		listView.setAdapter(new TabAppListAdapter(getActivity(), SystemUtils
				.getAppsNoSystom(getActivity())));
	}

	public void setOnListener() {

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	public class TabAppListAdapter extends BaseAdapter {

		private Context context;
		private List<App> mlist;

		public TabAppListAdapter(Context context, List<App> mlist) {
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
				holder.download_progress=(ProgressBar) convertView.findViewById(R.id.download_progress);
				holder.download_progress.setVisibility(View.GONE);
				holder.download = (Button) convertView
						.findViewById(R.id.btn_download_download);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.appname.setText(mlist.get(position).getName());
			holder.icon.setBackground(mlist.get(position).getAppIcon());
			holder.status.setText("已安装");
			// 设置控件属性
			holder.download.setText("打开");
			holder.download.setBackgroundResource(R.drawable.yibananniu);
			holder.download.setTextColor(R.color.black);
			holder.download.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Log.i("package", mlist.get(position).getPackageName());
					SystemUtils.startApp(getActivity(), mlist.get(position).getPackageName());
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
			ProgressBar download_progress;
			Button download;
		}
	}

}
