package com.personlife.utils;

import java.io.File;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.util.Auth;

/**
 * 
 * @author liugang
 * @date 2015年8月8日
 */
public class UpLoadHeadImage {
	private static String result=null;

	public static String uploadImg(String telphone) {
		UploadManager uploadManager = new UploadManager();
		File data = new File(Environment.getExternalStorageDirectory(),
				telphone+".jpg");
		String key = null;
		String accessKey = "6dnAU0jREe7QO0nD1ujr6CizVZ87HGhivNS1W9hR";
		String secretKey = "RYuzaeIJDvFb8KOa9OSlsmlVs7j9A6oFbzwjh9Z0";
		Auth auth = Auth.create(accessKey, secretKey);
		String bucketName = "customizelife";
		String token = auth.uploadToken(bucketName);

		uploadManager.put(data, key, token, new UpCompletionHandler() {

			@Override
			public void complete(String arg0, ResponseInfo arg1, JSONObject arg2) {
				// TODO Auto-generated method stub
				try {
					result = arg2.getString("key").toString();
					Log.i("keyd zhiaagandghl", result);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, null);
		return result;
	}
}
