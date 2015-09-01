package com.personlife.adapter;

import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.personlifep.R;
import com.loopj.android.http.RequestParams;
import com.personlife.bean.Star;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONObjectHttpResponseHandler;
import com.personlife.utils.ImageLoaderUtils;
import com.personlife.utils.PersonInfoLocal;
import com.personlife.utils.Utils;

/**
 * 
 * @author liugang
 * @date 2015年8月12日
 */
public class Register4_Adapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<Star> liststar;
	private Context context;
	private String telphone;

	public Register4_Adapter(Context context, List<Star> liststar,
			String telphone) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.liststar = liststar;
		this.telphone = telphone;
	}

	@Override
	public int getCount() {
		if (null != liststar) {
			return liststar.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		return liststar.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.register4_item, null);
			viewHolder = new ViewHolder();
			viewHolder.star_name = (TextView) convertView
					.findViewById(R.id.register4_grid_starname);
			viewHolder.picture = (ImageView) convertView
					.findViewById(R.id.register4_grid_staricon);
			viewHolder.starBackgroud = (ImageView) convertView
					.findViewById(R.id.register4_grid_starbackground);
			viewHolder.starSelected = (ImageView) convertView
					.findViewById(R.id.register4_grid_starxuanzhong);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.isSelected = false;
		viewHolder.star_name.setText(liststar.get(position).getNickname());
		ImageLoaderUtils.displayImageView(liststar.get(position).getThumb(),
				viewHolder.picture);
		viewHolder.starBackgroud.setVisibility(View.GONE);
		viewHolder.starSelected.setVisibility(View.GONE);
		final RequestParams request = new RequestParams();
		request.add("myphone", telphone);
		request.add("fphone", liststar.get(position).getPhone());
		viewHolder.picture.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!viewHolder.isSelected) {
					viewHolder.starBackgroud.setVisibility(View.VISIBLE);
					viewHolder.starSelected.setVisibility(View.VISIBLE);
					BaseAsyncHttp.postReq(context, "/follow/set", request,
							new JSONObjectHttpResponseHandler() {
								@Override
								public void jsonSuccess(JSONObject resp) {
									// TODO Auto-generated method stub
									int flag = resp.optInt("flag");
									if (flag == 1)
										Utils.showShortToast(context, "关注成功");
									else
										Utils.showShortToast(context, "已关注");
								}

								@Override
								public void jsonFail(JSONObject resp) {
									// TODO Auto-generated method stub
								}
							});
				} else {
					viewHolder.starBackgroud.setVisibility(View.GONE);
					viewHolder.starSelected.setVisibility(View.GONE);
					BaseAsyncHttp.postReq(context, "/follow/cancel", request,
							new JSONObjectHttpResponseHandler() {

								@Override
								public void jsonSuccess(JSONObject resp) {
									// TODO Auto-generated method stub
									Utils.showShortToast(context, "取消关注");
								}

								@Override
								public void jsonFail(JSONObject resp) {
									// TODO Auto-generated method stub

								}
							});
				}
				viewHolder.isSelected = !viewHolder.isSelected;
			}
		});

		return convertView;
	}

}

class ViewHolder {
	public ImageView picture;
	public ImageView starBackgroud;
	public ImageView starSelected;
	public TextView star_name;
	public Boolean isSelected;
}
