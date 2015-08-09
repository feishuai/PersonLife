package com.personlife.view.activity;




import java.util.HashSet;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.mapapi.map.Circle;
import com.example.personlifep.R;
import com.loopj.android.http.RequestParams;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONObjectHttpResponseHandler;
import com.personlife.utils.ActivityCollector;
import com.personlife.utils.ImageLoaderUtils;
import com.personlife.utils.Utils;
import com.personlife.view.activity.home.HomeActivity;
import com.personlife.view.fragment.CircleFragment;
import com.personlife.view.fragment.DiscoveryFragment;
import com.personlife.view.fragment.HomeFragment;
import com.personlife.view.fragment.PersonalCenter;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class MainActivity extends FragmentActivity implements OnClickListener{

	private int index;
	private TextView title;//当前页标题
	private ImageView[] tabButtons;//下方四个tab
	private TextView[] tabText;
	private int currentTabIndex=0;//当前tab
	private Button downloadButton;//一键下载 按钮
	private ImageButton txtSearch;//推荐界面的搜索按钮
	
	private Fragment[] fragments;
	private PersonalCenter personalCenter;//个人中心界面
	private HomeFragment homefragment;
	private DiscoveryFragment discoveryfragment;
	private CircleFragment circlefragment;
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_main);		
		ImageLoaderUtils.InitConfig(getApplicationContext());
		ActivityCollector.addActivity(this);
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
		if(pref.getString("password", "")!=null){
			initdataWithPassword();
		}else{
			initdataWithNoPassword();
		}
		
		init();
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		initdata();
	}

	public void initdataWithPassword(){
		
		RequestParams request = new RequestParams();
		request.put("phone", pref.getString("telephone", null));
		BaseAsyncHttp.postReq(getApplicationContext(),"/users/getinfo", request,
				new JSONObjectHttpResponseHandler() {

					@Override
					public void jsonSuccess(JSONObject resp) {
						try {
							editor.putString("id", resp.get("id").toString());
							editor.putString("userName", resp.get("nickname").toString());
							editor.putString("headUrl", resp.get("thumb").toString());
							editor.putString("signature", resp.get("signature").toString());
							editor.putString("sex", resp.get("gender").toString());
							editor.putString("location", resp.get("area").toString());
							editor.putString("job", resp.get("job").toString());
							String[] temp=resp.get("hobby").toString().split(" ");
							Set<String> set = new HashSet<String>();
							for(int i=0;i<temp.length;i++){
								set.add(temp[i]);
							}
							editor.putStringSet("hobby", set);
							editor.commit();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}

					@Override
					public void jsonFail(JSONObject resp) {
						// TODO Auto-generated method stub
					}
				});
		
	}
	public void initdataWithNoPassword(){
		
	}
	public void init(){
		title=(TextView) findViewById(R.id.txt_title);
		tabButtons=new ImageView[4];
		tabButtons[0]=(ImageView) findViewById(R.id.ib_home);
		tabButtons[1]=(ImageView) findViewById(R.id.ib_interact);
		tabButtons[2]=(ImageView) findViewById(R.id.ib_recom);
		tabButtons[3]=(ImageView) findViewById(R.id.ib_personal);		
		tabButtons[0].setSelected(true);//默认第一个tab被选中
		
		tabText=new TextView[4];
		tabText[0]=(TextView) findViewById(R.id.tv_home);
		tabText[1]=(TextView) findViewById(R.id.tv_interact);
		tabText[2]=(TextView) findViewById(R.id.tv_recom);
		tabText[3]=(TextView) findViewById(R.id.tv_personal);
		tabText[0].setTextColor(Color.GREEN);
		
		downloadButton=(Button) findViewById(R.id.txt_download);
		downloadButton.setVisibility(View.VISIBLE);//主页的一键下载按钮显示
		
		txtSearch=(ImageButton) findViewById(R.id.img_right);//推荐里的搜索按钮
		
		personalCenter=new PersonalCenter();
		homefragment = new HomeFragment();
		discoveryfragment = new DiscoveryFragment();
		circlefragment = new CircleFragment();
		fragments = new Fragment[] {homefragment,circlefragment,discoveryfragment,personalCenter};
		// 添加显示第一个fragment
		getSupportFragmentManager().beginTransaction()
			.add(R.id.fragment_container, personalCenter)
			.add(R.id.fragment_container,homefragment)
			.add(R.id.fragment_container, discoveryfragment)
			.add(R.id.fragment_container, circlefragment)
			.hide(personalCenter).hide(discoveryfragment).hide(circlefragment).show(homefragment).commit();

	}

	public void onTabClicked(View view) {
		downloadButton.setVisibility(View.GONE);
		txtSearch.setVisibility(View.GONE);
		switch (view.getId()) {
		case R.id.re_home:
			title.setText("我的APP");
			downloadButton.setVisibility(View.VISIBLE);
			index = 0;			
			break;
		case R.id.re_interact:
			index = 1;
			title.setText("朋友圈");
			break;
		case R.id.re_recommend:
			index = 2;
			title.setText("发现");
			txtSearch.setVisibility(View.VISIBLE);
			break;
		case R.id.re_personal:
			index = 3;
			title.setText("个人中心");
			break;
		}
		if (currentTabIndex != index) {
			FragmentTransaction trx = getSupportFragmentManager()
					.beginTransaction();
			trx.hide(fragments[currentTabIndex]);
			if (!fragments[index].isAdded()) {
				trx.add(R.id.fragment_container, fragments[index]);
			}
			trx.show(fragments[index]).commit();
//			if(index==3)
//				trx.show(personalCenter).commit();
//			else
//				trx.hide(personalCenter).commit();
		}
		tabButtons[currentTabIndex].setSelected(false);
		tabText[currentTabIndex].setTextColor(Color.BLACK);
		tabButtons[index].setSelected(true);// 把当前tab设为选中状态
		tabText[index].setTextColor(Color.GREEN);
		currentTabIndex = index;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}
	
}
