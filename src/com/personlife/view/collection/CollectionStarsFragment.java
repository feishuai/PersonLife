package com.personlife.view.collection;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.personlifep.R;
import com.personlife.bean.Star;
import com.personlife.bean.User;
import com.personlife.utils.ImageLoaderUtils;
import com.personlife.utils.Utils;
import com.personlife.widget.CircleImageView;
import com.personlife.widget.MyListView;

public class CollectionStarsFragment extends Fragment {
	private View layout;
	private MyListView lv;
	List<Star> mList;
	List<Star> deletedList;
	AppsAdapter appsAdapter;
	
	public CollectionStarsFragment(List<Star> mList) {
		// TODO Auto-generated constructor stub
		this.mList = mList;
		this.deletedList = new ArrayList<Star>();
	}
	public List<Star> getAppsList() {
		return mList;
	}
	public void setAppsList(List<Star> mList) {
		this.mList = mList;
	}
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		layout = inflater.inflate(R.layout.fragment_collection_stars, container,
				false);
		initView();
		return layout;
	}
	
	public void setDeleteMode(){
		appsAdapter.setDeleteMode();
	}
	
	public void setNormalMode(){
		if(updateData()){
			mList.removeAll(deletedList);
		}
		deletedList.clear();
		appsAdapter.setNormalMode();
	}
	
	public boolean updateData(){
		Utils.showShortToast(getActivity(), "删除应用收藏成功");
		return true;
		
	}
	public void initView() {
		lv = (MyListView) layout.findViewById(R.id.lv_collection_stars);
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
			ImageLoaderUtils.displayAppIcon(mList.get(position).getThumb(),holder.icon);
			holder.name.setText(mList.get(position).getNickname());
			// ImageLoaderUtils.displayAppIcon("https://ss0.bdstatic.com/-0U0bnSm1A5BphGlnYG/tam-ogel/5136becf77e9cfc440849e0b694fdd6e_121_121.jpg",
			// holder.icon);
//			holder.name.setText(mList.get(position).getUserName());
			if(isDelete)
				holder.state.setVisibility(View.VISIBLE);
			else
				holder.state.setVisibility(View.GONE);
			holder.state.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					if(isChecked){
						if(!deletedList.contains(mList.get(position)));
							deletedList.add(mList.get(position));
					}else
						deletedList.remove(mList.get(position));
				}
			});
			
//			设置state的状态为未选中
			holder.state.setChecked(false);
			Log.i("check "+position +"state", String.valueOf(holder.state.isChecked()));
//			convertView.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					Intent intent = new Intent(context, AppDetailActivity.class);
//					intent.putExtra(Constants.AppId, mlist.get(position)
//							.getId());
//					// intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//					context.startActivity(intent);
//
//				}
//			});
			return convertView;
		}

		public void setDeleteMode(){
			isDelete = true;
			notifyDataSetChanged();
		}
		
		public void setNormalMode(){
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