package com.personlife.view.activity.circle;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.example.personlifep.R;
import com.example.personlifep.R.layout;
import com.fasterxml.jackson.core.type.TypeReference;
import com.loopj.android.http.RequestParams;
import com.personlife.adapter.AppsAdapter;
import com.personlife.bean.App;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONObjectHttpResponseHandler;
import com.personlife.utils.ComplexPreferences;
import com.personlife.utils.Constants;
import com.personlife.utils.PersonInfoLocal;
import com.personlife.utils.Utils;
import com.personlife.view.activity.discovery.GuessActivity;
import com.personlife.view.activity.home.AllDownloadActivity;
import com.personlife.widget.MyListView;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.view.Gravity;

import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ShareAppListActivity extends Activity implements OnClickListener {
	private MyListView lv;
	private TextView mTitle;
	private Button mBack;
	private Button downloadButton;// 一键下载 按钮
	private ImageButton btnShare, btnCollect;// 分享按钮

	MyListView lvApps;
	List<App> apps;
	AppsAdapter appsadapter;
	int msgid;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share_applist);
		mTitle = (TextView) findViewById(R.id.txt_title);
		mTitle.setVisibility(View.GONE);
		mBack = (Button) findViewById(R.id.txt_left);
		mBack.setVisibility(View.VISIBLE);
		mBack.setOnClickListener(this);
		btnCollect = (ImageButton) findViewById(R.id.imgbtn_collect);
		btnCollect.setVisibility(View.VISIBLE);
		btnCollect.setOnClickListener(this);
		btnShare = (ImageButton) findViewById(R.id.imgbtn_share);
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
		downloadButton.setTextColor(getResources()
				.getColorStateList(R.color.bg));


		msgid = getIntent().getIntExtra("msgid", 1);
		lvApps = (MyListView) findViewById(R.id.lv_tuijian_apps);
		apps = new ArrayList<App>();
		apps = ComplexPreferences.getObject(getApplicationContext(),
				Constants.ShareAllDownloadApps,
				new TypeReference<ArrayList<App>>() {
				});
		appsadapter = new AppsAdapter(getApplicationContext(), apps);
		lvApps.setAdapter(appsadapter);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txt_left:
			finish();
			break;
		case R.id.txt_download:
			// ComplexPreferences.putObject(getApplicationContext(),
			// Constants.ShareAllDownloadApps, mList);

			// Utils.start_Activity(GuessActivity.this,
			// AllDownloadActivity.class,
			// new BasicNameValuePair("key",
			// Constants.ShareAllDownloadApps));

			Utils.start_Activity(ShareAppListActivity.this,
					AllDownloadActivity.class, new BasicNameValuePair("key",
							Constants.ShareAllDownloadApps));

			break;
		case R.id.imgbtn_share:
			Dialog dialog = new ShareDialog(ShareAppListActivity.this);

			// setContentView可以设置为一个View也可以简单地指定资源ID
			// LayoutInflater
			// li=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
			// View v=li.inflate(R.layout.dialog_layout, null);
			// dialog.setContentView(v);

			/*
			 * 获取圣诞框的窗口对象及参数对象以修改对话框的布局设置,
			 * 可以直接调用getWindow(),表示获得这个Activity的Window
			 * 对象,这样这可以以同样的方式改变这个Activity的属性.
			 */
			Window dialogWindow = dialog.getWindow();
			WindowManager.LayoutParams lp = dialogWindow.getAttributes();
			dialogWindow.setGravity(Gravity.RIGHT | Gravity.TOP);

			/*
			 * lp.x与lp.y表示相对于原始位置的偏移.
			 * 当参数值包含Gravity.LEFT时,对话框出现在左边,所以lp.x就表示相对左边的偏移,负值忽略.
			 * 当参数值包含Gravity.RIGHT时,对话框出现在右边,所以lp.x就表示相对右边的偏移,负值忽略.
			 * 当参数值包含Gravity.TOP时,对话框出现在上边,所以lp.y就表示相对上边的偏移,负值忽略.
			 * 当参数值包含Gravity.BOTTOM时,对话框出现在下边,所以lp.y就表示相对下边的偏移,负值忽略.
			 * 当参数值包含Gravity.CENTER_HORIZONTAL时
			 * ,对话框水平居中,所以lp.x就表示在水平居中的位置移动lp.x像素,正值向右移动,负值向左移动.
			 * 当参数值包含Gravity.CENTER_VERTICAL时
			 * ,对话框垂直居中,所以lp.y就表示在垂直居中的位置移动lp.y像素,正值向右移动,负值向左移动.
			 * gravity的默认值为Gravity.CENTER,即Gravity.CENTER_HORIZONTAL |
			 * Gravity.CENTER_VERTICAL.
			 * 
			 * 本来setGravity的参数值为Gravity.LEFT | Gravity.TOP时对话框应出现在程序的左上角,但在
			 * 我手机上测试时发现距左边与上边都有一小段距离,而且垂直坐标把程序标题栏也计算在内了, Gravity.LEFT,
			 * Gravity.TOP, Gravity.BOTTOM与Gravity.RIGHT都是如此,据边界有一小段距离
			 */
			lp.x = 5; // 新位置X坐标
			lp.y = 90; // 新位置Y坐标
//			lp.width = 300; // 宽度
			// lp.height = 300; // 高度
			lp.alpha = 0.7f; // 透明度

			// 当Window的Attributes改变时系统会调用此函数,可以直接调用以应用上面对窗口参数的更改,也可以用setAttributes
			// dialog.onWindowAttributesChanged(lp);
			dialogWindow.setAttributes(lp);

			/*
			 * 将对话框的大小按屏幕大小的百分比设置
			 */
			// WindowManager m = getWindowManager();
			// Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
			// WindowManager.LayoutParams p = dialogWindow.getAttributes();
			// // 获取对话框当前的参数值
			// p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
			// p.width = (int) (d.getWidth() * 0.65); // 宽度设置为屏幕的0.65
			// dialogWindow.setAttributes(p);

			dialog.show();
			break;
		case R.id.imgbtn_collect:
			RequestParams params = new RequestParams();
			params.add("phone", PersonInfoLocal.getPhone(getApplicationContext()));
			params.add("msg", String.valueOf(msgid));
			BaseAsyncHttp.postReq(getApplicationContext(), "/collect/set-msg",
					params, new JSONObjectHttpResponseHandler() {

						@Override
						public void jsonSuccess(JSONObject resp) {
							// TODO Auto-generated method stub
							int flag = resp.optInt("flag");
							if (flag == 1)
								Utils.showShortToast(getApplicationContext(),
										"收藏成功");
							else
								Utils.showShortToast(getApplicationContext(),
										"已收藏");
						}

						@Override
						public void jsonFail(JSONObject resp) {
							// TODO Auto-generated method stub
							Utils.showShortToast(getApplicationContext(),
									Constants.OnFailure);
						}
					});
			break;
		}
	}
}
