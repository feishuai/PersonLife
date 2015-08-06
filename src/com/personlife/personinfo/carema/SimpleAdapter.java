package com.personlife.personinfo.carema;

import com.example.personlifep.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class SimpleAdapter extends BaseAdapter {

  private LayoutInflater layoutInflater;

  public SimpleAdapter(Context context) {
    layoutInflater = LayoutInflater.from(context);
  }

  @Override
  public int getCount() {
    return 3;
  }

  @Override
  public Object getItem(int position) {
    return position;
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder viewHolder;
    View view = convertView;

    if (view == null) {
    	view = layoutInflater.inflate(R.layout.simple_list_item, parent, false);

      viewHolder = new ViewHolder();
      viewHolder.textView = (TextView) view.findViewById(R.id.text_view);
      view.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) view.getTag();
    }

    Context context = parent.getContext();
    switch (position) {
      case 0:
        viewHolder.textView.setText("拍摄");        
        break;
      case 1:
        viewHolder.textView.setText("从手机相册选择");        
        break;
      default:
        viewHolder.textView.setText("取消");       
        break;
    }

    return view;
  }

  static class ViewHolder {
    TextView textView;
  }
}
