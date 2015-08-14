package com.personlife.view.activity;

import java.util.HashSet;
import java.util.Set;

import com.example.personlifep.R;
import com.personlife.common.Utils;
import com.personlife.utils.ActivityCollector;
import com.personlife.utils.PersonInfoLocal;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 
 * @author liugang
 * @date 2015年8月8日
 */
public class RegisterActivity3 extends Activity implements OnClickListener {

	private Button back, nextstep;
	private TextView tv_title;
	private Button[] re_interests = new Button[12];
	private boolean[] flag = new boolean[12];
	private String telphone;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register3);
		ActivityCollector.addActivity(this);
		Intent intent=getIntent();
		telphone = intent.getStringExtra("telphone");
		init();
	}

	public void init() {
		back = (Button) findViewById(R.id.txt_left);
		back.setVisibility(View.VISIBLE);
		back.setOnClickListener(this);
		tv_title = (TextView) findViewById(R.id.txt_title);
		tv_title.setText("完善个人信息");				
		nextstep = (Button) findViewById(R.id.register3_nextstep);
		nextstep.setOnClickListener(this);
		int[] r = { R.id.re_interest_1, R.id.re_interest_2, R.id.re_interest_3,
				R.id.re_interest_4, R.id.re_interest_5, R.id.re_interest_6,
				R.id.re_interest_7, R.id.re_interest_8, R.id.re_interest_9,
				R.id.re_interest_10, R.id.re_interest_11, R.id.re_interest_12 };
		for (int i = 0; i < 12; i++) {
			re_interests[i] = (Button) findViewById(r[i]);
			re_interests[i].setOnClickListener(this);
		}
		for (int i = 0; i < 12; i++) {
			flag[i] = false;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txt_left:
			onBackPressed();
			break;
		case R.id.re_interest_1:
			flag[0] = !flag[0];
			if (flag[0] == true) {
				re_interests[0].setBackgroundResource(R.drawable.register_intest_selected1);
			} else {
				re_interests[0].setBackgroundResource(R.drawable.register_intest1);
			}
			break;
		case R.id.re_interest_2:
			flag[1] = !flag[1];
			if (flag[1] == true) {
				re_interests[1].setBackgroundResource(R.drawable.register_intest_selected2);
			} else {
				re_interests[1].setBackgroundResource(R.drawable.register_intest2);
			}
			break;
		case R.id.re_interest_3:
			flag[2] = !flag[2];
			if (flag[2] == true) {
				re_interests[2].setBackgroundResource(R.drawable.register_intest_selected3);
			} else {
				re_interests[2].setBackgroundResource(R.drawable.register_intest3);
			}
			break;
		case R.id.re_interest_4:
			flag[3] = !flag[3];
			if (flag[3] == true) {
				re_interests[3].setBackgroundResource(R.drawable.register_intest_selected3);
			} else {
				re_interests[3].setBackgroundResource(R.drawable.register_intest3);
			}
			break;
		case R.id.re_interest_5:
			flag[4] = !flag[4];
			if (flag[4] == true) {
				re_interests[4].setBackgroundResource(R.drawable.register_intest_selected1);
			} else {
				re_interests[4].setBackgroundResource(R.drawable.register_intest1);
			}
			break;
		case R.id.re_interest_6:
			flag[5] = !flag[5];
			if (flag[5] == true) {
				re_interests[5].setBackgroundResource(R.drawable.register_intest_selected2);
			} else {
				re_interests[5].setBackgroundResource(R.drawable.register_intest2);
			}
			break;
		case R.id.re_interest_7:
			flag[6] = !flag[6];
			if (flag[6] == true) {
				re_interests[6].setBackgroundResource(R.drawable.register_intest_selected2);
			} else {
				re_interests[6].setBackgroundResource(R.drawable.register_intest2);
			}
			break;
		case R.id.re_interest_8:
			flag[7] = !flag[7];
			if (flag[7] == true) {
				re_interests[7].setBackgroundResource(R.drawable.register_intest_selected3);
			} else {
				re_interests[7].setBackgroundResource(R.drawable.register_intest3);
			}
			break;
		case R.id.re_interest_9:
			flag[8] = !flag[8];
			if (flag[8] == true) {
				re_interests[8].setBackgroundResource(R.drawable.register_intest_selected1);
			} else {
				re_interests[8].setBackgroundResource(R.drawable.register_intest1);
			}
			break;
		case R.id.re_interest_10:
			flag[9] = !flag[9];
			if (flag[9] == true) {
				re_interests[9].setBackgroundResource(R.drawable.register_intest_selected1);
			} else {
				re_interests[9].setBackgroundResource(R.drawable.register_intest1);
			}
			break;
		case R.id.re_interest_11:
			flag[10] = !flag[10];
			if (flag[10] == true) {
				re_interests[10].setBackgroundResource(R.drawable.register_intest_selected3);
			} else {
				re_interests[10].setBackgroundResource(R.drawable.register_intest3);
			}
			break;
		case R.id.re_interest_12:
			flag[11] = !flag[11];
			if (flag[11] == true) {
				re_interests[11].setBackgroundResource(R.drawable.register_intest_selected2);
			} else {
				re_interests[11].setBackgroundResource(R.drawable.register_intest2);
			}
			break;
		case R.id.register3_nextstep:
			Set<String> set=new HashSet<String>();
			for(int i=0;i<12;i++){
				if(flag[i]==true){
					set.add(re_interests[i].getText().toString());
				}
			}
			PersonInfoLocal.storeRegisterHobbys(this, telphone, set);
			Intent intent=new Intent(this, RegisterActivity4.class);
			intent.putExtra("telphone", telphone);
			 startActivity(intent);
			break;
		}
		
			
	}
}
