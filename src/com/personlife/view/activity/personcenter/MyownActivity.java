package com.personlife.view.activity.personcenter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personlifep.R;
import com.loopj.android.http.RequestParams;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONObjectHttpResponseHandler;
import com.personlife.personinfo.carema.DialogPlus;
import com.personlife.personinfo.carema.Holder;
import com.personlife.personinfo.carema.ListHolder;
import com.personlife.personinfo.carema.OnCancelListener;
import com.personlife.personinfo.carema.OnDismissListener;
import com.personlife.personinfo.carema.OnItemClickListener;
import com.personlife.personinfo.carema.SimpleAdapter;
import com.personlife.utils.ActivityCollector;
import com.personlife.utils.PersonInfoLocal;
import com.personlife.utils.Utils;
import com.personlife.view.activity.LoginActivity;
import com.personlife.view.activity.personinfo.AreaSetting;
import com.personlife.view.activity.personinfo.Interests;
import com.personlife.view.activity.personinfo.NickName;
import com.personlife.view.activity.personinfo.PersonalSign;
import com.personlife.view.activity.personinfo.Profession;
import com.personlife.view.activity.personinfo.SetPassword;
import com.personlife.view.activity.personinfo.UserSex;
import com.personlife.widget.CircleImageView;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.util.Auth;

/**
 * 
 * @author liugang
 * @date 2015年6月22日
 */
