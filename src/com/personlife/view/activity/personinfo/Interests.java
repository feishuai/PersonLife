package com.personlife.view.activity.personinfo;

import java.util.HashSet;
import java.util.Set;

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
	private Button[] hobbys = new Button[12];
	private boolean[] flag = new boolean[12];
	private Set<String> set;
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
		StringBuffer sb = new StringBuffer();
		sb.append("");
		set = new HashSet<String>();		
		set=pref.getStringSet("hobby", null);
		if(set!=null){
			for (String str : set) {  
				sb.append(str+" ");
			}  
		}
		//sb里有兴趣爱好
		String temp=sb.toString();
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
		
		if(temp.contains(hobbys[0].getText().toString())){
			hobbys[0].setBackgroundResource(R.drawable.register_intest_selected1);
			flag[0]=true;
		}
		if(temp.contains(hobbys[1].getText().toString())){
			hobbys[1].setBackgroundResource(R.drawable.register_intest_selected2);
			flag[1]=true;
		}
		if(temp.contains(hobbys[2].getText().toString())){
			hobbys[2].setBackgroundResource(R.drawable.register_intest_selected3);
			flag[2]=true;
		}
		if(temp.contains(hobbys[3].getText().toString())){
			hobbys[3].setBackgroundResource(R.drawable.register_intest_selected3);
			flag[3]=true;
		}
		if(temp.contains(hobbys[4].getText().toString())){
			hobbys[4].setBackgroundResource(R.drawable.register_intest_selected1);
			flag[4]=true;
		}
		if(temp.contains(hobbys[5].getText().toString())){
			hobbys[5].setBackgroundResource(R.drawable.register_intest_selected2);
			flag[5]=true;
		}
		if(temp.contains(hobbys[6].getText().toString())){
			hobbys[6].setBackgroundResource(R.drawable.register_intest_selected2);
			flag[6]=true;
		}
		if(temp.contains(hobbys[7].getText().toString())){
			hobbys[7].setBackgroundResource(R.drawable.register_intest_selected3);
			flag[7]=true;
		}
		if(temp.contains(hobbys[8].getText().toString())){
			hobbys[8].setBackgroundResource(R.drawable.register_intest_selected1);
			flag[8]=true;
		}
		if(temp.contains(hobbys[9].getText().toString())){
			hobbys[9].setBackgroundResource(R.drawable.register_intest_selected1);
			flag[9]=true;
		}
		if(temp.contains(hobbys[10].getText().toString())){
			hobbys[10].setBackgroundResource(R.drawable.register_intest_selected3);
			flag[10]=true;
		}
		if(temp.contains(hobbys[11].getText().toString())){
			hobbys[11].setBackgroundResource(R.drawable.register_intest_selected2);
			flag[11]=true;
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
						set.add(hobbys[i].getText().toString());
					}
				}
				editor.putStringSet("hobby", set);
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
				hobbys[0].setBackgroundResource(R.drawable.register_intest_selected1);
			}else{
				hobbys[0].setBackgroundResource(R.drawable.register_intest1);
			}
			break;
		case R.id.interest_2:
			flag[1]=!flag[1];
			if(flag[1]==true){
				hobbys[1].setBackgroundResource(R.drawable.register_intest_selected2);
			}else{
				hobbys[1].setBackgroundResource(R.drawable.register_intest2);
			}
			break;
		case R.id.interest_3:
			flag[2]=!flag[2];
			if(flag[2]==true){
				hobbys[2].setBackgroundResource(R.drawable.register_intest_selected3);
			}else{
				hobbys[2].setBackgroundResource(R.drawable.register_intest3);
			}
			break;
		case R.id.interest_4:
			flag[3]=!flag[3];
			if(flag[3]==true){
				hobbys[3].setBackgroundResource(R.drawable.register_intest_selected3);
			}else{
				hobbys[3].setBackgroundResource(R.drawable.register_intest3);
			}
			break;
		case R.id.interest_5:
			flag[4]=!flag[4];
			if(flag[4]==true){
				hobbys[4].setBackgroundResource(R.drawable.register_intest_selected1);
			}else{
				hobbys[4].setBackgroundResource(R.drawable.register_intest1);
			}
			break;
		case R.id.interest_6:
			flag[5]=!flag[5];
			if(flag[5]==true){
				hobbys[5].setBackgroundResource(R.drawable.register_intest_selected2);
			}else{
				hobbys[5].setBackgroundResource(R.drawable.register_intest2);
			}
			break;
		case R.id.interest_7:
			flag[6]=!flag[6];
			if(flag[6]==true){
				hobbys[6].setBackgroundResource(R.drawable.register_intest_selected2);
			}else{
				hobbys[6].setBackgroundResource(R.drawable.register_intest2);
			}
			break;
		case R.id.interest_8:
			flag[7]=!flag[7];
			if(flag[7]==true){
				hobbys[7].setBackgroundResource(R.drawable.register_intest_selected3);
			}else{
				hobbys[7].setBackgroundResource(R.drawable.register_intest3);
			}
			break;
		case R.id.interest_9:
			flag[8]=!flag[8];
			if(flag[8]==true){
				hobbys[8].setBackgroundResource(R.drawable.register_intest_selected1);
			}else{
				hobbys[8].setBackgroundResource(R.drawable.register_intest1);
			}
			break;
		case R.id.interest_10:
			flag[9]=!flag[9];
			if(flag[9]==true){
				hobbys[9].setBackgroundResource(R.drawable.register_intest_selected1);
			}else{
				hobbys[9].setBackgroundResource(R.drawable.register_intest1);
			}
			break;
		case R.id.interest_11:
			flag[10]=!flag[10];
			if(flag[10]==true){
				hobbys[10].setBackgroundResource(R.drawable.register_intest_selected3);
			}else{
				hobbys[10].setBackgroundResource(R.drawable.register_intest3);
			}
			break;
		case R.id.interest_12:
			flag[11]=!flag[11];
			if(flag[11]==true){
				hobbys[11].setBackgroundResource(R.drawable.register_intest_selected2);
			}else{
				hobbys[11].setBackgroundResource(R.drawable.register_intest2);
			}
			break;
		default: break;
			
		}
	}
}
