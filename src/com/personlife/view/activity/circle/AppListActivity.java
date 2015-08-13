package com.personlife.view.activity.circle;

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
import com.personlife.bean.App;
import com.personlife.utils.ComplexPreferences;
import com.personlife.utils.SystemUtils;
import com.personlife.widget.MyListView;

public class AppListActivity extends Activity implements OnClickListener {
	MyListView lvApps;
	Button mBack, mSave;
	TextView mTitle;
	AppsAdapter appsAdapter;
	List<App> apps;
	List<App> selectedApps;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_download);
		lvApps = (MyListView) findViewById(R.id.lv_alldownload_apps);
		mBack = (Button) findViewById(R.id.txt_left);
		mTitle = (TextView) findViewById(R.id.txt_title);
		mSave = (Button) findViewById(R.id.txt_save);
		mTitle.setText("APP列表");
		mBack.setVisibility(View.VISIBLE);
		mSave.setVisibility(View.VISIBLE);
		mBack.setText("取消");
		mSave.setText("完成");
		mBack.setOnClickListener(this);
		mSave.setOnClickListener(this);
		initData();
	}

	private void initData() {
		selectedApps = new ArrayList<App>();
		apps = SystemUtils.getAppsNoSystom(getApplicationContext());
		appsAdapter = new AppsAdapter(getApplicationContext(), apps);
		lvApps.setAdapter(appsAdapter);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txt_left:
			setResult(RESULT_CANCELED);
			finish();
			break;
		case R.id.txt_save:
			ComplexPreferences complexPreferences = ComplexPreferences
					.getComplexPreferences(this, "pfy", MODE_PRIVATE);
			complexPreferences.putObject("selectedApps", selectedApps);
			complexPreferences.commit();
			setResult(RESULT_OK,getIntent());
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

			holder.size.setVisibility(View.GONE);
			holder.appicon.setImageDrawable(mlist.get(position).getAppIcon());
			holder.appname.setText(mlist.get(position).getName());

			holder.check
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							// TODO Auto-generated method stub
							Log.i("check changed" + position + " state",
									String.valueOf(isChecked));
							if (isChecked) {
								if (!selectedApps.contains(mlist.get(position)))
									selectedApps.add(mlist.get(position));
							} else {
								selectedApps.remove(mlist.get(position));
							}
							mTitle.setText("已选" + selectedApps.size() + "项");
						}
					});

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
