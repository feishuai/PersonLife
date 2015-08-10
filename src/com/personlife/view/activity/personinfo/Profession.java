package com.personlife.view.activity.personinfo;

import com.example.personlifep.R;
import com.personlife.utils.ActivityCollector;
import com.personlife.utils.PersonInfoLocal;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Selection;
import android.text.Spannable;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**  
 *   
 * @author liugang  
 * @date 2015年6月26日   
 */
public class Profession extends Activity implements OnClickListener{

	private TextView title,work;
	private Button back,finish;
	private Button[] jobs = new Button[9];

	private String telphone;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_profession);
		ActivityCollector.addActivity(this);
		Intent intent=getIntent();
		telphone=intent.getStringExtra("telphone");
		init();
	}

	public void init(){
		back=(Button) findViewById(R.id.txt_left);
		finish=(Button) findViewById(R.id.txt_save);
		title=(TextView) findViewById(R.id.txt_title);
		work = (TextView) findViewById(R.id.profession);
		int[] r={R.id.work_1,R.id.work_2,R.id.work_3,R.id.work_4,R.id.work_5,
				R.id.work_6,R.id.work_7,R.id.work_8,R.id.work_9};
		for(int i=0;i<9;i++){
			jobs[i] = (Button) findViewById(r[i]);
			jobs[i].setOnClickListener(this);
		}
		title.setText("职业");
		back.setVisibility(View.VISIBLE);
		finish.setVisibility(View.VISIBLE);
		work.setText(PersonInfoLocal.getJob(this, telphone));
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
				PersonInfoLocal.storeJob(Profession.this, telphone, work.getText().toString());
				finish();
			}
		});
		work.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
			    if (hasFocus) {
			        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
				    CharSequence text = work.getText();
				    if(text instanceof Spannable){
				    	Spannable span = (Spannable) text;
				    	Selection.setSelection(span,text.length());
				    }
			    }
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.work_1:
			PersonInfoLocal.storeJob(Profession.this, telphone,jobs[0].getText().toString());
			
			finish();
			break;
		case R.id.work_2:
			PersonInfoLocal.storeJob(Profession.this, telphone,jobs[1].getText().toString());
			
			finish();
			break;
		case R.id.work_3:
			PersonInfoLocal.storeJob(Profession.this, telphone,jobs[2].getText().toString());
			finish();
			break;
		case R.id.work_4:
			PersonInfoLocal.storeJob(Profession.this, telphone,jobs[3].getText().toString());
			finish();
			break;
		case R.id.work_5:
			PersonInfoLocal.storeJob(Profession.this, telphone,jobs[4].getText().toString());
			finish();
			break;
		case R.id.work_6:
			PersonInfoLocal.storeJob(Profession.this, telphone,jobs[5].getText().toString());
			finish();
			break;
		case R.id.work_7:
			PersonInfoLocal.storeJob(Profession.this, telphone,jobs[6].getText().toString());
			finish();
			break;
		case R.id.work_8:
			PersonInfoLocal.storeJob(Profession.this, telphone,jobs[7].getText().toString());
			finish();
			break;
		case R.id.work_9:
			PersonInfoLocal.storeJob(Profession.this, telphone,jobs[8].getText().toString());
			finish();
			break;
			default:break;
		}
	
	}
}
