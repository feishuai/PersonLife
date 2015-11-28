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
import android.widget.LinearLayout;
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
import com.personlife.utils.PersonInfoLocal;
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
	List<App> allapps, systemApps;
	List<App> localApps;
	Map<String, ArrayList<App>> tag2Apps;
	LinearLayout llLoad;
	Button btnLoad;

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
		llLoad = (LinearLayout) layout.findViewById(R.id.ll_load);
		btnLoad = (Button) layout.findViewById(R.id.btn_load);
	}

	public void setOnListener() {
		search.setOnClickListener(this);
		kind.setOnClickListener(this);
		btnLoad.setOnClickListener(this);
	}

	public void initData() {
		taglist = new ArrayList<String>();
		allapps = new ArrayList<App>();
		userapps = new ArrayList<App>();
		tag2Apps = new HashMap<String, ArrayList<App>>();
		kindsAdapter = new KindsAdapter(getActivity(), taglist,
				new HashMap<String, List<App>>());
		mLvApps.setAdapter(kindsAdapter);
		if (!Utils.isNetworkAvailable(getActivity())) {
			llLoad.setVisibility(View.VISIBLE);
			mLvApps.setVisibility(View.GONE);
		}
		if (userapps.size() == 0)
			userapps = SystemUtils.getAppsNoSystom(getActivity());
		uploadAppToServer();
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
								updateLocalApp();
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
							llLoad.setVisibility(View.VISIBLE);
							mLvApps.setVisibility(View.GONE);
						}
					});

		} else {
			taglist = ComplexPreferences.getObject(getActivity(), "tags",
					new TypeReference<ArrayList<String>>() {
					});
			updateLocalApp();
		}

	}

	public void uploadAppToServer() {
		RequestParams request = new RequestParams();
		request.put("phone", PersonInfoLocal.getPhone(getActivity()));
		request.put("platform", "android");
		for (int i = 0; i < userapps.size(); i++) {
			request.add("apps[" + i + "]", userapps.get(i).getPackageName());
		}
		BaseAsyncHttp.postReq(getActivity(), "/myapp/upload", request,
				new JSONObjectHttpResponseHandler() {

					@Override
					public void jsonSuccess(JSONObject resp) {
					}

					@Override
					public void jsonFail(JSONObject resp) {
						llLoad.setVisibility(View.VISIBLE);
						mLvApps.setVisibility(View.GONE);
					}
				});
	}

	public void updateLocalApp() {
		systemApps = SystemUtils.getUserApps(getActivity());
		localApps = new ArrayList<App>();
		RequestParams params = new RequestParams();
		for (int i = 0; i < systemApps.size(); i++) {
			params.add("packages[" + i + "]", systemApps.get(i)
					.getPackageName());
		}
		BaseAsyncHttp.postReq(getActivity(), "/message/before-send", params,
				new JSONObjectHttpResponseHandler() {

					@Override
					public void jsonSuccess(JSONObject resp) {
						// TODO Auto-generated method stub
						for (int i = 0; i < systemApps.size(); i++) {
							App app = systemApps.get(i);
							String packagename = app.getPackageName();
							JSONObject appjson = resp
									.optJSONObject(packagename);
							if (appjson.optInt("exist") == 1) {
								app.setId(appjson.optInt("appid"));
								app.setTag(appjson.optString("tag"));
								localApps.add(app);
							}
						}

						for (int i = 0; i < localApps.size(); i++) {
							String tag = localApps.get(i).getTag();
							String[] tags = tag.split(" ");
							for (String tagitem : tags) {
								ArrayList<App> apps = tag2Apps.get(tagitem);
								if (apps == null)
									apps = new ArrayList<App>();
								apps.add(localApps.get(i));
								tag2Apps.put(tagitem, apps);
							}
						}
						updateView();
					}

					@Override
					public void jsonFail(JSONObject resp) {
						// TODO Auto-generated method stub
						Utils.showShortToast(getActivity(), "网络故障");
						llLoad.setVisibility(View.VISIBLE);
						mLvApps.setVisibility(View.GONE);
					}
				});
	}

	public void updateView() {
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
								app.setStars((float) jsonapp.optDouble("stars"));
								app.setPackageName(jsonapp.optString("package"));
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
						llLoad.setVisibility(View.VISIBLE);
						mLvApps.setVisibility(View.GONE);
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
		case R.id.btn_load:
			llLoad.setVisibility(View.GONE);
			mLvApps.setVisibility(View.VISIBLE);
			initData();
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
			if (apps != null)
				allapps.addAll(apps);
			if (position == (tags.size() - 1))
				ComplexPreferences.putObject(getActivity(),
						Constants.HomeAllDownloadApps, allapps);
			holder.tvkind.setText(tags.get(position));
			ArrayList<App> myapps = tag2Apps.get(tags.get(position));
			int myappssize = 0;
			if (myapps != null)
				myappssize = myapps.size();
			holder.counts.setText("我的（" + String.valueOf(myappssize) + "）");
			holder.lvapps.setAdapter(new AppsAdapter(context, apps));
			holder.hlvMyapps.setAdapter(new MyAppsAdapter(tag2Apps.get(tags
					.get(position))));
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
			if (apps == null)
				return 0;
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
