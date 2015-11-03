package com.personlife.view.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.example.personlifep.R;
import com.loopj.android.http.RequestParams;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONObjectHttpResponseHandler;
import com.personlife.utils.ActivityCollector;
import com.personlife.utils.ImageLoaderUtils;
import com.personlife.utils.PersonInfoLocal;
import com.personlife.utils.Utils;
import com.umeng.analytics.MobclickAgent;

public class LoginActivity extends Activity implements OnClickListener {
	TextView username, password;
	TextView login, register, retrieve;
	// ImageButton weibologin;
	private SharedPreferences.Editor editor;
	private SharedPreferences pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		ImageLoaderUtils.InitConfig(getApplicationContext());
		ActivityCollector.addActivity(this);
		editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		if (pref.getString("islogin", "0").equals("1")) {
			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			intent.putExtra("telphone", pref.getString("telphone", ""));
			startActivity(intent);
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
		// weibologin = (ImageButton) findViewById(R.id.weibologin);
		login.setOnClickListener(this);
		register.setOnClickListener(this);
		retrieve.setOnClickListener(this);
		// weibologin.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.tv_login_login:
			// Utils.start_Activity(LoginActivity.this, MainActivity.class,
			// null);
			String name = username.getText().toString();
			String pwd = password.getText().toString();
			if (name.equals("") || pwd.equals("")) {
				Utils.showShortToast(getApplicationContext(), "用户名或密码不能为空！");
				return ;
			}
			final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
			pd.setCanceledOnTouchOutside(false);
			pd.setMessage("正在加载");
			pd.show();

			RequestParams params = new RequestParams();
			params.put("phone", name);
			params.put("pwd", pwd);
			BaseAsyncHttp.postReq(getApplicationContext(), "/users/login",
					params, new JSONObjectHttpResponseHandler() {

						@Override
						public void jsonSuccess(JSONObject resp) {
							// TODO Auto-generated method stub
							try {
								if (resp.get("flag").equals(1)) {
									editor.putString("islogin", "1");
									editor.putString("telphone", username
											.getText().toString());
									editor.commit();
									PersonInfoLocal.storeLoginTelAndPass(
											LoginActivity.this, username
													.getText().toString(),
											password.getText().toString());
									Intent intent = new Intent(
											LoginActivity.this,
											MainActivity.class);
									intent.putExtra("telphone", username
											.getText().toString());
									startActivity(intent);
									pd.dismiss();
									finish();
								} else {
									pd.dismiss();
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
							if (resp != null)
								Log.i("login", resp.toString());
							Toast.makeText(LoginActivity.this,
									"fail密码错误或者用户名错误", Toast.LENGTH_LONG)
									.show();
						}
					});

			break;
		case R.id.tv_login_register:

			Intent intent = new Intent(LoginActivity.this,
					RegisterActivity1.class);
			intent.putExtra("title", "注册");
			startActivity(intent);

			break;
		case R.id.tv_login_retrieve:
			Intent intentfind = new Intent(LoginActivity.this,
					RegisterActivity1.class);
			intentfind.putExtra("title", "找回密码");
			startActivity(intentfind);
			break;
		// case R.id.weibologin:
		// break;
		default:
			System.out.println("view is clicked");
			break;
		}
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

}
