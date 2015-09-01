package com.personlife.view.activity.home;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personlifep.R;
import com.loopj.android.http.RequestParams;
import com.personlife.bean.Comment;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONObjectHttpResponseHandler;
import com.personlife.utils.PersonInfoLocal;
import com.personlife.utils.Utils;

public class CommentAppActivity extends Activity implements OnClickListener {
	Button mBack,save;
	TextView mTitle;
	RatingBar stars;
	EditText content;
	
	String comments;
	int counts;
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
			counts = (int)stars.getRating();
			comments = content.getText().toString();
			if(counts==0){
				Utils.showShortToast(getApplication(), "请给出评分");
				return ;
			}
			if(comments.length() < 5){
				Utils.showShortToast(getApplication(), "请输入最少5个字的评价");
				return;
			}
			submitComment();
//			Toast.makeText(getApplicationContext(), con + counts, Toast.LENGTH_SHORT).show();
			break;
		}
	}

	private void submitComment() {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		params.add("appid", getIntent().getStringExtra("appid"));
		params.add("comments", comments);
		params.add("commentstars", String.valueOf(counts));
		params.add("phone", PersonInfoLocal.getPhone(getApplicationContext()));
		params.add("title", "真的是很好用的");
		Log.i("submit comment param is ", params.toString());
		BaseAsyncHttp.postReq(getApplicationContext(), "/app/submitcomment", params,
				new JSONObjectHttpResponseHandler() {

					@Override
					public void jsonSuccess(JSONObject resp) {
						if(resp.optInt("flag")==1){
							Utils.showShortToast(getApplication(), "提交评论成功");
							CommentAppActivity.this.setResult(1);
							CommentAppActivity.this.finish();
						}
					}

					@Override
					public void jsonFail(JSONObject resp) {
						// TODO Auto-generated method stub
						Utils.showShortToast(getApplication(), "因为网络问题，评论提交失败");
					}
				});
	}
}
