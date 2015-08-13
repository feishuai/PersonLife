package com.personlife.net;

import android.util.Log;

import com.github.snowdream.android.app.DownloadListener;
import com.github.snowdream.android.app.DownloadTask;
import com.personlife.utils.Constants;

public class DownloadTaskListener extends
		DownloadListener<Integer, DownloadTask> {
	int progress = 100;
	/**
	 * The download task has been added to the sqlite.
	 * <p/>
	 * operation of UI allowed.
	 *
	 * @param downloadTask
	 *            the download task which has been added to the sqlite.
	 */
	@Override
	public void onAdd(DownloadTask downloadTask) {
		super.onAdd(downloadTask);
		Log.i(Constants.Download, "onAdd()");
		Log.i(Constants.Download, downloadTask.toString());
	}

	/**
	 * The download task has been delete from the sqlite
	 * <p/>
	 * operation of UI allowed.
	 *
	 * @param downloadTask
	 *            the download task which has been deleted to the sqlite.
	 */
	@Override
	public void onDelete(DownloadTask downloadTask) {
		super.onDelete(downloadTask);
	}

	/**
	 * The download task is stop
	 * <p/>
	 * operation of UI allowed.
	 *
	 * @param downloadTask
	 *            the download task which has been stopped.
	 */
	@Override
	public void onStop(DownloadTask downloadTask) {
		super.onStop(downloadTask);
	}

	/**
	 * Runs on the UI thread before doInBackground(Params...).
	 */
	@Override
	public void onStart() {
		super.onStart();
	}

	/**
	 * Runs on the UI thread after publishProgress(Progress...) is invoked. The
	 * specified values are the values passed to publishProgress(Progress...).
	 *
	 * @param values
	 *            The values indicating progress.
	 */
	@Override
	public void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		if(values.length==1)
			progress = values[0];
	}

	/**
	 * Runs on the UI thread after doInBackground(Params...). The specified
	 * result is the value returned by doInBackground(Params...). This method
	 * won't be invoked if the task was cancelled.
	 *
	 * @param downloadTask
	 *            The result of the operation computed by
	 *            doInBackground(Params...).
	 */
	@Override
	public void onSuccess(DownloadTask downloadTask) {
		super.onSuccess(downloadTask);
		Log.i(Constants.Download, "success");
		// 安装应用
	}

	/**
	 * Applications should preferably override onCancelled(Object). This method
	 * is invoked by the default implementation of onCancelled(Object). Runs on
	 * the UI thread after cancel(boolean) is invoked and
	 * doInBackground(Object[]) has finished.
	 */
	@Override
	public void onCancelled() {
		super.onCancelled();
	}

	@Override
	public void onError(Throwable thr) {
		super.onError(thr);
		Log.i(Constants.Download, "download err");
		// 网络没打开 提示打开网络
	}

	/**
	 * Runs on the UI thread after doInBackground(Params...) when the task is
	 * finished or cancelled.
	 */
	@Override
	public void onFinish() {
		super.onFinish();
		Log.i(Constants.Download, "download finish");
		//下载结束后的操作
	}
}
