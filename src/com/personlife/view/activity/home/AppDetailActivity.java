package com.personlife.view.activity.home;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.personlifep.R;
import com.github.snowdream.android.app.DownloadListener;
import com.github.snowdream.android.app.DownloadStatus;
import com.github.snowdream.android.app.DownloadTask;
import com.j256.ormlite.stmt.query.In;
import com.loopj.android.http.RequestParams;
import com.personlife.bean.App;
import com.personlife.bean.Comment;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.DownloadTaskManager;
import com.personlife.net.JSONArrayHttpResponseHandler;
import com.personlife.net.JSONObjectHttpResponseHandler;
import com.personlife.utils.ComplexPreferences;
import com.personlife.utils.Constants;
import com.personlife.utils.ImageLoaderUtils;
import com.personlife.utils.PersonInfoLocal;
import com.personlife.utils.Utils;
import com.personlife.widget.HorizontialListView;

public class AppDetailActivity extends Activity implements OnClickListener {
	HorizontialListView hlvUrls, hlvLikes;
	Button mBack, mDownload;
	ImageView mIcon;
	ImageButton shoucang;
	TextView mTitle, mName, mSizeAndCounts, mIntro, mLog, mMore, mTime,
			mNumbers;
	RelativeLayout mComments;
//	RatingBar mStars;
	App app;
	List<String> urlsapp;
	List<App> likesapp;
	LikesAppAdapter likesadapter;
	UrlsAppAdapter urlsadapter;
	ProgressBar bar;
	int appid;
	Boolean isCollected = false; // 判断是否已在收藏列表

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
//		mStars = (RatingBar) findViewById(R.id.rb_app_rating);
		mSizeAndCounts = (TextView) findViewById(R.id.tv_app_sizeanddownloadcounts);
		mDownload = (Button) findViewById(R.id.btn_app_download);
		mIntro = (TextView) findViewById(R.id.tv_detail_intro);
		mLog = (TextView) findViewById(R.id.tv_detail_log);
		mMore = (TextView) findViewById(R.id.tv_detail_more);
		mTime = (TextView) findViewById(R.id.tv_detail_time);
		mNumbers = (TextView) findViewById(R.id.tv_detail_numbers);
		bar = (ProgressBar) findViewById(R.id.bar);
		shoucang = (ImageButton) findViewById(R.id.img_right);
		shoucang.setVisibility(View.VISIBLE);
		shoucang.setImageResource(R.drawable.shoucang1);
		shoucang.setOnClickListener(this);
		mBack.setVisibility(View.VISIBLE);
		mTitle.setText("网易云音乐");
		mBack.setOnClickListener(this);
		mMore.setOnClickListener(this);
		mComments.setOnClickListener(this);
		// mDownload.setOnClickListener(this);

