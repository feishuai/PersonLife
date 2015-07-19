package com.personlife.view.activity.home;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;

import com.example.personlifep.R;
import com.personlife.adapter.home.CommentListAdapter;
import com.personlife.bean.Comment;
import com.personlife.utils.ListViewUtils;
import com.personlife.widget.MyListView;

public class AppDetailActivity extends Activity {
	MyListView mLvCommentList;
	List<Comment> comments;
	CommentListAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_detail);
		mLvCommentList = (MyListView) findViewById(R.id.lv_comment_list);
		comments = new ArrayList<Comment>();
		comments.add(new Comment("这个还真不错", 2, "飞帅", "很好很好", new Date(2015, 6, 26)));
		comments.add(new Comment("比想象中的好", 3, "威少", "很好很好", new Date(2015, 6, 26)));
		mAdapter = new CommentListAdapter(this, comments);
		mLvCommentList.setAdapter(mAdapter);
		ListViewUtils.setListViewHeightBasedOnChildren(mLvCommentList);
	}
}
