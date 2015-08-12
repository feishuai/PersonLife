package com.personlife.view.activity.home;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.personlifep.R;
import com.loopj.android.http.RequestParams;
import com.personlife.bean.App;
import com.personlife.bean.Comment;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONArrayHttpResponseHandler;
import com.personlife.net.JSONObjectHttpResponseHandler;
import com.personlife.utils.ComplexPreferences;
import com.personlife.utils.ImageLoaderUtils;
import com.personlife.utils.Utils;
import com.personlife.widget.HorizontialListView;

public class AppDetailActivity extends Activity implements OnClickListener {
	HorizontialListView hlvUrls, hlvLikes;
	Button mBack;
	ImageView mIcon;
	TextView mTitle, mName, mSizeAndCounts, mDownload, mIntro, mLog, mMore,
			mTime, mNumbers;
	RelativeLayout mComments;
	RatingBar mStars;
	App app;
	List<String> urlsapp;
	List<App> likesapp;
	LikesAppAdapter likesadapter;
	UrlsAppAdapter urlsadapter;
	int appid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_detail);
		initViews();
		hlvUrls.setAdapter(urlsadapter);
		hlvLikes.setAdapter(likesadapter);
	}

	private void initViews() {
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

		initData();
	}

	private void initData() {
		appid = getIntent().getIntExtra("appid", 0);
		app = new App();
		urlsapp = new ArrayList<String>();
		likesapp = new ArrayList<App>();
		urlsadapter = new UrlsAppAdapter(urlsapp);
		likesadapter = new LikesAppAdapter(likesapp);

		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		params.add("appid", String.valueOf(appid));
		Log.i("get app detail info", String.valueOf(appid));
		BaseAsyncHttp.postReq(getApplicationContext(), "/app/getapp", params,
				new JSONObjectHttpResponseHandler() {

					@Override
					public void jsonSuccess(JSONObject resp) {
						// TODO Auto-generated method stub
						try {
							JSONObject jsonapp = resp.getJSONObject("basic");

							app.setIcon(jsonapp.getString("icon"));
							app.setSize(jsonapp.getString("size"));
							app.setDowloadcount(jsonapp.getInt("downloadcount"));
							app.setIntrodution(jsonapp
									.getString("introduction"));
							app.setName(jsonapp.getString("name"));
							app.setId(jsonapp.getInt("id"));
							app.setDownloadUrl(jsonapp.getString("android_url"));
							JSONArray jsonurls = resp
									.getJSONArray("picture_urls");
							app.setStars(jsonapp.getInt("stars"));
							app.setUpdateLog(jsonapp.getString("updated_log"));
							app.setUpdateDate(jsonapp.getLong("updated_at"));
							urlsapp.clear();
							for (int i = 0; i < jsonurls.length(); i++) {
								urlsapp.add(jsonurls.getJSONObject(i)
										.getString("picture"));
							}
							JSONArray jsoncomments = resp
									.getJSONArray("comments");
							List<Comment> comments = new ArrayList<Comment>();
							Comment comment = new Comment();
							for (int i = 0; i < jsoncomments.length(); i++) {
								JSONObject jsoncomment = jsoncomments
										.getJSONObject(i);
								comment.setComments(jsoncomment
										.getString("comments"));
								comment.setCommentstars(jsoncomment
										.getInt("commentstars"));
								comment.setUsernickname(jsoncomment
										.getString("usernickname"));
								comment.setUserthumb(jsoncomment
										.getString("userthumb"));
								comment.setCreated_at(jsoncomment
										.getLong("created_at"));
								comments.add(comment);
							}
							app.setComments(comments);
							updateView();
							getLikesapp();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void jsonFail(JSONObject resp) {
						// TODO Auto-generated method stub

					}
				});
	}

	protected void getLikesapp() {
		// TODO Auto-generated method stub
		Log.i("get app coments list", String.valueOf(appid));
		RequestParams params = new RequestParams();
		params.add("appid", String.valueOf(appid));
		BaseAsyncHttp.postReq(getApplicationContext(), "/myapp/like", params,
				new JSONArrayHttpResponseHandler() {

					@Override
					public void jsonSuccess(JSONArray resp) {
						// TODO Auto-generated method stub
						likesapp.clear();
						try {
							for (int i = 0; i < resp.length(); i++) {
								App app = new App();
								JSONObject jsonapp = resp.getJSONObject(i);
								app.setId(jsonapp.getInt("appid"));
								app.setIcon(jsonapp.getString("icon"));
								app.setName(jsonapp.getString("name"));
								likesapp.add(app);
							}
							updateLikes();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void jsonFail(JSONArray resp) {
						// TODO Auto-generated method stub

					}
				});

	}

	protected void updateView() {
		// TODO Auto-generated method stub
		mTitle.setText(app.getName());
		ImageLoaderUtils.displayAppIcon(app.getIcon(), mIcon);
		mName.setText(app.getName());
		mNumbers.setText("有" + app.getComments().size() + "人评分");
		mSizeAndCounts.setText("");
		mTime.setText(Utils.TimeStamp2DateChinese(app.getUpdateDate()));
		mStars.setRating(app.getStars());
		mLog.setText(app.getUpdateLog());

		ComplexPreferences complexPreferences = ComplexPreferences
				.getComplexPreferences(getApplication(), "pfy", MODE_PRIVATE);
		complexPreferences.putObject("comments", app.getComments());
		complexPreferences.commit();

		int downloadcounts = app.getDowloadcount();
		if (downloadcounts > 10000)
			mSizeAndCounts.setText(app.getSize() + "," + downloadcounts / 10000
					+ "万人下载");
		else
			mSizeAndCounts
					.setText(app.getSize() + "," + downloadcounts + "人下载");

		int numbersOfDisplay = 20;
		if (app.getIntrodution().length() < numbersOfDisplay) {
			mIntro.setText(app.getIntrodution());
			mMore.setVisibility(View.GONE);
		} else
			mIntro.setText(app.getIntrodution().substring(0, numbersOfDisplay));

		updateUrls();
	}

	protected void updateUrls() {
		// TODO Auto-generated method stub
		urlsadapter.setData(urlsapp);
		urlsadapter.notifyDataSetChanged();
	}

	protected void updateLikes() {
		// TODO Auto-generated method stub
		likesadapter.setData(likesapp);
		likesadapter.notifyDataSetChanged();
	}

	class UrlsAppAdapter extends BaseAdapter {
		List<String> urls;

		public UrlsAppAdapter(List<String> urls) {
			// TODO Auto-generated constructor stub
			this.urls = urls;
		}

		public void setData(List<String> urls) {
			this.urls = urls;
		}

		@Override
		public int getCount() {
			return urls.size();
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
			ImageView bg = (ImageView) retval.findViewById(R.id.iv_app_icon);
			ImageLoaderUtils.displayImageView(urls.get(position), bg);
			return retval;
		}

	};

	class LikesAppAdapter extends BaseAdapter {
		List<App> apps;

		public LikesAppAdapter(List<App> apps) {
			this.apps = apps;
			// TODO Auto-generated constructor stub
		}

		public void setData(List<App> apps) {
			this.apps = apps;
		}

		@Override
		public int getCount() {
			return apps.size();
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
			ImageView icon = (ImageView) retval.findViewById(R.id.iv_item_icon);
			TextView name = (TextView) retval.findViewById(R.id.tv_item_name);
			ImageLoaderUtils.displayAppIcon(apps.get(position).getIcon(), icon);
			name.setText(apps.get(position).getName());
			Utils.start_Activity(AppDetailActivity.this,
					AppDetailActivity.class, new BasicNameValuePair("appid",
							String.valueOf(apps.get(position).getId())));
			return retval;
		}
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txt_left:
			finish();
			break;
		case R.id.rl_detail_comments:
			Utils.start_Activity(AppDetailActivity.this, CommentActivity.class,
					new BasicNameValuePair("appid", String.valueOf(appid)));
			break;
		case R.id.tv_detail_more:
			mIntro.setText(app.getIntrodution());
			mMore.setVisibility(View.GONE);
		}
	}
}
