package com.personlife.view.activity.home;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.personlifep.R;
import com.personlife.bean.Comment;
import com.personlife.common.Utils;
import com.personlife.widget.ClearEditText;
import com.personlife.widget.MyListView;

public class CommentActivity extends Activity implements OnClickListener {
	MyListView comments;
	ImageView mIcon;
	Button mBtnBack, download;
	TextView mTitle;
	ClearEditText comment;
	private  List<Comment> lcomments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		comments = (MyListView) findViewById(R.id.lv_comment_comment);
		mBtnBack = (Button) findViewById(R.id.txt_left);
		mTitle = (TextView) findViewById(R.id.txt_title);
		comment = (ClearEditText) findViewById(R.id.et_comment_comment);
		lcomments = new ArrayList<Comment>();
		mBtnBack.setVisibility(View.VISIBLE);
		mBtnBack.setOnClickListener(this);
		comment.setOnClickListener(this);
		mTitle.setText("评论列表");
		
		initData();
	}

	public void initData() {
		// 评论列表
		lcomments.add(new Comment());
		lcomments.add(new Comment());
		lcomments.add(new Comment());
		lcomments.add(new Comment());
		comments.setAdapter(new CommentAdapter(lcomments));
	}
	
	class CommentAdapter extends BaseAdapter {
		private  List<Comment> lcomments; 
		
		public CommentAdapter(List<Comment> l) {
			// TODO Auto-generated constructor stub
			lcomments = l;
		}
		@Override
		public int getCount() {
			return lcomments.size();
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
					R.layout.layout_item_comment, null);
			TextView content = (TextView) retval
					.findViewById(R.id.tv_comment_content);
			ImageView icon = (ImageView) retval
					.findViewById(R.id.iv_comment_icon);
			TextView name = (TextView) retval
					.findViewById(R.id.tv_comment_name);
			TextView time = (TextView) retval
					.findViewById(R.id.tv_comment_time);
			RatingBar rates = (RatingBar) retval
					.findViewById(R.id.rb_comment_rating);
			// content.setText(lcomments.get(position).getComments());
			// name.setText(lcomments.get(position).getUsernickname());
			// time.setText(lcomments.get(position).getCreated_at());
			// rates.setNumStars(lcomments.get(position).getCommentstars());
			// ImageLoaderUtils.displayAppIcon(lcomments.get(position).getUserthumb(),
			// icon);
			return retval;
		}
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txt_left:
			finish();
			break;
		case R.id.btn_comment_download:
			break;
		case R.id.et_comment_comment:
			Utils.start_Activity(CommentActivity.this,
					CommentAppActivity.class, null);
			break;
		}
	}

}
