package com.personlife.view.activity.personcenter;

import com.example.personlifep.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * 
 * @author liugang
 * @date 2015年6月22日
 */
public class MyCollectionActivity extends Activity {

	private String telphone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.connection_friends);
		Intent intent = getIntent();
		telphone = intent.getStringExtra("telphone");
	}

}
