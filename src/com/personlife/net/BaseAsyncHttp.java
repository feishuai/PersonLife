package com.personlife.net;

import org.json.JSONArray;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class BaseAsyncHttp {
	
    public static final String HOST = "http://10.10.105.42:8080/v1";
   
//	public static final String HOST = "http://10.10.105.180:9000/v1";
    private static AsyncHttpClient client = new AsyncHttpClient();
    
    public static void postReq(String host, String url, RequestParams params, JsonHttpResponseHandler hander) {
    	client.post(host + url, params, hander);
    }
    public static void postReq(String url, RequestParams params, JsonHttpResponseHandler hander) {
        Log.i("personlife", HOST + url);
        client.post(HOST + url, params, hander);
    }

    public static void getReq(String host, String url, RequestParams params, JsonHttpResponseHandler hander) {
    	client.get(host + url, params, hander);
    }

    public static void getReq(String url, RequestParams params, JsonHttpResponseHandler hander) {
        Log.i("personlife", HOST + url);
        client.get(HOST + url, params, hander);
    }
    
    public static void downloadFile(String url,FileDownloadHandler handler){
        client.get(url,handler);
    }
}
