package com.personlife.view.activity.personcenter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.json.JSONObject;

import com.example.personlifep.R;
import com.loopj.android.http.RequestParams;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONObjectHttpResponseHandler;
import com.personlife.personinfo.carema.DialogPlus;
import com.personlife.personinfo.carema.GridHolder;
import com.personlife.personinfo.carema.Holder;
import com.personlife.personinfo.carema.ListHolder;
import com.personlife.personinfo.carema.OnBackPressListener;
import com.personlife.personinfo.carema.OnCancelListener;
import com.personlife.personinfo.carema.OnDismissListener;
import com.personlife.personinfo.carema.OnItemClickListener;
import com.personlife.personinfo.carema.SimpleAdapter;
import com.personlife.personinfo.carema.ViewHolder;
import com.personlife.utils.Utils;
import com.personlife.view.activity.personinfo.AreaSetting;
import com.personlife.view.activity.personinfo.Interests;
import com.personlife.view.activity.personinfo.NickName;
import com.personlife.view.activity.personinfo.PersonalSign;
import com.personlife.view.activity.personinfo.Profession;
import com.personlife.view.activity.personinfo.UserSex;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author liugang
 * @date 2015年6月22日
 */
public class MyownActivity extends Activity implements
		android.content.DialogInterface.OnClickListener {

	private Button tv_back;
	private TextView tv_title, nickname, sex, area, profession, interests,
			sign;
	private ImageView picture;
	private Uri imageUri;
	private SharedPreferences pref;
	public static final int TAKE_PHOTO = 1;
	public static final int CROP_PHOTO = 2;
	public static final int CHOOSE_PHOTO = 3;
	private Bitmap bitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myownactivity);
		init();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		init();
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		RequestParams request = new RequestParams();
		request.put("phone", pref.getString("telephone", null));
		request.put("nickname", pref.getString("userName", null));
		request.put("thumb", pref.getString("headUrl", null));
		request.put("gender", pref.getString("sex", null));
		request.put("area", pref.getString("location", null));
		request.put("job", pref.getString("job", null));
		request.put("hobby", pref.getString("hobby", null));
		request.put("signature", pref.getString("signature", null));
		BaseAsyncHttp.postReq("/users/modify", request,
				new JSONObjectHttpResponseHandler() {

					@Override
					public void jsonSuccess(JSONObject resp) {

					}

					@Override
					public void jsonFail(JSONObject resp) {
						// TODO Auto-generated method stub
					}
				});
	}

	public void init() {

		picture = (ImageView) findViewById(R.id.touxiangpicture);
		nickname = (TextView) findViewById(R.id.pernicheng);
		sex = (TextView) findViewById(R.id.persex);
		area = (TextView) findViewById(R.id.perarea);
		profession = (TextView) findViewById(R.id.perzhiye);
		interests = (TextView) findViewById(R.id.personinteresting);
		sign = (TextView) findViewById(R.id.personsign);
		tv_back = (Button) findViewById(R.id.txt_left);
		tv_back.setVisibility(View.VISIBLE);
		tv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		tv_title = (TextView) findViewById(R.id.txt_title);
		tv_title.setText("个人信息");
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);

		nickname.setText(pref.getString("userName", "用户名"));
		sex.setText(pref.getString("sex", "男"));
		area.setText(pref.getString("location", "地区"));
		profession.setText(pref.getString("job", "职业"));
		interests.setText(pref.getString("hobby", "兴趣"));
		sign.setText(pref.getString("signature", "个性签名"));
		File outputImage = new File(Environment.getExternalStorageDirectory(),
				"tempImage.jpg");
		try {
			if (!outputImage.exists()) {
				outputImage.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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
			Utils.start_Activity(this, NickName.class);
			break;
		case R.id.person_sex:
			Utils.start_Activity(this, UserSex.class);
			break;
		case R.id.person_area:
			Utils.start_Activity(this, AreaSetting.class);
			break;
		case R.id.person_zhiye:
			Utils.start_Activity(this, Profession.class);
			break;
		case R.id.person_interesting:
			Utils.start_Activity(this, Interests.class);
			break;
		case R.id.person_sign:
			Utils.start_Activity(this, PersonalSign.class);
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

		OnClickListener clickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		};

		OnItemClickListener itemClickListener = new OnItemClickListener() {
			@Override
			public void onItemClick(DialogPlus dialog, Object item, View view,
					int position) {

				switch (position) {
				case 0:
					Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					intent1.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
					startActivityForResult(intent1, TAKE_PHOTO);
					dialog.dismiss();
					break;
				case 1:
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
					intent.addCategory(Intent.CATEGORY_OPENABLE);
					intent.setType("image/*");
					startActivityForResult(intent, CHOOSE_PHOTO);
					dialog.dismiss();
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
		final DialogPlus dialog = DialogPlus.newDialog(this)
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
							.toString(), "tempImage.jpg", smallBitmap);
					picture.setImageBitmap(smallBitmap);
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
							.toString(), "tempImage.jpg", smallBitmap);
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
}
