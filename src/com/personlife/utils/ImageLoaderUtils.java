/**
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.personlife.utils;

import java.io.File;
import java.io.IOException;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.example.personlifep.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;


public class ImageLoaderUtils {

	// 图片加载类
		public static DisplayImageOptions binner_options;
		public static DisplayImageOptions icon_options;
		private static DisplayImageOptions user_icon_options;
		private static DisplayImageOptions girl_options;
		// Imageload缓存目录
		private static File cacheDir = new File(getHJYCacheDir() + "/Imageload");

		static {
			
			binner_options = new DisplayImageOptions.Builder()
					.showImageOnLoading(R.drawable.large_logo_default_4) // resource
					// or
					.showImageForEmptyUri(R.drawable.large_logo_default_4) // resource
					.showImageOnFail(R.drawable.large_logo_default_4) // resource or
					.cacheInMemory(false) // default
					.cacheOnDisc(true) // default
					.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
					.bitmapConfig(Bitmap.Config.ARGB_8888) // default
					.displayer(new SimpleBitmapDisplayer()) // default
					.build();
			icon_options = new DisplayImageOptions.Builder()
					.showImageOnLoading(R.drawable.ic_stub) // resource
					.showImageForEmptyUri(R.drawable.ic_empty) // resource
					.showImageOnFail(R.drawable.ic_error) // resource or
					.cacheInMemory(true) // default
					.cacheOnDisc(true) // default
					.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
					.bitmapConfig(Bitmap.Config.ARGB_8888) // default
					.displayer(new RoundedBitmapDisplayer(50)) // default
					.build();
			user_icon_options = new DisplayImageOptions.Builder()
					.showImageOnLoading(R.drawable.head) // resource
					.showImageForEmptyUri(R.drawable.head) // resource
					.showImageOnFail(R.drawable.head) // resource
					.cacheInMemory(true) // default
					.cacheOnDisc(true) // default
					.imageScaleType(ImageScaleType.NONE) // default
					.bitmapConfig(Bitmap.Config.ARGB_8888) // default
					.displayer(new SimpleBitmapDisplayer()) // default
					.build();
		}
		public static void InitConfig(Context context){
			ActivityManager am = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			int memClass = am.getMemoryClass();
			int cacheSize = 1024 * 1024 * memClass / 4; // 硬引用缓存容量，为系统可用内存的1/4
			ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
					context).threadPoolSize(4)
					.threadPriority(Thread.NORM_PRIORITY + 1)
					.tasksProcessingOrder(QueueProcessingType.FIFO)
					.denyCacheImageMultipleSizesInMemory()
					.memoryCache(new LruMemoryCache(cacheSize))
					.memoryCacheSize(cacheSize)
					.discCache(new UnlimitedDiskCache(cacheDir))
					.discCacheSize(30 * 1024 * 1024).discCacheFileCount(500)
					.imageDownloader(new BaseImageDownloader(context, 5*1000, 20*1000))
					.writeDebugLogs().build();
			Log.i("cacheDir is ",cacheDir.toString());
			ImageLoader.getInstance().init(config);

		}
		public static void displayAppIcon(String uri, ImageView imageView){
			ImageLoader.getInstance().displayImage(uri, imageView, icon_options);
		}
		
		public static void displayImageView(String uri, ImageView imageView){
			ImageLoader.getInstance().displayImage(uri, imageView, binner_options);
		}
		public static String getHJYCacheDir() {
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED))
				return Environment.getExternalStorageDirectory().toString()
						+ "/personlife/Cache";
			else
				return "/System/personlife/Cache";
		}

		public static String getHJYDownLoadDir() {
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED))
				return Environment.getExternalStorageDirectory().toString()
						+ "/Walk/Download";
			else {
				return "/System/com.Juns.Walk/Walk/Download";
			}
		}

		public static void deleteCacheDirFile(String filePath,
				boolean deleteThisPath) throws IOException {
			if (!TextUtils.isEmpty(filePath)) {
				File file = new File(filePath);
				if (file.isDirectory()) {// 处理目录
					File files[] = file.listFiles();
					for (int i = 0; i < files.length; i++) {
						deleteCacheDirFile(files[i].getAbsolutePath(), true);
					}
				}
				if (deleteThisPath) {
					if (!file.isDirectory()) {// 如果是文件，删除
						file.delete();
					} else {// 目录
						if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
							file.delete();
						}
					}
				}
			}
		}

}
