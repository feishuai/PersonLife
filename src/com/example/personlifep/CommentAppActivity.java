package com.example.personlifep;

import com.personlife.widget.ClearEditText;
import com.personlife.widget.MyListView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class CommentAppActivity extends Activity implements OnClickListener {
	Button mBack,save;
	TextView mTitle;
	RatingBar stars;
	EditText content;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment_app);
		mBack = (Button) findViewById(R.id.txt_left);
		save = (Button) findViewById(R.id.txt_save);
		mTitle = (TextView) findViewById(R.id.txt_title);
		stars = (RatingBar)findViewById(R.id.rb_comment_stars);
		content = (EditText)findViewById(R.id.dt_comment_content);
		mBack.setVisibility(View.VISIBLE);
		save.setVisibility(View.VISIBLE);
		mTitle.setVisibility(View.GONE);
		mBack.setOnClickListener(this);
		save.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.txt_left:
			finish();
			break;
		case R.id.txt_save:
			int counts = (int)stars.getRating();
			String con = content.getText().toString();
			Toast.makeText(getApplicationContext(), con + counts, Toast.LENGTH_SHORT).show();
			break;
		}
	}
}
