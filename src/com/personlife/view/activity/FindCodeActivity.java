package com.personlife.view.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.personlifep.R;
import com.loopj.android.http.RequestParams;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONObjectHttpResponseHandler;
import com.personlife.utils.ActivityCollector;
import com.personlife.utils.PersonInfoLocal;
import com.personlife.view.activity.personinfo.SetPassword;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**  
 *   
 * @author liugang  
 * @date 2015年8月27日   
 */
public class FindCodeActivity extends Activity implements OnClickListener{

	private String telphone;
	private Button back,setpwd;
	private TextView tv_title,tv_tishi;
	private EditText first,second;
	private SharedPreferences.Editor editor;
	private SharedPreferences pref;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setpwd);
		ActivityCollector.addActivity(this);
		Intent intent=getIntent();
		telphone=intent.getStringExtra("telphone");
		editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		initview();
	}
	public void initview(){
		tv_tishi=(TextView) findViewById(R.id.tishi);
		tv_tishi.setVisibility(View.GONE);
		back=(Button) findViewById(R.id.txt_left);
		back.setVisibility(View.VISIBLE);
		back.setOnClickListener(this);
		tv_title=(TextView) findViewById(R.id.txt_title);
		tv_title.setText("设置密码");
		setpwd = (Button) findViewById(R.id.setpwd_login_out);
		setpwd.setOnClickListener(this);
		setpwd.setText("确定");
		first=(EditText) findViewById(R.id.firstpwd);
		second=(EditText) findViewById(R.id.secondpwd);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.txt_left:
			onBackPressed();
			finish();
			break;
		case R.id.setpwd_login_out:
			String f=first.getText().toString();
			String s=second.getText().toString();
			if(!f.equals(s)){
				Toast.makeText(this, "输入不一致，请重新设置", Toast.LENGTH_SHORT).show();
				first.setText("");
				second.setText("");
			}else{
				RequestParams params = new RequestParams();
				params.put("phone", telphone);
				params.put("pwd", f);
				BaseAsyncHttp.postReq(getApplicationContext(),"/users/signup", params,
						new JSONObjectHttpResponseHandler() {

							@Override
							public void jsonSuccess(JSONObject resp) {
								// TODO Auto-generated method stub
								try {
									if (resp.get("flag").equals(1)) {
										editor.putString("islogin", "1");
										editor.putString("telphone", telphone);
										editor.commit();
										PersonInfoLocal.storeLoginTelAndPass(FindCodeActivity.this, telphone,
												first.getText().toString());
										Intent intent=new Intent(FindCodeActivity.this,MainActivity.class);
										intent.putExtra("telphone", telphone);
										startActivity(intent);
										finish();
										ActivityCollector.finishAll();
									} else {
										Toast.makeText(FindCodeActivity.this,
												"哎呀，失败了，再试一次吧", Toast.LENGTH_LONG)
												.show();
									}

								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

							@Override
							public void jsonFail(JSONObject resp) {
								// TODO Auto-generated method stub
								if(resp!=null)
									Log.i("login", resp.toString());
								Toast.makeText(FindCodeActivity.this,
										"设置失败,看看网络有没有问题", Toast.LENGTH_LONG)
										.show();
							}
						});
			}
		}
			
	}

}
