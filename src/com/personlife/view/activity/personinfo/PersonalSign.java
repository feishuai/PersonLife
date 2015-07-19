package com.personlife.view.activity.personinfo;

import com.example.personlifep.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_sign);
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
		Intent intent=getIntent();
		signedit.setText(intent.getStringExtra("sign"));
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
				
			}
		});
		signedit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
			    if (hasFocus) {
			        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
				    
			    }
			}
		});
	}
}
