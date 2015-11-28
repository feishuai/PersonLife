package com.personlife.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.personlifep.R;
import com.github.snowdream.android.app.DownloadListener;
import com.github.snowdream.android.app.DownloadStatus;
import com.github.snowdream.android.app.DownloadTask;
import com.personlife.bean.App;
import com.personlife.net.DownloadTaskManager;
import com.personlife.utils.Constants;
import com.personlife.utils.ImageLoaderUtils;
import com.personlife.utils.SystemUtils;
import com.personlife.view.activity.home.AppDetailActivity;

public class AppListAdapter extends BaseAdapter {

	private Context context;
	private List<App> mlist;

	public AppListAdapter(Context context, List<App> mlist) {
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
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
			holder.bar = (ProgressBar) convertView
					.findViewById(R.id.download_progress);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ImageLoaderUtils.displayAppIcon(mlist.get(position).getIcon(),
				holder.icon);
		holder.appname.setText(mlist.get(position).getName());
		holder.status.setText("未安装");
		holder.download.setText("下载");
		
		if (DownloadTaskManager.getDownloadTaskManager(context)
				.isHasDownloaded(mlist.get(position))) {
			long size = DownloadTaskManager.getDownloadTaskManager(context)
					.getDownloadTaskByApp(mlist.get(position)).getSize();
			if (size > 0) {
				int progress = DownloadTaskManager.getDownloadTaskManager(
						context).getDownloadProgress(mlist.get(position));
				if (progress == 100) {
					holder.status.setText("等待安装");
					holder.download.setText("安装");
					holder.bar.setVisibility(View.GONE);
				} else {
					if (DownloadTaskManager.getDownloadTaskManager(context)
							.getDownloadTaskByApp(mlist.get(position))
							.getStatus() == DownloadStatus.STATUS_RUNNING) {
						holder.download.setText("暂停");
						holder.status.setText("正在下载");
					} else {
						holder.status.setText("已暂停");
						holder.download.setText("继续");
					}
					holder.bar.setProgress(progress);
					holder.bar.setVisibility(View.VISIBLE);
				}
			}
		}
		
		if (SystemUtils.getUserApps(context).contains(mlist.get(position))) {
			holder.download.setText("打开");
			holder.status.setText("已安装");
			holder.bar.setVisibility(View.GONE);
		}
		
		holder.download.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (holder.download.getText().toString().equals("打开")) {
					SystemUtils.startApp(context, mlist.get(position)
							.getPackageName());
					return;
				}
				if (holder.download.getText().toString().equals("下载")) {
					if (DownloadTaskManager.getDownloadTaskManager(context)
							.isHasDownloaded(mlist.get(position))) {
						holder.download.setText("继续");
						holder.download.callOnClick();
						return;
					}
					holder.download.setText("暂停");
					holder.status.setText("正在下载");
					holder.bar.setVisibility(View.VISIBLE);
					DownloadTaskManager
							.getDownloadTaskManager(context)
							.startNewDownload(
									context,
									mlist.get(position),
									new DownloadListener<Integer, DownloadTask>() {
										@Override
										public void onProgressUpdate(
												Integer... values) {
											super.onProgressUpdate(values);
											holder.bar.setProgress(values[0]);
											if (values[0] == 100) {
												holder.download.setText("安装");
												holder.status.setText("等待安装");
											}
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
				if (holder.download.getText().toString().equals("安装")) {
					SystemUtils.openAppFronUri(context, mlist.get(position)
							.getDownloadPath());
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
											holder.bar.setProgress(values[0]);
											if (values[0] == 100) {
												holder.download.setText("安装");
												holder.status.setText("等待安装");
											}
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
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, AppDetailActivity.class);
				intent.putExtra(Constants.AppId, mlist.get(position).getId());
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
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
		ProgressBar bar;
	}

}
