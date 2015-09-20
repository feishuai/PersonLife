package com.personlife.view.activity.personcenter;

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
 * @date 2015年6月22日
 */
public class AboutUsActivity extends Activity {

	private Button back;
	private TextView title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aboutus);
		back = (Button) findViewById(R.id.txt_left);
		back.setVisibility(View.VISIBLE);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
				finish();
			}
		});
		title = (TextView) findViewById(R.id.txt_title);
		title.setText("关于我们");
	}

}
