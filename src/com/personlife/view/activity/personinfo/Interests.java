package com.personlife.view.activity.personinfo;

import com.example.personlifep.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**  
 *   
 * @author liugang  
 * @date 2015年6月26日   
 */
public class Interests extends Activity {

	private TextView title;
	private Button back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_interests);
		init();
	}
	public void init(){
		title=(TextView) findViewById(R.id.txt_title);
		back=(Button) findViewById(R.id.txt_left);
		title.setText("兴趣爱好");
		back.setVisibility(View.VISIBLE);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
	}
}
