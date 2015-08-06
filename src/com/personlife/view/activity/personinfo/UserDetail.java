package com.personlife.view.activity.personinfo;

import com.example.personlifep.R;
import com.personlife.utils.ActivityCollector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

//好友详情
public class UserDetail extends Activity implements OnClickListener {
	private TextView txt_title, tv_name, tv_accout;
	private Button back;
	private String Name, UserId;
	private Button btn_sendmsg;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friendmsg);
		ActivityCollector.addActivity(this);
		initview();
		setListener();
	}

	
	protected void initview() {
		txt_title = (TextView) findViewById(R.id.txt_title);
		txt_title.setText("详细资料");
		back = (Button) findViewById(R.id.txt_left);
		back.setVisibility(View.VISIBLE);
		btn_sendmsg = (Button) findViewById(R.id.btn_sendmsg);
		btn_sendmsg.setTag("1");
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_accout = (TextView) findViewById(R.id.tv_accout);
	}

	protected void initData() {
//		UserId = getIntent().getStringExtra("friendid");
//		Name = getIntent().getStringExtra("nickname");
//		if (TextUtils.isEmpty(UserId))
//			finish();
//		else {
//			User user = GloableParams.Users.get(UserId);
//			tv_name.setText(user.getUserName());
//			tv_accout.setText("微信号：" + UserId);
//		}
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
//			if ("1".equals(v.getTag().toString())) {
//				Intent intent = new Intent(this, ChatActivity.class);
//				intent.putExtra(Constants.NAME, Name);
//				intent.putExtra(Constants.TYPE, ChatActivity.CHATTYPE_SINGLE);
//				intent.putExtra(Constants.User_ID, UserId);
//				startActivity(intent);
//				overridePendingTransition(R.anim.push_left_in,
//						R.anim.push_left_out);
//			} else {
//				// TODO 添加好友
//			}
			break;
		default:
			break;
		}
	}

}
