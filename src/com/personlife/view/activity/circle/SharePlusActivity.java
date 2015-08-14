package com.personlife.view.activity.circle;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import android.widget.TextView;

import com.example.personlifep.R;
import com.fasterxml.jackson.core.type.TypeReference;
import com.personlife.bean.App;
import com.personlife.bean.User;
import com.personlife.utils.ComplexPreferences;
import com.personlife.utils.Constants;
import com.personlife.utils.DrawableStringUtils;
import com.personlife.utils.Utils;

public class SharePlusActivity extends Activity implements OnClickListener {
	Button mBack, save;
	TextView mTitle;
	EditText etContent;
	GridView gvApps;
	Button btnXiazai, btnFenxiang, btnShoucang;
	TextView tvRange;
	String sharekinds[] = { "下载", "分享", "收藏" };
	int selectedkind = 0;
	List<App>selectedApps ;
	App defaultapp;
	AppIconAdapter appsAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shareplus);
		mBack = (Button) findViewById(R.id.txt_left);
		save = (Button) findViewById(R.id.txt_save);
		etContent = (EditText) findViewById(R.id.dt_shareplus_content);
		gvApps = (GridView) findViewById(R.id.gv_shareplus_apps);
		btnXiazai = (Button) findViewById(R.id.btn_shareplus_xiazai);
		btnFenxiang = (Button) findViewById(R.id.btn_shareplus_fenxiang);
		btnShoucang = (Button) findViewById(R.id.btn_shareplus_shoucang);
		tvRange = (TextView) findViewById(R.id.tv_shareplus_range);
		mTitle = (TextView) findViewById(R.id.txt_title);

		mBack.setVisibility(View.VISIBLE);
		save.setVisibility(View.VISIBLE);
		mTitle.setVisibility(View.GONE);
		
		mBack.setText("取消");
		save.setText("发表");

		btnXiazai.setBackgroundResource(R.drawable.xuanzhong); // 默认选中下载

		mBack.setOnClickListener(this);
		save.setOnClickListener(this);
		btnXiazai.setOnClickListener(this);
		btnFenxiang.setOnClickListener(this);
		btnShoucang.setOnClickListener(this);
		tvRange.setOnClickListener(this);
		
		selectedApps = new ArrayList<App>();
		defaultapp = new App();
		defaultapp.setDrawableString(DrawableStringUtils.drawableToString(getResources().getDrawable(R.drawable.fabiaofenxiang)));
		selectedApps.add(defaultapp);
		
		appsAdapter = new AppIconAdapter(getApplicationContext(), selectedApps);
		gvApps.setAdapter(appsAdapter);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txt_left:
			finish();
			break;
		case R.id.txt_save:

			break;
		case R.id.tv_shareplus_range:
			Intent intent = new Intent(SharePlusActivity.this, ShareRangeActivity.class);
			startActivityForResult(intent, 2);
			break;
		case R.id.btn_shareplus_xiazai:
			selectedkind = 0;
			btnXiazai.setBackgroundResource(R.drawable.xuanzhong);
			btnFenxiang.setBackgroundResource(R.color.transparent);
			btnShoucang.setBackgroundResource(R.color.transparent);
			break;
		case R.id.btn_shareplus_fenxiang:
			selectedkind = 1;
			btnXiazai.setBackgroundResource(R.color.transparent);
			btnFenxiang.setBackgroundResource(R.drawable.xuanzhong);
			btnShoucang.setBackgroundResource(R.color.transparent);
			break;
		case R.id.btn_shareplus_shoucang:
			selectedkind = 2;
			btnXiazai.setBackgroundResource(R.color.transparent);
			btnFenxiang.setBackgroundResource(R.color.transparent);
			btnShoucang.setBackgroundResource(R.drawable.xuanzhong);
			break;

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		Log.i("resultCode", String.valueOf(resultCode));
		switch (resultCode) {
		case 1:
			 ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(this, Constants.SharePrefrencesName);
			 if(complexPreferences.getObject("selectedApps", new TypeReference<ArrayList<App>>(){})!=null){
				 selectedApps = complexPreferences.getObject("selectedApps", new TypeReference<ArrayList<App>>(){});
				 selectedApps.add(defaultapp);
				 appsAdapter.setData(selectedApps);
				 appsAdapter.notifyDataSetChanged();
			 }
			break;
		case 2:
			int selected = data.getIntExtra("rangeIndex", 0);
			
			Utils.showShortToast(getApplication(), String.valueOf(selected));
		default:
			break;
		}
	}
	public class AppIconAdapter extends BaseAdapter {
		private Context context;
		public List<App> apps;

		// Gets the context so it can be used later
		public AppIconAdapter(Context c, List<App> apps) {
			context = c;
			this.apps = apps;
		}
		public void setData(List<App> apps){
			this.apps = apps;
		}
		// Total number of things contained within the adapter
		public int getCount() {
			return apps.size();
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
			final int current = position;
			
			convertView = ((LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
					.inflate(R.layout.layout_grid_appicon, null);
			
			ImageView btn = (ImageView) convertView.findViewById(R.id.iv_shareplus_appicon);
			btn.setBackground(DrawableStringUtils.stringToDrawable(apps.get(position).getDrawableString()));
			btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(current == (getCount()-1)){
						Intent intent = new Intent(SharePlusActivity.this, AppListActivity.class);
						startActivityForResult(intent, 1);
					}
				}
			});

			return convertView;
		}
	}
}
