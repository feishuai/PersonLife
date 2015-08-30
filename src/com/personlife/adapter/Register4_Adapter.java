package com.personlife.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.personlifep.R;
import com.github.snowdream.android.util.Log;
import com.personlife.bean.Star;
import com.personlife.utils.ImageLoaderUtils;
import com.personlife.widget.CircleImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author liugang
 * @date 2015年8月12日
 */
public class Register4_Adapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<Star> liststar;
	private Context context;
	public Register4_Adapter(Context context,List<Star> liststar) {	
		this.context=context;
		inflater = LayoutInflater.from(context);
		this.liststar=liststar;
	}

	@Override
	public int getCount() {
		if (null != liststar) {
			return liststar.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		return liststar.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.register4_item, null);
			viewHolder = new ViewHolder();
			viewHolder.star_name = (TextView) convertView.findViewById(R.id.register4_grid_starname);
			viewHolder.picture = (ImageView) convertView.findViewById(R.id.register4_grid_staricon);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.star_name.setText(liststar.get(position).getNickname());
		ImageLoaderUtils.displayImageView(liststar.get(position).getThumb(), viewHolder.picture);
		
		return convertView;
	}

}

class ViewHolder {
	public ImageView picture;
	public TextView star_name;
}
