package com.personlife.view.activity.personcenter;

import com.example.personlifep.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ListView;

/**  
 *   
 * @author liugang  
 * @date 2015年8月7日   
 */
public class TabAppList extends Fragment implements OnClickListener{

	private Activity ctx;
	private View layout;
	private ListView listView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (layout == null) {
			ctx = this.getActivity();
			layout = ctx.getLayoutInflater().inflate(R.layout.fragment_task,null);
			initViews();
			setOnListener();
		} else {
			ViewGroup parent = (ViewGroup) layout.getParent();
			if (parent != null) {
				parent.removeView(layout);
			}
		}
		return layout;
	}
	public void initViews(){
		listView = (ListView) layout.findViewById(R.id.listview_tasklist);
	}
	public void setOnListener(){
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
