package com.personlife.view.activity.personcenter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import com.example.personlifep.R;
import com.github.snowdream.android.app.DownloadListener;
import com.github.snowdream.android.app.DownloadStatus;
import com.github.snowdream.android.app.DownloadTask;
import com.personlife.adapter.AppsAdapter;
import com.personlife.bean.App;
import com.personlife.common.Utils;
import com.personlife.net.DownloadTaskManager;
import com.personlife.utils.ImageLoaderUtils;
import com.personlife.utils.SystemUtils;
import com.personlife.view.activity.personcenter.TabAppList.TabAppListAdapter;

/**
 * 
 * @author liugang
 * @date 2015年8月7日
 */
public class TabTaskList extends Fragment implements OnClickListener {

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
		listView.setAdapter(new TabTaskAdapter(getActivity(),
				DownloadTaskManager.getDownloadTaskManager(getActivity())
						.getDownloadApps()));
	}

	public void setOnListener() {

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	public class TabTaskAdapter extends BaseAdapter {

		private Context context;
		private List<App> mlist;

		public TabTaskAdapter(Context context, List<App> mlist) {
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
				holder.bar = (ProgressBar) convertView
						.findViewById(R.id.download_progress);
				holder.download = (Button) convertView
						.findViewById(R.id.btn_download_download);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			long size = DownloadTaskManager.getDownloadTaskManager(context)
					.getDownloadTaskByApp(mlist.get(position)).getSize();
			if (size > 0) {
				int progress = DownloadTaskManager.getDownloadTaskManager(
						context).getDownloadProgress(mlist.get(position));
				if (progress == 100) {
					holder.status.setText("等待安装");
					holder.download.setText("安装");
				} else {
					if (DownloadTaskManager.getDownloadTaskManager(context)
							.getDownloadTaskByApp(mlist.get(position))
							.getStatus() == DownloadStatus.STATUS_RUNNING) {
						holder.download.setText("暂停");
						holder.status.setText("正在下载");
					}else{
						holder.status.setText("已暂停");
						holder.download.setText("继续");
						holder.bar.setProgress(progress);
						holder.bar.setVisibility(View.VISIBLE);
					}
				}
			}

			holder.download.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (holder.download.getText().toString().equals("安装")) {
						SystemUtils.openAppFronUri(getActivity(),
								mlist.get(position).getDownloadPath());
						return;
					}

					if (holder.download.getText().toString().equals("继续")) {
						if (DownloadTaskManager.getDownloadTaskManager(context)
								.getDownloadTaskByApp(mlist.get(position))
								.getStatus() == DownloadStatus.STATUS_RUNNING) {
							// Utils.showShortToast(getApplicationContext(),
							// "该应用已在下载,请在下载任务管理");
							DownloadTaskManager.getDownloadTaskManager(context)
									.stopDownload(context, mlist.get(position));
						}
						holder.download.setText("暂停");
						holder.status.setText("正在下载");
						holder.bar.setVisibility(View.VISIBLE);
						DownloadTaskManager
								.getDownloadTaskManager(context)
								.startContinueDownload(
										context,
										mlist.get(position),
										new DownloadListener<Integer, DownloadTask>() {
											@Override
											public void onProgressUpdate(
													Integer... values) {
												super.onProgressUpdate(values);
												holder.bar
														.setProgress(values[0]);
												if (values[0] == 100) {
													holder.download
															.setText("安装");
													holder.status
															.setText("等待安装");
												}
												Log.i("update progress", String
														.valueOf(values[0]));
											}

											@Override
											public void onStop(
													DownloadTask downloadTask) {
												super.onStop(downloadTask);
												holder.status.setText("已暂停");
												holder.download.setText("继续");
											}
										});
						return;
					}
					if (holder.download.getText().toString().equals("暂停")) {
						holder.status.setText("已暂停");
						holder.download.setText("继续");
						DownloadTaskManager.getDownloadTaskManager(context)
								.stopDownload(context, mlist.get(position));
						return;
					}
				}
			});

			holder.appname.setText(mlist.get(position).getName());
			holder.status.setText("已下载");
			ImageLoaderUtils.displayAppIcon(mlist.get(position).getIcon(),
					holder.icon);

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
			ProgressBar bar;
			Button download;
		}
	}
}
