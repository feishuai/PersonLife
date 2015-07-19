package com.personlife.view.activity;




import com.example.personlifep.R;
import com.personlife.utils.ImageLoaderUtils;
import com.personlife.view.activity.home.HomeActivity;
import com.personlife.view.fragment.HomeFragment;
import com.personlife.view.fragment.PersonalCenter;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_main);		
		ImageLoaderUtils.InitConfig(getApplicationContext());
		init();
		
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
		fragments = new Fragment[] {homefragment,homefragment,homefragment,personalCenter};
		// 添加显示第一个fragment
		getSupportFragmentManager().beginTransaction()
			.add(R.id.fragment_container, personalCenter)
			.add(R.id.fragment_container,homefragment)
			.hide(personalCenter).show(homefragment).commit();

	}

	public void onTabClicked(View view) {
		downloadButton.setVisibility(View.GONE);
		txtSearch.setVisibility(View.GONE);
		switch (view.getId()) {
		case R.id.re_home:
			title.setText("定制生活");
			downloadButton.setVisibility(View.VISIBLE);
			index = 0;			
			break;
		case R.id.re_interact:
			index = 1;
			title.setText("互动");
			break;
		case R.id.re_recommend:
			index = 2;
			title.setText("推荐");
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
