package com.personlife.view.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.personlifep.R;
import com.personlife.adapter.Register4_Adapter;
import com.personlife.utils.ActivityCollector;
import com.personlife.utils.Utils;
import com.personlife.view.activity.circle.CircleActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 * @author liugang
 * @date 2015年8月12日
 */
public class RegisterActivity4 extends Activity implements OnClickListener{

	private String telphone;
	private Button back, finishstep;
	private TextView tv_title;
	private GridView star_gridview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register4);
		ActivityCollector.addActivity(this);
		Intent intent = getIntent();
		telphone = intent.getStringExtra("telphone");
		initview();
		initdata();
	}

	public void initview() {
		back = (Button) findViewById(R.id.txt_left);
		back.setVisibility(View.VISIBLE);
		back.setOnClickListener(this);
		tv_title = (TextView) findViewById(R.id.txt_title);
		tv_title.setText("完善个人信息");
		star_gridview = (GridView) findViewById(R.id.star_gridview);
		finishstep=(Button) findViewById(R.id.register4_nextstep);
		finishstep.setOnClickListener(this);
	}

	public void initdata() {
		// 图片的文字标题
		String[] titles = new String[] { "李宇春", "范冰冰", "郑凯", "baby", "谁啊",
				"不认识", "又来？", "好烦啊", "额囧" };
		// 图片ID数组
		int[] images = new int[] { R.drawable.star1, R.drawable.star2,
				R.drawable.star3, R.drawable.star4, R.drawable.star5,
				R.drawable.star6, R.drawable.star4, R.drawable.star5,
				R.drawable.star6 };
		Register4_Adapter  adapter = new Register4_Adapter(titles, images, this);
		star_gridview.setAdapter(adapter);
		star_gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.register4_nextstep:
			
			Intent intent = new Intent(RegisterActivity4.this,MainActivity.class);
			intent.putExtra("telphone", telphone);
			startActivity(intent);												
			ActivityCollector.finishAll();
			break;
		case R.id.txt_left:
			onBackPressed();
			break;
		}
	}
}
