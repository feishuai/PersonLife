package com.personlife.view.activity.personinfo;

import com.example.personlifep.R;
import com.personlife.utils.ActivityCollector;

import android.app.Activity;
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
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_profession);
		ActivityCollector.addActivity(this);
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
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		work.setText(pref.getString("job", "职业"));
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
				editor=pref.edit();
				editor.putString("job",work.getText().toString());
				editor.commit();
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
			editor=pref.edit();
			editor.putString("job",jobs[0].getText().toString());
			editor.commit();
			finish();
			break;
		case R.id.work_2:
			editor=pref.edit();
			editor.putString("job",jobs[1].getText().toString());
			editor.commit();
			finish();
			break;
		case R.id.work_3:
			editor=pref.edit();
			editor.putString("job",jobs[2].getText().toString());
			editor.commit();
			finish();
			break;
		case R.id.work_4:
			editor=pref.edit();
			editor.putString("job",jobs[3].getText().toString());
			editor.commit();
			finish();
			break;
		case R.id.work_5:
			editor=pref.edit();
			editor.putString("job",jobs[4].getText().toString());
			editor.commit();
			finish();
			break;
		case R.id.work_6:
			editor=pref.edit();
			editor.putString("job",jobs[5].getText().toString());
			editor.commit();
			finish();
			break;
		case R.id.work_7:
			editor=pref.edit();
			editor.putString("job",jobs[6].getText().toString());
			editor.commit();
			finish();
			break;
		case R.id.work_8:
			editor=pref.edit();
			editor.putString("job",jobs[7].getText().toString());
			editor.commit();
			finish();
			break;
		case R.id.work_9:
			editor=pref.edit();
			editor.putString("job",jobs[8].getText().toString());
			editor.commit();
			finish();
			break;
			default:break;
		}
	
	}
}
