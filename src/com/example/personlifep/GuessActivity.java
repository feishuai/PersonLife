package com.example.personlifep;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import com.personlife.bean.App;
import com.personlife.utils.Constants;
import com.personlife.view.activity.home.AppDetailActivity;
import com.personlife.widget.MyListView;

public class GuessActivity extends Activity implements OnClickListener {
	private MyListView lv;
	private ImageView back1, back2;
	private TextView tvContent, mTitle;
	private Button mBack;
	private String[] titles = { "猜您喜欢", "办公必备", "您的朋友喜欢" };
	private String[] contents = { "根据您的爱好生成，实时更新", "根据您的职业给您推荐相关APP",
			"好朋友的喜好全部都在这儿呢" };
	private Integer[] urls = { R.drawable.back1, R.drawable.back2,
			R.drawable.back3 };
	private Integer[] icons = { R.drawable.caininxihuan,
			R.drawable.bangongbibei, R.drawable.nindepengyouxihuan };
	
	private AppListAdapter aApps;
	private List<App> mList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guess);
		lv = (MyListView) findViewById(R.id.lv_guess_app);
		back1 = (ImageView) findViewById(R.id.iv_guess_back);
		back2 = (ImageView) findViewById(R.id.iv_guess_back1);
		tvContent = (TextView) findViewById(R.id.tv_guess_content);
		mBack = (Button) findViewById(R.id.txt_left);
		mBack.setVisibility(View.VISIBLE);
		mBack.setOnClickListener(this);
		mTitle = (TextView) findViewById(R.id.txt_title);
		initData();
	}

	private void initData() {
		int kind = Integer.parseInt(getIntent().getStringExtra("kind"));
		mTitle.setText(titles[kind]);
		back1.setBackground(getResources().getDrawable(urls[kind]));
		back2.setBackground(getResources().getDrawable(icons[kind]));
		tvContent.setText(contents[kind]);
		mList = new ArrayList<App>();
		mList.add(new App());
		mList.add(new App());
		mList.add(new App());
		aApps = new AppListAdapter(getApplicationContext(), mList);
		lv.setAdapter(aApps);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txt_left:
			finish();
			break;
		}
	}

	private class AppListAdapter extends BaseAdapter {

		private Context context;
		private List<App> mlist;

		public AppListAdapter(Context context, List<App> mlist) {
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
			Log.i("adapter", "mlist size is " + mlist.size());
			if (convertView == null) {
				convertView = ((LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
						.inflate(R.layout.layout_item_download, null);
				holder = new ViewHolder();
				holder.appname = (TextView) convertView
						.findViewById(R.id.tv_download_name);
				holder.status = (TextView) convertView
						.findViewById(R.id.tv_download_status);
				holder.icon = (ImageView) convertView
						.findViewById(R.id.iv_download_icon);
				holder.download = (Button) convertView
						.findViewById(R.id.btn_download_download);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			// ImageLoaderUtils
			// .displayAppIcon(
			// "https://ss0.bdstatic.com/-0U0bnSm1A5BphGlnYG/tam-ogel/5136becf77e9cfc440849e0b694fdd6e_121_121.jpg",
			// holder.icon);
			// holder.appname.setText(mlist.get(position).getName());
			// holder.tag.setText(mlist.get(position).getTag());
			// holder.stars.setProgress(mlist.get(position).getStars());
			// holder.downloadcounts.setText("("
			// + mlist.get(position).getDowloadcount() + ")");
			holder.download.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(context, "downloading", Toast.LENGTH_SHORT)
							.show();
					;
				}
			});
			// ImageLoader.getInstance().displayImage(mlist.get(position).getBitmap(),holder.icon);
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
//					Intent intent = new Intent(context, AppDetailActivity.class);
//					intent.putExtra(Constants.AppId, mlist.get(position)
//							.getId());
//					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//					context.startActivity(intent);

				}
			});
			return convertView;
		}

		public void setData(List<App> list) {
			mlist = list;
		}

		private class ViewHolder {
			ImageView icon;
			TextView appname;
			TextView status;
			Button download;
		}

	}
}
