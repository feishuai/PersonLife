package com.personlife.view.activity.personinfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;

import com.example.personlifep.R;
import com.loopj.android.http.RequestParams;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONArrayHttpResponseHandler;
import com.personlife.utils.ActivityCollector;
import com.personlife.utils.PersonInfoLocal;
import com.personlife.widget.MyGridView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

/**
 * 
 * @author liugang
 * @date 2015年6月26日
 */
public class Interests extends Activity {

	private TextView title;
	private Button back, finish;
	private Set<String> set;
	private String telphone;
	public List<Integer> background = new ArrayList<Integer>();
	public List<Integer> backselected = new ArrayList<Integer>();
	private List<String> got = new ArrayList<String>();
	private MyGridView gridView;
	private boolean[] flag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_interests);
		ActivityCollector.addActivity(this);
		Intent intent = getIntent();
		telphone = intent.getStringExtra("telphone");
		init();
	}

	public void init() {
		title = (TextView) findViewById(R.id.txt_title);
		back = (Button) findViewById(R.id.txt_left);
		finish = (Button) findViewById(R.id.txt_save);
		title.setText("兴趣爱好");
		back.setVisibility(View.VISIBLE);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		finish.setVisibility(View.VISIBLE);
		finish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Set<String> temp = new HashSet<String>();
				temp.add("");
				for (int i = 0; i < got.size(); i++) {
					if (flag[i] == true) {
						temp.add(got.get(i));
					}
				}
				PersonInfoLocal.storeRegisterHobbys(Interests.this, telphone,
						temp);

				finish();
			}
		});
		gridView = (MyGridView) findViewById(R.id.gridviewintest_person);
		background.add(R.drawable.register_intest1);
		background.add(R.drawable.register_intest2);
		background.add(R.drawable.register_intest3);
		backselected.add(R.drawable.register_intest_selected1);
		backselected.add(R.drawable.register_intest_selected2);
		backselected.add(R.drawable.register_intest_selected3);
		RequestParams request = new RequestParams();
		BaseAsyncHttp.postReq(this, "/users/hobby", request,
				new JSONArrayHttpResponseHandler() {

					@Override
					public void jsonSuccess(JSONArray resp) {
						// TODO Auto-generated method stub
						for (int i = 0; i < resp.length(); i++) {
							got.add(resp.optJSONObject(i).optString("hobby"));
						}
						flag = new boolean[resp.length()];
						for (int i = 0; i < resp.length(); i++) {
							flag[i] = false;
						}
						StringBuffer sb = new StringBuffer();
						sb.append("");
						set = new HashSet<String>();
						set = PersonInfoLocal.getHobbys(Interests.this,
								telphone);
						if (set != null) {
							for (String str : set) {
								sb.append(str + " ");
							}
						}
						// sb里有兴趣爱好
						String temp = sb.toString().trim();
						IntestAdapter dapter = new IntestAdapter(
								getApplicationContext(), got, temp);
						gridView.setAdapter(dapter);

					}

					@Override
					public void jsonFail(JSONArray resp) {
						// TODO Auto-generated method stub

					}
				});

	}

	class IntestAdapter extends BaseAdapter {

		private Context context;
		private List<String> list;
		private String aihaos;

		public IntestAdapter(Context ctx, List<String> list, String temp) {
			context = ctx;
			this.list = list;
			aihaos = temp;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if (null != list) {
				return list.size();
			} else
				return 0;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			final ViewHolder viewholder;
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.interest_item, null);
				viewholder = new ViewHolder();
				viewholder.item = (Button) convertView
						.findViewById(R.id.intestitem);
				convertView.setTag(viewholder);
			} else
				viewholder = (ViewHolder) convertView.getTag();
			viewholder.item.setText(list.get(position));
			viewholder.item.setBackgroundResource(background.get(position % 3));
			if (aihaos.contains(list.get(position))) {
				viewholder.item.setBackgroundResource(backselected
						.get(position % 3));
				flag[position] = !flag[position];
			}
			viewholder.item.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					flag[position] = !flag[position];
					if (flag[position] == true) {
						viewholder.item.setBackgroundResource(backselected
								.get(position % 3));
					} else
						viewholder.item.setBackgroundResource(background
								.get(position % 3));
				}
			});
			return convertView;
		}

		class ViewHolder {
			Button item;
		}
	}
}
