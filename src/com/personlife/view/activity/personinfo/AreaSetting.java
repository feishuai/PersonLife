package com.personlife.view.activity.personinfo;

import java.util.ArrayList;
import java.util.List;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.example.personlifep.R;








import com.personlife.utils.ActivityCollector;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


/**  
 *   
 * @author liugang  
 * @date 2015年6月24日   
 */
public class AreaSetting extends Activity implements OnClickListener{

	private Button back;
	private TextView t_title;
	private TextView location;
	//地理定位
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	LocationClientOption option = new LocationClientOption();
	int i; 
	private SharedPreferences.Editor editor;
	private ListView listView;
	private List<String> locations = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_areasetting);
		ActivityCollector.addActivity(this);
		init();
		
	}

	public void init(){
		back=(Button) findViewById(R.id.txt_left);
		t_title=(TextView) findViewById(R.id.txt_title);
		location=(TextView) findViewById(R.id.location);
		listView = (ListView) findViewById(R.id.location_list);
		back.setVisibility(View.VISIBLE);
		t_title.setText("地区");
		mLocationClient = new LocationClient(this);//声明LocationClient类
	    mLocationClient.registerLocationListener(myListener);//注册监听函数
	    option.setLocationMode(LocationMode.Device_Sensors);//设置定位模式
	    option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
	    option.setIsNeedAddress(true);//返回的定位结果包含地址信息
	    mLocationClient.setLocOption(option);
	    mLocationClient.start();
	    i=mLocationClient.requestLocation();
	    editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mLocationClient.stop();
				onBackPressed();
				
			}
		});
		locations = CityData.getCityList();
		LocationAdapter adapter = new LocationAdapter(this, R.layout.location_item,locations);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				editor.putString("location",locations.get(position));
				editor.commit();
				finish();
			}
		});
	}
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation arg0) {
			// TODO Auto-generated method stub
			location.setText(arg0.getCountry()+" "
			+arg0.getProvince().substring(0, arg0.getProvince().length()-1)+" "
					+arg0.getCity().substring(0, arg0.getCity().length()-1));
		}
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String temp = location.getText().toString().substring(3);		
		editor.putString("location",temp);
		editor.commit();
		finish();
	}
	
}
