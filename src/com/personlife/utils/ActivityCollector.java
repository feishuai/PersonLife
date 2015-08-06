package com.personlife.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

/**  
 *   
 * @author liugang  
 * @date 2015年8月6日   
 */
public class ActivityCollector {

	public static List<Activity> activities = new ArrayList<Activity>();
	public static void addActivity(Activity activity){
		activities.add(activity);
	}
	public static void removeActivity(Activity activity){
		activities.remove(activity);
	}
	public static void finishAll(){
		
		for(Activity activity : activities){
			if(!activity.isFinishing()){
				activity.finish();
			}
		}
	}
}
