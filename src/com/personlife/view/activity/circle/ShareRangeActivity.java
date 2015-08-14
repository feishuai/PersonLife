package com.personlife.view.activity.circle;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.personlifep.R;

public class ShareRangeActivity extends Activity implements OnClickListener {
	Button mBack, save;
	TextView mTitle;
	String[] ranges = { "所有人可见", "仅好友可见", "仅自己可见" };
	int selected = 0;
	ListView lv;
	RangesAdapter rangesAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sharerange);
		mBack = (Button) findViewById(R.id.txt_left);
		save = (Button) findViewById(R.id.txt_save);

		mTitle = (TextView) findViewById(R.id.txt_title);

		mBack.setVisibility(View.VISIBLE);
		save.setVisibility(View.VISIBLE);
		mTitle.setVisibility(View.GONE);

		mBack.setText("取消");
		save.setText("完成");

		mBack.setOnClickListener(this);
		save.setOnClickListener(this);

		lv = (ListView) findViewById(R.id.lv_sharerange_list);
		rangesAdapter = new RangesAdapter(getApplicationContext());
		lv.setAdapter(rangesAdapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				selected = position;
				rangesAdapter.notifyDataSetChanged();
			}
		});
	}

	class RangesAdapter extends BaseAdapter {

		private Context context;

		public RangesAdapter(Context context) {
			this.context = context;
		}

		@Override
		public int getCount() {
			return ranges.length;
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
						.inflate(R.layout.layout_item_sharerange, null);
				holder = new ViewHolder();
				holder.range = (TextView) convertView
						.findViewById(R.id.tv_sharerange_range);
				holder.select = (ImageView) convertView
						.findViewById(R.id.iv_sharerange_select);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.range.setText(ranges[position]);
			holder.select.setVisibility(View.GONE);
			if (position == selected)
				holder.select.setVisibility(View.VISIBLE);
			return convertView;
		}

		class ViewHolder {
			ImageView select;
			TextView range;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txt_left:
			finish();
			break;
		case R.id.txt_save:
			setResult(2, getIntent().putExtra("rangeIndex", selected));
			finish();
			break;
		}
	}

}
