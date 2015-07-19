package com.personlife.net;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.personlife.utils.Utils;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Jim on 2015/2/3.
 */
public abstract class JSONArrayHttpResponseHandler extends JsonHttpResponseHandler {
	
	@Override
	public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
		// TODO Auto-generated method stub
		super.onSuccess(statusCode, headers, response);
		Log.i("personlife JSONArray", response.toString());
		jsonSuccess(response);
	}
    public abstract void jsonSuccess(JSONArray resp);
    public abstract void jsonFail(JSONArray resp);
    @Override
    public void onFailure(int statusCode, Header[] headers,
    		Throwable throwable, JSONArray errorResponse) {
    	// TODO Auto-generated method stub
    	super.onFailure(statusCode, headers, throwable, errorResponse);
    	Log.i("personlife JSONArray", "statusCode:"+statusCode);
    	jsonFail(errorResponse);
    }
}
