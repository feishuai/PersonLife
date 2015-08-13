package com.personlife.net;

import java.util.List;

import android.app.Activity;
import android.content.Context;

import com.personlife.utils.ComplexPreferences;
import com.personlife.utils.Constants;

public class DownloadTaskManager {
	static List<String> downloadUrls;

	public static void save(Context context){
		if(downloadUrls!=null){
			ComplexPreferences complexPreferences = ComplexPreferences
					.getComplexPreferences(context, Constants.SharePrefrencesName, Activity.MODE_PRIVATE);
			complexPreferences.putObject("selectedApps", downloadUrls);
			complexPreferences.commit();
		}
			
	}
}
