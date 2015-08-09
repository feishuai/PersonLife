package com.personlife.view.activity;


import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personlifep.R;
import com.loopj.android.http.RequestParams;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONObjectHttpResponseHandler;
import com.personlife.utils.Utils;

public class LoginActivity extends Activity implements OnClickListener {
	TextView username, password;
	TextView login, register, retrieve;
	private SharedPreferences.Editor editor;
	private SharedPreferences pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		if (pref.getString("islogin", "0").equals("1")) {
			Utils.start_Activity(LoginActivity.this, MainActivity.class);
			finish();
		} else {
			initViews();
		}

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

		switch (v.getId()) {
		case R.id.tv_login_login:
			RequestParams params = new RequestParams();
			params.put("phone", username.getText().toString());
			params.put("pwd", password.getText().toString());
			BaseAsyncHttp.postReq(getApplicationContext(),"/users/login", params,
					new JSONObjectHttpResponseHandler() {

						@Override
						public void jsonSuccess(JSONObject resp) {
							// TODO Auto-generated method stub
							try {
								if (resp.get("flag").equals(1)) {
									editor.putString("islogin", "1");
									editor.putString("telephone", username.getText().toString());									
									editor.putString("password", password.getText().toString());
									editor.commit();
									Utils.start_Activity(LoginActivity.this,MainActivity.class);
									finish();
								} else {
									Toast.makeText(LoginActivity.this,
											"密码错误或者用户名错误", Toast.LENGTH_LONG)
											.show();
								}

							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						@Override
						public void jsonFail(JSONObject resp) {
							// TODO Auto-generated method stub
							Log.i("login", resp.toString());
							Toast.makeText(LoginActivity.this,
									"fail密码错误或者用户名错误", Toast.LENGTH_LONG)
									.show();
						}
					});

			break;
		case R.id.tv_login_register:
			Utils.start_Activity(LoginActivity.this,RegisterActivity1.class);
			break;
		case R.id.tv_login_retrieve:

			break;
		default:
			System.out.println("view is clicked");
			break;
		}
	}



}