		initData();
	}

	private void initData() {

		appid = getIntent().getIntExtra("appid", 0);
		Log.i("appdetail appid", String.valueOf(appid));
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
							app.setUpdateLog(jsonapp.getString("updated_log"));
							app.setUpdateDate(jsonapp.getLong("updated_at"));
							app.setProfile(jsonapp.getString("profile"));
							app.setStars((float)jsonapp.optDouble("stars"));
							urlsapp.clear();
							for (int i = 0; i < jsonurls.length(); i++) {
								urlsapp.add(jsonurls.getJSONObject(i)
										.getString("picture"));
							}
							JSONArray jsoncomments = resp
									.getJSONArray("comments");
							List<Comment> comments = new ArrayList<Comment>();
							for (int i = 0; i < jsoncomments.length(); i++) {
								Comment comment = new Comment();
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
							app.setDownloadPath(Constants.DownloadPath
									+ app.getName() + ".apk");
							ComplexPreferences pre = ComplexPreferences
									.getComplexPreferences(
											getApplicationContext(),
											Constants.SharePrefrencesName);
							pre.putObject(Constants.SelectedApp, app);
							pre.commit();
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
		initDownload();
		mTitle.setText(app.getName());
		ImageLoaderUtils.displayAppIcon(app.getIcon(), mIcon);
		mName.setText(app.getName());
		mNumbers.setText("有" + app.getComments().size() + "人评分");
		mSizeAndCounts.setText("");
		mTime.setText(Utils.TimeStamp2DateChinese(app.getUpdateDate()));
//		mStars.setRating(app.getStars());
		mLog.setText(app.getUpdateLog());

		ComplexPreferences complexPreferences = ComplexPreferences
				.getComplexPreferences(getApplication(),
						Constants.SharePrefrencesName);
		complexPreferences.putObject("comments", app.getComments());
		complexPreferences.commit();

		int downloadcounts = app.getDowloadcount();
		if (downloadcounts > 10000)
			mSizeAndCounts.setText(app.getStars() + "分," + downloadcounts / 10000
					+ "万人下载");
		else
			mSizeAndCounts
					.setText(app.getStars() + "分," + downloadcounts + "人下载");

		int numbersOfDisplay = 20;
		if (app.getIntrodution().length() < numbersOfDisplay) {
			mIntro.setText(app.getIntrodution());
			mMore.setVisibility(View.GONE);
		} else
			mIntro.setText(app.getIntrodution().substring(0, numbersOfDisplay));

		updateUrls();
	}

	private void initDownload() {
		// TODO Auto-generated method stub

		if (DownloadTaskManager.getDownloadTaskManager(getApplicationContext())
				.isHasDownloaded(app)) {
			long size = DownloadTaskManager
					.getDownloadTaskManager(getApplicationContext())
					.getDownloadTaskByApp(app).getSize();
			if (size > 0) {
				int progress = DownloadTaskManager.getDownloadTaskManager(
						getApplicationContext()).getDownloadProgress(app);
				if (progress == 100)
					mDownload.setText("已下载");
				else
					mDownload.setText("继续");
				bar.setVisibility(View.VISIBLE);
				bar.setProgress(progress);
			}
		}

		mDownload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mDownload.getText().toString().equals("已下载")) {
					Utils.showShortToast(getApplicationContext(),
							"该应用已下载，请到下载任务中管理");
					return;
				}
				if (mDownload.getText().toString().equals("下载")) {
					if (DownloadTaskManager.getDownloadTaskManager(
							getApplicationContext()).isHasDownloaded(app)) {
						mDownload.setText("继续");
						mDownload.callOnClick();
						Utils.showShortToast(getApplicationContext(),
								"该应用已在下载列表中");
						return;
					}
					mDownload.setText("暂停");
					bar.setVisibility(View.VISIBLE);
					DownloadTaskManager.getDownloadTaskManager(
							getApplicationContext()).startNewDownload(
							getApplicationContext(), app,
							new DownloadListener<Integer, DownloadTask>() {
								@Override
								public void onProgressUpdate(Integer... values) {
									super.onProgressUpdate(values);
									bar.setProgress(values[0]);
									if (values[0] == 100) {
										mDownload.setText("已下载");
									}
									Log.i("update progress",
											String.valueOf(values[0]));
								}
							});
					return;
				}
				if (mDownload.getText().toString().equals("继续")) {
					mDownload.setText("暂停");
					bar.setVisibility(View.VISIBLE);
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
									bar.setProgress(values[0]);
									if (values[0] == 100) {
										mDownload.setText("已下载");
									}
									Log.i("update progress",
											String.valueOf(values[0]));
								}
							});
					return;
				}
				if (mDownload.getText().toString().equals("暂停")) {
					mDownload.setText("继续");
					DownloadTaskManager.getDownloadTaskManager(
							getApplicationContext()).stopDownload(
							getApplicationContext(), app);
					return;
				}
			}
		});

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
			final int posi = position;
			ImageView icon = (ImageView) retval.findViewById(R.id.iv_item_icon);
			TextView name = (TextView) retval.findViewById(R.id.tv_item_name);
			ImageLoaderUtils.displayAppIcon(apps.get(position).getIcon(), icon);
			name.setText(apps.get(position).getName());
			icon.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(AppDetailActivity.this,
							AppDetailActivity.class);
					intent.putExtra("appid", apps.get(posi).getId());
					AppDetailActivity.this.startActivity(intent);
				}
			});

			return retval;
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == 1) {
			RequestParams params = new RequestParams();
			params.add("appid", String.valueOf(appid));
			BaseAsyncHttp.postReq(getApplicationContext(), "/app/getapp",
					params, new JSONObjectHttpResponseHandler() {
						@Override
						public void jsonSuccess(JSONObject resp) {
							// TODO Auto-generated method stub
							try {
								JSONArray jsoncomments = resp
										.getJSONArray("comments");
								List<Comment> comments = new ArrayList<Comment>();
								for (int i = 0; i < jsoncomments.length(); i++) {
									Comment comment = new Comment();
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
								ComplexPreferences pre = ComplexPreferences
										.getComplexPreferences(
												getApplicationContext(),
												Constants.SharePrefrencesName);
								pre.putObject(Constants.SelectedApp, app);
								pre.commit();
								mNumbers.setText("有" + app.getComments().size()
										+ "人评分");
								ComplexPreferences complexPreferences = ComplexPreferences
										.getComplexPreferences(
												getApplication(),
												Constants.SharePrefrencesName);
								complexPreferences.putObject("comments",
										app.getComments());
								complexPreferences.commit();
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
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txt_left:
			finish();
			break;
		case R.id.rl_detail_comments:
			Intent intent = new Intent(AppDetailActivity.this,
					CommentActivity.class);
			intent.putExtra("appid", String.valueOf(appid));
			startActivityForResult(intent, 1);
			break;
		case R.id.tv_detail_more:
			mIntro.setText(app.getIntrodution());
			mMore.setVisibility(View.GONE);
			break;
		case R.id.img_right:
			// TODO Auto-generated method stub
			RequestParams params = new RequestParams();
			params.add("app", String.valueOf(appid));
			params.add("phone",
					PersonInfoLocal.getPhone(getApplicationContext()));
			if (isCollected) {
				Utils.showShortToast(getApplicationContext(), "已收藏");
				return;
			}

			BaseAsyncHttp.postReq(getApplicationContext(), "/collect/set-app",
					params, new JSONObjectHttpResponseHandler() {

						@Override
						public void jsonSuccess(JSONObject resp) {
							// TODO Auto-generated method stub
							int flag = resp.optInt("flag");
							if (flag == 1)
								Utils.showShortToast(getApplicationContext(),
										"收藏成功");
							else
								Utils.showShortToast(getApplicationContext(),
										"已收藏");
							isCollected = true;
						}

						@Override
						public void jsonFail(JSONObject resp) {
							// TODO Auto-generated method stub

						}
					});
			break;
		}
	}
}
