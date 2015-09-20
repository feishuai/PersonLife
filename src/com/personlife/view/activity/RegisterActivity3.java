package com.personlife.view.activity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.personlifep.R;
import com.loopj.android.http.RequestParams;
import com.personlife.bean.Star;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONArrayHttpResponseHandler;
import com.personlife.net.JSONObjectHttpResponseHandler;
import com.personlife.utils.ActivityCollector;
import com.personlife.utils.PersonInfoLocal;
import com.personlife.widget.MyGridView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

/**
 * 
 * @author liugang
 * @date 2015年8月8日
 */
public class RegisterActivity3 extends Activity implements OnClickListener {

	private Button back, nextstep;
	private TextView tv_title;
	public List<Integer> background = new ArrayList<Integer>();
	public List<Integer> backselected = new ArrayList<Integer>();
	private List<String> got = new ArrayList<String>();
	private String telphone;
	private MyGridView gridView;
	private boolean[] flag;
	private int size;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register3);
		ActivityCollector.addActivity(this);
		Intent intent = getIntent();
		telphone = intent.getStringExtra("telphone");
		init();
	}

	public void init() {
		back = (Button) findViewById(R.id.txt_left);
		back.setVisibility(View.VISIBLE);
		back.setOnClickListener(this);
		tv_title = (TextView) findViewById(R.id.txt_title);
		tv_title.setText("完善个人信息");
		nextstep = (Button) findViewById(R.id.register3_nextstep);
		nextstep.setOnClickListener(this);
		gridView = (MyGridView) findViewById(R.id.gridviewintest);
		background.add(R.drawable.register_intest1);
		background.add(R.drawable.register_intest2);
		background.add(R.drawable.register_intest3);
		backselected.add(R.drawable.register_intest_selected1);
		backselected.add(R.drawable.register_intest_selected2);
		backselected.add(R.drawable.register_intest_selected3);
		RequestParams request = new RequestParams();
		BaseAsyncHttp.postReq(this, "/app/allkind", request,
				new JSONArrayHttpResponseHandler() {

					@Override
					public void jsonSuccess(JSONArray resp) {
						// TODO Auto-generated method stub
						for (int i = 0; i < resp.length(); i++) {

							got.add(resp.optJSONObject(i).optString("second"));
						}
						size = resp.length();
						flag = new boolean[resp.length()];
						for (int i = 0; i < resp.length(); i++) {
							flag[i] = false;
						}
						IntestAdapter dapter = new IntestAdapter(
								getApplicationContext(), got);
						gridView.setAdapter(dapter);

					}

					@Override
					public void jsonFail(JSONArray resp) {
						// TODO Auto-generated method stub

					}
				});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txt_left:
			onBackPressed();
			break;

		case R.id.register3_nextstep:
			int isselect = 0;
			Set<String> set = new HashSet<String>();
			for (int i = 0; i < size; i++) {
				if (flag[i] == true) {
					set.add(got.get(i));
					isselect = 1;
				}
			}
			if (isselect == 0) {
				Toast.makeText(RegisterActivity3.this, "请选择爱好",
						Toast.LENGTH_SHORT).show();
			} else {
				PersonInfoLocal.storeRegisterHobbys(this, telphone, set);
				Intent intent = new Intent(this, RegisterActivity4.class);
				intent.putExtra("telphone", telphone);
				startActivity(intent);
				RequestParams request = new RequestParams();
				request.put("phone", telphone);
				request.put("nickname",
						PersonInfoLocal.getNcikName(this, telphone));
				request.put("thumb", PersonInfoLocal.getHeadKey(this, telphone));
				request.put("gender", "");
				request.put("area", "");
				request.put("job", "");
				StringBuffer sb = new StringBuffer();
				sb.append("");
				if (set != null) {
					for (String str : set) {
						sb.append(str + " ");
					}
				}
				request.put("hobby", sb.toString());
				request.put("signature", "");
				BaseAsyncHttp.postReq(getApplicationContext(), "/users/modify",
						request, new JSONObjectHttpResponseHandler() {

							@Override
							public void jsonSuccess(JSONObject resp) {
								// TODO Auto-generated method stub
								try {
									if (resp.get("flag").equals(0)) {
										// Toast.makeText(MyownActivity.this,
										// "修改信息失败", Toast.LENGTH_SHORT)
										// .show();
									} else {
										// Toast.makeText(MyownActivity.this,
										// "修改信息成功", Toast.LENGTH_SHORT)
										// .show();
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

							@Override
							public void jsonFail(JSONObject resp) {
								// TODO Auto-generated method stub
								// Toast.makeText(MyownActivity.this,
								// "Fail修改信息失败", Toast.LENGTH_SHORT)
								// .show();
							}
						});
			}

			break;
		}

	}

	class IntestAdapter extends BaseAdapter {

		private Context context;
		private List<String> list;

		public IntestAdapter(Context ctx, List<String> list) {
			context = ctx;
			this.list = list;
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
