package com.personlife.view.collection;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.personlifep.R;
import com.loopj.android.http.RequestParams;
import com.personlife.bean.App;
import com.personlife.bean.Shuoshuo;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONObjectHttpResponseHandler;
import com.personlife.utils.ComplexPreferences;
import com.personlife.utils.Constants;
import com.personlife.utils.ImageLoaderUtils;
import com.personlife.utils.PersonInfoLocal;
import com.personlife.utils.Utils;
import com.personlife.view.activity.circle.ShareAppListActivity;
import com.personlife.widget.ClearEditText;
import com.personlife.widget.HorizontialListView;
import com.personlife.widget.MyListView;

public class CollectionSharesFragment extends Fragment {
	private View layout;
	private MyListView lv;
	List<Shuoshuo> mList;
	List<Shuoshuo> deletedList;
	ShuoshuosAdapter shuoshuosAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		layout = inflater.inflate(R.layout.fragment_collection_shares,
				container, false);
		initView();
		return layout;
	}

	public CollectionSharesFragment(List<Shuoshuo> mList) {
		this.mList = mList;
		this.deletedList = new ArrayList<Shuoshuo>();
	}

	public List<Shuoshuo> getShuoshuosList() {
		return mList;
	}

	public void setShuoshuosList(List<Shuoshuo> mList) {
		this.mList = mList;
		if (shuoshuosAdapter != null)
			shuoshuosAdapter.notifyDataSetChanged();
	}

	public void setDeleteMode() {
		shuoshuosAdapter.setDeleteMode();
	}

	public void setNormalMode() {
		int n = deletedList.size();
		for (int i = 0; i < n; i++) {
			RequestParams params = new RequestParams();
			params.add("phone", PersonInfoLocal.getPhone(getActivity()));
			params.add("msg", String.valueOf(deletedList.get(i).getMsgid()));
			BaseAsyncHttp.postReq(getActivity(), "/collect/cancel-msg", params,
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

		Utils.showShortToast(getActivity(), "删除" + n + "个分享收藏成功");
		mList.removeAll(deletedList);
		deletedList.clear();
		shuoshuosAdapter.setNormalMode();
	}

	public void initView() {
		lv = (MyListView) layout.findViewById(R.id.lv_collection_shares);
		shuoshuosAdapter = new ShuoshuosAdapter(getActivity());
		lv.setAdapter(shuoshuosAdapter);

	}

	class ShuoshuosAdapter extends BaseAdapter {

		private Context context;
		private Boolean isDelete = false;

		public ShuoshuosAdapter(Context context) {
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
						.inflate(R.layout.layout_item_shuoshuo, null);
				holder = new ViewHolder();
				holder.state = (CheckBox) convertView
						.findViewById(R.id.cb_shuoshuo_state);
				holder.name = (TextView) convertView
						.findViewById(R.id.tv_shuoshuo_name);
				holder.beforetime = (TextView) convertView
						.findViewById(R.id.tv_shuoshuo_beforetime);
				holder.icon = (ImageView) convertView
						.findViewById(R.id.iv_shuoshuo_icon);
				holder.content = (TextView) convertView
						.findViewById(R.id.tv_shuoshuo_content);
				holder.comment = (ImageView) convertView
						.findViewById(R.id.iv_shuoshuo_pinglun);
				holder.praise = (ImageView) convertView
						.findViewById(R.id.iv_shuoshuo_dianzan);
				holder.comments = (MyListView) convertView
						.findViewById(R.id.lv_shuoshuo_comment);
				holder.person = (TextView) convertView
						.findViewById(R.id.tv_shuoshuo_praise);
				holder.pinlun = (ClearEditText) convertView
						.findViewById(R.id.et_shuoshuo_comment);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.praise.setVisibility(View.GONE);
			holder.comment.setVisibility(View.GONE);
			holder.comment.setVisibility(View.GONE);
			holder.person.setVisibility(View.GONE);
			holder.pinlun.setVisibility(View.GONE);
			if (mList.get(position).getApps().size() > 4)
				holder.apps.setAdapter(new MyAppsAdapter(mList.get(position)
						.getApps().subList(0, 4)));
			else
				holder.apps.setAdapter(new MyAppsAdapter(mList.get(position)
						.getApps()));
			holder.content.setText("           "
					+ mList.get(position).getContent());
			holder.beforetime.setText(Utils.TimeStamp2Date(mList.get(position)
					.getCollecttime()));
			ImageLoaderUtils.displayImageView(mList.get(position).getThumb(),
					holder.icon);
			if (isDelete)
				holder.state.setVisibility(View.VISIBLE);
			else
				holder.state.setVisibility(View.GONE);
			holder.more.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (!isDelete) {
						ComplexPreferences.putObject(getActivity(),
								Constants.ShareAllDownloadApps,
								mList.get(position).getApps());
						Intent intent = new Intent(context,
								ShareAppListActivity.class);
						intent.putExtra("msgid", mList.get(position).getMsgid());
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(intent);
					}
				}
			});
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

		private class ViewHolder {
			ImageView icon;
			TextView name, person;
			TextView beforetime;
			TextView status;
			TextView content;
			HorizontialListView apps;
			ImageView comment, praise, more;
			CheckBox state;
			MyListView comments;
			ClearEditText pinlun;
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
			ImageLoaderUtils.displayAppIcon(apps.get(position).getIcon(),
					appicon);
			appname.setVisibility(View.GONE);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					appicon.getLayoutParams());
			lp.setMargins(0, 35, 0, 0);
			appicon.setLayoutParams(lp);
			return retval;
		}
	}
}
