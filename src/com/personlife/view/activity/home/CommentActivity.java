package com.personlife.view.activity.home;

import com.example.personlifep.R;
import com.personlife.widget.MyListView;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

public class CommentActivity extends Activity {
	MyListView comments;
	ImageButton mBack;
	ImageView mIcon;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		comments = (MyListView) findViewById(R.id.lv_comment_comment);
		comments.setAdapter(mAUrls);
	}

	private static String[] urls = new String[] {   "Text #2", "Text #3" };
	private BaseAdapter mAUrls = new BaseAdapter() {

		@Override
		public int getCount() {
			return urls.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View retval = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.layout_item_comment, null);
			// TextView title = (TextView) retval.findViewById(R.id.title);
			// title.setText(dataObjects[position]);

			return retval;
		}

	};

}
