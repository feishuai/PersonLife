package com.personlife.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.personlifep.R;
import com.personlife.bean.Star;
import com.personlife.utils.ImageLoaderUtils;
import com.personlife.view.activity.circle.CircleActivity;

public class StarAdapter extends BaseAdapter {

	private Context context;
	private List<Star> mList;

	public StarAdapter(Context context, List<Star> mList) {
		this.context = context;
		this.mList = mList;
	}

	@Override
	public int getCount() {
		return mList.size();
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
		if (convertView == null) {
			convertView = ((LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
					.inflate(R.layout.layout_item_starrecommend, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView
					.findViewById(R.id.tv_starrecommend_name);
			holder.fanscounts = (TextView) convertView
					.findViewById(R.id.tv_starrecommend_fanscounts);
			holder.icon = (ImageView) convertView
					.findViewById(R.id.iv_starrecommend_icon);
			holder.home = (ImageButton) convertView
					.findViewById(R.id.ib_starrecommend_home);
			holder.lastesttime = (TextView) convertView
					.findViewById(R.id.tv_starrecommend_lastesttime);
			holder.downloadcounts = (TextView) convertView
					.findViewById(R.id.tv_starrecommend_downloadcounts);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ImageLoaderUtils.displayImageView(mList.get(position).getThumb(),
				holder.icon);
		holder.name.setText(mList.get(position).getNickname());
		holder.fanscounts.setText(mList.get(position).getFollower());
		holder.downloadcounts.setText(mList.get(position).getShared());
		holder.home.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, CircleActivity.class);
				intent.putExtra("starphone", mList.get(position).getPhone());
				intent.putExtra("starnickname", mList.get(position)
						.getNickname());
				intent.putExtra("starthumb", mList.get(position).getThumb());
				intent.putExtra("starfollowers", mList.get(position)
						.getFollower());
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
			}
		});
		return convertView;
	}

	public void setData(List<Star> list) {
		this.mList = list;
	}

	class ViewHolder {
		ImageView icon;
		TextView downloadcounts;
		TextView name;
		TextView fanscounts;
		TextView lastesttime;
		ImageButton home;
	}
}
