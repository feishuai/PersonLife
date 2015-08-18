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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personlifep.R;
import com.personlife.bean.App;
import com.personlife.utils.ImageLoaderUtils;
import com.personlife.utils.Utils;
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
	}
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		layout = inflater.inflate(R.layout.fragment_collection_apps, container,
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
			ImageLoaderUtils.displayAppIcon(mList.get(position).getIcon(),holder.icon);
			holder.appname.setText(mList.get(position).getName());
			holder.download.setVisibility(View.GONE);
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
			TextView appname;
			TextView status;
			TextView intro;
			TextView beforetime;
			Button download;
			CheckBox state;
		}
	}
}