public class MyownActivity extends Activity implements
		android.content.DialogInterface.OnClickListener {

	private Button tv_back, login_out;
	private TextView tv_title, nickname, sex, area, profession, interests,
			sign;
	private CircleImageView picture;
	private Uri imageUri;
	public static final int TAKE_PHOTO = 1;
	public static final int CROP_PHOTO = 2;
	public static final int CHOOSE_PHOTO = 3;
	private Bitmap bitmap;
	private boolean isChanged = false;
	 DialogPlus dialog;

	private String telphone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myownactivity);
		ActivityCollector.addActivity(this);
		Intent intent = getIntent();
		telphone = intent.getStringExtra("telphone");
		init();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		init();
		update();
	}

	public void update() {
		RequestParams request = new RequestParams();
		request.put("phone", telphone);
		request.put("nickname",
				PersonInfoLocal.getNcikName(MyownActivity.this, telphone));
		request.put("thumb",
				PersonInfoLocal.getHeadKey(MyownActivity.this, telphone));
		request.put("gender",
				PersonInfoLocal.getSex(MyownActivity.this, telphone));
		request.put("area",
				PersonInfoLocal.getLocation(MyownActivity.this, telphone));
		request.put("job", PersonInfoLocal.getJob(MyownActivity.this, telphone));
		StringBuffer sb = new StringBuffer();
		sb.append("");
		Set<String> set = new HashSet<String>();
		set = PersonInfoLocal.getHobbys(MyownActivity.this, telphone);

		if (set != null) {
			for (String str : set) {
				sb.append(str + " ");
			}

		}
		request.put("hobby", sb.toString().trim());
		request.put("signature",
				PersonInfoLocal.getSignature(MyownActivity.this, telphone));
		BaseAsyncHttp.postReq(getApplicationContext(), "/users/modify",
				request, new JSONObjectHttpResponseHandler() {

					@Override
					public void jsonSuccess(JSONObject resp) {
						try {
							if (resp.get("flag").equals(0)) {
								// Toast.makeText(MyownActivity.this,
								// "修改信息失败", Toast.LENGTH_SHORT)
								// .show();
							} else {
								// Toast.makeText(MyownActivity.this,
								// "修改信息成功", Toast.LENGTH_SHORT)
								// .show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void jsonFail(JSONObject resp) {
						// TODO Auto-generated method stub
						// Toast.makeText(MyownActivity.this,
						// "Fail修改信息失败", Toast.LENGTH_SHORT)
						// .show();
					}
				});

	}

	public void init() {

		picture = (CircleImageView) findViewById(R.id.touxiangpicture);
		nickname = (TextView) findViewById(R.id.pernicheng);
		sex = (TextView) findViewById(R.id.persex);
		area = (TextView) findViewById(R.id.perarea);
		profession = (TextView) findViewById(R.id.perzhiye);
		interests = (TextView) findViewById(R.id.personinteresting);
		sign = (TextView) findViewById(R.id.personsign);
		tv_back = (Button) findViewById(R.id.txt_left);
		login_out = (Button) findViewById(R.id.login_out);
		tv_back.setVisibility(View.VISIBLE);
		tv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setResult(1);
				onBackPressed();
			}
		});
		login_out.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (PersonInfoLocal.getPersonPassword(MyownActivity.this,
						telphone).equals("")) {
					Intent intent = new Intent(MyownActivity.this,
							SetPassword.class);
					intent.putExtra("telphone", telphone);
					startActivity(intent);

				} else {
					SharedPreferences.Editor editor = PreferenceManager
							.getDefaultSharedPreferences(MyownActivity.this)
							.edit();
					editor.putString("islogin", "0");
					editor.commit();
					Utils.start_Activity(MyownActivity.this,
							LoginActivity.class);
					ActivityCollector.finishAll();
				}

			}
		});
		tv_title = (TextView) findViewById(R.id.txt_title);
		tv_title.setText("个人信息");

		nickname.setText(PersonInfoLocal.getNcikName(this, telphone));
		sex.setText(PersonInfoLocal.getSex(this, telphone));
		area.setText(PersonInfoLocal.getLocation(this, telphone));
		profession.setText(PersonInfoLocal.getJob(this, telphone));
		StringBuffer sb = new StringBuffer();
		sb.append("");
		Set<String> set = new HashSet<String>();
		set = PersonInfoLocal.getHobbys(MyownActivity.this, telphone);
		// Iterator it = set.iterator();
		if (set != null) {
			for (String str : set) {
				sb.append(str + " ");
			}
			// while (it.hasNext()) {
			// String str = (String) it.next();
			// sb.append(str+" ");
			// }
		}
		interests.setText(sb.toString());
		sign.setText(PersonInfoLocal.getSignature(MyownActivity.this, telphone));
		File outputImage = new File(Environment.getExternalStorageDirectory(),
				telphone + ".jpg");

		imageUri = Uri.fromFile(outputImage);

		try {
			bitmap = BitmapFactory.decodeStream(getContentResolver()
					.openInputStream(imageUri));
			picture.setImageBitmap(bitmap);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void onclickPersonInfo(View view) {
		switch (view.getId()) {
		case R.id.person_touxiang:
			showDialog();
			break;
		case R.id.person_nicheng:
			Intent intent1 = new Intent(this, NickName.class);
			intent1.putExtra("telphone", telphone);
			startActivity(intent1);
			break;
		case R.id.person_sex:
			Intent intent2 = new Intent(this, UserSex.class);
			intent2.putExtra("telphone", telphone);
			startActivity(intent2);
			break;
		case R.id.person_area:
			Intent intent3 = new Intent(this, AreaSetting.class);
			intent3.putExtra("telphone", telphone);
			startActivity(intent3);
			break;
		case R.id.person_zhiye:
			Intent intent4 = new Intent(this, Profession.class);
			intent4.putExtra("telphone", telphone);
			startActivity(intent4);

			break;
		case R.id.person_interesting:
			Intent intent5 = new Intent(this, Interests.class);
			intent5.putExtra("telphone", telphone);
			startActivity(intent5);
			break;
		case R.id.person_sign:
			Intent intent6 = new Intent(this, PersonalSign.class);
			intent6.putExtra("telphone", telphone);
			startActivity(intent6);
			break;
		}
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub

	}

	private void showDialog() {
		Holder holder;
		holder = new ListHolder();

		OnItemClickListener itemClickListener = new OnItemClickListener() {
			@Override
			public void onItemClick(DialogPlus dialog, Object item, View view,
					int position) {

				switch (position) {
				case 0:
					Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					intent1.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
					dialog.dismiss();
					startActivityForResult(intent1, TAKE_PHOTO);
					break;
				case 1:
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
					intent.addCategory(Intent.CATEGORY_OPENABLE);
					intent.setType("image/*");
					dialog.dismiss();
					startActivityForResult(intent, CHOOSE_PHOTO);
					break;
				case 2:
					dialog.dismiss();
					break;
				}

			}
		};

		OnDismissListener dismissListener = new OnDismissListener() {
			@Override
			public void onDismiss(DialogPlus dialog) {
				// Toast.makeText(MainActivity.this,
				// "dismiss listener invoked!", Toast.LENGTH_SHORT).show();
			}
		};

		OnCancelListener cancelListener = new OnCancelListener() {
			@Override
			public void onCancel(DialogPlus dialog) {
				// Toast.makeText(MainActivity.this, "cancel listener invoked!",
				// Toast.LENGTH_SHORT).show();
			}
		};

		SimpleAdapter adapter = new SimpleAdapter(MyownActivity.this);

		showOnlyContentDialog(holder, adapter, itemClickListener,
				dismissListener, cancelListener);
	}

	private void showOnlyContentDialog(Holder holder, BaseAdapter adapter,
			OnItemClickListener itemClickListener,
			OnDismissListener dismissListener, OnCancelListener cancelListener) {
		 dialog = DialogPlus.newDialog(MyownActivity.this)
				.setContentHolder(holder).setGravity(Gravity.BOTTOM)
				.setAdapter(adapter).setOnItemClickListener(itemClickListener)
				.setOnDismissListener(dismissListener)
				.setOnCancelListener(cancelListener).setExpanded(false)
				.setCancelable(true).create();
		dialog.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case TAKE_PHOTO:
			if (resultCode == RESULT_OK) {
				Intent intent = new Intent("com.android.camera.action.CROP");
				intent.setDataAndType(imageUri, "image/*");
				intent.putExtra("scale", true);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(intent, CROP_PHOTO);
			}
			break;
		case CROP_PHOTO:
			if (resultCode == RESULT_OK) {
				try {
					Bitmap bitmap = BitmapFactory
							.decodeStream(getContentResolver().openInputStream(
									imageUri));
					Bitmap smallBitmap = zoomBitmap(bitmap, 60, 60);
					bitmap.recycle();
					savePhotoToSDCard(Environment.getExternalStorageDirectory()
							.toString(), telphone + ".jpg", smallBitmap);
					picture.setImageBitmap(smallBitmap);
					uploadImg(this, telphone);

					// PersonInfoLocal.storeHeadkey(this, telphone,
					// "http://7xkbeq.com1.z0.glb.clouddn.com/"+returnPath);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			break;
		case CHOOSE_PHOTO:
			if (resultCode == RESULT_OK) {
				Uri originalUri = data.getData();
				try {
					Bitmap photo = BitmapFactory
							.decodeStream(getContentResolver().openInputStream(
									originalUri));
					Bitmap smallBitmap = zoomBitmap(photo, 120, 120);
					photo.recycle();
					picture.setImageBitmap(smallBitmap);
					savePhotoToSDCard(Environment.getExternalStorageDirectory()
							.toString(), telphone + ".jpg", smallBitmap);
					uploadImg(this, telphone);

					// PersonInfoLocal.storeHeadkey(this, telphone,
					// "http://7xkbeq.com1.z0.glb.clouddn.com/"+returnPath);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			break;
		default:
			break;
		}
	}
	
	public Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) width / w);
		float scaleHeight = ((float) height / h);
		matrix.postScale(scaleWidth, scaleHeight);// 利用矩阵进行缩放不会造成内存溢出
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
		return newbmp;
	}

	public void savePhotoToSDCard(String path, String photoName,
			Bitmap photoBitmap) {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File photoFile = new File(path, photoName); // 在指定路径下创建文件
			FileOutputStream fileOutputStream = null;
			try {
				fileOutputStream = new FileOutputStream(photoFile);
				if (photoBitmap != null) {
					if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100,
							fileOutputStream)) {
						fileOutputStream.flush();
					}
				}

			} catch (FileNotFoundException e) {
				photoFile.delete();
				e.printStackTrace();
			} catch (IOException e) {
				photoFile.delete();
				e.printStackTrace();
			} finally {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void uploadImg(final Context ctx, final String telphone) {
		if (dialog.isShowing())
			dialog.dismiss();
		final ProgressDialog pd = new ProgressDialog(MyownActivity.this);
		pd.setCanceledOnTouchOutside(false);
		pd.setMessage("正在上传");
		pd.show();
		
		BaseAsyncHttp.postReq(ctx, "/users/token", new RequestParams(),
				new JSONObjectHttpResponseHandler() {

					@Override
					public void jsonSuccess(JSONObject resp) {
						String token = resp.optString("token");
						UploadManager uploadManager = new UploadManager();
						String url = Environment.getExternalStorageDirectory() + "/" +
								telphone + ".jpg";
						uploadManager.put(url, null, token, new UpCompletionHandler() {
							
							@Override
							public void complete(String arg0, ResponseInfo arg1, JSONObject arg2) {
								// TODO Auto-generated method stub
								try {
									String result = arg2.getString("key").toString();
									Log.i("keyd zhiaagandghl", result);
									PersonInfoLocal.storeHeadkey(ctx, telphone,
											"http://7xkbeq.com1.z0.glb.clouddn.com/" + result);
									update();
									pd.dismiss();
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}, null);
					}

					@Override
					public void jsonFail(JSONObject resp) {
						pd.dismiss();
						Utils.showShortToast(getApplicationContext(), "网络故障");
					}
				});
	}
}
