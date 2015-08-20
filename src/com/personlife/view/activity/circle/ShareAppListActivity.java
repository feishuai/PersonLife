package com.personlife.view.activity.circle;

import org.apache.http.message.BasicNameValuePair;

import com.example.personlifep.R;
import com.example.personlifep.R.layout;
import com.personlife.utils.ComplexPreferences;
import com.personlife.utils.Constants;
import com.personlife.utils.Utils;
import com.personlife.view.activity.discovery.GuessActivity;
import com.personlife.view.activity.home.AllDownloadActivity;
import com.personlife.widget.MyListView;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ShareAppListActivity extends Activity implements OnClickListener{
	private MyListView lv;
	private TextView mTitle;
	private Button mBack;
	private Button downloadButton;// 一键下载 按钮
	private ImageButton btnShare,btnCollect;// 分享按钮
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share_applist);
		mTitle = (TextView) findViewById(R.id.txt_title);
		mTitle.setVisibility(View.GONE);
		mBack = (Button) findViewById(R.id.txt_left);
		mBack.setVisibility(View.VISIBLE);
		mBack.setOnClickListener(this);
		btnCollect = (ImageButton)findViewById(R.id.imgbtn_collect);
		btnCollect.setVisibility(View.VISIBLE);
		btnCollect.setOnClickListener(this);
		btnShare = (ImageButton)findViewById(R.id.imgbtn_share);
		btnShare.setVisibility(View.VISIBLE);
		btnShare.setOnClickListener(this);
		downloadButton = (Button) findViewById(R.id.txt_download);
		downloadButton.setVisibility(View.VISIBLE);// 主页的一键下载按钮显示
		downloadButton.setOnClickListener(this);
		Drawable xiazai = getResources().getDrawable(R.drawable.yijianxiazai);
		// / 这一步必须要做,否则不会显示.
		xiazai.setBounds(0, 0, xiazai.getMinimumWidth(),
				xiazai.getMinimumHeight());
		downloadButton.setCompoundDrawables(xiazai, null, null, null);
		downloadButton.setTextColor(getResources().getColorStateList(R.color.bg));
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txt_left:
			finish();
			break;
		case R.id.txt_download:
//			ComplexPreferences.putObject(getApplicationContext(),
//					Constants.ShareAllDownloadApps, mList);
//			Utils.start_Activity(GuessActivity.this, AllDownloadActivity.class,
//					new BasicNameValuePair("key",
//							Constants.ShareAllDownloadApps));
			break;
		case R.id.imgbtn_share:
			break;
		case R.id.imgbtn_collect:
			break;
		}
	}
}
