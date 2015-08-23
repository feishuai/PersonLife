package com.personlife.view.activity.circle;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.personlifep.R;
import com.klinker.android.link_builder.Link;
import com.klinker.android.link_builder.LinkBuilder;
import com.loopj.android.http.RequestParams;
import com.personlife.bean.App;
import com.personlife.bean.Reply;
import com.personlife.bean.Shuoshuo;
import com.personlife.bean.Star;
import com.personlife.net.BaseAsyncHttp;
import com.personlife.net.JSONObjectHttpResponseHandler;
import com.personlife.utils.ComplexPreferences;
import com.personlife.utils.Constants;
import com.personlife.utils.ImageLoaderUtils;
import com.personlife.utils.ListViewUtils;
import com.personlife.utils.PersonInfoLocal;
import com.personlife.utils.Utils;
import com.personlife.widget.ClearEditText;
import com.personlife.widget.HorizontialListView;
import com.personlife.widget.MyListView;

public class CircleFriendsFragment extends Fragment {
	View layout;
	ListView lv;
	List<Shuoshuo> mlist;
	ShuoshuoAdapter mAdapter;
	Boolean isLoad = false;

	public CircleFriendsFragment(List<Shuoshuo> list) {
		// TODO Auto-generated constructor stub
		this.mlist = list;
		// initView();
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		layout = inflater.inflate(R.layout.fragment_circle_friends, container,
				false);
		mAdapter = new ShuoshuoAdapter(getActivity());
		isLoad = true;
		initData();
		initView();
		return layout;
	}

	public Boolean getIsLoad() {
		return isLoad;
	}

	public void updateData(List<Shuoshuo> list) {
		mAdapter.setData(list);
		mAdapter.notifyDataSetChanged();
	}

	public int getListViewLayoutParams() {
		if (lv == null)
			return 0;
		int listViewHeight = ListViewUtils
				.setListViewHeightBasedOnChildren1(lv);
		Log.i("listview height", String.valueOf(listViewHeight));
		return listViewHeight;
	}

	public void initData() {

	}

	public void initView() {
		lv = (ListView) layout.findViewById(R.id.lv_circle_shuoshuo);
		lv.setAdapter(mAdapter);
	}

	private void showCommentPopopWindow(View v) {
		// TODO Auto-generated method stub
		final View view = v;
		// 一个自定义的布局，作为显示的内容
		View contentView = LayoutInflater.from(getActivity()).inflate(
				R.layout.layout_popup_comment, null);

		final EditText comment = (EditText) contentView
				.findViewById(R.id.et_popup_comment);
		comment.setFocusableInTouchMode(true);
		comment.requestFocus();

		Timer timer = new Timer();
		InputMethodManager inputManager = (InputMethodManager) comment
				.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);

