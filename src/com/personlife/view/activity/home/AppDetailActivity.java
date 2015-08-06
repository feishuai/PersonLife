package com.personlife.view.activity.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.personlifep.R;
import com.personlife.common.Utils;
import com.personlife.widget.HorizontialListView;

public class AppDetailActivity extends Activity implements OnClickListener{
	HorizontialListView hlvUrls,hlvLikes;
	Button mBack;
	ImageView mIcon;
	TextView mTitle,mName,mSizeAndCounts,mDownload,mIntro,mLog,mMore,mTime,mNumbers;
	RelativeLayout mComments;
	RatingBar mStars;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_detail);
		initViews();
		hlvUrls.setAdapter(mAUrls);
		hlvLikes.setAdapter(mALikes);
	}
	private void initViews(){
		hlvUrls = (HorizontialListView) findViewById(R.id.hlv_detail_urls);
		hlvLikes = (HorizontialListView) findViewById(R.id.hlv_detail_likes);
		mTitle = (TextView) findViewById(R.id.txt_title);
		mBack = (Button) findViewById(R.id.txt_left);
		mIcon = (ImageView) findViewById(R.id.iv_app_icon);
		mName = (TextView) findViewById(R.id.tv_app_appname);
		mComments = (RelativeLayout) findViewById(R.id.rl_detail_comments);
		mStars = (RatingBar) findViewById(R.id.rb_app_rating);
		mSizeAndCounts = (TextView) findViewById(R.id.tv_app_sizeanddownloadcounts);
		mDownload = (TextView) findViewById(R.id.tv_app_download);
		mIntro = (TextView) findViewById(R.id.tv_detail_intro);
		mLog = (TextView) findViewById(R.id.tv_detail_log);
		mMore = (TextView) findViewById(R.id.tv_detail_more);
		mTime = (TextView) findViewById(R.id.tv_detail_time);
		mNumbers = (TextView) findViewById(R.id.tv_detail_numbers);
		mBack.setVisibility(View.VISIBLE);
		mTitle.setText("网易云音乐");
		mBack.setOnClickListener(this);
		mMore.setOnClickListener(this);
		mComments.setOnClickListener(this);
		mDownload.setOnClickListener(this);
	}
	private static String[] urls = new String[] { "Text #1","Text #1","Text #1","Text #1", "Text #2",
			"Text #3" };
	private BaseAdapter mAUrls = new BaseAdapter() {

		@Override
		public int getCount() {
			return urls.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View retval = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.layout_item_image, null);
//			TextView title = (TextView) retval.findViewById(R.id.title);
//			title.setText(dataObjects[position]);

			return retval;
		}

	};
	
	private BaseAdapter mALikes = new BaseAdapter() {

		@Override
		public int getCount() {
			return urls.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View retval = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.layout_item_like, null);
//			TextView title = (TextView) retval.findViewById(R.id.title);
//			title.setText(dataObjects[position]);

			return retval;
		}

	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.txt_left:
			finish();
			break;
		case R.id.rl_detail_comments:
			Utils.start_Activity(AppDetailActivity.this, CommentActivity.class, null);
			break;
		}
	}
}
