package com.personlife.view.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.personlifep.ClassificationActivity;
import com.example.personlifep.R;
import com.personlife.adapter.home.AppListAdapter;
import com.personlife.bean.App;
import com.personlife.utils.Utils;
import com.personlife.view.activity.home.AppSearchActivity;
import com.personlife.widget.ClearEditText;
import com.personlife.widget.MyListView;

public class HomeFragment extends Fragment implements OnClickListener{
	private ClearEditText search;
	private Button kind;
	private Activity ctx;
	private View layout;
	private MyListView mLvApplist1,mLvApplist2,mLvApplist3;
	private AppListAdapter mAdapter;
	private List<App> apps = new ArrayList<App>();
	private TextView mTvMore1;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (layout == null) {
			ctx = this.getActivity();
			layout = ctx.getLayoutInflater().inflate(R.layout.fragment_home,null);
			initView();
			setOnListener();
			updateContent();
		} else {
			ViewGroup parent = (ViewGroup) layout.getParent();
			if (parent != null) {
				parent.removeView(layout);
			}
		}
		return layout;
	}
	public void initView(){
		search = (ClearEditText)layout.findViewById(R.id.et_home_search);
		mLvApplist1 =(MyListView)layout.findViewById(R.id.lv_app_list1);
		mLvApplist2 =(MyListView)layout.findViewById(R.id.lv_app_list2);
		mLvApplist3 =(MyListView)layout.findViewById(R.id.lv_app_list3);
		mTvMore1 = (TextView)layout.findViewById(R.id.tv_home_more1);
		kind = (Button)layout.findViewById(R.id.btn_home_class);
		mTvMore1.setOnClickListener(this);
		kind.setOnClickListener(this);
	}
	public void updateContent(){
		apps.add(new App("淘宝",5,"很好",1000));
		apps.add(new App("天猫",5,"很好",9999));
		apps.add(new App("搜狐",1,"一般",10));
		apps.add(new App("奇艺",2,"一般",10));
		if(apps.size() > 3)
			mAdapter = new AppListAdapter(getActivity(), apps.subList(0, 2));
		else
			mAdapter = new AppListAdapter(getActivity(), apps);
		mLvApplist1.setAdapter(mAdapter);
		mLvApplist2.setAdapter(mAdapter);
		mLvApplist3.setAdapter(mAdapter);
	}
	
	public void setOnListener(){
		search.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.et_home_search:
			Intent intent=new Intent(getActivity(),AppSearchActivity.class);
		    startActivity(intent);
			break;
		case R.id.tv_home_more1:
			mAdapter.setData(apps);
			mAdapter.notifyDataSetChanged();
			mTvMore1.setVisibility(View.GONE);
			break;
		case R.id.btn_home_class:
			Utils.start_Activity(getActivity(), ClassificationActivity.class, null);
			break;
		default:
			break;
		}
		
	}
}
