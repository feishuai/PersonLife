package com.personlife.view.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

import com.example.personlifep.R;
import com.loopj.android.http.RequestParams;
import com.personlife.bean.UserInfo;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONObjectHttpResponseHandler;
import com.personlife.utils.Constants;
import com.personlife.utils.ImageLoaderUtils;
import com.personlife.utils.PersonInfoLocal;
import com.personlife.utils.Utils;
import com.personlife.view.activity.MainActivity;
import com.personlife.view.activity.personcenter.AboutUsActivity;
import com.personlife.view.activity.personcenter.ConnectionActivity;
import com.personlife.view.activity.personcenter.FeedBackActivity;
import com.personlife.view.activity.personcenter.MessageActivity;
import com.personlife.view.activity.personcenter.MyCollectionActivity;
import com.personlife.view.activity.personcenter.MyownActivity;
import com.personlife.view.activity.personcenter.SecureActivity;
import com.personlife.view.activity.personcenter.SettingActivity;
import com.personlife.view.activity.personcenter.TaskList;
import com.personlife.view.activity.personcenter.TongzhiActivity;
import com.personlife.view.activity.personinfo.AreaSetting;
import com.personlife.view.activity.personinfo.Interests;
import com.personlife.view.activity.personinfo.NickName;
import com.personlife.view.activity.personinfo.PersonalSign;
import com.personlife.view.activity.personinfo.Profession;
import com.personlife.view.collection.CollectionActivity;
import com.personlife.widget.CircleImageView;

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
public class PersonalCenter extends Fragment implements OnClickListener {

	private Activity ctx;
	private View layout;
	private TextView username, personsign, info, tongzhi;
	private ImageView sex;
	private ImageView head;
	private Uri imageUri;
	private Bitmap bitmap;
	private String telphone, headuri;

	// 若Fragement定义有带参构造函数，则一定要定义public的默认的构造函数
	public PersonalCenter() {
	}