		TextView sure = (TextView) contentView.findViewById(R.id.tv_popup_sure);
		sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Utils.showShortToast(getActivity(), comment.getText()
						.toString());
			}
		});
		final PopupWindow popupWindow = new PopupWindow(contentView,
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);

		// 使其聚集
		popupWindow.setFocusable(true);
		// 设置允许在外点击消失
		popupWindow.setOutsideTouchable(true);
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		popupWindow.setBackgroundDrawable(new BitmapDrawable());

		// popupWindow.setTouchInterceptor(new OnTouchListener() {
		//
		// @Override
		// public boolean onTouch(View v, MotionEvent event) {
		//
		// Log.i("mengdd", "onTouch : ");
		//
		// return false;
		// // 这里如果返回true的话，touch事件将被拦截
		// // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
		// }
		// });

		// 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
		// 我觉得这里是API的一个bug
		// popupWindow.setBackgroundDrawable(getResources().getDrawable(
		// R.drawable.selectmenu_bg_downward));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		popupWindow.showAsDropDown(view);
	}

	class ShuoshuoAdapter extends BaseAdapter {

		private Context context;

		public ShuoshuoAdapter(Context context) {
			this.context = context;
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
			if (convertView == null) {
				convertView = ((LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
						.inflate(R.layout.layout_item_shuoshuo, null);
				holder = new ViewHolder();
				holder.name = (TextView) convertView
						.findViewById(R.id.tv_shuoshuo_name);
				holder.beforetime = (TextView) convertView
						.findViewById(R.id.tv_shuoshuo_beforetime);
				holder.status = (TextView) convertView
						.findViewById(R.id.tv_shuoshuo_status);
				holder.icon = (ImageView) convertView
						.findViewById(R.id.iv_shuoshuo_icon);
				holder.content = (TextView) convertView
						.findViewById(R.id.tv_shuoshuo_content);
				holder.apps = (HorizontialListView) convertView
						.findViewById(R.id.hlv_shuoshuo_apps);
				holder.comment = (ImageView) convertView
						.findViewById(R.id.iv_shuoshuo_pinglun);
				holder.praise = (ImageView) convertView
						.findViewById(R.id.iv_shuoshuo_dianzan);
				holder.more = (ImageView) convertView
						.findViewById(R.id.iv_shuoshuo_more);
				holder.comments = (MyListView) convertView
						.findViewById(R.id.lv_shuoshuo_comment);
				holder.person = (TextView) convertView
						.findViewById(R.id.tv_shuoshuo_praise);
				holder.pinlun = (ClearEditText) convertView
						.findViewById(R.id.et_shuoshuo_comment);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			Drawable drawable = getResources().getDrawable(R.drawable.dianzan1);
			drawable.setBounds(0, 0, 40, 40);// 第一0是距左边距离，第二0是距上边距离，40分别是长宽
			holder.person.setCompoundDrawables(drawable, null, null, null);// 只放左边
			List<Star> stars = mlist.get(position).getStars();
			if (stars.size() == 0) {
				holder.person.setVisibility(View.GONE);
			} else {
				String text = stars.get(0).getNickname();
				for (int i = 1; i < stars.size(); i++) {
					text += ", " + stars.get(i).getNickname();
				}
				holder.person.setText(text + " ");
			}
			for (int i = 0; i < stars.size(); i++) {
				if (stars.get(i).getPhone().equals(PersonInfoLocal.getPhone())) {
					holder.isPraised = true;
					holder.praise.setImageDrawable(getResources().getDrawable(
							R.drawable.dianzan2));
					break;
				}
			}
			if (stars.size() > 0)
				LinkBuilder.on(holder.person).addLinks(getStarsLinks(stars))
						.build();
			holder.comments.setAdapter(new MyCommentsAdapter(mlist
					.get(position).getReplies()));
			if (mlist.get(position).getApps().size() > 4)
				holder.apps.setAdapter(new MyAppsAdapter(mlist.get(position)
						.getApps().subList(0, 4)));
			else
				holder.apps.setAdapter(new MyAppsAdapter(mlist.get(position)
						.getApps()));
			holder.praise.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String text = holder.person.getText().toString();
					String nickname = PersonInfoLocal.getNickname();
					RequestParams request = new RequestParams();
					request.add("phone", PersonInfoLocal.getPhone());
					request.add("msgid",
							String.valueOf(mlist.get(position).getMsgid()));
					if (holder.isPraised) {
						holder.praise.setImageDrawable(getResources()
								.getDrawable(R.drawable.dianzan1));
						if (text.equals(nickname + " ")) {
							holder.person.setText("");
							holder.person.setVisibility(View.GONE);
							return;
						}
						if (text.indexOf(nickname) == 0)
							text = text.replace(nickname + ", ", "");
						else
							text = text.replace(", " + nickname, "");
						holder.person.setText(text);
						BaseAsyncHttp.postReq(getActivity(), "/message/zan",
								request, new JSONObjectHttpResponseHandler() {

									@Override
									public void jsonSuccess(JSONObject resp) {
										// TODO Auto-generated method stub
									}

									@Override
									public void jsonFail(JSONObject resp) {
										// TODO Auto-generated method stub

									}
								});
					} else {
						holder.praise.setImageDrawable(getResources()
								.getDrawable(R.drawable.dianzan2));
						if (holder.person.getVisibility() == View.GONE) {
							holder.person.setVisibility(View.VISIBLE);
							holder.person.setText(nickname + " ");
						} else
							holder.person.setText(text + ", " + nickname);
						BaseAsyncHttp.postReq(getActivity(),
								"/message/cancel-zan", request,
								new JSONObjectHttpResponseHandler() {

									@Override
									public void jsonSuccess(JSONObject resp) {
										// TODO Auto-generated method stub
									}

									@Override
									public void jsonFail(JSONObject resp) {
										// TODO Auto-generated method stub
									}
								});
					}
					holder.isPraised = !holder.isPraised;
				}
			});
			// 弹出评论框
			holder.comment.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showCommentPopopWindow(v);
				}
			});
			holder.more.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ComplexPreferences.putObject(getActivity(),
							Constants.ShareAllDownloadApps, mlist.get(position)
									.getApps());
					Intent intent = new Intent(context,
							ShareAppListActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.putExtra("msgid", mlist.get(position).getMsgid());
					context.startActivity(intent);
				}
			});
			return convertView;
		}

		public void setData(List<Shuoshuo> list) {
			mlist = list;
		}

		class ViewHolder {
			ImageView icon;
			TextView name, person;
			TextView beforetime;
			TextView status;
			TextView content;
			HorizontialListView apps;
			ImageView comment, praise, more;
			MyListView comments;
			ClearEditText pinlun;
			Boolean isPraised = false;
		}
	}

	class MyAppsAdapter extends BaseAdapter {
		List<App> apps;

		public MyAppsAdapter(List<App> apps) {
			this.apps = apps;
		}

		@Override
		public int getCount() {
			return apps.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final int pos = position;
			View retval = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.layout_item_like, null);
			ImageView appicon = (ImageView) retval
					.findViewById(R.id.iv_item_icon);
			TextView appname = (TextView) retval
					.findViewById(R.id.tv_item_name);
			ImageLoaderUtils.displayAppIcon(apps.get(position).getIcon(),
					appicon);
			appname.setVisibility(View.GONE);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					appicon.getLayoutParams());
			lp.setMargins(0, 35, 0, 0);
			appicon.setLayoutParams(lp);
			return retval;
		}
	}

	class MyCommentsAdapter extends BaseAdapter {
		List<Reply> replies;

		public MyCommentsAdapter(List<Reply> replies) {
			this.replies = replies;
		}

		@Override
		public int getCount() {
			return replies.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final int pos = position;
			View retval = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.layout_item_shuoshuo_comment, null);
			TextView comment = (TextView) retval
					.findViewById(R.id.tv_shuoshuo_comment);
			Reply reply = replies.get(position);
			if (reply.getTophone() != null && reply.getTophone() != ""
					&& reply.getTophone() != "null")
				comment.setText(reply.getFromnickname() + "回复"
						+ reply.getTonickname() + ":" + reply.getContent()
						+ " ");
			else
				comment.setText(reply.getFromnickname() + ":"
						+ reply.getContent() + " ");
			LinkBuilder.on(comment).addLinks(getCommentLinks(reply)).build();
			return retval;
		}
	}

	private List<Link> getCommentLinks(Reply reply) {
		List<Link> links = new ArrayList<Link>();
		Link fromstar = new Link(reply.getFromnickname());
		fromstar.setHighlightAlpha(.4f)
				.setTextColor(Color.parseColor("#23c89e")).setUnderlined(false)
				.setOnClickListener(new Link.OnClickListener() {

					@Override
					public void onClick(String clickedText) {
						// TODO Auto-generated method stub
						Utils.showShortToast(getActivity(), clickedText);
						// Utils.start_Activity(getActivity(),
						// CircleActivity.class, new BasicNameValuePair(
						// "phone", ""));
					}
				});
		links.add(fromstar);
		if (reply.getTophone() == null || reply.getTophone() == "")
			return links;
		Link tostar = new Link(reply.getTonickname());
		tostar.setHighlightAlpha(.4f).setTextColor(Color.parseColor("#23c89e"))
				.setUnderlined(false)
				.setOnClickListener(new Link.OnClickListener() {
					@Override
					public void onClick(String clickedText) {
						Utils.showShortToast(getActivity(), clickedText);
						// TODO Auto-generated method stub
						// Utils.start_Activity(getActivity(),
						// CircleActivity.class, new BasicNameValuePair(
						// "phone", ""));
					}
				});
		links.add(tostar);
		Link content = new Link(reply.getContent());
		content.setHighlightAlpha(.4f)
				.setTextColor(Color.parseColor("#000000")).setUnderlined(false)
				.setOnClickListener(new Link.OnClickListener() {

					@Override
					public void onClick(String clickedText) {
						Utils.showShortToast(getActivity(), clickedText);
						// TODO Auto-generated method stub
						// Utils.start_Activity(getActivity(),
						// CircleActivity.class, new BasicNameValuePair(
						// "phone", ""));
					}
				});
		links.add(content);
		return links;
	}

	private List<Link> getStarsLinks(List<Star> stars) {
		List<Link> links = new ArrayList<Link>();
		for (int i = 0; i < stars.size(); i++) {
			final Star star = stars.get(i);
			Link link = new Link(star.getNickname());
			link.setHighlightAlpha(.4f)
					.setTextColor(Color.parseColor("#23c89e"))
					.setUnderlined(false)
					.setOnClickListener(new Link.OnClickListener() {

						@Override
						public void onClick(String clickedText) {
							// TODO Auto-generated method stub
							// Utils.start_Activity(getActivity(),
							// CircleActivity.class, new BasicNameValuePair(
							// "phone", star.getPhone()));
							Utils.showShortToast(getActivity(), clickedText);
						}
					});
			links.add(link);
		}
		return links;
	}
}
