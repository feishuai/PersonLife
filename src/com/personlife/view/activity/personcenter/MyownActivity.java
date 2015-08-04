package com.personlife.view.activity.personcenter;

import org.apache.http.message.BasicNameValuePair;

import com.example.personlifep.R;
import com.personlife.bean.UserInfo;
import com.personlife.utils.Utils;
import com.personlife.view.activity.personinfo.AreaSetting;
import com.personlife.view.activity.personinfo.Interests;
import com.personlife.view.activity.personinfo.NickName;
import com.personlife.view.activity.personinfo.PersonalSign;
import com.personlife.view.activity.personinfo.Profession;
import com.personlife.view.activity.personinfo.UserSex;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**  
 *   
 * @author liugang  
 * @date 2015年6月22日   
 */
public class MyownActivity extends Activity{

	private Button tv_back;
	private TextView tv_title,nickname,sex,area,profession,interests,sign;
	private UserInfo userInfo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myownactivity);
		init();
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		init();
	}

	public void init(){
		nickname=(TextView) findViewById(R.id.pernicheng);
		sex=(TextView) findViewById(R.id.persex);
		area=(TextView) findViewById(R.id.perarea);
		profession=(TextView) findViewById(R.id.perzhiye);
		interests=(TextView) findViewById(R.id.personinteresting);
		sign=(TextView) findViewById(R.id.personsign);
		tv_back=(Button) findViewById(R.id.txt_left);
		tv_back.setVisibility(View.VISIBLE);
		tv_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		tv_title=(TextView) findViewById(R.id.txt_title);
		tv_title.setText("个人信息");
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		
		nickname.setText(pref.getString("userName", "用户名"));
		sex.setText(pref.getString("sex", "男"));
		area.setText(pref.getString("location", "地区"));
		profession.setText(pref.getString("job", "职业"));
		interests.setText(pref.getString("hobby", "兴趣"));
		sign.setText(pref.getString("signature", "个性签名"));
	}
	
	
	public void onclickPersonInfo(View view){
		switch(view.getId()){
		case R.id.person_touxiang :
			Utils.start_Activity(this, MyownActivity.class);
			break;
		case R.id.person_nicheng :
			Utils.start_Activity(this, NickName.class);			
			break;
		case R.id.person_sex :
			Utils.start_Activity(this, UserSex.class);
			break;
		case R.id.person_area :
			Utils.start_Activity(this, AreaSetting.class);
			break;
		case R.id.person_zhiye :
			Utils.start_Activity(this, Profession.class);
			break;
		case R.id.person_interesting :
			Utils.start_Activity(this, Interests.class);
			break;
		case R.id.person_sign :
			Utils.start_Activity(this, PersonalSign.class);
			break;
		}
	}

}
