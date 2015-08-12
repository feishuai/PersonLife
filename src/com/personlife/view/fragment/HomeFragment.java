package com.personlife.view.fragment;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.loopj.android.http.RequestParams;
import com.personlife.adapter.AppListAdapter;
import com.personlife.adapter.AppsAdapter;
import com.personlife.bean.App;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONArrayHttpResponseHandler;
import com.personlife.net.JSONObjectHttpResponseHandler;
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
	private List<App> apps;
	private List<String> kinds;
	private KindsApps ka;
	private List<List<App>> kindsapps;
	private List<String> kindlist;
	KindsAdapter kindsAdapter;

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
		kindsapps = new ArrayList<List<App>>();
		kinds = new ArrayList<String>();
		kindlist = new ArrayList<String>();
		apps = new ArrayList<App>();
		ka = new KindsApps();
		
		apps.add(new App());
		apps.add(new App());
		apps.add(new App());
		kindsapps.add(apps);
		kindsapps.add(apps);
		kindsapps.add(apps);
		kinds.add("清晨");
		kinds.add("清晨");
		kinds.add("清晨");
		ka.setKinds(kinds);
		ka.setKindsapps(kindsapps);
		ka.setUserapps(kindsapps);
		kindsAdapter = new KindsAdapter(getActivity(), ka);
		mLvApps.setAdapter(kindsAdapter);
		
		BaseAsyncHttp.postReq(getActivity(), "/app/allkind", null,
				new JSONArrayHttpResponseHandler() {

					@Override
					public void jsonSuccess(JSONArray resp) {
						// TODO Auto-generated method stub
						kindlist.clear();
						for (int i = 0; i < resp.length(); i++) {
							try {
								kindlist.add(resp.getJSONObject(i).getString(
										"kind"));
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						getAllKindsApps();
					}

					@Override
					public void jsonFail(JSONArray resp) {
						// TODO Auto-generated method stub
						return;
					}
				});
		

		Log.i("kinds", String.valueOf(kinds.size()));// 3
	}

	protected void getAllKindsApps() {
		kindsapps.clear();
		kinds.clear();
		// TODO Auto-generated method stub
		for (int i = 0; i < kindlist.size(); i++) {
			final String kind = kindlist.get(i);
			RequestParams params = new RequestParams();
			params.add("kind", kind);
			BaseAsyncHttp.postReq(getActivity(), "/app/kind", params,
					new JSONObjectHttpResponseHandler() {

						@Override
						public void jsonSuccess(JSONObject resp) {
							// TODO Auto-generated method stub
							List<App> applist = new ArrayList<App>();
							try {
								JSONArray jsonapps = resp.getJSONArray("item");
								for (int i = 0; i < jsonapps.length(); i++) {
									App app = new App();
									JSONObject jsonapp = jsonapps
											.getJSONObject(i);
									app.setIcon(jsonapp.getString("icon"));
									app.setSize(jsonapp.getString("size"));
									app.setDowloadcount(jsonapp
											.getInt("downloadcount"));
									app.setIntrodution(jsonapp
											.getString("introduction"));
									app.setName(jsonapp.getString("name"));
									app.setId(jsonapp.getInt("id"));
									app.setDownloadUrl(jsonapp.getString("android_url"));
									applist.add(app);
								}
								kinds.add(kind);
								kindsapps.add(applist);
								Log.i("kindsapps size is ", String.valueOf(kindsapps.size()));
								updateView();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						@Override
						public void jsonFail(JSONObject resp) {
							// TODO Auto-generated method stub

						}
					});
		}
	}

	protected void updateView() {
		// TODO Auto-generated method stub
		ka.setKinds(kinds);
		Log.i("updateview kindsapps size is", String.valueOf(kindsapps.size()));
		ka.setKindsapps(kindsapps);
		ka.setUserapps(kindsapps);
		kindsAdapter.setData(ka);
		kindsAdapter.notifyDataSetChanged();
		
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
			Utils.start_Activity(getActivity(), ClassificationActivity.class,
					null);
			// Utils.start_Activity(getActivity(), RecommendActivity.class,
			// null);
			break;
		default:
			break;
		}
	}

	class KindsAdapter extends BaseAdapter {

		private Context context;
		private KindsApps kaa;

		public KindsAdapter(Context context, KindsApps ka) {
			this.context = context;
			this.kaa = ka;
		}

		@Override
		public int getCount() {
			return ka.getKinds().size();
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
			// ImageLoaderUtils.displayAppIcon("https://ss0.bdstatic.com/-0U0bnSm1A5BphGlnYG/tam-ogel/5136becf77e9cfc440849e0b694fdd6e_121_121.jpg",
			// holder.icon);
			// holder.lvapps.setOnItemClickListener(new OnItemClickListener() {
			//
			// @Override
			// public void onItemClick(AdapterView<?> parent, View view,
			// int position, long id) {
			// // TODO Auto-generated method stub
			// Intent intent = new Intent(context, AppDetailActivity.class);
			// intent.putExtra(Constants.AppId, mlist.get(position)
			// .getId());
			// context.startActivity(intent);
			//
			// }
			//
			// });
			holder.tvkind.setText(kaa.getKinds().get(position));
			holder.counts.setText("我的（" + kaa.getUserapps().size() + "）");
//			Log.i("kind size is ", String.valueOf(kaa.getKinds().size()));
//			Log.i("position is ", String.valueOf(position));
			List<App> apps = kaa.getKindsapps().get(position);
			
			if (apps.size() > 3)
				apps = apps.subList(0, 3);
			holder.lvapps.setAdapter(new AppsAdapter(context, apps));

			holder.hlvMyapps.setAdapter(new MyAppsAdapter(apps));

			holder.more.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(context,RecommendActivity.class);
					intent.putExtra("kind", kaa.getKinds().get(position));
					context.startActivity(intent);
				}
			});
			// Intent intent = new Intent(context, AppDetailActivity.class);
			// intent.putExtra(Constants.AppId, mlist.get(position)
			// .getId());
			// context.startActivity(intent);
			// }
			// });
			return convertView;
		}

		public void setData(KindsApps ka) {
			this.kaa = ka;
		}

		class ViewHolder {
			TextView tvkind;
			TextView counts;
			Button more;
			HorizontialListView hlvMyapps;
			MyListView lvapps;
		}
	}

	private static String[] urls = new String[] { "Text #1", "Text #1",
			"Text #1", "Text #1" };

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
			View retval = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.layout_item_like, null);
			ImageView appicon = (ImageView) retval
					.findViewById(R.id.iv_item_icon);
			TextView appname = (TextView) retval
					.findViewById(R.id.tv_item_name);
			appname.setVisibility(View.GONE);
			return retval;
		}

	}

	class KindsApps {
		List<String> kinds;
		List<List<App>> kindsapps;
		List<List<App>> userapps;

		public KindsApps() {
			kinds = new ArrayList<String>();
			kindsapps = new ArrayList<List<App>>();
			userapps = new ArrayList<List<App>>();
		}

		public List<String> getKinds() {
			return kinds;
		}

		public void setKinds(List<String> kinds) {
			this.kinds.clear();
			this.kinds.addAll(kinds);
		}

		public List<List<App>> getKindsapps() {
			return kindsapps;
		}

		public void setKindsapps(List<List<App>> kindsapps) {
			this.kindsapps.clear();
			this.kindsapps.addAll(kindsapps);
		}

		public List<List<App>> getUserapps() {
			return userapps;
		}

		public void setUserapps(List<List<App>> userapps) {
			this.userapps.clear();
			this.userapps.addAll(userapps);
		}

	}
}
