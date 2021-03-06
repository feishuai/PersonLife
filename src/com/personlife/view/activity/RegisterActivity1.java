package com.personlife.view.activity;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.example.personlifep.R;
import com.loopj.android.http.RequestParams;
import com.personlife.common.DES;
import com.personlife.common.Utils;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONObjectHttpResponseHandler;
import com.personlife.utils.ActivityCollector;
import com.personlife.utils.Constants;
import com.personlife.utils.DownloadHeadImg;
import com.personlife.utils.PersonInfoLocal;

//注册
public class RegisterActivity1 extends Activity implements OnClickListener {
	private TextView txt_title;
	private Button btn_nextstep, btn_send, back;
	private EditText et_usertel, et_code;
	private MyCount mc;
	private String title;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_register1);
		super.onCreate(savedInstanceState);
		ActivityCollector.addActivity(this);
		title = getIntent().getStringExtra("title");
		initControl();
		setListener();
	}

	protected void initControl() {
		txt_title = (TextView) findViewById(R.id.txt_title);
		txt_title.setText(title);
		back = (Button) findViewById(R.id.txt_left);
		back.setVisibility(View.VISIBLE);
		btn_send = (Button) findViewById(R.id.btn_send);
		btn_send.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.btn_enable_green));
		btn_send.setTextColor(0xFFD0EFC6);
		btn_send.setEnabled(false);
		btn_nextstep = (Button) findViewById(R.id.btn_nextstep);
		btn_nextstep.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.btn_enable_green));
		btn_nextstep.setTextColor(0xFFD0EFC6);
		btn_nextstep.setEnabled(false);
		et_usertel = (EditText) findViewById(R.id.et_usertel);
		et_code = (EditText) findViewById(R.id.et_code);
	}

	protected void setListener() {
		back.setOnClickListener(this);
		btn_send.setOnClickListener(this);
		btn_nextstep.setOnClickListener(this);
		et_usertel.addTextChangedListener(new TelTextChange());
		et_code.addTextChangedListener(new TextChange());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.txt_left:
			onBackPressed();
			finish();
			break;
		case R.id.btn_send:
			if (mc == null) {
				mc = new MyCount(60000, 1000); // 第一参数是总的时间，第二个是间隔时间
			}
			mc.start();
			getCode();
			break;
		case R.id.btn_nextstep:
			// getRegister();

			// Intent intent = new
			// Intent(RegisterActivity1.this,RegisterActivity2.class);
			// intent.putExtra("telphone", et_usertel.getText().toString());
			// startActivity(intent);
			// finish();

			String telphone = et_usertel.getText().toString();
			String code = et_code.getText().toString().trim();
			RequestParams request = new RequestParams();
			request.put("phone", telphone);
			request.put("num", code);
			BaseAsyncHttp.postReq(getApplicationContext(), "/users/verify",
					request, new JSONObjectHttpResponseHandler() {

						@Override
						public void jsonSuccess(JSONObject resp) {
							try {
								if (resp.getString("flag").equals("1")) {
									PersonInfoLocal.storeRegisterTel(
											RegisterActivity1.this, et_usertel
													.getText().toString());
									skipToNext();

								} else {
									Toast.makeText(RegisterActivity1.this,
											"验证码错误", Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						@Override
						public void jsonFail(JSONObject resp) {
							// TODO Auto-generated method stub
							Toast.makeText(RegisterActivity1.this, "网络问题",
									Toast.LENGTH_SHORT).show();
						}
					});

			break;
		default:
			break;
		}
	}

	private void skipToNext() {
		if (title.equals("找回密码")) {
			Intent intent = new Intent(RegisterActivity1.this,
					FindCodeActivity.class);
			intent.putExtra("telphone", et_usertel.getText().toString());
			startActivity(intent);
		} else {
			Intent intent = new Intent(RegisterActivity1.this,
					RegisterActivity2.class);
			intent.putExtra("telphone", et_usertel.getText().toString());
			startActivity(intent);
		}
	}

	private void getCode() {
		if (title.equals("注册")) {
			String telphone = et_usertel.getText().toString();
			RequestParams request = new RequestParams();
			request.put("phone", telphone);
			request.put("find", 0);
			BaseAsyncHttp.postReq(getApplicationContext(), "/users/send",
					request, new JSONObjectHttpResponseHandler() {

						@Override
						public void jsonSuccess(JSONObject resp) {
							try {
								if (resp.getString("flag").equals("0")) {
									Toast.makeText(RegisterActivity1.this,
											"您已注册过", Toast.LENGTH_SHORT).show();
								} else {
									Toast.makeText(RegisterActivity1.this,
											"发送成功", Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						@Override
						public void jsonFail(JSONObject resp) {
							// TODO Auto-generated method stub
							Toast.makeText(RegisterActivity1.this, "网络发送失败",
									Toast.LENGTH_SHORT).show();
						}
					});
		} else {
			String telphone = et_usertel.getText().toString();
			RequestParams request = new RequestParams();
			request.put("phone", telphone);
			request.put("find", 1);
			BaseAsyncHttp.postReq(getApplicationContext(), "/users/send",
					request, new JSONObjectHttpResponseHandler() {
						@Override
						public void jsonSuccess(JSONObject resp) {
							try {
								if (resp.getString("flag").equals("0")) {
									Toast.makeText(RegisterActivity1.this,
											"获取验证码失败", Toast.LENGTH_SHORT)
											.show();
								} else {
									Toast.makeText(RegisterActivity1.this,
											"发送成功", Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						@Override
						public void jsonFail(JSONObject resp) {
							// TODO Auto-generated method stub
							Toast.makeText(RegisterActivity1.this, "网络发送失败",
									Toast.LENGTH_SHORT).show();
						}
					});
		}
	}

	// 手机号 EditText监听器
	class TelTextChange implements TextWatcher {

		@Override
		public void afterTextChanged(Editable arg0) {

		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {

		}

		@Override
		public void onTextChanged(CharSequence cs, int start, int before,
				int count) {
			String phone = et_usertel.getText().toString();
			if (phone.length() == 11) {
				if (Utils.isMobileNO(phone)) {
					btn_send.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.btn_bg_green));
					btn_send.setTextColor(0xFFFFFFFF);
					btn_send.setEnabled(true);

				} else {
					et_usertel.requestFocus();
					Utils.showLongToast(getApplicationContext(), "请输入正确的手机号码！");
				}
			} else {
				btn_send.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.btn_enable_green));
				btn_send.setTextColor(0xFFD0EFC6);
				btn_send.setEnabled(false);
			}
		}
	}

	// EditText监听器
	class TextChange implements TextWatcher {

		@Override
		public void afterTextChanged(Editable arg0) {

		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {

		}

		@Override
		public void onTextChanged(CharSequence cs, int start, int before,
				int count) {
			boolean Sign1 = et_code.getText().length() == 4;
			boolean Sign2 = et_usertel.getText().length() > 0;

			if (Sign1 & Sign2) {
				btn_nextstep.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.btn_enable_green));
				btn_nextstep.setTextColor(0xFFFFFFFF);
				btn_nextstep.setEnabled(true);
			} else {
				btn_nextstep.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.btn_enable_green));
				btn_nextstep.setTextColor(0xFFD0EFC6);
				btn_nextstep.setEnabled(false);
			}
		}
	}

	/* 定义一个倒计时的内部类 */
	private class MyCount extends CountDownTimer {
		public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			btn_send.setEnabled(true);
			btn_send.setText("发送验证码");
		}

		@Override
		public void onTick(long millisUntilFinished) {
			btn_send.setEnabled(false);
			btn_send.setText("(" + millisUntilFinished / 1000 + ")秒");
			btn_send.setEnabled(false);
		}
	}

	// private void initUserList() {
	// Intent intent = new Intent(RegisterActivity1.this, MainActivity.class);
	// startActivity(intent);
	// overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
	// finish();
	// }
}
