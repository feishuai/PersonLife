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
public class Profession extends Activity {

	private TextView title;
	private Button back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_profession);
		init();
	}

	public void init(){
		back=(Button) findViewById(R.id.txt_left);
		title=(TextView) findViewById(R.id.txt_title);
		title.setText("职业");
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
