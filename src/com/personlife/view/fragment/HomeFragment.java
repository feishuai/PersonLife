package com.personlife.view.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
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
import com.personlife.utils.ComplexPreferences;
import com.personlife.utils.Constants;
import com.personlife.utils.DrawableStringUtils;
import com.personlife.utils.SystemUtils;
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
		if (ComplexPreferences.getObject(getActivity(), "tags",
				new TypeReference<ArrayList<String>>() {
				}) == null) {
			taglist.add("清晨");
			taglist.add("午后");
			ComplexPreferences.putObject(getActivity(), "tags", taglist);
		} else
			taglist = ComplexPreferences.getObject(getActivity(), "tags",
					new TypeReference<ArrayList<String>>() {
					});
		userapps = SystemUtils.getUserApps(getActivity());

		kindsAdapter = new KindsAdapter(getActivity(), taglist);
		mLvApps.setAdapter(kindsAdapter);
		updateView();
	}

	protected void updateView() {
		// TODO Auto-generated method stub
		kindsAdapter.setData(taglist);
		kindsAdapter.notifyDataSetChanged();
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

		public KindsAdapter(Context context, List<String> tags) {
			this.context = context;
			this.tags = tags;
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
			final List<App> apps = new ArrayList<App>();
			RequestParams params = new RequestParams();
			params.add("tag", tags.get(position));
			BaseAsyncHttp.postReq(getActivity(), "/myapp/tag", params,
					new JSONArrayHttpResponseHandler() {

						@Override
						public void jsonSuccess(JSONArray resp) {
							// TODO Auto-generated method stub
							try {
								for (int i = 0; i < resp.length() && i < 3; i++) {
									App app = new App();
									JSONObject jsonapp = resp.getJSONObject(i);
									app.setIcon(jsonapp.getString("icon"));
									app.setSize(jsonapp.getString("size"));
									app.setDowloadcount(jsonapp
											.getInt("downloadcount"));
									app.setIntrodution(jsonapp
											.getString("introduction"));
									app.setName(jsonapp.getString("name"));
									app.setId(jsonapp.getInt("appid"));
									app.setDownloadUrl(jsonapp
											.getString("android_url"));
									app.setProfile(jsonapp.getString("profile"));
									app.setDownloadPath(Constants.DownloadPath
											+ app.getName() + ".apk");
									apps.add(app);
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							allapps.addAll(apps);
							if (position == tags.size())
								ComplexPreferences.putObject(getActivity(),
										Constants.HomeAllDownloadApps, allapps);
							holder.tvkind.setText(tags.get(position));
							holder.counts.setText("我的（" + 2 + "）");
							holder.lvapps.setAdapter(new AppsAdapter(context,
									apps));
							int tribe = position * 3;
							if ((tribe + 3) > userapps.size())
								holder.hlvMyapps.setAdapter(new MyAppsAdapter(
										userapps.subList(0, 3)));
							else
								holder.hlvMyapps.setAdapter(new MyAppsAdapter(
										userapps.subList(position * 3,
												position * 3 + 3)));
						}

						@Override
						public void jsonFail(JSONArray resp) {
							// TODO Auto-generated method stub
						}
					});
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

		public void setData(List<String> tags) {
			this.tags = tags;
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
