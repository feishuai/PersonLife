package com.personlife.view.activity.personcenter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import com.example.personlifep.R;
import com.personlife.adapter.AppsAdapter;
import com.personlife.bean.App;

import com.personlife.view.activity.personcenter.TabAppList.TabAppListAdapter;

/**
 * 
 * @author liugang
 * @date 2015年8月7日
 */
public class TabTaskList extends Fragment implements OnClickListener {

	private Activity ctx;
	private View layout;
	private ListView listView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (layout == null) {
			ctx = this.getActivity();
			layout = ctx.getLayoutInflater().inflate(R.layout.fragment_task,null);
			initViews();
			initData();
			setOnListener();
		} else {
			ViewGroup parent = (ViewGroup) layout.getParent();
			if (parent != null) {
				parent.removeView(layout);
			}
		}
		return layout;
	}

	public void initViews() {
		listView = (ListView) layout.findViewById(R.id.listview_tasklist);
	}

	public void initData() {
		List<App>mList = new ArrayList<App>();
		mList.add(new App());
		mList.add(new App());
		mList.add(new App());
		listView.setAdapter( new TabTaskAdapter(getActivity(), mList));
	}

	public void setOnListener() {

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	public class TabTaskAdapter extends BaseAdapter {

		private Context context;
		private List<App> mlist;

		public TabTaskAdapter(Context context, List<App> mlist) {
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
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			final ViewHolder holder; 
			Log.i("adapter", "mlist size is " + mlist.size());
			if (convertView == null) {
				convertView = ((LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
						.inflate(R.layout.layout_item_download, null);
				holder = new ViewHolder();
				holder.appname = (TextView) convertView
						.findViewById(R.id.tv_download_name);
				holder.status = (TextView) convertView
						.findViewById(R.id.tv_download_status);
				holder.icon = (ImageView) convertView
						.findViewById(R.id.iv_download_icon);
				holder.download_progress = (ProgressBar) convertView.findViewById(R.id.download_progress);
				holder.download_button = (Button) convertView
						.findViewById(R.id.btn_download_download);
				holder.flag=false;
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
//			Handler mHandler = new Handler() {  
//	              public void handleMessage(Message msg) {  
//	                  if (msg.what == 1) {  
//	                      String url = (String) msg.obj;  
//	                      int length = msg.arg1;  
//	                       
//	                      if (holder.download_progress != null) {  
//	                          // 设置进度条按读取的length长度更新  
//	                    	  holder.download_progress.incrementProgressBy(length);  
//	                          if (holder.download_progress.getProgress() == holder.download_progress.getMax()) {   
//	                              // 下载完成后清除进度条并将map中的数据清空  
//	                        	  holder.download_progress.setVisibility(View.GONE);
//	                               
//	                              AppsAdapter.downloaders.get(url).delete(url);  
//	                              AppsAdapter.downloaders.get(url).reset();  
//	                              AppsAdapter.downloaders.remove(url);  
//	                          }  
//	                      }  
//	                  }  
//	              }  
//	          };
//			String musicName="fbb77294-6041-41ac-befa-37e237bd41f2.jpg";
//			String urlstr = AppsAdapter.URL + musicName;  
//            String localfile = AppsAdapter.SD_PATH + musicName; 
//			
//            //设置下载线程数为4，这里是我为了方便随便固定的  
//            int threadcount = 4;  
//            // 初始化一个downloader下载器  
//            Downloader downloader = AppsAdapter.downloaders.get(urlstr);  
//            if (downloader == null) {  
//                downloader = new Downloader(urlstr, localfile, threadcount, ctx, mHandler);  
//                AppsAdapter.downloaders.put(urlstr, downloader);  
//            }  
//     
//           // 得到下载信息类的个数组成集合  
//           LoadInfo loadInfo = downloader.getDownloaderInfors();  
//           // 显示进度条  
//           
//           if (holder.download_progress == null) {  
//    
//        	   holder.download_progress.setMax(loadInfo.getFileSize());  
//        	   holder.download_progress.setProgress(loadInfo.getComplete());  
//
//           } 
//           // 调用方法开始下载  
//           downloader.download();  
			
			holder.download_button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					holder.flag=!holder.flag;
					if(holder.flag==true){
						holder.download_button.setText("暂停");
						
					}else{
						holder.download_button.setText("下载");
					}
					//下载app
					
				}
			});
			// ImageLoader.getInstance().displayImage(mlist.get(position).getBitmap(),holder.icon);
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					
				}
			});
			
			return convertView;
		}

		public void setData(List<App> list) {
			mlist = list;
		}

		private class ViewHolder {
			ImageView icon;
			TextView appname;
			TextView status;
			ProgressBar download_progress;
			Button download_button;
			boolean flag;
		}
	}
}
