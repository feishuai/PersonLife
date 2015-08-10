package com.personlife.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personlifep.R;
import com.personlife.bean.App;

public class AppsAdapter extends BaseAdapter {

	private Context context;
	private List<App> mlist;

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
					.inflate(R.layout.layout_item_tuijian, null);
			holder = new ViewHolder();
			holder.appname = (TextView) convertView
					.findViewById(R.id.tv_tuijian_name);
			holder.status = (TextView) convertView
					.findViewById(R.id.tv_tuijian_status);
			holder.intro = (TextView) convertView
					.findViewById(R.id.tv_tuijian_intro);
			holder.icon = (ImageView) convertView
					.findViewById(R.id.iv_tuijian_icon);
			holder.download = (Button) convertView
					.findViewById(R.id.btn_tuijian_tuijian);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// ImageLoaderUtils.displayAppIcon("https://ss0.bdstatic.com/-0U0bnSm1A5BphGlnYG/tam-ogel/5136becf77e9cfc440849e0b694fdd6e_121_121.jpg",
		// holder.icon);
	
		holder.download.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(
						context,
						"downloading "
								+ mlist.get(position).getDownloadUrl(),
						Toast.LENGTH_SHORT).show();
				;
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
		TextView status;
		TextView intro;
		Button download;
	}
}