package com.personlife.view.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.http.message.BasicNameValuePair;


import org.json.JSONException;
import org.json.JSONObject;

import com.example.personlifep.R;
import com.loopj.android.http.RequestParams;
import com.personlife.bean.UserInfo;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONObjectHttpResponseHandler;
import com.personlife.utils.PersonInfoLocal;
import com.personlife.utils.Utils;
import com.personlife.view.activity.MainActivity;
import com.personlife.view.activity.personcenter.AboutUsActivity;
import com.personlife.view.activity.personcenter.ConnectionActivity;
import com.personlife.view.activity.personcenter.FeedBackActivity;
import com.personlife.view.activity.personcenter.MyCollectionActivity;
import com.personlife.view.activity.personcenter.MyownActivity;
import com.personlife.view.activity.personcenter.SecureActivity;
import com.personlife.view.activity.personcenter.SettingActivity;
import com.personlife.view.activity.personcenter.TaskList;
import com.personlife.view.activity.personcenter.TongzhiActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
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
	private Uri imageUri;
	private Bitmap bitmap;
	private String telphone,headuri;
	public PersonalCenter(String tel) {
		// TODO Auto-generated constructor stub
		super();
		telphone=tel;
	}
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

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initViews();
	}

	private void initViews() {
		username = (TextView) layout.findViewById(R.id.tvname);
		personsign = (TextView) layout.findViewById(R.id.tvpersonsign);
		head=(ImageView) layout.findViewById(R.id.head);
		sex=(ImageView) layout.findViewById(R.id.iv_sex);
		//联网获取用户信息
		
		if(PersonInfoLocal.getNcikName(getActivity(), telphone).length()!=0){
			headuri=PersonInfoLocal.getHeadUri(getActivity(), telphone);
			username.setText(PersonInfoLocal.getNcikName(getActivity(), telphone));
			personsign.setText(PersonInfoLocal.getSignature(getActivity(), telphone));
			if(PersonInfoLocal.getSex(getActivity(), telphone).equals("男"))
				sex.setImageResource(R.drawable.ic_sex_male);
			else 
				sex.setImageResource(R.drawable.ic_sex_female);
			Bitmap photo;
			try {
				photo = BitmapFactory
						.decodeStream(getActivity().getContentResolver().openInputStream(Uri.parse(headuri)));
				head.setImageBitmap(photo);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
			RequestParams request = new RequestParams();
			request.put("phone", telphone);
			BaseAsyncHttp.postReq(getActivity().getApplicationContext(),"/users/getinfo", request,
					new JSONObjectHttpResponseHandler() {

						@Override
						public void jsonSuccess(JSONObject resp) {
							try {
								username.setText(resp.get("nickname").toString());
								personsign.setText(resp.get("signature").toString());
								if(resp.get("gender").toString().equals("男"))
									sex.setImageResource(R.drawable.ic_sex_male);
								else 
									sex.setImageResource(R.drawable.ic_sex_female);
								Bitmap photo;
								try {
									photo = BitmapFactory
											.decodeStream(getActivity().getContentResolver().openInputStream(Uri.parse(Environment.getExternalStorageDirectory()
													.getPath() + "/" + telphone + ".jpg")));
									head.setImageBitmap(photo);
									photo.recycle();
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
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
		headinit();
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
		layout.findViewById(R.id.txt_downloadlist).setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.view_user:
			Intent intent=new Intent(getActivity(), MyownActivity.class);
			intent.putExtra("telphone", telphone);
			startActivity(intent);
	
			break;
		case R.id.txt_downloadlist:
			Utils.start_Activity(getActivity(), TaskList.class);
			break;
		case R.id.txt_mycollection:
//			Utils.start_Activity(getActivity(), MyCollectionActivity.class,
//					new BasicNameValuePair("NAME", "我的收藏"));
			break;
		case R.id.txt_connection:
			Utils.start_Activity(getActivity(), ConnectionActivity.class,
					new BasicNameValuePair("NAME", "通讯录"));
			break;
		case R.id.txt_tongzhi:
//			Utils.start_Activity(getActivity(), TongzhiActivity.class,
//					new BasicNameValuePair("NAME", "通知"));
			break;
		case R.id.txt_yinsi_anquan:
//			Utils.start_Activity(getActivity(), SecureActivity.class,
//					new BasicNameValuePair("NAME", "隐私与安全"));
			break;
		case R.id.txt_tongyongsetting:
//			Utils.start_Activity(getActivity(), SettingActivity.class,
//					new BasicNameValuePair("NAME", "通用设置"));
			break;
		case R.id.txt_yijianfankui:
//			Utils.start_Activity(getActivity(), FeedBackActivity.class,
//					new BasicNameValuePair("NAME", "意见反馈"));
			break;
		case R.id.txt_aboutus:
//			Utils.start_Activity(getActivity(), AboutUsActivity.class,
//					new BasicNameValuePair("NAME", "关于我们"));
			break;
		default:
			break;
		}
	}
	public void headinit(){
		File outputImage = new File(Environment.getExternalStorageDirectory(),
				telphone+".jpg");
		try {
			if (!outputImage.exists()) {
				outputImage.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		imageUri = Uri.fromFile(outputImage);

		try {
			bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver()
					.openInputStream(imageUri));
			head.setImageBitmap(bitmap);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
