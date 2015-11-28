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
import com.fasterxml.jackson.core.type.TypeReference;
import com.personlife.bean.App;
import com.personlife.utils.ComplexPreferences;
import com.personlife.utils.Constants;
import com.personlife.utils.DrawableStringUtils;
import com.personlife.utils.Utils;
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
		setContentView(R.layout.activity_app_select);
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
		apps = ComplexPreferences.getObject(getApplicationContext(),
				Constants.ExistedApp, new TypeReference<ArrayList<App>>() {
				});
		appsAdapter = new AppsAdapter(getApplicationContext());
		lvApps.setAdapter(appsAdapter);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txt_left:
			finish();
			break;
		case R.id.txt_save:
			if (appsAdapter.getSelectedIndex() < 0) {
				Utils.showShortToast(getApplicationContext(), "请选择一个应用");
				return;
			}
			ComplexPreferences.putObject(getApplicationContext(),
					Constants.SelectedSharedApp,
					apps.get(appsAdapter.getSelectedIndex()));
			setResult(1);
			finish();
			break;
		}
	}

	class AppsAdapter extends BaseAdapter {

		private Context context;
		private Boolean isAll = false;
		private int selectedIndex = -1;

		public AppsAdapter(Context context) {
			this.context = context;
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
			holder.appicon.setImageDrawable(DrawableStringUtils
					.stringToDrawable(apps.get(position).getDrawableString()));
			holder.appname.setText(apps.get(position).getName());
			if (selectedIndex == position)
				holder.check.setChecked(true);
			else
				holder.check.setChecked(false);
			// holder.check
			// .setOnCheckedChangeListener(new OnCheckedChangeListener() {
			//
			// @Override
			// public void onCheckedChanged(CompoundButton buttonView,
			// boolean isChecked) {
			// if (isChecked) {
			// if (!selectedApps.contains(mlist.get(position)))
			// selectedApps.add(mlist.get(position));
			// } else {
			// selectedApps.remove(mlist.get(position));
			// }
			// mTitle.setText("已选" + selectedApps.size() + "项");
			// Log.d(AppListActivity.class.getClass().getName(),
			// String.valueOf(position));
			// if (isChecked)
			// if(selectedIndex != position){
			// selectedIndex = position;
			// notifyDataSetChanged();
			// }
			// }
			// });
			convertView.setClickable(false);
			holder.check.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.d(AppListActivity.class.getClass().getName(),
							String.valueOf(position));
					selectedIndex = position;
					notifyDataSetChanged();
				}
			});
			return convertView;
		}

		public void setAll() {
			this.isAll = true;
		}

		public int getSelectedIndex() {
			return selectedIndex;
		}

		class ViewHolder {
			ImageView appicon;
			TextView appname;
			TextView size;
			CheckBox check;
		}
	}

}
