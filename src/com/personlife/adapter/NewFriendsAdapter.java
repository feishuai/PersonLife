package com.personlife.adapter;

import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.personlifep.R;
import com.github.snowdream.android.util.Log;
import com.loopj.android.http.RequestParams;
import com.personlife.bean.UserFriend;
import com.personlife.common.Utils;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONObjectHttpResponseHandler;
import com.personlife.utils.ImageLoaderUtils;

public class NewFriendsAdapter extends BaseAdapter {
	private Context context;
	private List<UserFriend> mList;
	private String mytelphone;

	public NewFriendsAdapter(Context ctx, List<UserFriend> list, String s) {
		context = ctx;
		mList = list;
		mytelphone = s;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.layout_item_newfriend, parent, false);
			holder = new ViewHolder();
			holder.photo = (ImageView) convertView.findViewById(R.id.img_photo);
			holder.nickname = (TextView) convertView
					.findViewById(R.id.txt_name);
			holder.accept = (Button) convertView.findViewById(R.id.txt_add);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ImageLoaderUtils.displayAppIcon(mList.get(position).getThumb(),
				holder.photo);
		holder.nickname.setText(mList.get(position).getNickname());
		holder.accept.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (holder.isAccepted) {
					return;
				}

				RequestParams request = new RequestParams();
				request.put("myphone", mytelphone);
				request.put("fphone", mList.get(position).getPhone());
				request.put("agree", 1);
				Log.d("accepted", "" + holder.isAccepted);
				BaseAsyncHttp.postReq(context, "/friend/acceptadd", request,
						new JSONObjectHttpResponseHandler() {

							@Override
							public void jsonSuccess(JSONObject resp) {
								// TODO Auto-generated method stub
								holder.accept.setText("已添加");
								Log.d("accepted", "success " + holder.isAccepted);
								Utils.showShortToast(context, "已同意添加该好友！");
								holder.isAccepted = true;
							}

							@Override
							public void jsonFail(JSONObject resp) {
								// TODO Auto-generated method stub
								Log.d("accepted", "fail " + holder.isAccepted);
							}
						});
			}
		});
		return convertView;
	}

	class ViewHolder {
		ImageView photo;
		TextView nickname;
		Button accept;
		Boolean isAccepted = false;
	}
}
