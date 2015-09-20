package com.personlife.view.activity.personcenter;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.personlifep.R;
import com.loopj.android.http.RequestParams;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONObjectHttpResponseHandler;
import com.personlife.utils.ActivityCollector;
import com.personlife.utils.PersonInfoLocal;
import com.personlife.view.activity.LoginActivity;
import com.personlife.view.activity.personinfo.SetPassword;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author liugang
 * @date 2015年6月22日
 */
public class SecureActivity extends Activity {

	private Button back, setpwd;
	private TextView tv_title;
	private EditText first, second;
	private String telphone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_secure);
		telphone = getIntent().getStringExtra("telphone");
		back = (Button) findViewById(R.id.txt_left);
		back.setVisibility(View.VISIBLE);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
				finish();
			}
		});
		tv_title = (TextView) findViewById(R.id.txt_title);
		tv_title.setText("修改密码");
		first = (EditText) findViewById(R.id.firstpwdchange);
		second = (EditText) findViewById(R.id.secondpwdchange);
		setpwd = (Button) findViewById(R.id.setpwd_change);
		setpwd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String f = first.getText().toString();
				String s = second.getText().toString();
				if (!f.equals(s)) {
					Toast.makeText(getApplicationContext(), "输入不一致，请重新设置",
							Toast.LENGTH_SHORT).show();
					first.setText("");
					second.setText("");
				} else {
					RequestParams params = new RequestParams();
					params.put("phone", telphone);
					params.put("pwd", f);
					BaseAsyncHttp.postReq(getApplicationContext(),
							"/users/signup", params,
							new JSONObjectHttpResponseHandler() {

								@Override
								public void jsonSuccess(JSONObject resp) {
									// TODO Auto-generated method stub
									try {
										if (resp.get("flag").equals(1)) {
											PersonInfoLocal
													.storeLoginTelAndPass(
															SecureActivity.this,
															telphone, first
																	.getText()
																	.toString());
											onBackPressed();
											finish();

										} else {
											Toast.makeText(SecureActivity.this,
													"哎呀，失败了，再试一次吧",
													Toast.LENGTH_LONG).show();
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
									Toast.makeText(SecureActivity.this,
											"设置失败,看看网络有没有问题", Toast.LENGTH_LONG)
											.show();
								}
							});
				}
			}
		});
	}

}
