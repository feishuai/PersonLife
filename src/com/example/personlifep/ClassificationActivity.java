package com.example.personlifep;

import java.util.ArrayList;
import java.util.HashMap;

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
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.personlife.utils.Utils;

public class ClassificationActivity extends Activity implements OnClickListener {
	private String[] changjing = { "全部", "清晨", "午后", "黄昏", "出差", "学习", "工作",
			"午休", "运动", "酒吧", "驾车", "旅游", "地铁", "聚会", "度假", "散步" };
	private String[] zhuti = { "全部", "游戏", "70后", "80后", "怀旧", "90后", "00后",
			"儿童", "音乐" };
	private GridView gvchangjing, gvzhuti;
	private Button mBack;
	private TextView mTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_classification);
		gvchangjing = (GridView) findViewById(R.id.gv_class_changjing);
		gvzhuti = (GridView) findViewById(R.id.gv_class_zhuti);
		mBack = (Button) findViewById(R.id.txt_left);
		mTitle = (TextView) findViewById(R.id.txt_title);
		mBack.setVisibility(View.VISIBLE);
		mBack.setOnClickListener(this);
		mTitle.setText("全部分类");
		initData();
	}

	private void initData() {
		// 添加并且显示
		gvchangjing.setAdapter(new ButtonAdapter(getApplicationContext(),
				changjing));
		gvzhuti.setAdapter(new ButtonAdapter(getApplicationContext(), zhuti));
		
	}

	public class ButtonAdapter extends BaseAdapter {
		private Context mContext;
		public String[] strs;

		// Gets the context so it can be used later
		public ButtonAdapter(Context c, String[] strs) {
			mContext = c;
			this.strs = strs;
		}

		// Total number of things contained within the adapter
		public int getCount() {
			return strs.length;
		}

		// Require for structure, not really used in my code.
		public Object getItem(int position) {
			return null;
		}

		// Require for structure, not really used in my code. Can be used to get
		// the id of an item in the adapter for manual control.
		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			final Button btn;
			convertView = ((LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
					.inflate(R.layout.layout_grid_class, null);
			btn = (Button) convertView.findViewById(R.id.btn_class_kind);
			btn.setText(strs[position]);
			if (position == 0) {
				btn.setTextColor(R.color.yellow);
			}

			btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					btn.setBackgroundResource(R.drawable.xuanzhong);
				}
			});

			return convertView;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txt_left:
			finish();
			break;

		default:
			break;
		}
	}
}
