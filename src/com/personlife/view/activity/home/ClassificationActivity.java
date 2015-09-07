package com.personlife.view.activity.home;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.personlifep.R;
import com.fasterxml.jackson.core.type.TypeReference;
import com.personlife.utils.ComplexPreferences;

public class ClassificationActivity extends Activity implements OnClickListener {
	private String[] changjing = { "全部", "清晨", "午后", "黄昏", "出差", "学习", "工作",
			"午休", "运动", "酒吧", "驾车", "旅游", "地铁", "聚会", "度假", "散步" };
	private String[] zhuti = { "全部", "游戏", "70后", "80后", "怀旧", "90后", "00后",
			"儿童", "音乐" };
	private Boolean[] isZhutiSelected, isChangjingSelected;
	private GridView gvchangjing, gvzhuti;
	private Button mBack;
	private TextView mTitle;
	private List<String> selectedtags;

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
		selectedtags = ComplexPreferences.getObject(getApplicationContext(),
				"tags", new TypeReference<ArrayList<String>>() {
				});
		isZhutiSelected = new Boolean[zhuti.length];
		isChangjingSelected = new Boolean[changjing.length];
		for (int i = 0; i < isZhutiSelected.length; i++) {
			isZhutiSelected[i] = false;
		}
		for (int i = 0; i < isChangjingSelected.length; i++) {
			isChangjingSelected[i] = false;
		}
		for (int i = 0; i < selectedtags.size(); i++) {
			String tag = selectedtags.get(i);
			for (int j = 0; j < changjing.length; j++) {
				if (changjing[j].equals(tag)) {
					isChangjingSelected[j] = true;
					return;
				}
			}
			for (int j = 0; j < zhuti.length; j++) {
				if (zhuti[j].equals(tag)) {
					isZhutiSelected[j] = true;
					return;
				}
			}
		}
		// 添加并且显示
		gvchangjing.setAdapter(new ButtonAdapter(getApplicationContext(),
				changjing, 0));
		gvzhuti.setAdapter(new ButtonAdapter(getApplicationContext(), zhuti, 1));

	}

	public class ButtonAdapter extends BaseAdapter {
		private Context mContext;
		private String[] strs;
		private int dif;

		// Gets the context so it can be used later
		public ButtonAdapter(Context c, String[] strs, int dif) {
			mContext = c;
			this.strs = strs;
			this.dif = dif;
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
			final int current = position;
			convertView = ((LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
					.inflate(R.layout.layout_grid_class, null);
			btn = (Button) convertView.findViewById(R.id.btn_class_kind);
			btn.setText(strs[position]);
			if (position == 0) {
				btn.setTextColor(R.color.yellow);
			}
			if (dif == 0) {
				if (isChangjingSelected[current])
					btn.setBackgroundResource(R.drawable.fenleixuanze);
			} else {
				if (isZhutiSelected[current])
					btn.setBackgroundResource(R.drawable.fenleixuanze);
			}

			btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (dif == 0) {
						if (current == 0) {
							for (int i = 0; i < isChangjingSelected.length; i++) {
								isChangjingSelected[i] = true;
								notifyDataSetChanged();
							}
							btn.setBackgroundResource(R.drawable.fenleixuanze);
							return;
						}
						isChangjingSelected[current] = !isChangjingSelected[current];
						if (isChangjingSelected[current])
							btn.setBackgroundResource(R.drawable.fenleixuanze);
						else
							btn.setBackgroundResource(R.color.transparent);
					} else {
						if (current == 0) {
							for (int i = 0; i < isZhutiSelected.length; i++) {
								isZhutiSelected[i] = true;
								notifyDataSetChanged();
							}
							btn.setBackgroundResource(R.drawable.fenleixuanze);
							return;
						}
						isZhutiSelected[current] = !isZhutiSelected[current];
						if (isZhutiSelected[current])
							btn.setBackgroundResource(R.drawable.fenleixuanze);
						else
							btn.setBackgroundResource(R.color.transparent);
					}
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
			List<String> tags = new ArrayList<String>();
			for (int i = 1; i < isChangjingSelected.length; i++) {
				if (isChangjingSelected[i])
					tags.add(changjing[i]);
			}
			for (int i = 1; i < isZhutiSelected.length; i++) {
				if (isZhutiSelected[i])
					tags.add(zhuti[i]);
			}
			if (tags.size() > 0) {
				ComplexPreferences.putObject(getApplicationContext(), "tags",
						tags);
			}
			setResult(1);
			finish();
			break;

		default:
			break;
		}
	}
}
