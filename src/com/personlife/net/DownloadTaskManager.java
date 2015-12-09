package com.personlife.net;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.snowdream.android.app.DownloadListener;
import com.github.snowdream.android.app.DownloadManager;
import com.github.snowdream.android.app.DownloadTask;
import com.personlife.bean.App;
import com.personlife.utils.ComplexPreferences;
import com.personlife.utils.Constants;

public class DownloadTaskManager {
	private static HashMap<App, DownloadTask> appmaptask;
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
		appmaptask = new HashMap<App, DownloadTask>();
		for (int i = 0; i < apps.size(); i++) {
			DownloadTask downloadTask = new DownloadTask(context);
			downloadTask.setUrl(apps.get(i).getDownloadUrl());
			downloadTask.setPath(apps.get(i).getDownloadPath());
			downloadManager.add(downloadTask,
					new DownloadListener<Integer, DownloadTask>());
			appmaptask.put(apps.get(i), downloadTask);
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

	public static List<App> getDownloadApps() {
		return apps;
	}

	public static List<App> getDownloadApps(Context context) {
		ComplexPreferences pre = ComplexPreferences.getComplexPreferences(
				context, Constants.SharePrefrencesName);
		List<App> apps = pre.getObject(Constants.DownloadApps,
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
		return progress;
	}

	public static Boolean isHasDownloaded(App app) {
		for (App downloadapp : apps) {
			if (downloadapp.getDownloadUrl().equals(app.getDownloadUrl())) {
				return true;
			}
		}
		return false;
	}

	public static void startNewDownload(Context context, App app,
			DownloadListener<Integer, DownloadTask> listener) {
		DownloadTask downloadTask = new DownloadTask(context);
		downloadTask.setUrl(app.getDownloadUrl());
		downloadTask.setPath(app.getDownloadPath());
		downloadManager.add(downloadTask, listener);
		downloadManager.start(downloadTask, listener); // pending when
		appmaptask.put(app, downloadTask);
		apps.add(app);
		saveDownloadApps(context, apps);
	}

	public static void startContinueDownload(Context context, App app,
			DownloadListener<Integer, DownloadTask> listener) {
		DownloadTask task = getDownloadTaskByApp(app);
		downloadManager.start(task, listener);
	}

	public static void stopDownload(Context context, App app) {
		downloadManager.stop(getDownloadTaskByApp(app),
				new DownloadListener<Integer, DownloadTask>());
	}

	public static Boolean isDowning(Context context) {
		return false;
	}

	public static DownloadTask getDownloadTaskByApp(App app) {
		for (int i = 0; i < apps.size(); i++) {
			App downloadapp = apps.get(i);
			if (downloadapp.getDownloadUrl().equals(app.getDownloadUrl()))
				return appmaptask.get(downloadapp);
		}
		return null;
	}

	// public static getDownloadAppStatus(Context context, App app) {
	// appmaptask.get(app).getStatus();
	// }

	public static int getProgressByDownload(DownloadTask task) {
		long filesize = 0;
		File file = new File(task.getPath());
		if (file.exists()) {
			filesize = file.length();
		}

		int progress = 0;
		if (task.getSize() > 0) {
			progress = (int) (filesize * 100 / task.getSize());
		}

		return progress;
	}

}
