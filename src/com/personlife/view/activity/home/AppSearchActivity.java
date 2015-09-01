package com.personlife.view.activity.home;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.example.personlifep.R;
import com.fasterxml.jackson.core.type.TypeReference;
import com.loopj.android.http.RequestParams;
import com.personlife.adapter.AppListAdapter;
import com.personlife.adapter.AppsAdapter;
import com.personlife.bean.App;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONArrayHttpResponseHandler;
import com.personlife.net.JSONObjectHttpResponseHandler;
import com.personlife.utils.ComplexPreferences;
import com.personlife.utils.Constants;
import com.personlife.utils.Utils;
import com.personlife.widget.MyListView;

public class AppSearchActivity extends Activity implements OnClickListener {

	private Button cancel;
	private EditText search;
	private MyListView lvResult, lvHistory;
	private LinearLayout llLabel;
	private ScrollView slResult, slHistory;
	private TextView tvClearHistory;
	private TextView[] tvLables = new TextView[3];
	private String[] lables = { "90后", "工作", "旅游" };
	private int[] idLables = { R.id.tv_search_label1, R.id.tv_search_label2,
			R.id.tv_search_label3 };
	private List<String> history;
	private List<App> apps;
	private HistoryAdapter historyAdapter;
	private ResultAdapter resultAdapter;
	private AppsAdapter appsAdapter;
	Button mBack;
	TextView mTitle, clear;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_search);
		initView();
		initData();
	}

	private void initView() {
		mBack = (Button) findViewById(R.id.txt_left);
		mTitle = (TextView) findViewById(R.id.txt_title);
		cancel = (Button) findViewById(R.id.btn_search_concel);
		cancel.setOnClickListener(this);
		search = (EditText) findViewById(R.id.et_search_search);
		// search.requestFocus();

		mTitle.setText("搜索");
		mBack.setVisibility(View.VISIBLE);
		mBack.setOnClickListener(this);

		llLabel = (LinearLayout) findViewById(R.id.ll_search_label);
		slResult = (ScrollView) findViewById(R.id.sl_search_result);
		slHistory = (ScrollView) findViewById(R.id.sl_search_history);
		tvClearHistory = (TextView) findViewById(R.id.tv_search_clear);

		lvHistory = (MyListView) findViewById(R.id.lv_search_history);
		lvResult = (MyListView) findViewById(R.id.lv_search_result);
		clear = (TextView) findViewById(R.id.tv_search_clear);
		clear.setOnClickListener(this);

		for (int i = 0; i < lables.length; i++) {
			tvLables[i] = (TextView) findViewById(idLables[i]);
			tvLables[i].setText(lables[i]);
			tvLables[i].setOnClickListener(this);
		}

		apps = new ArrayList<App>();
//		resultAdapter = new ResultAdapter(apps);
//		lvResult.setAdapter(resultAdapter);
		appsAdapter = new AppsAdapter(getApplicationContext(), apps);
		lvResult.setAdapter(appsAdapter);

		ComplexPreferences pre = ComplexPreferences.getComplexPreferences(this,
				Constants.SharePrefrencesName);
		history = pre.getObject("history",
				new TypeReference<ArrayList<String>>() {
				});
		pre.commit();
		if (history == null)
			history = new ArrayList<String>();
		historyAdapter = new HistoryAdapter(history);
		lvHistory.setAdapter(historyAdapter);

		search.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus)
					showHistory();
				Log.i("focus change", String.valueOf(hasFocus));
			}
		});

		search.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// TODO Auto-generated method stub
				Log.i("AppSearchActivity actionId is ",
						String.valueOf(actionId));
				switch (actionId) {
				case EditorInfo.IME_ACTION_NEXT:
				case EditorInfo.IME_ACTION_DONE:
					// 添加搜索
					String key = search.getText().toString().trim();
					showResult(search.getText().toString().trim());
					history.add(key);
					ComplexPreferences pre = ComplexPreferences
							.getComplexPreferences(getApplicationContext(),
									Constants.SharePrefrencesName);
					pre.putObject("history", history);
					pre.commit();
					break;
				}
				return true;
			}
		});

	}

	private void initData() {
		// TODO Auto-generated method stub

	}

	public void showHistory() {
		if (history != null) {
			historyAdapter.setData(history);
			historyAdapter.notifyDataSetChanged();
		}

		llLabel.setVisibility(View.GONE);
		slHistory.setVisibility(View.VISIBLE);
		slResult.setVisibility(View.GONE);
	}

	public void showResult(String key) {
		llLabel.setVisibility(View.GONE);
		slResult.setVisibility(View.VISIBLE);
		slHistory.setVisibility(View.GONE);
//		resultAdapter.clear();
		appsAdapter.clear();
		RequestParams params = new RequestParams();
		params.add("name", key);
		BaseAsyncHttp.postReq(getApplicationContext(), "/app/search", params,
				new JSONArrayHttpResponseHandler() {

					@Override
					public void jsonSuccess(JSONArray resp) {
						// TODO Auto-generated method stub
						List<App> applist = new ArrayList<App>();
						if (resp.length() == 0)
							Utils.showLongToast(getApplicationContext(),
									"没有结果，请重新查找");
						try {
							for (int i = 0; i < resp.length(); i++) {
								App app = new App();
								JSONObject jsonapp = resp.getJSONObject(i);
								app.setIcon(jsonapp.getString("icon"));
								app.setSize(jsonapp.getString("size"));
								app.setDowloadcount(jsonapp
										.getInt("downloadcount"));
								app.setIntrodution(jsonapp
										.getString("introduction"));
								app.setName(jsonapp.getString("name"));
								app.setId(jsonapp.getInt("id"));
								app.setProfile(jsonapp.getString("profile"));
								app.setDownloadUrl(jsonapp
										.getString("android_url"));
								app.setDownloadPath(Constants.DownloadPath
										+ app.getName() + ".apk");
								applist.add(app);
							}
//							resultAdapter.setData(applist);
//							resultAdapter.notifyDataSetChanged();
							appsAdapter.setData(applist);
							appsAdapter.notifyDataSetChanged();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void jsonFail(JSONArray resp) {
						// TODO Auto-generated method stub
						Utils.showLongToast(getApplicationContext(), "请连接网络");
					}
				});
	}

	public void showKindResult(String kind) {
		llLabel.setVisibility(View.GONE);
		slResult.setVisibility(View.VISIBLE);
		slHistory.setVisibility(View.GONE);
		appsAdapter.clear();
		RequestParams params = new RequestParams();
		params.add("tag", kind);
		BaseAsyncHttp.postReq(getApplicationContext(), "/myapp/tag", params,
				new JSONObjectHttpResponseHandler() {

					@Override
					public void jsonSuccess(JSONObject resp) {
						// TODO Auto-generated method stub
						List<App> applist = new ArrayList<App>();
						try {
							JSONArray jsonapps = resp.getJSONArray("item");
							for (int i = 0; i < jsonapps.length(); i++) {
								App app = new App();
								JSONObject jsonapp = jsonapps.getJSONObject(i);
								app.setIcon(jsonapp.getString("icon"));
								app.setSize(jsonapp.getString("size"));
								app.setDowloadcount(jsonapp
										.getInt("downloadcount"));
								app.setIntrodution(jsonapp
										.getString("introduction"));
								app.setName(jsonapp.getString("name"));
								app.setId(jsonapp.getInt("appid"));
								app.setDownloadUrl(jsonapp
										.getString("android_url"));
								app.setProfile(jsonapp.getString("profile"));
								app.setDownloadPath(Constants.DownloadPath
										+ app.getName() + ".apk");
								applist.add(app);
							}
							appsAdapter.setData(applist);
							appsAdapter.notifyDataSetChanged();
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txt_left:
			finish();
			break;
		case R.id.btn_search_concel:
			search.setText("");
			search.clearFocus();
			llLabel.setVisibility(View.VISIBLE);
			slHistory.setVisibility(View.GONE);
			slResult.setVisibility(View.GONE);
			break;
		case R.id.tv_search_label1:
			showKindResult(lables[0]);
			break;
		case R.id.tv_search_label2:
			showKindResult(lables[1]);
			break;
		case R.id.tv_search_label3:
			showKindResult(lables[2]);
			break;
		case R.id.tv_search_clear:
			history.clear();
			ComplexPreferences pre = ComplexPreferences.getComplexPreferences(
					this, Constants.SharePrefrencesName);
			pre.putObject("hostory", history);
			pre.commit();
			historyAdapter.setData(history);
			historyAdapter.notifyDataSetChanged();

		default:
			break;
		}
	}

	class HistoryAdapter extends BaseAdapter {
		List<String> history;

		public HistoryAdapter(List<String> mlist) {
			this.history = mlist;
		}

		@Override
		public int getCount() {
			return history.size();
		}

		@Override
		public Object getItem(int arg0) {
			return arg0;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			convertView = ((LayoutInflater) getApplicationContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
					.inflate(R.layout.layout_item_result, null);
			TextView name = (TextView) convertView
					.findViewById(R.id.tv_search_name);
			ImageView iv = (ImageView) convertView
					.findViewById(R.id.iv_search_toubiao);
			iv.setBackgroundResource(R.drawable.sousuojieguotoubiao);
			name.setText(history.get(position));
			Log.i("result history size", String.valueOf(history.size()));
			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					showResult(history.get(position));
				}
			});
			return convertView;
		}

		public void setData(List<String> list) {
			this.history = list;
		}

	}

	class ResultAdapter extends BaseAdapter {
		List<App> apps;

		public ResultAdapter(List<App> mlist) {
			this.apps = mlist;
		}

		@Override
		public int getCount() {
			return apps.size();
		}

		@Override
		public Object getItem(int arg0) {
			return arg0;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			convertView = ((LayoutInflater) getApplicationContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
					.inflate(R.layout.layout_item_result, null);
			TextView name = (TextView) convertView
					.findViewById(R.id.tv_search_name);
			name.setText(apps.get(position).getName());
			Log.i("search result", String.valueOf(apps.size()));
			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(AppSearchActivity.this, AppDetailActivity.class);
					intent.putExtra(Constants.AppId, apps.get(position).getId());
					AppSearchActivity.this.startActivity(intent);
				}
			});
			return convertView;
		}

		public void setData(List<App> list) {
			this.apps = list;
		}

		public void clear() {
			apps.clear();
			notifyDataSetChanged();
		}

	}

}
