package com.personlife.view.activity.personcenter;

import com.example.personlifep.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

/**  
 *   
 * @author liugang  
 * @date 2015年6月22日   
 */
public class ConnectionActivity extends FragmentActivity{

	private ImageButton back,plus;
	private TextView title;
	private Fragment friend;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.connection_friends);
		init();
	}
	public void init(){
		back=(ImageButton) findViewById(R.id.imgbtn_back);
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
		
		//
		friend=new Fragment_Friends();
		getSupportFragmentManager().beginTransaction()
		.add(R.id.friend_container, friend).show(friend).commit();
	}
}
