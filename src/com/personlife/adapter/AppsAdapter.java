package com.personlife.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
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

import com.personlife.common.Utils;
import com.personlife.net.DownloadTaskManager;

import com.personlife.utils.Constants;
import com.personlife.utils.ImageLoaderUtils;
import com.personlife.utils.SystemUtils;
import com.personlife.view.activity.home.AppDetailActivity;

public class AppsAdapter extends BaseAdapter {

	private Context context;
	private List<App> mlist;

	public static final String SD_PATH = Environment
			.getExternalStorageDirectory().toString() + "/";

	public AppsAdapter(Context context, List<App> mlist) {
		this.mlist = new ArrayList<App>();
		this.context = context;

		this.mlist.addAll(mlist);
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
					.inflate(R.layout.layout_item_tuijian, null);
			holder = new ViewHolder();
			holder.bar = (ProgressBar) convertView.findViewById(R.id.bar);
			holder.appname = (TextView) convertView
					.findViewById(R.id.tv_tuijian_name);
			holder.status = (TextView) convertView
					.findViewById(R.id.tv_tuijian_status);
			holder.intro = (TextView) convertView
					.findViewById(R.id.tv_tuijian_intro);
			holder.icon = (ImageView) convertView
					.findViewById(R.id.iv_tuijian_icon);
			holder.download = (Button) convertView
					.findViewById(R.id.btn_tuijian_tuijian);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ImageLoaderUtils.displayAppIcon(mlist.get(position).getIcon(),
				holder.icon);
		holder.appname.setText(mlist.get(position).getName());
		int counts = mlist.get(position).getDowloadcount();
		if (counts > 10000)
			holder.status.setText(counts / 10000 + "万人下载 "
					+ mlist.get(position).getSize());
		else
			holder.status.setText(counts + "人下载 "
					+ mlist.get(position).getSize());
		holder.intro.setText(mlist.get(position).getProfile());
		if (DownloadTaskManager.getDownloadTaskManager(context)
				.isHasDownloaded(mlist.get(position))) {
			long size = DownloadTaskManager.getDownloadTaskManager(context)
					.getDownloadTaskByApp(mlist.get(position)).getSize();
			if (size > 0) {
				int progress = DownloadTaskManager.getDownloadTaskManager(
						context).getDownloadProgress(mlist.get(position));
				if (progress == 100)
					holder.download.setText("已下载");
				else
					holder.download.setText("继续");
				holder.bar.setVisibility(View.VISIBLE);
				holder.bar.setProgress(progress);
			}
		}

		holder.download.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (holder.download.getText().toString().equals("已下载")) {
					Utils.showShortToast(context, "该应用已下载，请到下载任务中管理");
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
												holder.download.setText("已下载");
											}
											Log.i("update progress",
													String.valueOf(values[0]));
										}
									});
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
												holder.download.setText("已下载");
												SystemUtils
														.openAppFronUri(
																context,
																mlist.get(
																		position)
																		.getDownloadPath());
											}
											Log.i("update progress",
													String.valueOf(values[0]));
										}

										@Override
										public void onStop(
												DownloadTask downloadTask) {
											super.onStop(downloadTask);
											holder.download.setText("继续");
										}
									});
					return;
				}
				if (holder.download.getText().toString().equals("暂停")) {
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
				Log.i("click appid detail info",
						String.valueOf(mlist.get(position).getId()));
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);

			}
		});
		return convertView;
	}

	public void setData(List<App> list) {
		this.mlist.clear();
		this.mlist.addAll(list);
	}

	class ViewHolder {
		ImageView icon;
		TextView appname;
		TextView status;
		TextView intro;
		Button download;
		ProgressBar bar;
	}

	public void clear() {
		mlist.clear();
		notifyDataSetChanged();
	}
}