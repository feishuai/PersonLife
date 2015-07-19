package com.personlife.view.activity;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.personlifep.R;
import com.loopj.android.http.*;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONObjectHttpResponseHandler;
import com.personlife.utils.Utils;

public class LoginActivity extends Activity implements OnClickListener {
	TextView username, password;
	TextView login, register, retrieve;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initViews();
	}

	private void initViews() {
		username = (TextView) findViewById(R.id.et_login_username);
		password = (TextView) findViewById(R.id.et_login_password);
		login = (TextView) findViewById(R.id.tv_login_login);
		register = (TextView) findViewById(R.id.tv_login_register);
		retrieve = (TextView) findViewById(R.id.tv_login_retrieve);
		login.setOnClickListener(this);
		register.setOnClickListener(this);
		retrieve.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		System.out.println(v.getId());
		switch (v.getId()) {
		case R.id.tv_login_login:
			System.out.println("start");
			RequestParams params = new RequestParams();
			params.put("phone", "1");
			params.put("pwd", "123456");

			BaseAsyncHttp.postReq("/users/login", params,
					new JSONObjectHttpResponseHandler() {

						@Override
						public void jsonSuccess(JSONObject resp) {
							// TODO Auto-generated method stub
							Log.i("login", resp.toString());
						}

						@Override
						public void jsonFail(JSONObject resp) {
							// TODO Auto-generated method stub
//							Log.i("login", resp.toString());
						}
					});
			Utils.start_Activity(LoginActivity.this, MainActivity.class, null);
			break;
		case R.id.tv_login_register:

			break;
		case R.id.tv_login_retrieve:

			break;
		default:
			System.out.println("view is clicked");
			break;
		}
	}


}
