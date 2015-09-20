package com.personlife.view.activity.personcenter;

import com.example.personlifep.R;
import com.personlife.utils.ActivityCollector;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author liugang
 * @date 2015年8月7日
 */
public class TaskList extends FragmentActivity implements OnClickListener {

	private TextView tv_title;
	private Button back;
	private ImageView tab_task, tab_applist;
	private TabTaskList taskList;
	private TabAppList appList;
	private int currentTabIndex = 0;// 当前tab
	private Fragment[] fragments;
	private int index;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ActivityCollector.addActivity(this);
		setContentView(R.layout.activity_tasklist);
		initView();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	public void initView() {
		tv_title = (TextView) findViewById(R.id.txt_title);
		back = (Button) findViewById(R.id.txt_left);
		tv_title.setText("任务列表");
		back.setVisibility(View.VISIBLE);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		tab_applist = (ImageView) findViewById(R.id.ib_hengxian2);
		tab_task = (ImageView) findViewById(R.id.ib_hengxian1);
		tab_task.setSelected(true);
		taskList = new TabTaskList();
		appList = new TabAppList();
		fragments = new Fragment[] { taskList, appList };
		getSupportFragmentManager().beginTransaction()
				.add(R.id.task_container, taskList)
				.add(R.id.task_container, appList).hide(appList).show(taskList)
				.commit();

	}

	public void onTabClicked(View view) {
		switch (view.getId()) {
		case R.id.re_tasklist:
			tab_task.setImageResource(R.drawable.tab_hengxian_selected);
			tab_applist.setImageResource(R.drawable.tab_hengxian_normal);
			index = 0;
			break;
		case R.id.re_applist:
			tab_applist.setImageResource(R.drawable.tab_hengxian_selected);
			tab_task.setImageResource(R.drawable.tab_hengxian_normal);
			index = 1;
			break;

		}
		if (currentTabIndex != index) {
			FragmentTransaction trx = getSupportFragmentManager()
					.beginTransaction();
			trx.hide(fragments[currentTabIndex]);
			if (!fragments[index].isAdded()) {
				trx.add(R.id.task_container, fragments[index]);
			}
			trx.show(fragments[index]).commit();
		}
		currentTabIndex = index;
	}
}
