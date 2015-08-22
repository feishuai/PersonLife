package com.personlife.view.activity.circle;

import com.example.personlifep.R;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ShareDialog extends Dialog implements
		android.view.View.OnClickListener {

	Context context;
	private LinearLayout layout_quanzi,layout_wx, layout_sina, layout_qq;

	public ShareDialog(Context context) {
		super(context, R.style.FullHeightDialog);
		// TODO Auto-generated constructor stub
		setContentView(R.layout.dialog_layout_share);
		this.context = context;
		layout_quanzi=(LinearLayout) findViewById(R.id.quanzi);
		layout_quanzi.setOnClickListener(this);
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
			Toast.makeText(context, "wxchat", Toast.LENGTH_SHORT).show();
			break;
		case R.id.qq:
			Toast.makeText(context, "qq", Toast.LENGTH_SHORT).show();
			
			break;
		case R.id.sina:
			Toast.makeText(context, "sina", Toast.LENGTH_SHORT).show();
			Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
			
			Platform.ShareParams sParams = new SinaWeibo.ShareParams();
			sParams.text = "text";
			weibo.share(sParams);
			this.dismiss();
			break;
		case R.id.quanzi:
			Toast.makeText(context, "圈子", Toast.LENGTH_SHORT).show();
		default:
			break;
		}
	}

}
