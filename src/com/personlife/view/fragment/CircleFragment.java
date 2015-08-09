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

import com.example.personlifep.R;
import com.personlife.adapter.AppListAdapter;
import com.personlife.bean.App;
import com.personlife.utils.Utils;
import com.personlife.view.activity.home.AppSearchActivity;
import com.personlife.view.activity.home.ClassificationActivity;
import com.personlife.view.fragment.HomeFragment.KindsAdapter;
import com.personlife.widget.ClearEditText;
import com.personlife.widget.MyListView;

public class CircleFragment extends Fragment implements OnClickListener {
	private ClearEditText search;
	private Button kind;
	private Activity ctx;
	private View layout;
	private MyListView mLvApps;

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
//		search = (ClearEditText) layout.findViewById(R.id.et_home_search);
	}

	public void setOnListener() {
	}

	public void initData() {
	
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
//		case R.id.et_home_search:
//			Intent intent = new Intent(getActivity(), AppSearchActivity.class);
//			startActivity(intent);
//			break;
//		case R.id.btn_home_class:
//			Utils.start_Activity(getActivity(), ClassificationActivity.class,
//					null);
//			// Utils.start_Activity(getActivity(), RecommendActivity.class,
//			// null);
//			break;
		default:
			break;
		}
	}
}
