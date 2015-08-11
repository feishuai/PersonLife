package com.personlife.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personlifep.R;
import com.personlife.bean.App;
import com.personlife.download.Downloader;
import com.personlife.download.LoadInfo;
import com.personlife.utils.Constants;
import com.personlife.view.activity.home.AppDetailActivity;

public class AppsAdapter extends BaseAdapter {

	private Context context;
	private List<App> mlist;

	// 固定下载的资源路径，这里可以设置网络上的地址
	public static final String URL = "http://www.gzevergrandefc.com/UploadFile/photos/2013-06/";
	// 固定存放下载的音乐的路径：SD卡目录下
	public static final String SD_PATH = Environment.getExternalStorageDirectory().toString();
	// 存放各个下载器
	public static Map<String, Downloader> downloaders = new HashMap<String, Downloader>();

	public AppsAdapter(Context context, List<App> mlist) {
		this.context = context;
		this.mlist = mlist;
	}

	@Override
	public int getCount() {
		return mlist.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arg0;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = ((LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
					.inflate(R.layout.layout_item_tuijian, null);
			holder = new ViewHolder();
			holder.appname = (TextView) convertView
					.findViewById(R.id.tv_tuijian_name);
			holder.status = (TextView) convertView
					.findViewById(R.id.tv_tuijian_status);
			holder.intro = (TextView) convertView
					.findViewById(R.id.tv_tuijian_intro);
			holder.icon = (ImageView) convertView
					.findViewById(R.id.iv_tuijian_icon);
			holder.download = (Button) convertView
					.findViewById(R.id.btn_tuijian_tuijian);
			holder.flag = false;
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// ImageLoaderUtils.displayAppIcon("https://ss0.bdstatic.com/-0U0bnSm1A5BphGlnYG/tam-ogel/5136becf77e9cfc440849e0b694fdd6e_121_121.jpg",
		// holder.icon);

		holder.download.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				holder.flag = !holder.flag;
				Toast.makeText(context,
						"downloading " + mlist.get(position).getDownloadUrl(),
						Toast.LENGTH_SHORT).show();
				if (holder.flag == true) {
					holder.download.setText("暂停");
					String musicName = "fbb77294-6041-41ac-befa-37e237bd41f2.jpg";
					String urlstr = URL + musicName;
					String localfile = SD_PATH + "/"+musicName;
					// 设置下载线程数为4，这里是我为了方便随便固定的
					int threadcount = 4;
					// 初始化一个downloader下载器
					Downloader downloader = downloaders.get(urlstr);
					if (downloader == null) {
						downloader = new Downloader(urlstr, localfile,threadcount, context, null);
						downloaders.put(urlstr, downloader);
					}
					if (downloader.isdownloading())
						return;
					// 得到下载信息类的个数组成集合  
		             LoadInfo loadInfo = downloader.getDownloaderInfors(); 
					// 调用方法开始下载
					downloader.download();

				} else {
					holder.download.setText("下载");
					String musicName = "fbb77294-6041-41ac-befa-37e237bd41f2.jpg";
					String urlstr = URL + musicName;
					downloaders.get(urlstr).pause();
				}

			}
		});
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, AppDetailActivity.class);
				intent.putExtra(Constants.AppId, mlist.get(position).getId());
				// intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);

			}
		});
		return convertView;
	}

	public void setData(List<App> list) {
		mlist = list;
	}

	class ViewHolder {
		ImageView icon;
		TextView appname;
		TextView status;
		TextView intro;
		Button download;
		boolean flag;
	}
}