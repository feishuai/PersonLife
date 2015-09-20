package com.personlife.view.activity.home;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.snowdream.android.app.DownloadListener;
import com.github.snowdream.android.app.DownloadStatus;
import com.github.snowdream.android.app.DownloadTask;
import com.personlife.bean.App;
import com.personlife.bean.Comment;
import com.personlife.net.DownloadTaskManager;
import com.personlife.utils.ComplexPreferences;
import com.personlife.utils.Constants;
import com.personlife.utils.ImageLoaderUtils;
import com.personlife.utils.Utils;
import com.personlife.widget.ClearEditText;
import com.personlife.widget.MyListView;

public class CommentActivity extends Activity implements OnClickListener {
	MyListView comments;
	ImageView mIcon;
	Button mBtnBack, download;
	TextView mTitle;
	ClearEditText comment;
	CommentAdapter commentAdapter;
	List<Comment> lcomments;
	App app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		comments = (MyListView) findViewById(R.id.lv_comment_comment);
		mBtnBack = (Button) findViewById(R.id.txt_left);
		mTitle = (TextView) findViewById(R.id.txt_title);
		comment = (ClearEditText) findViewById(R.id.et_comment_comment);
		download = (Button) findViewById(R.id.btn_comment_download);
		download.setOnClickListener(this);
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
		commentAdapter = new CommentAdapter(lcomments);
		comments.setAdapter(commentAdapter);
		ComplexPreferences complexPreferences = ComplexPreferences
				.getComplexPreferences(getApplication(),
						Constants.SharePrefrencesName);
		List<Comment> commentsLocal = complexPreferences.getObject("comments",
				new TypeReference<List<Comment>>() {
				});
		app = complexPreferences.getObject(Constants.SelectedApp,
				new TypeReference<App>() {
				});

		if (DownloadTaskManager.getDownloadTaskManager(getApplicationContext())
				.isHasDownloaded(app)) {
			long size = DownloadTaskManager
					.getDownloadTaskManager(getApplicationContext())
					.getDownloadTaskByApp(app).getSize();
			if (size > 0) {
				int progress = DownloadTaskManager.getDownloadTaskManager(
						getApplicationContext()).getDownloadProgress(app);
				if (progress == 100)
					download.setText("已下载");
				else
					download.setText("继续");
			}
		}

		if (commentsLocal != null) {
			Log.i("comment activity comment size is ",
					String.valueOf(commentsLocal.size()));
			// lcomments = commentsLocal;
			commentAdapter.setData(commentsLocal);
			commentAdapter.notifyDataSetChanged();
		}

	}

	class CommentAdapter extends BaseAdapter {
		private List<Comment> lcomments;

		public CommentAdapter(List<Comment> l) {
			// TODO Auto-generated constructor stub
			lcomments = l;
		}

		public void setData(List<Comment> l) {
			this.lcomments = l;
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
			content.setText(lcomments.get(position).getComments());
			name.setText(lcomments.get(position).getUsernickname());
			time.setText(Utils.TimeStamp2Date(lcomments.get(position)
					.getCreated_at()));
			rates.setRating(lcomments.get(position).getCommentstars());
			ImageLoaderUtils.displayImageView(lcomments.get(position)
					.getUserthumb(), icon);
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
			if (download.getText().toString().equals("已下载")) {
				Utils.showShortToast(getApplicationContext(),
						"该应用已下载，请到下载任务中管理");
				return;
			}
			if (download.getText().toString().equals("下载")) {
				if (DownloadTaskManager.getDownloadTaskManager(
						getApplicationContext()).isHasDownloaded(app)) {
					download.setText("继续");
					download.callOnClick();
					Utils.showShortToast(getApplicationContext(), "该应用已在下载列表中");
					return;
				}
				download.setText("暂停");
				DownloadTaskManager.getDownloadTaskManager(
						getApplicationContext()).startNewDownload(
						getApplicationContext(), app,
						new DownloadListener<Integer, DownloadTask>() {
							@Override
							public void onProgressUpdate(Integer... values) {
								super.onProgressUpdate(values);
								if (values[0] == 100) {
									download.setText("已下载");
								}
								Log.i("update progress",
										String.valueOf(values[0]));
							}
						});
				return;
			}
			if (download.getText().toString().equals("继续")) {
				download.setText("暂停");
				if (DownloadTaskManager
						.getDownloadTaskManager(getApplicationContext())
						.getDownloadTaskByApp(app).getStatus() == DownloadStatus.STATUS_RUNNING) {
					// Utils.showShortToast(getApplicationContext(),
					// "该应用已在下载,请在下载任务管理");
					DownloadTaskManager.getDownloadTaskManager(
							getApplicationContext()).stopDownload(
							getApplicationContext(), app);

				}
				DownloadTaskManager.getDownloadTaskManager(
						getApplicationContext()).startContinueDownload(
						getApplicationContext(), app,
						new DownloadListener<Integer, DownloadTask>() {
							@Override
							public void onProgressUpdate(Integer... values) {
								super.onProgressUpdate(values);
								if (values[0] == 100) {
									download.setText("已下载");
								}
								Log.i("update progress",
										String.valueOf(values[0]));
							}
						});
				return;
			}
			if (download.getText().toString().equals("暂停")) {
				download.setText("继续");
				DownloadTaskManager.getDownloadTaskManager(
						getApplicationContext()).stopDownload(
						getApplicationContext(), app);
				return;
			}
			break;
		case R.id.et_comment_comment:
			Intent intent = new Intent(CommentActivity.this,
					CommentAppActivity.class);
			intent.putExtra("appid", getIntent().getStringExtra("appid"));
			startActivityForResult(intent, 1);
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 1) {
			setResult(1);
			finish();
		}
	}
}
