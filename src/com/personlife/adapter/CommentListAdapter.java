package com.personlife.adapter;

import java.util.Date;
import java.util.List;

import com.example.personlifep.R;
import com.personlife.bean.Comment;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;



public class CommentListAdapter extends BaseAdapter {

	private Context context;
	private List<Comment> mlist;
	public CommentListAdapter(Context context, List<Comment> mlist) {
		this.context = context;
		this.mlist = mlist;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mlist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		Log.i("CommentListAdapter", "mlist size is " + mlist.size());
		if (convertView == null) {
			convertView = ((LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
					.inflate(R.layout.layout_item_comment, null);
			holder = new ViewHolder();
//			holder.title = (TextView) convertView
//					.findViewById(R.id.tv_comment_title);
//			holder.createdDate = (TextView) convertView
//					.findViewById(R.id.tv_comment_createdDate);
//			holder.username = (TextView) convertView
//					.findViewById(R.id.tv_comment_username);
			holder.content = (TextView) convertView
					.findViewById(R.id.tv_comment_content);
			holder.stars = (RatingBar) convertView
					.findViewById(R.id.rb_comment_rating);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.title.setText(String.format("%d��%s",position+1,mlist.get(position).getTitle()));
//		holder.stars.setProgress(mlist.get(position).getStars());
//		holder.content.setText(mlist.get(position).getContent());
//		holder.username.setText(mlist.get(position).getUsername());
//		Date createdDate = mlist.get(position).getCreatedDate();
//		holder.createdDate.setText(String.format("%d��%d��%d��", createdDate.getYear(),createdDate.getMonth(),createdDate.getDay()));
		return convertView;
	}
	public void setData(List<Comment> list) {
		mlist = list;
	}

	class ViewHolder {
		TextView title;
		TextView username;
		TextView createdDate;
		RatingBar stars;
		TextView content;
	}
}
