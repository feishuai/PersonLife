package com.personlife.net;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.snowdream.android.app.DownloadListener;
import com.github.snowdream.android.app.DownloadManager;
import com.github.snowdream.android.app.DownloadStatus;
import com.github.snowdream.android.app.DownloadTask;
import com.personlife.bean.App;
import com.personlife.utils.ComplexPreferences;
import com.personlife.utils.Constants;
import com.personlife.utils.Utils;

public class DownloadTaskManager {
	private static List<DownloadTask> tasks;
	private static List<App> apps;
	private static DownloadManager downloadManager;
	private static DownloadTaskManager downloadTaskManager;

	private DownloadTaskManager(Context context) {
		downloadManager = new DownloadManager(context);
		List<DownloadTask> task = new ArrayList<DownloadTask>();
		if (getDownloadApps(context) == null) {
			apps = new ArrayList<App>();
		} else
			apps = getDownloadApps(context);
		tasks = new ArrayList<DownloadTask>();
		for (int i = 0; i < apps.size(); i++) {
			DownloadTask downloadTask = new DownloadTask(context);
			downloadTask.setUrl(apps.get(i).getDownloadUrl());
			downloadTask.setPath(apps.get(i).getDownloadPath());
			downloadManager.add(downloadTask, null);
			// downloadManager.start(downloadTask, null);
		}
	}

	public static DownloadTaskManager getDownloadTaskManager(Context context) {
		if (downloadTaskManager == null) {
			downloadTaskManager = new DownloadTaskManager(context);
		}
		return downloadTaskManager;
	}

	public static void saveDownloadApps(Context context, List<App> apps) {
		ComplexPreferences pre = ComplexPreferences.getComplexPreferences(
				context, Constants.SharePrefrencesName);
		pre.putObject(Constants.DownloadApps, apps);
		pre.commit();
	}

	public static List<App> getDownloadApps(Context context) {
		ComplexPreferences pre = ComplexPreferences.getComplexPreferences(
				context, Constants.SharePrefrencesName);
		ArrayList<App> apps = pre.getObject(Constants.DownloadApps,
				new TypeReference<ArrayList<App>>() {
				});
		return apps;
	}

	public static int getDownloadStatus(Context context, App app) {
		DownloadTask downloadTask = new DownloadTask(context);
		downloadTask.setUrl(app.getDownloadUrl());
		downloadManager.add(downloadTask, null);
		downloadManager.start(downloadTask, null);
		return downloadTask.getStatus();
	}

	public static DownloadTask getDownloadTaskByApp(App app) {
		return tasks.get(apps.indexOf(app));
	}

	public static int getDownloadProgress(App app) {
		DownloadTask downloadTask = getDownloadTaskByApp(app);
		long filesize = 0l;
		File file = new File(app.getDownloadPath());
		if (file.exists()) {
			filesize = file.length();
		}
		int progress = 0;
		if (downloadTask.getSize() > 0) {
			progress = (int) (filesize * 100 / downloadTask.getSize());
		}
		return 0;
	}

	public static Boolean isHasDownloaded(App app) {
		for (App downloadapp : apps) {
			Log.i("download app url", downloadapp.getDownloadUrl());
			Log.i("app url", app.getDownloadUrl());
			Log.i("result",
					String.valueOf(downloadapp.getDownloadUrl().equals(
							app.getDownloadPath())));
			if (downloadapp.getDownloadUrl() == app.getDownloadUrl()) {
				return true;
			}
		}
		return false;
	}

	public static int startDownload(Context context, App app) {
		DownloadTask downloadTask = new DownloadTask(context);
		downloadTask.setUrl(app.getDownloadUrl());
		downloadTask.setPath(app.getDownloadPath());
		downloadManager.add(downloadTask,
				new DownloadListener<Integer, DownloadTask>());
		downloadManager.start(downloadTask,
				new DownloadListener<Integer, DownloadTask>());
		switch (downloadTask.getStatus()) {
		case DownloadStatus.STATUS_FAILED:
			Utils.showShortToast(context, "请连接网络");
			break;
		case DownloadStatus.STATUS_RUNNING:
		case DownloadStatus.STATUS_FINISHED:
			tasks.add(downloadTask);
			tasks.add(downloadTask);
			apps.add(app);
			saveDownloadApps(context, apps);
			break;
		}
		return downloadTask.getStatus();
	}

	public static Boolean isDowning(Context context) {
		return false;
	}

	public static Boolean getDownloadAppStatus(String url) {
		return false;
	}
}
