package com.personlife.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.http.Header;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;

/**
 * 
 * @author liugang
 * @date 2015年8月9日
 */
public class DownloadHeadImg {
	/**
	 * @param url
	 *            要下载的文件URL
	 * @throws Exception
	 */
	public static void downloadFile(String url, final String filename)
			throws Exception {

		AsyncHttpClient client = new AsyncHttpClient();
		// 指定文件类型
		String[] allowedContentTypes = new String[] { "image/png", "image/jpeg" };
		// 获取二进制数据如图片和其他文件
		client.get(url, new BinaryHttpResponseHandler(allowedContentTypes) {

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				String tempPath = Environment.getExternalStorageDirectory()
						.getPath() + "/" + filename + ".jpg";
				// TODO Auto-generated method stub

				Bitmap photoBitmap = BitmapFactory.decodeByteArray(arg2, 0,
						arg2.length);

				File photoFile = new File(tempPath);
				// 若存在则删除
				if (photoFile.exists())
					photoFile.delete();
				// 创建文件
				try {
					photoFile.createNewFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				FileOutputStream fileOutputStream = null;
				try {
					fileOutputStream = new FileOutputStream(photoFile);
					if (photoBitmap != null) {
						if (photoBitmap.compress(Bitmap.CompressFormat.JPEG,
								100, fileOutputStream)) {
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

		});

	}
}
