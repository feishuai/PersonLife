package com.personlife.net;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.personlife.bean.App;

public class DataHelper {
	public static List<String> getAllkind(Context context) {
		final List<String> kinds = new ArrayList<String>();
		new AsyncHttpClient().post("/app/allkind", new JsonHttpResponseHandler());
		BaseAsyncHttp.postReq(context, "/app/allkind", null,
				new JSONArrayHttpResponseHandler() {

					@Override
					public void jsonSuccess(JSONArray resp) {
						// TODO Auto-generated method stub
						for (int i = 0; i < resp.length(); i++) {
							try {
								kinds.add(resp.getJSONObject(i).getString(
										"kind"));
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}

					@Override
					public void jsonFail(JSONArray resp) {
						// TODO Auto-generated method stub
						return;
					}
				});
		return kinds;
	}

	public static List<App> getAppsByKind(Context context, String kind) {
		List<App> apps = new ArrayList<App>();
		RequestParams params = new RequestParams();
		params.add("kind", kind);
		BaseAsyncHttp.postReq(context, "/app/kind", params,
				new JSONObjectHttpResponseHandler() {

					@Override
					public void jsonSuccess(JSONObject resp) {
						// TODO Auto-generated method stub
						try {
							JSONArray jsonapps = resp.getJSONArray("item");
							for (int i = 0; i < jsonapps.length(); i++) {
								App app = new App();
								JSONObject jsonapp = jsonapps.getJSONObject(i);
								
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void jsonFail(JSONObject resp) {
						// TODO Auto-generated method stub

					}
				});
		return apps;
	}
}
