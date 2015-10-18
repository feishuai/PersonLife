package com.personlife.net;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.personlife.utils.Utils;

public class BaseAsyncHttp {

	public static final String HOST = "http://120.26.218.252:8080/v1";

	// public static final String HOST = "http://10.10.105.180:9000/v1";
	// private static AsyncHttpClient client = new AsyncHttpClient();

	public static void postReq(Context context, String host, String url,
			RequestParams params, JsonHttpResponseHandler hander) {
		AsyncHttpClient client = new AsyncHttpClient();
		client.post(host + url, params, hander);
	}

	public static void postReq(Context context, String url,
			RequestParams params, JsonHttpResponseHandler hander) {
		AsyncHttpClient client = new AsyncHttpClient();
		if (Utils.isNetworkAvailable(context))
			client.post(HOST + url, params, hander);
		else
			Utils.showLongToast(context, "请连接网络");
	}

	public static void getReq(Context context, String host, String url,
			RequestParams params, JsonHttpResponseHandler hander) {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(host + url, params, hander);
	}

	public static void getReq(Context context, String url,
			RequestParams params, JsonHttpResponseHandler hander) {
		AsyncHttpClient client = new AsyncHttpClient();
		if (Utils.isNetworkAvailable(context))
			client.get(HOST + url, params, hander);
		else
			Utils.showLongToast(context, "请连接网络");
	}

	public static void downloadFile(Context context, String url,
			FileDownloadHandler handler) {
		AsyncHttpClient client = new AsyncHttpClient();
		if (Utils.isNetworkAvailable(context))
			client.get(url, handler);
		else
			Utils.showLongToast(context, "请连接网络");
	}
}
