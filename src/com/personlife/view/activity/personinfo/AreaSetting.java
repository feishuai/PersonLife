package com.personlife.view.activity.personinfo;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.example.personlifep.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
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
	
	private ListView listView;
	private String[] locations={"sgd","a","b","c","d","e","f"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_areasetting);
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
	    
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mLocationClient.stop();
				onBackPressed();
				
			}
		});
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,locations);
		listView.setAdapter(adapter);
	}
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation arg0) {
			// TODO Auto-generated method stub
			location.setText(arg0.getCountry()+" "+arg0.getProvince()+" "+arg0.getCity());
		}
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
}
