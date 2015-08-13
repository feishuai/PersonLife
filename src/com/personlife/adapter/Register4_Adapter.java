package com.personlife.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.personlifep.R;
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
	private List<Picture> pictures;

	public Register4_Adapter(String[] titles, int[] images, Context context) {
		super();
		pictures = new ArrayList<Picture>();
		inflater = LayoutInflater.from(context);
		for (int i = 0; i < images.length; i++) {
			Picture picture = new Picture(titles[i], images[i]);
			pictures.add(picture);
		}
	}

	@Override
	public int getCount() {
		if (null != pictures) {
			return pictures.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		return pictures.get(position);
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
		viewHolder.star_name.setText(pictures.get(position).getTitle());
		viewHolder.picture.setBackgroundResource(pictures.get(position).getImageId());
		return convertView;
	}

}

class ViewHolder {
	public ImageView picture;
	public TextView star_name;
}

class Picture {
	private String title;
	private int imageId;

	public Picture() {
		super();
	}

	public Picture(String title, int imageId) {
		super();
		this.title = title;
		this.imageId = imageId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
}