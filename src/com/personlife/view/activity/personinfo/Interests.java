package com.personlife.view.activity.personinfo;

import com.example.personlifep.R;
import com.personlife.utils.ActivityCollector;

import android.app.Activity;
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
 * @date 2015年6月26日
 */
public class Interests extends Activity implements OnClickListener{

	private TextView title;
	private Button back, finish;
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;
	private StringBuffer interests = new StringBuffer();
	private Button[] hobbys = new Button[12];
	private boolean[] flag = new boolean[12];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_interests);
		ActivityCollector.addActivity(this);
		init();
	}

	public void init() {
		title = (TextView) findViewById(R.id.txt_title);
		back = (Button) findViewById(R.id.txt_left);
		finish = (Button) findViewById(R.id.txt_save);
		title.setText("兴趣爱好");
		back.setVisibility(View.VISIBLE);
		finish.setVisibility(View.VISIBLE);
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		int[] r={R.id.interest_1,R.id.interest_2,R.id.interest_3,R.id.interest_4,R.id.interest_5,
				R.id.interest_6,R.id.interest_7,R.id.interest_8,R.id.interest_9,R.id.interest_10,
				R.id.interest_11,R.id.interest_12};
		for(int i=0;i<12;i++){
			hobbys[i] = (Button) findViewById(r[i]);
			hobbys[i].setOnClickListener(this);
		}
		for(int i=0;i<12;i++){
			flag[i]=false;
		}
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		finish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				editor = pref.edit();
				for(int i=0;i<12;i++){
					if(flag[i]==true){
						interests.append(hobbys[i].getText().toString()+" ");
					}
				}
				editor.putString("hobby", interests.toString());
				editor.commit();
				finish();
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.interest_1:
			flag[0]=!flag[0];
			if(flag[0]==true){
				hobbys[0].setBackgroundColor(Color.GREEN);
			}else{
				hobbys[0].setBackgroundColor(Color.WHITE);
			}
			break;
		case R.id.interest_2:
			flag[1]=!flag[1];
			if(flag[1]==true){
				hobbys[1].setBackgroundColor(Color.GREEN);
			}else{
				hobbys[1].setBackgroundColor(Color.WHITE);
			}
			break;
		case R.id.interest_3:
			flag[2]=!flag[2];
			if(flag[2]==true){
				hobbys[2].setBackgroundColor(Color.GREEN);
			}else{
				hobbys[2].setBackgroundColor(Color.WHITE);
			}
			break;
		case R.id.interest_4:
			flag[3]=!flag[3];
			if(flag[3]==true){
				hobbys[3].setBackgroundColor(Color.GREEN);
			}else{
				hobbys[3].setBackgroundColor(Color.WHITE);
			}
			break;
		case R.id.interest_5:
			flag[4]=!flag[4];
			if(flag[4]==true){
				hobbys[4].setBackgroundColor(Color.GREEN);
			}else{
				hobbys[4].setBackgroundColor(Color.WHITE);
			}
			break;
		case R.id.interest_6:
			flag[5]=!flag[5];
			if(flag[5]==true){
				hobbys[5].setBackgroundColor(Color.GREEN);
			}else{
				hobbys[5].setBackgroundColor(Color.WHITE);
			}
			break;
		case R.id.interest_7:
			flag[6]=!flag[6];
			if(flag[6]==true){
				hobbys[6].setBackgroundColor(Color.GREEN);
			}else{
				hobbys[6].setBackgroundColor(Color.WHITE);
			}
			break;
		case R.id.interest_8:
			flag[7]=!flag[7];
			if(flag[7]==true){
				hobbys[7].setBackgroundColor(Color.GREEN);
			}else{
				hobbys[7].setBackgroundColor(Color.WHITE);
			}
			break;
		case R.id.interest_9:
			flag[8]=!flag[8];
			if(flag[8]==true){
				hobbys[8].setBackgroundColor(Color.GREEN);
			}else{
				hobbys[8].setBackgroundColor(Color.WHITE);
			}
			break;
		case R.id.interest_10:
			flag[9]=!flag[9];
			if(flag[9]==true){
				hobbys[9].setBackgroundColor(Color.GREEN);
			}else{
				hobbys[9].setBackgroundColor(Color.WHITE);
			}
			break;
		case R.id.interest_11:
			flag[10]=!flag[10];
			if(flag[10]==true){
				hobbys[10].setBackgroundColor(Color.GREEN);
			}else{
				hobbys[10].setBackgroundColor(Color.WHITE);
			}
			break;
		case R.id.interest_12:
			flag[11]=!flag[11];
			if(flag[11]==true){
				hobbys[11].setBackgroundColor(Color.GREEN);
			}else{
				hobbys[11].setBackgroundColor(Color.WHITE);
			}
			break;
		default: break;
			
		}
	}
}
