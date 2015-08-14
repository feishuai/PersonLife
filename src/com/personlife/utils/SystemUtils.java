package com.personlife.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.personlife.bean.App;

/**
 * @author pfy
 *
 */
public class SystemUtils {
	/**
	 * @param context
	 */
	public static List<PackageInfo> getAllApps(Context context) {
		PackageManager pManager = context.getPackageManager();
		List<PackageInfo> paklist = pManager.getInstalledPackages(0);
		return paklist;
	}

	/**
	 * @param context
	 * @return
	 */
	public static List<PackageInfo> getAllAppsNoSystem(Context context) {
		List<PackageInfo> apps = new ArrayList<PackageInfo>();
		PackageManager pManager = context.getPackageManager();
		List<PackageInfo> paklist = pManager.getInstalledPackages(0);
		for (int i = 0; i < paklist.size(); i++) {
			PackageInfo pak = (PackageInfo) paklist.get(i);
			if ((pak.applicationInfo.flags & pak.applicationInfo.FLAG_SYSTEM) <= 0) {
				// customs applications
				Log.i("not system application",
						pManager.getApplicationLabel(pak.applicationInfo)
								.toString());
				apps.add(pak);
			}
		}
		return apps;
	}

	/**
	 * @param context
	 * @return
	 */
	public static List<ResolveInfo> getShareApps(Context context) {
		List<ResolveInfo> mApps = new ArrayList<ResolveInfo>();
		Intent intent = new Intent(Intent.ACTION_SEND, null);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.setType("text/plain");
		PackageManager pManager = context.getPackageManager();
		mApps = pManager.queryIntentActivities(intent,
				PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);

		return mApps;
	}

	public static List<App> getAppsNoSystom(Context context) {
		List<App> mList = new ArrayList<App>();
		List<PackageInfo> packages = SystemUtils.getAllAppsNoSystem(context);
		for (int i = 0; i < packages.size(); i++) {
			PackageInfo packageInfo = packages.get(i);
			App app = new App();
			app.setName(packageInfo.applicationInfo.loadLabel(
					context.getPackageManager()).toString());
			app.setDrawableString(DrawableStringUtils.drawableToString(packageInfo.applicationInfo.loadIcon(context
					.getPackageManager())));
			app.setPackageName(packageInfo.packageName);
			mList.add(app);
		}
		return mList;
	}

	public static void startApp(Context context, String packageName) {
		if (packageName == null || packageName == "") {
			Toast.makeText(context, "这个应用程序无法正常启动", Toast.LENGTH_SHORT).show();
			return;
		}
		Intent i = context.getPackageManager().getLaunchIntentForPackage(
				packageName);
		// 如果该程序不可启动（像系统自带的包，有很多是没有入口的）会返回NULL
		if (i != null)
			context.startActivity(i);
		else
			Toast.makeText(context, "这个应用程序无法启动", Toast.LENGTH_SHORT).show();
	}

	void openAppFronUri(Context context ,String uri) {
		// TODO Auto-generated method stub
		Log.e("OpenFile", uri);
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(uri)),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
	}
}
