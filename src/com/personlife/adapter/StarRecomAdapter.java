package com.personlife.adapter;

import java.util.List;

import com.example.personlifep.R;
import com.personlife.bean.Star;
import com.personlife.utils.ImageLoaderUtils;

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
 * @date 2015年8月13日   
 */
public class StarRecomAdapter extends BaseAdapter{

	private LayoutInflater inflater;
	private List<Star> liststar;
	public StarRecomAdapter(Context ctx,List<Star> list){
		super();
		inflater = LayoutInflater.from(ctx);
		this.liststar=list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(null!=liststar){
			return liststar.size();
		}else return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return liststar.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if(convertView==null){
			convertView=inflater.inflate(R.layout.layout_grid_star, null);
			viewHolder=new ViewHolder();
			viewHolder.starpict=(ImageView) convertView.findViewById(R.id.iv_grid_staricon);
			viewHolder.starname=(TextView) convertView.findViewById(R.id.tv_grid_starname);
			viewHolder.counts=(TextView) convertView.findViewById(R.id.tv_grid_counts);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		viewHolder.starname.setText(liststar.get(position).getNickname());
		viewHolder.counts.setText(liststar.get(position).getShared());
		ImageLoaderUtils.displayImageView(liststar.get(position).getThumb(), viewHolder.starpict);
		return convertView;
	}
	class ViewHolder{
		public ImageView starpict;
		public TextView starname;
		public TextView counts;
	}
}
