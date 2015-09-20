package com.personlife.view.activity.discovery;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.personlifep.R;
import com.loopj.android.http.RequestParams;
import com.personlife.adapter.StarAdapter;
import com.personlife.bean.Star;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONArrayHttpResponseHandler;
import com.personlife.utils.Utils;
import com.personlife.widget.MyListView;

public class StarSearchActivity extends Activity implements OnClickListener {
	private Button sure, mBack;
	private TextView mTitle;
	private EditText search;
	private MyListView lvResult;
	private StarAdapter starAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_star_search);
		mBack = (Button) findViewById(R.id.txt_left);
		mTitle = (TextView) findViewById(R.id.txt_title);
		sure = (Button) findViewById(R.id.btn_search_concel);
		sure.setOnClickListener(this);
		search = (EditText) findViewById(R.id.et_search_search);
		// search.requestFocus();

		mTitle.setText("明星搜索");
		mBack.setVisibility(View.VISIBLE);
		mBack.setOnClickListener(this);
		lvResult = (MyListView) findViewById(R.id.lv_search_result);
		starAdapter = new StarAdapter(getApplicationContext(),
				new ArrayList<Star>());
		lvResult.setAdapter(starAdapter);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txt_left:
			finish();
			break;
		case R.id.btn_search_concel:
			if (search.getText().toString().equals("")) {
				Utils.showShortToast(getApplicationContext(), "请先输入查询内容");
				return;
			}
			RequestParams request = new RequestParams();
			request.add("name", search.getText().toString());
			BaseAsyncHttp.postReq(this, "/users/search-star", request,
					new JSONArrayHttpResponseHandler() {

						@Override
						public void jsonSuccess(JSONArray resp) {
							// TODO Auto-generated method stub
							List<Star> list_all = new ArrayList<Star>();
							for (int i = 0; i < resp.length(); i++) {
								Star star = new Star();
								star.setPhone(resp.optJSONObject(i).optString(
										"phone"));
								star.setNickname(resp.optJSONObject(i)
										.optString("nickname"));
								star.setThumb(resp.optJSONObject(i).optString(
										"thumb"));
								star.setFollower(resp.optJSONObject(i)
										.optString("follower"));
								star.setShared(resp.optJSONObject(i).optString(
										"shared"));
								list_all.add(star);
							}
							starAdapter.setData(list_all);
							starAdapter.notifyDataSetChanged();
							if (list_all.size() == 0)
								Utils.showShortToast(getApplicationContext(),
										"没有结果");
						}

						@Override
						public void jsonFail(JSONArray resp) {
							// TODO Auto-generated method stub

						}
					});
			break;
		default:
			break;
		}
	}
}
