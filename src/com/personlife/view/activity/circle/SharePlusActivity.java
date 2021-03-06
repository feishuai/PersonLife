package com.personlife.view.activity.circle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.tsz.afinal.core.Arrays;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

import com.alibaba.fastjson.serializer.AppendableSerializer;
import com.example.personlifep.R;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.snowdream.android.app.DownloadListener;
import com.github.snowdream.android.app.DownloadTask;
import com.loopj.android.http.RequestParams;
import com.personlife.bean.App;
import com.personlife.bean.Comment;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.DownloadTaskManager;
import com.personlife.net.JSONObjectHttpResponseHandler;
import com.personlife.utils.ComplexPreferences;
import com.personlife.utils.Constants;
import com.personlife.utils.DrawableStringUtils;
import com.personlife.utils.PersonInfoLocal;
import com.personlife.utils.SystemUtils;
import com.personlife.utils.Utils;
import com.personlife.view.activity.personcenter.AboutUsActivity;
import com.personlife.view.activity.personcenter.FeedBackActivity;

public class SharePlusActivity extends Activity implements OnClickListener {
	Button mBack, save;
	TextView mTitle, hasLabel;
	EditText etContent;
	GridView gvLabels;
	App selectedApp;
	App defaultapp;
	List<App> existedApps;
	List<App> systemApps;
	ImageView appicon;
	List<String> selectedLabels;
	List<String> appLabels = new ArrayList<String>();
	RatingBar stars;
	LabelAdapter labelAdapter;
	ImageView wechatcomment, wechat, sina;
	Boolean isSelected[];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shareplus);
		mBack = (Button) findViewById(R.id.txt_left);
		save = (Button) findViewById(R.id.txt_save);
		etContent = (EditText) findViewById(R.id.dt_shareplus_content);
		gvLabels = (GridView) findViewById(R.id.gv_shareplus_labels);
		mTitle = (TextView) findViewById(R.id.txt_title);
		appicon = (ImageView) findViewById(R.id.iv_shareplus_app);
		stars = (RatingBar) findViewById(R.id.rb_shareplus_stars);
		hasLabel = (TextView) findViewById(R.id.tv_shareplus_hasLabel);
		wechatcomment = (ImageView) findViewById(R.id.wechatcomment);
		wechat = (ImageView) findViewById(R.id.wechat);
		sina = (ImageView) findViewById(R.id.sina);
		mBack.setVisibility(View.VISIBLE);
		save.setVisibility(View.VISIBLE);
		mTitle.setVisibility(View.GONE);

		mBack.setText("取消");
		save.setText("发表");

		mBack.setOnClickListener(this);
		save.setOnClickListener(this);
		appicon.setOnClickListener(this);
		wechatcomment.setOnClickListener(this);
		wechat.setOnClickListener(this);
		sina.setOnClickListener(this);

		isSelected = new Boolean[3];
		for (int i = 0; i < isSelected.length; i++) {
			isSelected[i] = false;
		}

		ShareSDK.initSDK(getApplicationContext());

		selectedApp = new App();
		selectedLabels = new ArrayList<String>();
		labelAdapter = new LabelAdapter(getApplicationContext(),
				new ArrayList<String>());
		gvLabels.setAdapter(labelAdapter);

		systemApps = SystemUtils.getUserApps(getApplicationContext());
		existedApps = new ArrayList<App>();
		RequestParams params = new RequestParams();
		for (int i = 0; i < systemApps.size(); i++) {
			params.add("packages[" + i + "]", systemApps.get(i)
					.getPackageName());
		}
		BaseAsyncHttp.postReq(getApplicationContext(), "/message/before-send",
				params, new JSONObjectHttpResponseHandler() {

					@Override
					public void jsonSuccess(JSONObject resp) {
						// TODO Auto-generated method stub
						for (int i = 0; i < systemApps.size(); i++) {
							App app = systemApps.get(i);
							String packagename = app.getPackageName();
							JSONObject appjson = resp
									.optJSONObject(packagename);
							if (appjson.optInt("exist") == 1) {
								app.setId(appjson.optInt("appid"));
								app.setIcon(appjson.optString("icon"));
								existedApps.add(app);
							}
						}
						ComplexPreferences.putObject(getApplicationContext(),
								Constants.ExistedApp, existedApps);
					}

					@Override
					public void jsonFail(JSONObject resp) {
						// TODO Auto-generated method stub
						Utils.showShortToast(getApplicationContext(), "网络故障");
					}
				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.txt_left:
			finish();
			break;
		case R.id.txt_save:
			String content = etContent.getText().toString();
			if (content.length() < 5) {
				Utils.showShortToast(getApplicationContext(), "请至少输入5个字");
				return;
			}
			if (TextUtils.isEmpty(selectedApp.getPackageName())) {
				Utils.showShortToast(getApplication(), "请选择一个应用");
				return;
			}
			int counts = (int) stars.getRating();
			if (counts == 0) {
				Utils.showShortToast(getApplication(), "请给出评分");
				return;
			}
			RequestParams params = new RequestParams();
			params.add("phone",
					PersonInfoLocal.getPhone(getApplicationContext()));
			params.add("content", content);
			params.add("appstars", String.valueOf(counts));
			params.add("apps[0][id]", String.valueOf(selectedApp.getId()));
			Log.d("params", params.toString());
			BaseAsyncHttp.postReq(getApplicationContext(), "/message/send",
					params, new JSONObjectHttpResponseHandler() {

						@Override
						public void jsonSuccess(JSONObject resp) {
							// TODO Auto-generated method stub
							Utils.showShortToast(getApplicationContext(),
									"发表分享成功");
							setResult(1);
							finish();
						}

						@Override
						public void jsonFail(JSONObject resp) {
							// TODO Auto-generated method stub
							Utils.showShortToast(getApplicationContext(),
									"网络故障，发表分享失败");
						}
					});

			String sharedUrl = Constants.PrefixShareUrl
					+ String.valueOf(selectedApp.getId());
			if (isSelected[0]) {
				ShareParams wxcomment = new ShareParams();
				wxcomment.setTitle("请下载我的App");
				wxcomment.setText("我们这里有最精彩的应用，快快来加入我们吧！");
				wxcomment.setUrl(sharedUrl);
				wxcomment.setImageData(DrawableStringUtils
						.stringtoBitmap(selectedApp.getDrawableString()));
				wxcomment.setShareType(Platform.SHARE_WEBPAGE);
				Platform wei = ShareSDK.getPlatform(WechatMoments.NAME);
				wei.share(wxcomment);
			}
			if (isSelected[1]) {
				ShareParams wechat = new ShareParams();
				wechat.setTitle("请下载我的App");
				wechat.setText("我们这里有最精彩的应用，快快来加入我们吧！");
				wechat.setUrl(sharedUrl);
				wechat.setImageUrl(selectedApp.getIcon());
				wechat.setShareType(Platform.SHARE_WEBPAGE);
				Platform wei = ShareSDK.getPlatform(Wechat.NAME);
				wei.share(wechat);
			}
			if (isSelected[2]) {
				ShareParams sinasp = new ShareParams();
				sinasp.setText("请下载我的App。  " + sharedUrl);
				sinasp.setImageUrl(selectedApp.getIcon());
				// sinasp.setImageData(DrawableStringUtils.stringtoBitmap(selectedApp.getDrawableString()));
				Platform sina = ShareSDK.getPlatform(SinaWeibo.NAME);
				sina.share(sinasp);
			}
			break;
		case R.id.iv_shareplus_app:
			Intent intent = new Intent(SharePlusActivity.this,
					AppListActivity.class);
			startActivityForResult(intent, 1);
			break;
		case R.id.wechatcomment:
			isSelected[0] = !isSelected[0];
			if (isSelected[0])
				wechatcomment.setImageResource(R.drawable.wechatcomment1);
			else
				wechatcomment.setImageResource(R.drawable.wechatcomment);
			break;
		case R.id.wechat:
			isSelected[1] = !isSelected[1];
			if (isSelected[1])
				wechat.setImageResource(R.drawable.wxchat1);
			else
				wechat.setImageResource(R.drawable.weixinlogin);
			break;
		case R.id.sina:
			isSelected[2] = !isSelected[2];
			if (isSelected[2])
				sina.setImageResource(R.drawable.sina1);
			else
				sina.setImageResource(R.drawable.weibologin);
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		Log.i("resultCode", String.valueOf(resultCode));
		switch (resultCode) {
		case 1:
			selectedApp = ComplexPreferences.getObject(getApplicationContext(),
					Constants.SelectedSharedApp, new TypeReference<App>() {
					});
			appicon.setBackground(DrawableStringUtils
					.stringToDrawable(selectedApp.getDrawableString()));
			break;
		}
	}

	class LabelAdapter extends BaseAdapter {
		private Context mContext;
		private List<String> labels;

		// Gets the context so it can be used later
		public LabelAdapter(Context mContent, List<String> labels) {
			this.mContext = mContent;
			this.labels = labels;
		}

		// Total number of things contained within the adapter
		public int getCount() {
			return labels.size();
		}

		// Require for structure, not really used in my code.
		public Object getItem(int position) {
			return null;
		}

		// Require for structure, not really used in my code. Can be used to get
		// the id of an item in the adapter for manual control.
		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			final Button btn;
			convertView = ((LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
					.inflate(R.layout.layout_grid_class, null);
			btn = (Button) convertView.findViewById(R.id.btn_class_kind);
			btn.setText(labels.get(position));
			final int pos = position;
			btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (selectedLabels.contains(labels.get(pos))) {
						selectedLabels.remove(labels.get(pos));
						btn.setBackgroundResource(R.color.transparent);
					} else {
						selectedLabels.add(labels.get(pos));
						btn.setBackgroundResource(R.drawable.fenleixuanze);
					}
				}
			});
			return convertView;
		}

		public void setData(List<String> labels) {
			this.labels = labels;
		}
	}
}
