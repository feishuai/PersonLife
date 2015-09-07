package com.personlife.view.activity.personcenter;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.personlifep.R;
import com.loopj.android.http.RequestParams;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONObjectHttpResponseHandler;
import com.personlife.utils.PersonInfoLocal;
import com.personlife.utils.Utils;

/**  
 *   
 * @author liugang  
 * @date 2015年6月22日   
 */
public class FeedBackActivity extends Activity{

	private Button back,send;
	private TextView title;
	private EditText content;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedbacck);
		back=(Button) findViewById(R.id.txt_left);
		back.setVisibility(View.VISIBLE);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
				finish();
			}
		});
		content = (EditText) findViewById(R.id.content);
		send=(Button) findViewById(R.id.txt_save);
		send.setVisibility(View.VISIBLE);
		send.setText("发送");
		send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(content.getText().toString().equals("")){
					Utils.showShortToast(getApplicationContext(), "评价不能为空");
					return;
				}
				RequestParams params = new RequestParams();
				params.put("phone", PersonInfoLocal.getPhone(getApplicationContext()));
				params.put("message", content.getText().toString());
				BaseAsyncHttp.postReq(getApplicationContext(), "/users/judge",
						params, new JSONObjectHttpResponseHandler() {

							@Override
							public void jsonSuccess(JSONObject resp) {
								// TODO Auto-generated method stub
								if(resp.optInt("flag",0)==1){
									Utils.showShortToast(getApplicationContext(), "谢谢您的反馈！");
									FeedBackActivity.this.finish();
								}
							}

							@Override
							public void jsonFail(JSONObject resp) {
								// TODO Auto-generated method stub
							}
						});
//				onBackPressed();
//				finish();
			}
		});
		title=(TextView) findViewById(R.id.txt_title);
		title.setText("意见反馈");
	}

}
