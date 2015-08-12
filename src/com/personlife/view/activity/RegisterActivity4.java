package com.personlife.view.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.personlifep.R;
import com.personlife.utils.ActivityCollector;
import com.personlife.utils.Utils;
import com.personlife.view.activity.circle.CircleActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 * @author liugang
 * @date 2015年8月12日
 */
public class RegisterActivity4 extends Activity {

	private String telphone;
	private Button back, finishstep;
	private TextView tv_title;
	private GridView star_gridview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register4);
		ActivityCollector.addActivity(this);
		Intent intent = getIntent();
		telphone = intent.getStringExtra("telphone");
		initview();
		initdata();
	}

	public void initview() {
		back = (Button) findViewById(R.id.txt_left);
		back.setVisibility(View.GONE);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		tv_title = (TextView) findViewById(R.id.txt_title);
		tv_title.setText("完善个人信息");
		star_gridview = (GridView) findViewById(R.id.star_gridview);
	}

	public void initdata() {
		// 生成动态数组，并且转入数据
		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("register4_grid_staricon", R.drawable.star1);// 添加图像资源的ID
		map.put("register4_grid_starname", "李宇春");// 按序号做ItemText
		
		lstImageItem.add(map);
		map = new HashMap<String, Object>();
		map.put("register4_grid_staricon", R.drawable.star2);// 添加图像资源的ID
		map.put("register4_grid_starname", "范冰冰");// 按序号做ItemText
		
		lstImageItem.add(map);
		map = new HashMap<String, Object>();
		map.put("register4_grid_staricon", R.drawable.star3);// 添加图像资源的ID
		map.put("register4_grid_starname", "郑凯");// 按序号做ItemText
		
		lstImageItem.add(map);
		map = new HashMap<String, Object>();
		map.put("register4_grid_staricon", R.drawable.star4);// 添加图像资源的ID
		map.put("register4_grid_starname", "baby");// 按序号做ItemText
		
		lstImageItem.add(map);
		map = new HashMap<String, Object>();
		map.put("register4_grid_staricon", R.drawable.star5);// 添加图像资源的ID
		map.put("register4_grid_starname", "谁啊");// 按序号做ItemText
		
		lstImageItem.add(map);
		map = new HashMap<String, Object>();
		map.put("register4_grid_staricon", R.drawable.star6);// 添加图像资源的ID
		map.put("register4_grid_starname", "不认识");// 按序号做ItemText
		
		lstImageItem.add(map);
		map = new HashMap<String, Object>();
		map.put("register4_grid_staricon", R.drawable.star4);// 添加图像资源的ID
		map.put("register4_grid_starname", "又来？");// 按序号做ItemText
		
		lstImageItem.add(map);
		map = new HashMap<String, Object>();
		map.put("register4_grid_staricon", R.drawable.star5);// 添加图像资源的ID
		map.put("register4_grid_starname", "好烦");// 按序号做ItemText
		
		lstImageItem.add(map);
		map = new HashMap<String, Object>();
		map.put("register4_grid_staricon", R.drawable.star6);// 添加图像资源的ID
		map.put("register4_grid_starname", "额囧");// 按序号做ItemText
		
		lstImageItem.add(map);
		// 生成适配器的ImageItem <====> 动态数组的元素，两者一一对应
		SimpleAdapter saImageItems = new SimpleAdapter(this, // 没什么解释
				lstImageItem,// 数据来源
				R.layout.register4_item,// night_item的XML实现

				// 动态数组与ImageItem对应的子项
				new String[] { "register4_grid_staricon", "register4_grid_starname"
						 },

				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
				new int[] { R.id.register4_grid_staricon, R.id.register4_grid_starname
						 });
		// 添加并且显示
		star_gridview.setAdapter(saImageItems);
		// 添加消息处理
		star_gridview.setOnItemClickListener(new ItemClickListener());
	}
	// 当AdapterView被单击(触摸屏或者键盘)，则返回的Item单击事件
		class ItemClickListener implements OnItemClickListener {
			public void onItemClick(AdapterView<?> arg0,// The AdapterView where the
														// click happened
					View arg1,// The view within the AdapterView that was clicked
					int arg2,// The position of the view in the adapter
					long arg3// The row id of the item that was clicked
			) {
				
			}

		}
}
