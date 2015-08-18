package com.personlife.view.activity.personcenter;

import com.example.personlifep.R;
import com.personlife.utils.ActivityCollector;
import com.personlife.utils.Utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**  
 *   
 * @author liugang  
 * @date 2015年6月22日   
 */
public class ConnectionActivity extends FragmentActivity{

	private ImageButton plus;
	private Button back;
	private TextView title;
	private Fragment friend;
	private String telphone;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.connection_friends);
		ActivityCollector.addActivity(this);
		Intent intent=getIntent();
		telphone=intent.getStringExtra("telphone");
		init();
	}
	public void init(){
		back=(Button) findViewById(R.id.txt_left);
		plus=(ImageButton) findViewById(R.id.imgbtn_plus);
		title=(TextView) findViewById(R.id.txt_title);
		title.setText("通讯录");
		back.setVisibility(View.VISIBLE);
		plus.setVisibility(View.VISIBLE);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		plus.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(ConnectionActivity.this,SearchUser.class);
				intent.putExtra("telphone", telphone);
				startActivity(intent);
				
			}
		});
		
		friend=new Fragment_Friends(telphone);
		getSupportFragmentManager().beginTransaction()
		.add(R.id.friend_container, friend).show(friend).commit();
	}
}
