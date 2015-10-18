package com.personlife.view.activity.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.personlifep.R;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.RequestParams;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONObjectHttpResponseHandler;
import com.personlife.utils.ComplexPreferences;

public class ClassificationActivity extends Activity implements OnClickListener {
	private Map<String, ArrayList<String>> classmap = new HashMap<String, ArrayList<String>>();
	private List<String> keys = new ArrayList<String>();
	private Button mBack;
	private TextView mTitle;
	private List<String> selectedtags;
	private ColorStateList yellowColorStateList;
	private ListView listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview);
		mBack = (Button) findViewById(R.id.txt_left);
		mTitle = (TextView) findViewById(R.id.txt_title);
		mBack.setVisibility(View.VISIBLE);
		mBack.setOnClickListener(this);
		mTitle.setText("全部分类");
		listview = (ListView) findViewById(R.id.listview);
		yellowColorStateList = getResources().getColorStateList(R.color.yellow);
		RequestParams params = new RequestParams();
		BaseAsyncHttp.postReq(getApplicationContext(), "/app/tag-commend",
				params, new JSONObjectHttpResponseHandler() {
					@Override
					public void jsonSuccess(JSONObject resp) {
						// TODO Auto-generated method stub
						Iterator it = resp.keys();
						while (it.hasNext()) {
							String key = (String) it.next();
							keys.add(key);
							ArrayList<String> value = new ArrayList<String>();
							try {
								value = new ObjectMapper().readValue(
										resp.getString(key),
										new TypeReference<ArrayList<String>>() {
										});
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							classmap.put(key, value);
						}
						initData();
					}

					@Override
					public void jsonFail(JSONObject resp) {
						// TODO Auto-generated method stub
					}
				});
	}

	private void initData() {
		selectedtags = ComplexPreferences.getObject(getApplicationContext(),
				"tags", new TypeReference<ArrayList<String>>() {
				});
		listview.setAdapter(new ClassificationAdapter(getApplicationContext()));
	}

	class ClassificationAdapter extends BaseAdapter {
		private Context mContext;
		private TextView tvclass;
		private GridView gvtags;

		// Gets the context so it can be used later
		public ClassificationAdapter(Context mContext) {
			this.mContext = mContext;
		}

		// Total number of things contained within the adapter
		public int getCount() {
			return classmap.size();
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
			convertView = ((LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
					.inflate(R.layout.layout_item_classification, null);
			tvclass = (TextView) convertView.findViewById(R.id.tv_class);
			gvtags = (GridView) convertView.findViewById(R.id.gv_tags);
			tvclass.setText(keys.get(position));
			gvtags.setAdapter(new ButtonAdapter(getApplicationContext(),
					classmap.get(keys.get(position))));
			return convertView;
		}
	}

	class ButtonAdapter extends BaseAdapter {
		private Context mContext;
		private List<String> tags;
		private Boolean allselected;

		// Gets the context so it can be used later
		public ButtonAdapter(Context mContent, List<String> tags) {
			this.mContext = mContent;
			this.tags = tags;
			this.allselected = false;
		}

		// Total number of things contained within the adapter
		public int getCount() {
			return tags.size() + 1;
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
			if (position == 0) {
				btn.setText("全部");
				btn.setTextColor(yellowColorStateList);
			} else {
				btn.setText(tags.get(position - 1));
				if (selectedtags.contains(tags.get(position - 1)))
					btn.setBackgroundResource(R.drawable.fenleixuanze);
			}
			if (allselected) {
				btn.setBackgroundResource(R.drawable.fenleixuanze);
			}
			btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (current == 0) {
						if (!allselected) {
							allselected = true;
							selectedtags.removeAll(tags);
							selectedtags.addAll(tags);
							notifyDataSetChanged();
						} else {
							allselected = false;
							selectedtags.removeAll(tags);
							notifyDataSetChanged();
						}
						return;
					} else {
						if (selectedtags.contains(tags.get(current - 1))) {
							selectedtags.remove(tags.get(current - 1));
							btn.setBackgroundResource(R.color.transparent);
						} else {
							selectedtags.add(tags.get(current - 1));
							btn.setBackgroundResource(R.drawable.fenleixuanze);
						}
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
			ComplexPreferences.putObject(getApplicationContext(), "tags",
					selectedtags);
			setResult(1);
			finish();
			break;
		default:
			break;
		}
	}
}
