package com.personlife.view.fragment;

import org.apache.http.message.BasicNameValuePair;










import com.example.personlifep.R;
import com.personlife.bean.UserInfo;
import com.personlife.utils.Utils;
import com.personlife.view.activity.personcenter.AboutUsActivity;
import com.personlife.view.activity.personcenter.ConnectionActivity;
import com.personlife.view.activity.personcenter.FeedBackActivity;
import com.personlife.view.activity.personcenter.MyCollectionActivity;
import com.personlife.view.activity.personcenter.MyownActivity;
import com.personlife.view.activity.personcenter.SecureActivity;
import com.personlife.view.activity.personcenter.SettingActivity;
import com.personlife.view.activity.personcenter.TongzhiActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ImageView;
import android.widget.TextView;

/**  
 *   
 * @author liugang  
 * @date 2015年6月21日   
 */
public class PersonalCenter extends Fragment implements OnClickListener{

	private Activity ctx;
	private View layout;
	private TextView username, personsign;
	private ImageView head,sex;//头像
	private UserInfo userInfo;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (layout == null) {
			ctx = this.getActivity();
			layout = ctx.getLayoutInflater().inflate(R.layout.fragment_personalcenter,null);
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

	private void initViews() {
		username = (TextView) layout.findViewById(R.id.tvname);
		personsign = (TextView) layout.findViewById(R.id.tvpersonsign);
		head=(ImageView) layout.findViewById(R.id.head);
		sex=(ImageView) layout.findViewById(R.id.iv_sex);
		//联网获取用户信息
		userInfo=new UserInfo();
		userInfo.setNickname("刘刚");
		userInfo.setSex("男");
		userInfo.setArea("杭州");
		userInfo.setProfession("Java工程师");
		userInfo.setInterests("看美女");
		userInfo.setSign("至今仍放你在心上是谓念念");
		
		
		username.setText(userInfo.getNickname());
		personsign.setText(userInfo.getSign());
		if(userInfo.getSex().equals("男"))
			sex.setImageResource(R.drawable.ic_sex_male);
		else 
			sex.setImageResource(R.drawable.ic_sex_female);
	}

	private void setOnListener() {
		layout.findViewById(R.id.view_user).setOnClickListener(this);
		layout.findViewById(R.id.txt_mycollection).setOnClickListener(this);
		layout.findViewById(R.id.txt_connection).setOnClickListener(this);
		layout.findViewById(R.id.txt_tongzhi).setOnClickListener(this);
		layout.findViewById(R.id.txt_yinsi_anquan).setOnClickListener(this);
		layout.findViewById(R.id.txt_tongyongsetting).setOnClickListener(this);
		layout.findViewById(R.id.txt_yijianfankui).setOnClickListener(this);
		layout.findViewById(R.id.txt_aboutus).setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.view_user:
			Intent intent=new Intent();
			intent.setClass(getActivity(), MyownActivity.class);
//			Bundle bundle=new Bundle();
//			bundle.putSerializable("userinfo", userInfo);
//			intent.putExtras(bundle);
			startActivity(intent);		
			break;
		case R.id.txt_mycollection:
			Utils.start_Activity(getActivity(), MyCollectionActivity.class,
					new BasicNameValuePair("NAME", "我的收藏"));
			break;
		case R.id.txt_connection:
			Utils.start_Activity(getActivity(), ConnectionActivity.class,
					new BasicNameValuePair("NAME", "通讯录"));
			break;
		case R.id.txt_tongzhi:
			Utils.start_Activity(getActivity(), TongzhiActivity.class,
					new BasicNameValuePair("NAME", "通知"));
			break;
		case R.id.txt_yinsi_anquan:
			Utils.start_Activity(getActivity(), SecureActivity.class,
					new BasicNameValuePair("NAME", "隐私与安全"));
			break;
		case R.id.txt_tongyongsetting:
			Utils.start_Activity(getActivity(), SettingActivity.class,
					new BasicNameValuePair("NAME", "通用设置"));
			break;
		case R.id.txt_yijianfankui:
			Utils.start_Activity(getActivity(), FeedBackActivity.class,
					new BasicNameValuePair("NAME", "意见反馈"));
			break;
		case R.id.txt_aboutus:
			Utils.start_Activity(getActivity(), AboutUsActivity.class,
					new BasicNameValuePair("NAME", "关于我们"));
			break;
		default:
			break;
		}
	}
}