	public PersonalCenter(String tel) {
		// TODO Auto-generated constructor stub
		telphone = tel;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (layout == null) {
			ctx = getActivity();
			layout = ctx.getLayoutInflater().inflate(
					R.layout.fragment_personalcenter, null);
			ShareSDK.initSDK(getActivity());
			username = (TextView) layout.findViewById(R.id.tvname);
			personsign = (TextView) layout.findViewById(R.id.tvpersonsign);
			head = (ImageView) layout.findViewById(R.id.head);
			sex = (ImageView) layout.findViewById(R.id.iv_sex);
			// 联网获取用户信息
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

	public void initViews() {

		if (PersonInfoLocal.getNcikName(getActivity(), telphone).length() != 0) {
			headuri = PersonInfoLocal.getHeadUri(getActivity(), telphone);
			username.setText(PersonInfoLocal.getNcikName(getActivity(),
					telphone));
			personsign.setText(PersonInfoLocal.getSignature(getActivity(),
					telphone));
			if (PersonInfoLocal.getSex(getActivity(), telphone).equals("男"))
				sex.setImageResource(R.drawable.ic_sex_male);
			else
				sex.setImageResource(R.drawable.ic_sex_female);
			ImageLoaderUtils.displayImageView(
					PersonInfoLocal.getHeadKey(ctx, telphone), head);
			// Bitmap photo;
			// try {
			// photo = BitmapFactory
			// .decodeStream(getActivity().getContentResolver().openInputStream(Uri.parse(headuri)));
			// head.setImageBitmap(photo);
			// } catch (FileNotFoundException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }

		} else {
			RequestParams request = new RequestParams();
			request.add("starphone", telphone);
			request.put("myphone", telphone);
			BaseAsyncHttp.postReq(getActivity().getApplicationContext(),
					"/users/getinfo", request,
					new JSONObjectHttpResponseHandler() {

						@Override
						public void jsonSuccess(JSONObject resp) {
							try {
								JSONObject userjson = resp
										.optJSONObject("user");
								username.setText(userjson.get("nickname")
										.toString());
								personsign.setText(userjson.get("signature")
										.toString());
								if (userjson.get("gender").toString()
										.equals("男"))
									sex.setImageResource(R.drawable.ic_sex_male);
								else
									sex.setImageResource(R.drawable.ic_sex_female);
								ImageLoaderUtils.displayImageView(
										PersonInfoLocal.getHeadKey(ctx,
												telphone), head);
								PersonInfoLocal.storeNickname(getActivity(),
										telphone, userjson.get("nickname")
												.toString());
								PersonInfoLocal.storeSex(getActivity(),
										telphone, userjson.get("gender")
												.toString());
								PersonInfoLocal.storeLocation(getActivity(),
										telphone, userjson.get("area")
												.toString());
								PersonInfoLocal.storeJob(getActivity(),
										telphone, userjson.get("job")
												.toString());
								PersonInfoLocal.storeHobby(getActivity(),
										telphone, userjson.get("hobby")
												.toString());
								PersonInfoLocal.storeSignature(getActivity(),
										telphone, userjson.get("signature")
												.toString());

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
		// headinit();
	}

	private void setOnListener() {
		layout.findViewById(R.id.view_user).setOnClickListener(this);
		layout.findViewById(R.id.txt_mycollection).setOnClickListener(this);
		layout.findViewById(R.id.txt_connection).setOnClickListener(this);
		layout.findViewById(R.id.txt_tongzhi).setOnClickListener(this);
		layout.findViewById(R.id.txt_yinsi_anquan).setOnClickListener(this);
		// layout.findViewById(R.id.txt_tongyongsetting).setOnClickListener(this);
		layout.findViewById(R.id.txt_yijianfankui).setOnClickListener(this);
		layout.findViewById(R.id.txt_aboutus).setOnClickListener(this);
		layout.findViewById(R.id.txt_downloadlist).setOnClickListener(this);
		layout.findViewById(R.id.txt_message).setOnClickListener(this);
		layout.findViewById(R.id.sina).setOnClickListener(this);
		layout.findViewById(R.id.wxchat).setOnClickListener(this);
		layout.findViewById(R.id.wechat).setOnClickListener(this);
		layout.findViewById(R.id.qq).setOnClickListener(this);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == 1)
			initViews();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.view_user:
			Intent intent = new Intent(getActivity(), MyownActivity.class);
			intent.putExtra("telphone", telphone);
			startActivityForResult(intent, 1);

			break;
		case R.id.txt_downloadlist:
			Utils.start_Activity(getActivity(), TaskList.class);
			break;
		case R.id.txt_mycollection:

			Intent intent_c = new Intent(getActivity(),
					CollectionActivity.class);
			intent_c.putExtra("telphone", telphone);
			startActivity(intent_c);
			break;
		case R.id.txt_connection:
			Intent intent_connection = new Intent(getActivity(),
					ConnectionActivity.class);
			intent_connection.putExtra("telphone", telphone);
			startActivity(intent_connection);
			break;
		case R.id.txt_message:
			Utils.start_Activity(getActivity(), MessageActivity.class);
			break;
		case R.id.txt_tongzhi:
			Utils.start_Activity(getActivity(), TongzhiActivity.class);
			break;
		case R.id.txt_yinsi_anquan:
			Intent intentyinsi = new Intent(getActivity(), SecureActivity.class);
			intentyinsi.putExtra("telphone", telphone);
			startActivity(intentyinsi);
			break;
		// case R.id.txt_tongyongsetting:
		// Utils.start_Activity(getActivity(), SettingActivity.class,
		// new BasicNameValuePair("NAME", "通用设置"));
		// break;
		// case R.id.txt_tuijian:
		//
		// ShareSDK.initSDK(ctx);
		// OnekeyShare oks = new OnekeyShare();
		// // 关闭sso授权
		// oks.disableSSOWhenAuthorize();
		//
		// // 分享时Notification的图标和文字 2.5.9以后的版本不调用此方法
		// // oks.setNotification(R.drawable.ic_launcher,
		// // getString(R.string.app_name));
		// // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		// oks.setTitle(getString(R.string.share));
		// // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		// oks.setTitleUrl("http://sharesdk.cn");
		// // text是分享文本，所有平台都需要这个字段
		// oks.setText("我是分享文本");
		// // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		// // oks.setImagePath("/sdcard/test.jpg");// 确保SDcard下面存在此张图片
		// // url仅在微信（包括好友和朋友圈）中使用
		// oks.setUrl("http://sharesdk.cn");
		// // comment是我对这条分享的评论，仅在人人网和QQ空间使用
		// oks.setComment("我是测试评论文本");
		// // site是分享此内容的网站名称，仅在QQ空间使用
		// oks.setSite(getString(R.string.app_name));
		// // siteUrl是分享此内容的网站地址，仅在QQ空间使用
		// oks.setSiteUrl("http://sharesdk.cn");
		//
		// // 启动分享GUI
		// oks.show(ctx);
		//
		// break;
		case R.id.wechat:
			ShareParams weixin = new ShareParams();
			weixin.setTitle("请下载我的App");
			weixin.setText("我们这里有最精彩的应用，快快来加入我们吧！");
			weixin.setUrl(Constants.AppDownloadUrl);
			weixin.setImageUrl(Constants.AppIconUrl);
			weixin.setShareType(Platform.SHARE_WEBPAGE);
			Platform weicomment = ShareSDK.getPlatform(WechatMoments.NAME);
			weicomment.share(weixin);
			break;
		case R.id.wxchat:
			ShareParams wechat = new ShareParams();
			wechat.setTitle("请下载我的App");
			wechat.setText("我们这里有最精彩的应用，快快来加入我们吧！");
			wechat.setUrl(Constants.AppDownloadUrl);
			wechat.setImageUrl(Constants.AppIconUrl);
			wechat.setShareType(Platform.SHARE_WEBPAGE);
			Platform wei = ShareSDK.getPlatform(Wechat.NAME);
			wei.share(wechat);
			break;
		case R.id.qq:
			ShareParams sp = new ShareParams();
			sp.setTitle("请下载我的App");
			sp.setTitleUrl(Constants.AppDownloadUrl); // 标题的超链接
			sp.setText("我们这里有最精彩的应用，快快来加入我们吧！");
			sp.setImageUrl(Constants.AppIconUrl);
			Platform qq = ShareSDK.getPlatform(QQ.NAME);
			qq.share(sp);
			break;
		case R.id.sina:
			ShareParams sinasp = new ShareParams();
			sinasp.setText("请下载我的App! " + Constants.AppDownloadUrl);
			sinasp.setImageUrl(Constants.AppIconUrl);
			Platform sina = ShareSDK.getPlatform(SinaWeibo.NAME);
			sina.share(sinasp);
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
	// public void headinit(){
	// File outputImage = new File(Environment.getExternalStorageDirectory(),
	// telphone+".jpg");
	// try {
	// if (!outputImage.exists()) {
	// outputImage.createNewFile();
	// }
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// imageUri = Uri.fromFile(outputImage);
	//
	// try {
	// bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver()
	// .openInputStream(imageUri));
	// head.setImageBitmap(bitmap);
	// } catch (FileNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// }
}
