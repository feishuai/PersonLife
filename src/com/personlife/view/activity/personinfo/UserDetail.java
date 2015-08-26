package com.personlife.view.activity.personinfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.personlifep.R;
import com.loopj.android.http.RequestParams;
import com.personlife.adapter.ContactAdapter;
import com.personlife.adapter.UserAdapter;
import com.personlife.bean.User;
import com.personlife.bean.UserFriend;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONObjectHttpResponseHandler;
import com.personlife.utils.ActivityCollector;
import com.personlife.utils.FriendsUtils;
import com.personlife.utils.ImageLoaderUtils;
import com.personlife.view.activity.personcenter.SearchUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//好友详情
public class UserDetail extends Activity implements OnClickListener {
	private TextView txt_title, tv_name, tv_region, tv_sign;
	private Button back;
	private String UserPhone;
	private Button btn_sendmsg;
	private ImageView sex, head;
	private String phone;
	private String mytelphone;
	private String where;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friendmsg);
		ActivityCollector.addActivity(this);
		Intent intent = getIntent();
		phone = intent.getStringExtra("phone");
		mytelphone = intent.getStringExtra("mytelphone");
		where = intent.getStringExtra("fromwhere");
		if (where.equals("search")) {
			initview();
			setListener();

		}
		if (where.equals("friend")) {
			initviewfr();
			setListener();
			initData();
		}

	}

	protected void initview() {
		tv_region = (TextView) findViewById(R.id.tv_region);
		tv_region.setText(getIntent().getStringExtra("area"));
		tv_sign = (TextView) findViewById(R.id.tv_sign);
		tv_sign.setText(getIntent().getStringExtra("signature"));
		sex = (ImageView) findViewById(R.id.iv_sex);
		if (getIntent().getStringExtra("gender").equals("男"))
			sex.setImageResource(R.drawable.ic_sex_male);
		else
			sex.setImageResource(R.drawable.ic_sex_female);
		head = (ImageView) findViewById(R.id.iv_avatar);
		ImageLoaderUtils.displayAppIcon(getIntent().getStringExtra("thumb"),
				head);
		txt_title = (TextView) findViewById(R.id.txt_title);
		txt_title.setText("详细资料");
		back = (Button) findViewById(R.id.txt_left);
		back.setVisibility(View.VISIBLE);

		btn_sendmsg = (Button) findViewById(R.id.btn_sendmsg);
		btn_sendmsg.setTag("1");
		if (getIntent().getIntExtra("isFriend", 0) == 1) {
			btn_sendmsg.setText("查看Ta的主页");
		} else
			btn_sendmsg.setText("添加好友");
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_name.setText(getIntent().getStringExtra("nickname"));
	}

	protected void initviewfr() {
		tv_region = (TextView) findViewById(R.id.tv_region);

		tv_sign = (TextView) findViewById(R.id.tv_sign);

		sex = (ImageView) findViewById(R.id.iv_sex);

		head = (ImageView) findViewById(R.id.iv_avatar);

		txt_title = (TextView) findViewById(R.id.txt_title);
		txt_title.setText("详细资料");
		back = (Button) findViewById(R.id.txt_left);
		back.setVisibility(View.VISIBLE);

		btn_sendmsg = (Button) findViewById(R.id.btn_sendmsg);
		btn_sendmsg.setTag("1");
		btn_sendmsg.setText("查看主页");
		btn_sendmsg.setVisibility(View.GONE);
		tv_name = (TextView) findViewById(R.id.tv_name);

	}

	protected void initData() {

		RequestParams request = new RequestParams();
		request.put("phone", phone);

		BaseAsyncHttp.postReq(getApplicationContext(), "/users/getinfo",
				request, new JSONObjectHttpResponseHandler() {

					@Override
					public void jsonSuccess(JSONObject resp) {
						// TODO Auto-generated method stub
						try {

							tv_name.setText(resp.getString("nickname")
									.toString());
							if (resp.get("gender").toString().equals("男"))
								sex.setImageResource(R.drawable.ic_sex_male);
							else
								sex.setImageResource(R.drawable.ic_sex_female);
							tv_region
									.setText(resp.getString("area").toString());
							tv_sign.setText(resp.getString("signature")
									.toString());
							ImageLoaderUtils.displayAppIcon(
									resp.getString("thumb"), head);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void jsonFail(JSONObject resp) {
						// TODO Auto-generated method stub
						Toast.makeText(UserDetail.this, "fail",
								Toast.LENGTH_SHORT);
					}
				});

	}

	protected void setListener() {
		back.setOnClickListener(this);
		btn_sendmsg.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.txt_left:
			finish();
			break;
		case R.id.btn_sendmsg:
			RequestParams request = new RequestParams();
			request.put("myphone", mytelphone);
			request.put("fphone", phone);
			BaseAsyncHttp.postReq(getApplicationContext(), "/friend/requestadd",
					request, new JSONObjectHttpResponseHandler() {

						@Override
						public void jsonSuccess(JSONObject resp) {
							// TODO Auto-generated method stub
							Toast.makeText(UserDetail.this, "发送好友请求成功", Toast.LENGTH_SHORT).show();

						}

						@Override
						public void jsonFail(JSONObject resp) {
							// TODO Auto-generated method stub

						}
					});

			break;
		default:
			break;
		}
	}

}
