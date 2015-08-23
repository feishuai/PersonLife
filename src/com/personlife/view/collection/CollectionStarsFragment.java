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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.personlifep.R;
import com.loopj.android.http.RequestParams;
import com.personlife.bean.Star;
import com.personlife.bean.User;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONObjectHttpResponseHandler;
import com.personlife.utils.Constants;
import com.personlife.utils.ImageLoaderUtils;
import com.personlife.utils.PersonInfoLocal;
import com.personlife.utils.Utils;
import com.personlife.view.activity.circle.CircleActivity;
import com.personlife.view.activity.home.AppDetailActivity;
import com.personlife.widget.CircleImageView;
import com.personlife.widget.MyListView;

public class CollectionStarsFragment extends Fragment {
	private View layout;
	private MyListView lv;
	List<Star> mList;
	List<Star> deletedList;
	StarsAdapter starsAdapter;

	public CollectionStarsFragment(List<Star> mList) {
		// TODO Auto-generated constructor stub
		this.mList = mList;
		this.deletedList = new ArrayList<Star>();
	}

	public List<Star> getAppsList() {
		return mList;
	}

	public void setStarsList(List<Star> mList) {
		this.mList = mList;
		if (starsAdapter != null)
			starsAdapter.notifyDataSetChanged();
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		layout = inflater.inflate(R.layout.fragment_collection_stars,
				container, false);
		initView();
		return layout;
	}

	public void setDeleteMode() {
		starsAdapter.setDeleteMode();
	}

	public void setNormalMode() {
		int n = deletedList.size();
		for (int i = 0; i < n; i++) {
			RequestParams params = new RequestParams();
			params.add("myphone", PersonInfoLocal.getPhone());
			params.add("fphone", deletedList.get(i).getPhone());

			BaseAsyncHttp.postReq(getActivity(), "/follow/cancel", params,
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
		Utils.showShortToast(getActivity(), "删除" + n + " 位明星收藏成功");
		mList.removeAll(deletedList);
		deletedList.clear();
		starsAdapter.setNormalMode();
	}

	public boolean updateData() {
		Utils.showShortToast(getActivity(), "删除应用收藏成功");
		return true;

	}

	public void initView() {
		lv = (MyListView) layout.findViewById(R.id.lv_collection_stars);
		starsAdapter = new StarsAdapter(getActivity());
		lv.setAdapter(starsAdapter);

	}

	class StarsAdapter extends BaseAdapter {

		private Context context;
		private Boolean isDelete = false;

		public StarsAdapter(Context context) {
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
						.inflate(R.layout.layout_item_star, null);
				holder = new ViewHolder();
				holder.name = (TextView) convertView
						.findViewById(R.id.tv_star_name);
				holder.signature = (TextView) convertView
						.findViewById(R.id.tv_star_signature);
				holder.icon = (ImageView) convertView
						.findViewById(R.id.iv_star_icon);
				holder.home = (ImageButton) convertView
						.findViewById(R.id.ib_star_home);
				holder.state = (CheckBox) convertView
						.findViewById(R.id.cb_star_state);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			ImageLoaderUtils.displayAppIcon(mList.get(position).getThumb(),
					holder.icon);
			holder.name.setText(mList.get(position).getNickname());
			holder.signature.setText(mList.get(position).getSignature());
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
			Log.i("check " + position + "state",
					String.valueOf(holder.state.isChecked()));
			holder.home.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (!isDelete) {
						Intent intent = new Intent(context,
								CircleActivity.class);
						intent.putExtra("phone", mList.get(position)
								.getPhone());
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
			TextView name;
			TextView signature;
			ImageButton home;
			CheckBox state;
		}
	}
}