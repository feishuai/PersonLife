package com.personlife.view.activity.personinfo;

import com.example.personlifep.R;
import com.loopj.android.http.PersistentCookieStore;
import com.personlife.utils.ActivityCollector;
import com.personlife.utils.PersonInfoLocal;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author liugang
 * @date 2015年6月24日
 */
public class UserSex extends Activity {

	private TextView tv_title;
	private Button t_left;
	private ImageView man_duihao;
	private ImageView woman_duihao;
	private String telphone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_sex);
		ActivityCollector.addActivity(this);
		Intent intent = getIntent();
		telphone = intent.getStringExtra("telphone");
		init();
	}

	public void init() {
		tv_title = (TextView) findViewById(R.id.txt_title);
		t_left = (Button) findViewById(R.id.t_left);
		man_duihao = (ImageView) findViewById(R.id.man_duihao);
		woman_duihao = (ImageView) findViewById(R.id.woman_duihao);
		tv_title.setText("性别");
		t_left.setVisibility(View.VISIBLE);

		if (PersonInfoLocal.getSex(this, telphone).equals("男")) {
			man_duihao.setVisibility(View.VISIBLE);
			woman_duihao.setVisibility(View.GONE);
		} else {
			woman_duihao.setVisibility(View.VISIBLE);
			man_duihao.setVisibility(View.GONE);
		}

		t_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
	}

	public void onclickSex(View view) {
		switch (view.getId()) {
		case R.id.man:
			man_duihao.setVisibility(View.VISIBLE);
			woman_duihao.setVisibility(View.GONE);
			// 添加保存性别的代码
			PersonInfoLocal.storeSex(this, telphone, "男");
			finish();
			break;
		case R.id.woman:
			woman_duihao.setVisibility(View.VISIBLE);
			man_duihao.setVisibility(View.GONE);
			// 添加保存性别的代码
			PersonInfoLocal.storeSex(this, telphone, "女");
			finish();
			break;
		}
	}
}
