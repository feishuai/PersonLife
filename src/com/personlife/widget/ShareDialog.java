package com.personlife.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

import com.example.personlifep.R;
import com.personlife.bean.App;
import com.personlife.utils.Constants;
import com.personlife.utils.DrawableStringUtils;

/**
 * Created by liug on 15/12/3.
 */
public class ShareDialog extends Dialog implements android.view.View.OnClickListener{
    private LinearLayout wechatcomment,wechat,sina;
    private Context context;
    private App app;
    View localView;
    public ShareDialog(Context context,App app) {
        super(context);
        this.context=context;
        this.app = app;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 这句代码换掉dialog默认背景，否则dialog的边缘发虚透明而且很宽
        // 总之达不到想要的效果
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater inflater = LayoutInflater.from(context);
        //((AnimationActivity) context).getLayoutInflater();
        localView = inflater.inflate(R.layout.sharedialog, null);
        localView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_bottom_to_top));
        setContentView(localView);
        // 这句话起全屏的作用
        getWindow().setLayout(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
        initViews();
        setOnclick();
    }
    private void initViews(){
        wechatcomment= (LinearLayout) findViewById(R.id.wechatcomment);
        wechat= (LinearLayout) findViewById(R.id.wechat);
        sina= (LinearLayout) findViewById(R.id.sina);
    }
    private void setOnclick(){
        wechatcomment.setOnClickListener(this);
        wechat.setOnClickListener(this);
        sina.setOnClickListener(this);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.dismiss();
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
    	String sharedUrl = Constants.PrefixShareUrl
				+ String.valueOf(app.getId());
    	switch (v.getId()) {
		case R.id.wechatcomment:
			ShareParams wxcomment = new ShareParams();
			wxcomment.setTitle("请下载我的App");
			wxcomment.setText("我们这里有最精彩的应用，快快来加入我们吧！");
			wxcomment.setUrl(sharedUrl);
			wxcomment.setImageUrl(app.getIcon());
			wxcomment.setShareType(Platform.SHARE_WEBPAGE);
			Platform wc = ShareSDK.getPlatform(WechatMoments.NAME);
			wc.share(wxcomment);
			this.dismiss();
			break;
		case R.id.wechat:
			ShareParams wechat = new ShareParams();
			wechat.setTitle("请下载我的App");
			wechat.setText("我们这里有最精彩的应用，快快来加入我们吧！");
			wechat.setUrl(sharedUrl);
			wechat.setImageUrl(app.getIcon());
			wechat.setShareType(Platform.SHARE_WEBPAGE);
			Platform we = ShareSDK.getPlatform(Wechat.NAME);
			we.share(wechat);
			this.dismiss();
			break;
		case R.id.sina:
			ShareParams sinasp = new ShareParams();
			sinasp.setText("请下载我的App。  " + sharedUrl);
			sinasp.setImageUrl(app.getIcon());
			// sinasp.setImageData(DrawableStringUtils.stringtoBitmap(selectedApp.getDrawableString()));
			Platform sn = ShareSDK.getPlatform(SinaWeibo.NAME);
			sn.share(sinasp);
			this.dismiss();
			break;
		}
    }
}
