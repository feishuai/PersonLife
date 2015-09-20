package com.personlife.view.activity.home;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.personlifep.R;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.snowdream.android.app.DownloadListener;
import com.github.snowdream.android.app.DownloadTask;
import com.personlife.bean.App;
import com.personlife.net.DownloadTaskManager;
import com.personlife.utils.ComplexPreferences;
import com.personlife.utils.Constants;
import com.personlife.utils.ImageLoaderUtils;
import com.personlife.utils.Utils;
import com.personlife.widget.MyListView;

public class AllDownloadActivity extends Activity implements OnClickListener {
	MyListView lvApps;
	Button mBack, mAll, download;
	TextView mTitle;
	AppsAdapter appsAdapter;
	List<App> apps;
	List<App> mDownloadList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_download);
		lvApps = (MyListView) findViewById(R.id.lv_alldownload_apps);
		mBack = (Button) findViewById(R.id.txt_left);
		mTitle = (TextView) findViewById(R.id.txt_title);
		mAll = (Button) findViewById(R.id.btn_title_right);
		download = (Button) findViewById(R.id.btn_alldownload_download);
		download.setOnClickListener(this);
		mTitle.setText("已选0项");
		mBack.setVisibility(View.VISIBLE);
		mAll.setVisibility(View.VISIBLE);
		mBack.setText("取消");
		mAll.setText("全选");
		mBack.setOnClickListener(this);
		mAll.setOnClickListener(this);
		initData();
	}

	private void initData() {
		mDownloadList = new ArrayList<App>();
		apps = new ArrayList<App>();
		apps = ComplexPreferences.getComplexPreferences(
				getApplicationContext(), Constants.SharePrefrencesName)
				.getObject(getIntent().getStringExtra("key"),
						new TypeReference<ArrayList<App>>() {
						});
		// RequestParams params = new RequestParams();
		// params.add("kind", "娱乐");
		// BaseAsyncHttp.postReq(getApplicationContext(), "/app/kind", params,
		// new JSONObjectHttpResponseHandler() {
		//
		// @Override
		// public void jsonSuccess(JSONObject resp) {
		// // TODO Auto-generated method stub
		// try {
		// JSONArray jsonapps = resp.getJSONArray("item");
		// for (int i = 0; i < jsonapps.length(); i++) {
		// App app = new App();
		// JSONObject jsonapp = jsonapps.getJSONObject(i);
		// app.setIcon(jsonapp.getString("icon"));
		// app.setSize(jsonapp.getString("size"));
		// app.setDowloadcount(jsonapp
		// .getInt("downloadcount"));
		// app.setIntrodution(jsonapp
		// .getString("introduction"));
		// app.setName(jsonapp.getString("name"));
		// app.setId(jsonapp.getInt("id"));
		// app.setDownloadUrl(jsonapp
		// .getString("android_url"));
		// app.setProfile(jsonapp.getString("profile"));
		// app.setDownloadPath(Constants.DownloadPath
		// + app.getName() + ".apk");
		// apps.add(app);
		// }
		appsAdapter = new AppsAdapter(getApplicationContext(), apps);
		lvApps.setAdapter(appsAdapter);
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		//
		// @Override
		// public void jsonFail(JSONObject resp) {
		// // TODO Auto-generated method stub
		//
		// }
		// });
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txt_left:
			finish();
			break;
		case R.id.btn_title_right:
			appsAdapter.setAll();
			appsAdapter.notifyDataSetChanged();
			break;
		case R.id.btn_alldownload_download:
			for (int i = 0; i < mDownloadList.size(); i++) {
				App app = mDownloadList.get(i);
				if (DownloadTaskManager.getDownloadTaskManager(
						getApplicationContext()).isHasDownloaded(app)) {
					DownloadTaskManager.getDownloadTaskManager(
							getApplicationContext()).startContinueDownload(
							getApplicationContext(), app,
							new DownloadListener<Integer, DownloadTask>());
				} else {
					DownloadTaskManager.getDownloadTaskManager(
							getApplicationContext()).startNewDownload(
							getApplicationContext(), app,
							new DownloadListener<Integer, DownloadTask>());
				}

			}
			Utils.showShortToast(getApplicationContext(), mDownloadList.size()
					+ "个应用正在下载");
			finish();
			break;
		}
	}

	class AppsAdapter extends BaseAdapter {

		private Context context;
		private List<App> mlist;
		private Boolean isAll = false;

		public AppsAdapter(Context context, List<App> mlist) {
			this.context = context;
			this.mlist = mlist;
		}

		@Override
		public int getCount() {
			return mlist.size();
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
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = ((LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
						.inflate(R.layout.layout_item_alldownload, null);
				holder = new ViewHolder();
				holder.appname = (TextView) convertView
						.findViewById(R.id.tv_alldown_appname);
				holder.size = (TextView) convertView
						.findViewById(R.id.tv_alldown_size);
				holder.appicon = (ImageView) convertView
						.findViewById(R.id.iv_alldown_appicon);
				holder.check = (CheckBox) convertView
						.findViewById(R.id.cb_alldown_state);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			ImageLoaderUtils.displayAppIcon(mlist.get(position).getIcon(),
					holder.appicon);
			holder.appname.setText(mlist.get(position).getName());
			holder.size.setText("(" + mlist.get(position).getSize() + ")");
			holder.check
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							// TODO Auto-generated method stub
							Log.i("check changed" + position + " state",
									String.valueOf(isChecked));
							if (isChecked) {
								if (!mDownloadList.contains(mlist.get(position)))
									mDownloadList.add(mlist.get(position));
							} else {
								mDownloadList.remove(mlist.get(position));
							}
							mTitle.setText("已选" + mDownloadList.size() + "项");
						}
					});
			Log.i("check " + position + " state",
					String.valueOf(holder.check.isChecked()));
			if (isAll)
				holder.check.setChecked(true);

			return convertView;
		}

		public void setAll() {
			this.isAll = true;
		}

		class ViewHolder {
			ImageView appicon;
			TextView appname;
			TextView size;
			CheckBox check;
		}
	}

}
