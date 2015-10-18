package com.personlife.view.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.personlifep.R;
import com.fasterxml.jackson.core.type.TypeReference;
import com.loopj.android.http.RequestParams;
import com.personlife.adapter.AppListAdapter;
import com.personlife.adapter.AppsAdapter;
import com.personlife.bean.App;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONArrayHttpResponseHandler;
import com.personlife.net.JSONObjectHttpResponseHandler;
import com.personlife.utils.ComplexPreferences;
import com.personlife.utils.Constants;
import com.personlife.utils.DrawableStringUtils;
import com.personlife.utils.SystemUtils;
import com.personlife.utils.Utils;
import com.personlife.view.activity.home.AppSearchActivity;
import com.personlife.view.activity.home.ClassificationActivity;
import com.personlife.view.activity.home.RecommendActivity;
import com.personlife.widget.ClearEditText;
import com.personlife.widget.HorizontialListView;
import com.personlife.widget.MyListView;

public class HomeFragment extends Fragment implements OnClickListener {
	private ClearEditText search;
	private Button kind;
	private Activity ctx;
	private View layout;
	private MyListView mLvApps;
	private AppListAdapter mAdapter;
	private List<App> userapps;
	private List<String> taglist;
	KindsAdapter kindsAdapter;
	List<App> allapps;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (layout == null) {
			ctx = this.getActivity();
			layout = ctx.getLayoutInflater().inflate(R.layout.fragment_home,
					null);
			initView();
			setOnListener();
			initData();
		} else {
			ViewGroup parent = (ViewGroup) layout.getParent();
			if (parent != null) {
				parent.removeView(layout);
			}
		}
		return layout;
	}

	public void initView() {
		search = (ClearEditText) layout.findViewById(R.id.et_home_search);
		kind = (Button) layout.findViewById(R.id.btn_home_class);
		mLvApps = (MyListView) layout.findViewById(R.id.lv_home_kinds);
	}

	public void setOnListener() {
		search.setOnClickListener(this);
		kind.setOnClickListener(this);
	}

	public void initData() {
		taglist = new ArrayList<String>();
		allapps = new ArrayList<App>();
		userapps = new ArrayList<App>();
		kindsAdapter = new KindsAdapter(getActivity(), taglist,
				new HashMap<String, List<App>>());
		mLvApps.setAdapter(kindsAdapter);
		if (userapps.size() == 0)
			userapps = SystemUtils.getUserApps(getActivity());

		if (ComplexPreferences.getObject(getActivity(), "tags",
				new TypeReference<ArrayList<String>>() {
				}) == null) {
			RequestParams params = new RequestParams();
			BaseAsyncHttp.postReq(getActivity(), "/myapp/tag8", params,
					new JSONArrayHttpResponseHandler() {
						@Override
						public void jsonSuccess(JSONArray resp) {
							// TODO Auto-generated method stub
							try {
								for (int i = 0; i < resp.length(); i++) {
									String tag = resp.getString(i);
									taglist.add(tag);
								}
								updateView();
								ComplexPreferences.putObject(getActivity(),
										"tags", taglist);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						@Override
						public void jsonFail(JSONArray resp) {
							// TODO Auto-generated method stub
						}
					});

		} else {
			taglist = ComplexPreferences.getObject(getActivity(), "tags",
					new TypeReference<ArrayList<String>>() {
					});
			updateView();
		}

	}

	protected void updateView() {
		// TODO Auto-generated method stub
		
		final ProgressDialog pd = new ProgressDialog(getActivity());
		pd.setCanceledOnTouchOutside(false);
		pd.setMessage("正在加载");
		pd.show();
		RequestParams request = new RequestParams();
		for (int i = 0; i < taglist.size(); i++) {
			request.add("tag[" + i + "]", taglist.get(i));
		}
		BaseAsyncHttp.postReq(getActivity().getApplicationContext(),
				"/myapp/tag-to-apps", request,
				new JSONObjectHttpResponseHandler() {

					@Override
					public void jsonSuccess(JSONObject resp) {
						Map<String, List<App>> maps = new HashMap<String, List<App>>();
						for (int i = 0; i < taglist.size(); i++) {
							String tag = taglist.get(i);
							JSONArray jsonapps = resp.optJSONArray(tag);
							List<App> apps = new ArrayList<App>();
							for (int j = 0; j < jsonapps.length() && j < 3; j++) {
								App app = new App();
								JSONObject jsonapp = jsonapps.optJSONObject(j);
								app.setIcon(jsonapp.optString("icon"));
								app.setSize(jsonapp.optString("size"));
								app.setDowloadcount(jsonapp
										.optInt("downloadcount"));
								app.setIntrodution(jsonapp
										.optString("introduction"));
								app.setName(jsonapp.optString("name"));
								app.setId(jsonapp.optInt("id"));
								app.setDownloadUrl(jsonapp
										.optString("android_url"));
								app.setProfile(jsonapp.optString("profile"));
								app.setDownloadPath(Constants.DownloadPath
										+ app.getName() + ".apk");
								app.setStars((float)jsonapp.optDouble("stars"));
								apps.add(app);
							}
							maps.put(tag, apps);
							pd.dismiss();
							kindsAdapter.setData(taglist, maps);
							kindsAdapter.notifyDataSetChanged();
						}
					}

					@Override
					public void jsonFail(JSONObject resp) {
						// TODO Auto-generated method stub
						pd.dismiss();
						Utils.showShortToast(getActivity(), "获取数据出错");
					}
				});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		Log.i("resultCode", String.valueOf(resultCode));
		switch (resultCode) {
		case 1:
			taglist = ComplexPreferences.getObject(getActivity(), "tags",
					new TypeReference<ArrayList<String>>() {
					});
			updateView();
			break;
		case 2:
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.et_home_search:
			Intent intent = new Intent(getActivity(), AppSearchActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_home_class:
			Intent intentclass = new Intent(getActivity(),
					ClassificationActivity.class);
			startActivityForResult(intentclass, 1);
			break;
		default:
			break;
		}
	}

	class KindsAdapter extends BaseAdapter {

		private Context context;
		private List<String> tags;
		private Map<String, List<App>> maps;

		public KindsAdapter(Context context, List<String> tags,
				Map<String, List<App>> maps) {
			this.context = context;
			this.tags = tags;
			this.maps = maps;
		}

		@Override
		public int getCount() {
			return tags.size();
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
			final ViewHolder holder;
			if (convertView == null) {
				convertView = ((LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
						.inflate(R.layout.layout_item_home, null);
				holder = new ViewHolder();
				holder.tvkind = (TextView) convertView
						.findViewById(R.id.tv_home_kind);
				holder.counts = (TextView) convertView
						.findViewById(R.id.tv_home_counts);
				holder.more = (Button) convertView
						.findViewById(R.id.btn_home_more);
				holder.hlvMyapps = (HorizontialListView) convertView
						.findViewById(R.id.hlv_home_apps);
				holder.lvapps = (MyListView) convertView
						.findViewById(R.id.lv_home_apps);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			List<App> apps = new ArrayList<App>();
			apps = maps.get(tags.get(position));
			if (position == 0)
				allapps.clear();
			allapps.addAll(apps);
			if (position == (tags.size() - 1))
				ComplexPreferences.putObject(getActivity(),
						Constants.HomeAllDownloadApps, allapps);
			holder.tvkind.setText(tags.get(position));
			holder.counts.setText("我的（" + 3 + "）");
			holder.lvapps.setAdapter(new AppsAdapter(context, apps));
			int tribe = position * 3;
			if ((tribe + 3) > userapps.size())
				holder.hlvMyapps.setAdapter(new MyAppsAdapter(userapps.subList(
						0, 3)));
			else
				holder.hlvMyapps.setAdapter(new MyAppsAdapter(userapps.subList(
						position * 3, position * 3 + 3)));

			holder.more.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(context, RecommendActivity.class);
					intent.putExtra("kind", tags.get(position));
					context.startActivity(intent);
				}
			});
			return convertView;
		}

		public void setData(List<String> tags, Map<String, List<App>> maps) {
			this.tags = tags;
			this.maps = maps;
		}

		class ViewHolder {
			TextView tvkind;
			TextView counts;
			Button more;
			HorizontialListView hlvMyapps;
			MyListView lvapps;
		}
	}

	class MyAppsAdapter extends BaseAdapter {
		List<App> apps;

		public MyAppsAdapter(List<App> apps) {
			this.apps = apps;
		}

		@Override
		public int getCount() {
			return apps.size();
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
			final int pos = position;
			View retval = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.layout_item_like, null);
			ImageView appicon = (ImageView) retval
					.findViewById(R.id.iv_item_icon);
			TextView appname = (TextView) retval
					.findViewById(R.id.tv_item_name);
			appicon.setImageDrawable(DrawableStringUtils.stringToDrawable(apps
					.get(position).getDrawableString()));
			appname.setVisibility(View.GONE);
			appicon.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					SystemUtils.startApp(getActivity(), apps.get(pos)
							.getPackageName());
				}
			});
			return retval;
		}

	}
}
