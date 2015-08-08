package com.personlife.adapter.home;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personlifep.R;
import com.personlife.bean.App;
import com.personlife.utils.Constants;
import com.personlife.utils.ImageLoaderUtils;
import com.personlife.view.activity.home.AppDetailActivity;



public class AppListAdapter extends BaseAdapter {

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
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		Log.i("adapter", "mlist size is " + mlist.size());
		if (convertView == null) {
			convertView = ((LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
					.inflate(R.layout.layout_item_app, null);
			holder = new ViewHolder();
			holder.appname = (TextView) convertView
					.findViewById(R.id.tv_app_appname);
			holder.tag = (TextView) convertView.findViewById(R.id.tv_app_tag);
			holder.downloadcounts = (TextView) convertView
					.findViewById(R.id.tv_app_downloadcounts);
			holder.icon = (ImageView) convertView
					.findViewById(R.id.iv_app_icon);
			holder.download = (TextView) convertView
					.findViewById(R.id.tv_app_download);
			holder.stars = (RatingBar) convertView
					.findViewById(R.id.rb_app_rating);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ImageLoaderUtils.displayAppIcon("https://ss0.bdstatic.com/-0U0bnSm1A5BphGlnYG/tam-ogel/5136becf77e9cfc440849e0b694fdd6e_121_121.jpg", holder.icon);
		holder.appname.setText(mlist.get(position).getName());
		holder.tag.setText(mlist.get(position).getTag());
		holder.stars.setProgress(mlist.get(position).getStars());
		holder.downloadcounts.setText("("
				+ mlist.get(position).getDowloadcount() + ")");
		holder.download.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(context,
						"downloading " + mlist.get(position).getDownloadUrl(),
						Toast.LENGTH_SHORT).show();
				;
			}
		});
		// ImageLoader.getInstance().displayImage(mlist.get(position).getBitmap(),holder.icon);
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Toast.makeText(context,
				// "app's id is"+mlist.get(position).getId(),
				// Toast.LENGTH_SHORT).show();;
				Log.i("adapter", "app's id is " + mlist.get(position).getId());
				Intent intent = new Intent(context, AppDetailActivity.class);
				intent.putExtra(Constants.AppId, mlist.get(position).getId());
				context.startActivity(intent);

			}
		});
		return convertView;
	}

	public void setData(List<App> list) {
		mlist = list;
	}

	class ViewHolder {
		ImageView icon;
		TextView appname;
		TextView tag;
		TextView downloadcounts;
		RatingBar stars;
		TextView download;
	}

}
