package com.personlife.view.collection;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.personlifep.R;
import com.loopj.android.http.RequestParams;
import com.personlife.bean.App;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONObjectHttpResponseHandler;
import com.personlife.utils.Constants;
import com.personlife.utils.ImageLoaderUtils;
import com.personlife.utils.PersonInfoLocal;
import com.personlife.utils.Utils;
import com.personlife.view.activity.home.AppDetailActivity;
import com.personlife.widget.MyListView;

public class CollectionAppsFragment extends Fragment {
	private View layout;
	private MyListView lv;
	List<App> mList;
	List<App> deletedList;
	AppsAdapter appsAdapter;

	public CollectionAppsFragment(List<App> mList) {
		// TODO Auto-generated constructor stub
		this.mList = mList;
		this.deletedList = new ArrayList<App>();
	}

	public List<App> getAppsList() {
		return mList;
	}

	public void setAppsList(List<App> mList) {
		this.mList = mList;
		if (appsAdapter != null)
			appsAdapter.notifyDataSetChanged();
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		layout = inflater.inflate(R.layout.fragment_collection_apps, container,
				false);
		initView();
		return layout;
	}

	public void setDeleteMode() {
		appsAdapter.setDeleteMode();
	}

	public void setNormalMode() {
		int n = deletedList.size();
		for (int i = 0; i < n; i++) {
			RequestParams params = new RequestParams();
			params.add("phone", PersonInfoLocal.getPhone(getActivity()));
			params.add("app", String.valueOf(deletedList.get(i).getId()));

			BaseAsyncHttp.postReq(getActivity(), "/collect/cancel-app", params,
					new JSONObjectHttpResponseHandler() {

						@Override
						public void jsonSuccess(JSONObject resp) {
							// TODO Auto-generated method stub
						}

						@Override
						public void jsonFail(JSONObject resp) {
							// TODO Auto-generated method stub

						}
					});
		}

		Utils.showShortToast(getActivity(), "删除" + n + "个应用收藏成功");
		mList.removeAll(deletedList);
		deletedList.clear();
		appsAdapter.setNormalMode();
	}

	public void initView() {
		lv = (MyListView) layout.findViewById(R.id.lv_collection_apps);
		appsAdapter = new AppsAdapter(getActivity());
		lv.setAdapter(appsAdapter);

	}

	class AppsAdapter extends BaseAdapter {

		private Context context;
		private Boolean isDelete = false;

		public AppsAdapter(Context context) {
			this.context = context;
		}

		@Override
		public int getCount() {
			return mList.size();
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
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = ((LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
						.inflate(R.layout.layout_item_tuijian, null);
				holder = new ViewHolder();
				holder.appname = (TextView) convertView
						.findViewById(R.id.tv_tuijian_name);
				holder.status = (TextView) convertView
						.findViewById(R.id.tv_tuijian_status);
				holder.intro = (TextView) convertView
						.findViewById(R.id.tv_tuijian_intro);
				holder.icon = (ImageView) convertView
						.findViewById(R.id.iv_tuijian_icon);
				holder.download = (Button) convertView
						.findViewById(R.id.btn_tuijian_tuijian);
				holder.state = (CheckBox) convertView
						.findViewById(R.id.cb_tuijian_state);
				holder.beforetime = (TextView) convertView
						.findViewById(R.id.tv_tuijian_beforetime);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			ImageLoaderUtils.displayAppIcon(mList.get(position).getIcon(),
					holder.icon);
			holder.appname.setText(mList.get(position).getName());
			int counts = mList.get(position).getDowloadcount();
			if (counts > 10000)
				holder.status.setText(counts / 10000 + "万人下载 "
						+ mList.get(position).getSize());
			else
				holder.status.setText(counts + "人下载 "
						+ mList.get(position).getSize());
//			if (counts > 10000)
//				holder.status.setText(counts / 10000 + "万人下载  ");
//			else
//				holder.status.setText(counts + "人下载 ");
//			holder.status.append(mList.get(position).getStars()+"分");
			holder.intro.setText(mList.get(position).getProfile());
			holder.download.setVisibility(View.GONE);
			if (isDelete)
				holder.state.setVisibility(View.VISIBLE);
			else
				holder.state.setVisibility(View.GONE);
			holder.state
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							// TODO Auto-generated method stub
							if (isChecked) {
								if (!deletedList.contains(mList.get(position)))
									;
								deletedList.add(mList.get(position));
							} else
								deletedList.remove(mList.get(position));
						}
					});

			// 设置state的状态为未选中
			holder.state.setChecked(false);
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (!isDelete) {
						Intent intent = new Intent(context,
								AppDetailActivity.class);
						intent.putExtra(Constants.AppId, mList.get(position)
								.getId());
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(intent);
					}

				}
			});
			return convertView;
		}

		public void setDeleteMode() {
			isDelete = true;
			notifyDataSetChanged();
		}

		public void setNormalMode() {
			isDelete = false;
			notifyDataSetChanged();
		}

		class ViewHolder {
			ImageView icon;
			TextView appname;
			TextView status;
			TextView intro;
			TextView beforetime;
			Button download;
			CheckBox state;
		}
	}
}
