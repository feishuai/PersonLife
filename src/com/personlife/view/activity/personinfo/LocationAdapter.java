package com.personlife.view.activity.personinfo;

import com.example.personlifep.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**  
 *   
 * @author liugang  
 * @date 2015年8月6日   
 */
public class LocationAdapter extends ArrayAdapter<String>{

	private int resourceId;
	public LocationAdapter(Context context, int resource, String[] objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		resourceId = resource;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		String item = getItem(position);
		View view;
		ViewHolder viewHolder;
		
		if(convertView==null){
			view=LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.tx = (TextView) view.findViewById(R.id.item_location);
			view.setTag(viewHolder);
			
		}else{
			 view = convertView;
			 viewHolder = (ViewHolder) view.getTag();
		}

		viewHolder.tx.setText(item);
		return view;
	}
	class ViewHolder {
		TextView tx;
	}
}
