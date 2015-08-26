package com.personlife.view.activity.circle;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.favorite.WechatFavorite;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

import com.example.personlifep.R;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ShareDialog extends Dialog implements
		android.view.View.OnClickListener {

	Context context;
	private LinearLayout layout_quanzi, layout_wx, layout_sina, layout_qq;
	private Platform platForm;

	public ShareDialog(Context context) {
		super(context, R.style.FullHeightDialog);
		// TODO Auto-generated constructor stub
		setContentView(R.layout.dialog_layout_share);
		this.context = context;
//		layout_quanzi = (LinearLayout) findViewById(R.id.quanzi);
//		layout_quanzi.setOnClickListener(this);
		layout_wx = (LinearLayout) findViewById(R.id.wxchat);
		layout_wx.setOnClickListener(this);
		layout_sina = (LinearLayout) findViewById(R.id.sina);
		layout_sina.setOnClickListener(this);
		layout_qq = (LinearLayout) findViewById(R.id.qq);
		layout_qq.setOnClickListener(this);
		ShareSDK.initSDK(context);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.wxchat:
			
			ShareParams weixin=new ShareParams();
			weixin.setText("heh");
			Platform wei=ShareSDK.getPlatform(Wechat.NAME);
			wei.share(weixin);
			dismiss();
			break;
		case R.id.qq:
			
			ShareParams sp = new ShareParams();
			sp.setTitle("测试分享的标题");
			sp.setTitleUrl("http://sharesdk.cn"); // 标题的超链接
			sp.setText("测试分享的文本");
			sp.setImageUrl("http://www.someserver.com/测试图片网络地址.jpg");
			sp.setSite("发布分享的网站名称");
			sp.setSiteUrl("发布分享网站的地址");

			Platform qzone = ShareSDK.getPlatform(QZone.NAME);

			qzone.share(sp);
			dismiss();
			break;
		case R.id.sina:
			
			ShareParams sinasp = new ShareParams();

			sinasp.setText("测试分享的文本");

			Platform sina = ShareSDK.getPlatform(SinaWeibo.NAME);
			sina.share(sinasp);
			dismiss();

			break;
//		case R.id.quanzi:
//			Toast.makeText(context, "圈子", Toast.LENGTH_SHORT).show();
		default:
			break;
		}
	}
}
