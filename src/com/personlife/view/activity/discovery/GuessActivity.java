package com.personlife.view.activity.discovery;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.personlifep.R;
import com.loopj.android.http.RequestParams;
import com.personlife.adapter.AppListAdapter;
import com.personlife.bean.App;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONArrayHttpResponseHandler;
import com.personlife.utils.ComplexPreferences;
import com.personlife.utils.Constants;
import com.personlife.utils.PersonInfoLocal;
import com.personlife.utils.Utils;
import com.personlife.view.activity.circle.ShareAppListActivity;
import com.personlife.view.activity.circle.ShareDialog;
import com.personlife.view.activity.home.AllDownloadActivity;
import com.personlife.widget.MyListView;

public class GuessActivity extends Activity implements OnClickListener {
	private MyListView lv;
	private ImageView back1, back2;
	private TextView tvContent, mTitle;
	private Button mBack;
	private String[] titles = { "猜您喜欢", "办公必备", "您的朋友喜欢" };
	private String[] contents = { "根据您的爱好生成，实时更新", "根据您的职业给您推荐相关APP",
			"好朋友的喜好全部都在这儿呢" };
	private Integer[] urls = { R.drawable.back1, R.drawable.back2,
			R.drawable.back3 };
	private Integer[] icons = { R.drawable.caininxihuan,
			R.drawable.bangongbibei, R.drawable.nindepengyouxihuan };

	private AppListAdapter aApps;
	private List<App> mList = new ArrayList<App>();
	private Button downloadButton;// 一键下载 按钮
	private ImageButton btnShare;// 分享按钮

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guess);
		lv = (MyListView) findViewById(R.id.lv_guess_app);
		back1 = (ImageView) findViewById(R.id.iv_guess_back);
		back2 = (ImageView) findViewById(R.id.iv_guess_back1);
		tvContent = (TextView) findViewById(R.id.tv_guess_content);
		mBack = (Button) findViewById(R.id.txt_left);
		mBack.setVisibility(View.VISIBLE);
		mBack.setOnClickListener(this);
		mTitle = (TextView) findViewById(R.id.txt_title);
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
		initData();
	}

	private void initData() {
		int kind = Integer.parseInt(getIntent().getStringExtra("kind"));
		mTitle.setText(titles[kind]);
		back1.setBackground(getResources().getDrawable(urls[kind]));
		back2.setBackground(getResources().getDrawable(icons[kind]));
		tvContent.setText(contents[kind]);
		RequestParams request = new RequestParams();
		request.add("phone", PersonInfoLocal.getPhone(getApplicationContext()));
		switch (kind) {
		case 0:
			BaseAsyncHttp.postReq(this, "/app/guess", request,
					new JSONArrayHttpResponseHandler() {
						@Override
						public void jsonSuccess(JSONArray resp) {
							// TODO Auto-generated method stub
							aApps = new AppListAdapter(getApplicationContext(),
									getApps(resp));
							lv.setAdapter(aApps);
						}

						@Override
						public void jsonFail(JSONArray resp) {
							// TODO Auto-generated method stub

						}
					});
			break;
		case 1:
			BaseAsyncHttp.postReq(this, "/app/work", request,
					new JSONArrayHttpResponseHandler() {
						@Override
						public void jsonSuccess(JSONArray resp) {
							// TODO Auto-generated method stub
							aApps = new AppListAdapter(getApplicationContext(),
									getApps(resp));
							lv.setAdapter(aApps);
						}

						@Override
						public void jsonFail(JSONArray resp) {
							// TODO Auto-generated method stub

						}
					});
			break;
		case 2:
			BaseAsyncHttp.postReq(this, "/friend/like", request,
					new JSONArrayHttpResponseHandler() {
						@Override
						public void jsonSuccess(JSONArray resp) {
							// TODO Auto-generated method stub
							aApps = new AppListAdapter(getApplicationContext(),
									getApps(resp));
							lv.setAdapter(aApps);
						}

						@Override
						public void jsonFail(JSONArray resp) {
							// TODO Auto-generated method stub

						}
					});
			break;
		}
	}

	public List<App> getApps(JSONArray resp) {
		List<App> apps = new ArrayList<App>();
		for (int i = 0; i < resp.length(); i++) {
			App appInfo = new App();
			appInfo.setId(resp.optJSONObject(i).optInt("id"));
			appInfo.setName(resp.optJSONObject(i).optString("name"));
			appInfo.setVersion(resp.optJSONObject(i).optString("version"));
			appInfo.setDownloadUrl(resp.optJSONObject(i).optString(
					"android_url"));
			appInfo.setStars(resp.optJSONObject(i).optInt("stars"));
			appInfo.setDowloadcount(resp.optJSONObject(i).optInt(
					"downloadcount"));
			appInfo.setIntrodution(resp.optJSONObject(i).optString(
					"introduction"));
			appInfo.setUpdateDate(resp.optJSONObject(i).optLong("updated_at"));
			appInfo.setSize(resp.optJSONObject(i).optString("size"));
			appInfo.setIcon(resp.optJSONObject(i).optString("icon"));
			appInfo.setUpdateLog(resp.optJSONObject(i).optString("updated_log"));
			appInfo.setProfile(resp.optJSONObject(i).optString("profile"));
			appInfo.setDownloadPath(Constants.DownloadPath + appInfo.getName()
					+ ".apk");
			apps.add(appInfo);
		}
		return apps;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txt_left:
			finish();
			break;
		case R.id.txt_download:
			ComplexPreferences.putObject(getApplicationContext(),
					Constants.ShareAllDownloadApps, mList);
			Utils.start_Activity(GuessActivity.this, AllDownloadActivity.class,
					new BasicNameValuePair("key",
							Constants.ShareAllDownloadApps));
			break;
		case R.id.imgbtn_share:
			Dialog dialog = new ShareDialog(GuessActivity.this);

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
			// lp.width = 300; // 宽度
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
		}
	}

}
