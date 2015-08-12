package com.personlife.view.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
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
import android.provider.Telephony;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personlifep.R;
import com.personlife.personinfo.carema.DialogPlus;
import com.personlife.personinfo.carema.Holder;
import com.personlife.personinfo.carema.ListHolder;
import com.personlife.personinfo.carema.OnCancelListener;
import com.personlife.personinfo.carema.OnDismissListener;
import com.personlife.personinfo.carema.OnItemClickListener;
import com.personlife.personinfo.carema.SimpleAdapter;
import com.personlife.utils.ActivityCollector;
import com.personlife.utils.PersonInfoLocal;
import com.personlife.utils.UpLoadHeadImage;
import com.personlife.utils.Utils;
import com.personlife.view.activity.personcenter.MyownActivity;

/**
 * 
 * @author liugang
 * @date 2015年8月8日
 */
public class RegisterActivity2 extends Activity implements
		android.content.DialogInterface.OnClickListener, OnClickListener {

	private Button back, nextstep;
	private ImageView re_picture;
	private EditText nickname;
	private TextView tv_title;
	private Uri imageUri;
	private Bitmap bitmap;
	public static final int TAKE_PHOTO = 1;
	public static final int CROP_PHOTO = 2;
	public static final int CHOOSE_PHOTO = 3;
	private String returnPath, telphone;
	private int flag = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register2);
		ActivityCollector.addActivity(this);
		Intent intent = getIntent();
		telphone = intent.getStringExtra("telphone");
		init();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub

	}

	public void init() {
		back = (Button) findViewById(R.id.txt_left);
		back.setVisibility(View.VISIBLE);
		back.setOnClickListener(this);
		nextstep = (Button) findViewById(R.id.register2_nextstep);
		nextstep.setOnClickListener(this);
		nickname = (EditText) findViewById(R.id.register2_nickname);
		re_picture = (ImageView) findViewById(R.id.register2_touxiang);
		re_picture.setOnClickListener(this);
		tv_title = (TextView) findViewById(R.id.txt_title);
		tv_title.setText("设置个人资料");

		File outputImage = new File(Environment.getExternalStorageDirectory(),
				telphone + ".jpg");
		try {
			if (outputImage.exists()) {
				outputImage.delete();
			}
			outputImage.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		imageUri = Uri.fromFile(outputImage);
		re_picture.setImageResource(R.drawable.register_touxiang);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txt_left:
			onBackPressed();
			break;
		case R.id.register2_nextstep:
			 if(nickname.getText().toString().length()==0){
				 Toast.makeText(this, "请输入昵称", Toast.LENGTH_SHORT).show();
			 }else if(flag==0){
				 Toast.makeText(this, "请设置头像", Toast.LENGTH_SHORT).show();
			 }else{
				 returnPath=UpLoadHeadImage.uploadImg(this,telphone);
				 PersonInfoLocal.storeRegisterNickName(RegisterActivity2.this, telphone,
						 nickname.getText().toString(), imageUri.toString(),"http://7xkbeq.com1.z0.glb.clouddn.com"+returnPath);
				 Intent intent = new Intent(RegisterActivity2.this,RegisterActivity3.class);
				 intent.putExtra("telphone", telphone);
				 startActivity(intent);
				 finish();
				 
			 }			 
			break;
		case R.id.register2_touxiang:
			showDialog();
			break;
		}
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

		SimpleAdapter adapter = new SimpleAdapter(RegisterActivity2.this);

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
							.toString(), telphone+".jpg", smallBitmap);
					re_picture.setImageBitmap(smallBitmap);
					flag=1;
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
					re_picture.setImageBitmap(smallBitmap);
					flag=1;
					savePhotoToSDCard(Environment.getExternalStorageDirectory().toString(), telphone+".jpg", smallBitmap);
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
