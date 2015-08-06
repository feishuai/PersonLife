package com.personlife.view.activity.personinfo;

import com.example.personlifep.R;
import com.personlife.utils.ActivityCollector;

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
public class PersonalSign extends Activity {

	private TextView title,signedit;
	private Button cancle,save;

	private SharedPreferences pref;
	private SharedPreferences.Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_sign);
		ActivityCollector.addActivity(this);
		init();
	}
	public void init(){
		title=(TextView) findViewById(R.id.txt_title);
		signedit=(TextView) findViewById(R.id.sign);
		cancle=(Button) findViewById(R.id.t_left);
		save=(Button) findViewById(R.id.txt_save);
		title.setText("个性签名");
		cancle.setVisibility(View.VISIBLE);
		save.setVisibility(View.VISIBLE);
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		signedit.setText(pref.getString("signature", "个性签名"));
		cancle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				editor=pref.edit();
				editor.putString("signature",signedit.getText().toString());
				editor.commit();
				finish();
			}
		});
		signedit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
			    if (hasFocus) {
			        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
			        CharSequence text = signedit.getText();
				    if(text instanceof Spannable){
				    	Spannable span = (Spannable) text;
				    	Selection.setSelection(span,text.length());
				    }
			    }
			}
		});
	}
}
