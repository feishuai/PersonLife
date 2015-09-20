package com.personlife.view.activity.circle;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personlifep.R;
import com.personlife.adapter.AppListAdapter;
import com.personlife.bean.App;
import com.personlife.utils.DrawableStringUtils;
import com.personlife.utils.ImageLoaderUtils;
import com.personlife.utils.ListViewUtils;
import com.personlife.utils.SystemUtils;
import com.personlife.widget.MyListView;

public class CircleOtherAppsFragment extends Fragment {
	private View layout;
	private MyListView lv;
	private AppListAdapter appsAdapter;
	private List<App> apps;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		layout = inflater.inflate(R.layout.fragment_circle_apps, container,
				false);
		appsAdapter = new AppListAdapter(getActivity(), apps); // 不能放在构造函数里面，否个getActivity()返回null
		initView();
		return layout;
	}

	public CircleOtherAppsFragment(List<App> apps) {
		// TODO Auto-generated constructor stub
		this.apps = apps;
	}

	public int getListViewLayoutParams() {
		if (lv == null)
			return 0;
		int listViewHeight = ListViewUtils
				.setListViewHeightBasedOnChildren1(lv);
		return listViewHeight;
	}

	public void setData(List<App> apps) {
		this.apps = apps;
		if (appsAdapter != null)
			appsAdapter.notifyDataSetChanged();
	}

	public void initView() {
		lv = (MyListView) layout.findViewById(R.id.lv_circle_apps);
		lv.setAdapter(appsAdapter);
	}
}
