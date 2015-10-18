package com.personlife.view.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
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
import com.personlife.view.activity.circle.ShareAppListActivity;
import com.personlife.widget.ClearEditText;
import com.personlife.widget.HorizontialListView;
import com.personlife.widget.MyListView;
import com.personlife.widget.pullrefresh.PullRefreshLayout;

public class NewCircleFragment extends Fragment {
	View layout;
	ListView lv;
	List<Shuoshuo> mlist;
	ShuoshuoAdapter mAdapter;
	Star star;
	Boolean isLoaded = false;
	String phone;
	PullRefreshLayout prlayout;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		layout = inflater.inflate(R.layout.fragment_circle_newfriends, container,
				false);
		mAdapter = new ShuoshuoAdapter(getActivity());
		star = new Star();
		star.setPhone(PersonInfoLocal.getPhone(getActivity()));
		RequestParams request = new RequestParams();
		request.add("phone", star.getPhone());
		BaseAsyncHttp.postReq(getActivity(), "/users/getinfo", request,
				new JSONObjectHttpResponseHandler() {

					@Override
					public void jsonSuccess(JSONObject resp) {
						// TODO Auto-generated method stub
						star.setPhone(resp.optString("phone"));
						star.setNickname(resp.optString("nickname"));
						star.setThumb(resp.optString("thumb"));
						star.setFollower(resp.optString("follower"));
						initData();
					}

					@Override
					public void jsonFail(JSONObject resp) {
						// TODO Auto-generated method stub

					}
				});
		initData();
		initView();
		prlayout = (PullRefreshLayout) layout.findViewById(R.id.swipeRefreshLayout);
		// listen refresh event
		prlayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
		    @Override
		    public void onRefresh() {
		        // start refresh
		    	 prlayout.postDelayed(new Runnable() {
	                    @Override
	                    public void run() {
	                        prlayout.setRefreshing(false);
	                    }
	                }, 3000);
		    	 initData();
		    }
		});


		return layout;
	}

	public void updateData(List<Shuoshuo> list) {
		this.mlist = list;
		if (mAdapter != null)
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
		final List<Shuoshuo> shuoshuos = new ArrayList<Shuoshuo>();
		RequestParams requestShuoshuo = new RequestParams();
		requestShuoshuo.add("phone", star.getPhone());
		BaseAsyncHttp.postReq(getActivity(), "/message/get", requestShuoshuo,
				new JSONObjectHttpResponseHandler() {
					@Override
					public void jsonSuccess(JSONObject resp) {
						// TODO Auto-generated method stub
						try {
							JSONArray jsonshuoshuos = resp.getJSONArray("item");
							for (int i = 0; i < jsonshuoshuos.length(); i++) {
								Shuoshuo shuoshuo = new Shuoshuo();
								JSONObject jsonshuoshuo = jsonshuoshuos
										.getJSONObject(i);
								shuoshuo.setContent(jsonshuoshuo
										.getString("content"));
								shuoshuo.setCreatedtime(jsonshuoshuo
										.getInt("created_at"));
								shuoshuo.setArea(jsonshuoshuo.getString("area"));
								shuoshuo.setKind(jsonshuoshuo.getString("kind"));
								shuoshuo.setMsgid(jsonshuoshuo.getInt("id"));
								shuoshuo.setNickname(jsonshuoshuo
										.getString("nickname"));
								shuoshuo.setThumb(jsonshuoshuo
										.getString("thumb"));
								JSONArray jsonapps = jsonshuoshuo
										.getJSONArray("apps");
								List<App> shuoshuoapps = new ArrayList<App>();
								for (int j = 0; j < jsonapps.length(); j++) {
									App app = new App();
									JSONObject jsonapp = jsonapps
											.getJSONObject(j);
									app.setIcon(jsonapp.getString("icon"));
									app.setSize(jsonapp.getString("size"));
									app.setDowloadcount(jsonapp
											.getInt("downloadcount"));
									app.setIntrodution(jsonapp
											.getString("introduction"));
									app.setName(jsonapp.getString("name"));
									app.setId(jsonapp.getInt("id"));
									app.setDownloadUrl(jsonapp
											.getString("android_url"));
									app.setProfile(jsonapp.getString("profile"));
									app.setDownloadPath(Constants.DownloadPath
											+ app.getName() + ".apk");
									shuoshuoapps.add(app);
								}
								shuoshuo.setApps(shuoshuoapps);
								JSONArray jsonreplies = jsonshuoshuo
										.getJSONArray("replys");
								List<Reply> shuoshuoreplies = new ArrayList<Reply>();
								for (int k = 0; k < jsonreplies.length(); k++) {
									Reply reply = new Reply();
									JSONObject jsonreply = jsonreplies
											.getJSONObject(k);
									reply.setFromphone(jsonreply
											.getString("fromphone"));
									reply.setFromnickname(jsonreply
											.getString("fromnickname"));
									reply.setTophone(jsonreply
											.getString("tophone"));
									reply.setTonickname(jsonreply
											.getString("tonickname"));
									reply.setContent(jsonreply
											.getString("content"));
									shuoshuoreplies.add(reply);
								}
								shuoshuo.setReplies(shuoshuoreplies);
								List<Star> shuoshuozans = new ArrayList<Star>();
								JSONArray jsonzans = jsonshuoshuo
										.getJSONArray("zan");
								for (int l = 0; l < jsonzans.length(); l++) {
									Star zan = new Star();
									JSONObject jsonzan = jsonzans
											.getJSONObject(l);
									zan.setPhone(jsonzan.getString("phone"));
									zan.setNickname(jsonzan
											.getString("nickname"));
									shuoshuozans.add(zan);
								}
								shuoshuo.setStars(shuoshuozans);
								shuoshuos.add(shuoshuo);
							}
							updateData(shuoshuos);

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void jsonFail(JSONObject resp) {
						// TODO Auto-generated method stub
					}
				});
	}

	public void initView() {
		lv = (ListView) layout.findViewById(R.id.lv_circle_shuoshuo);
		mlist = new ArrayList<Shuoshuo>();
		lv.setAdapter(mAdapter);
	}

	private void showCommentPopopWindow(View v, int msgid, Reply reply,
			final MyCommentsAdapter adapter) {
		// TODO Auto-generated method stub
		final View view = v;
		final Reply freply = reply;
		final Reply newreply = new Reply();
		// 一个自定义的布局，作为显示的内容
		View contentView = LayoutInflater.from(getActivity()).inflate(
				R.layout.layout_popup_comment, null);

		final EditText comment = (EditText) contentView
				.findViewById(R.id.et_popup_comment);
		comment.setFocusableInTouchMode(true);
		comment.requestFocus();
		if (freply != null)
			comment.setHint("回复 " + freply.getFromnickname() + ":");
		InputMethodManager inputManager = (InputMethodManager) comment
				.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
		TextView sure = (TextView) contentView.findViewById(R.id.tv_popup_sure);
		final PopupWindow popupWindow = new PopupWindow(contentView,
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
		final RequestParams request = new RequestParams();
		request.add("fphone", star.getPhone());
		request.add("msgid", String.valueOf(msgid));
		newreply.setFromphone(star.getPhone());
		newreply.setFromnickname(star.getNickname());
		sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String content = comment.getText().toString();
				if (content.equals("") || content == null) {
					Utils.showShortToast(getActivity(), "评价不能为空");
					return;
				}
				request.add("content", content);
				newreply.setContent(content);
				if (freply == null)
					request.add("tphone", "");
				else
					request.add("tphone", freply.getFromphone());
				if (freply != null) {
					newreply.setTophone(freply.getFromphone());
					newreply.setTonickname(freply.getFromnickname());
				}
				BaseAsyncHttp.postReq(getActivity(), "/message/reply", request,
						new JSONObjectHttpResponseHandler() {

							@Override
							public void jsonSuccess(JSONObject resp) {
								// TODO Auto-generated method stub
								Utils.showShortToast(getActivity(), "评价成功");
								adapter.getData().add(newreply);
								adapter.notifyDataSetChanged();
								popupWindow.dismiss();
								InputMethodManager imm = (InputMethodManager) comment
										.getContext()
										.getSystemService(
												getActivity().INPUT_METHOD_SERVICE);
								imm.toggleSoftInput(0,
										InputMethodManager.HIDE_NOT_ALWAYS);
							}

							@Override
							public void jsonFail(JSONObject resp) {
								// TODO Auto-generated method stub
								Utils.showShortToast(getActivity(), "评价失败");
							}
						});
			}
		});

		// 使其聚集
		popupWindow.setFocusable(true);
		// 设置允许在外点击消失
		popupWindow.setOutsideTouchable(true);
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		popupWindow.setBackgroundDrawable(new BitmapDrawable());

		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// popupWindow.setFocusable(true);
		popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
		popupWindow
				.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
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
			holder.content.setText("           "
					+ mlist.get(position).getContent());
			holder.name.setText(mlist.get(position).getNickname());
			holder.status.setText(mlist.get(position).getKind());
			ImageLoaderUtils.displayImageView(mlist.get(position).getThumb(),
					holder.icon);
			holder.beforetime.setText(Utils.TimeStamp2Date(mlist.get(position)
					.getCreatedtime()));
			Drawable drawable = getResources().getDrawable(R.drawable.dianzan1);
			drawable.setBounds(0, 0, 40, 40);// 第一0是距左边距离，第二0是距上边距离，40分别是长宽
			holder.person.setCompoundDrawables(drawable, null, null, null);// 只放左边
			holder.person.setText("");
			holder.person.setVisibility(View.VISIBLE);
			holder.isPraised = false;
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
			// 判断用户是否有点赞
			holder.praise.setImageDrawable(getResources().getDrawable(
					R.drawable.dianzan1));
			for (int i = 0; i < stars.size(); i++) {
				if (stars.get(i).getPhone()
						.equals(PersonInfoLocal.getPhone(getActivity()))) {
					holder.isPraised = true;
					holder.praise.setImageDrawable(getResources().getDrawable(
							R.drawable.dianzan2));
					break;
				}
			}
			if (stars.size() > 0)
				LinkBuilder.on(holder.person).addLinks(getStarsLinks(stars))
						.build();
			final MyCommentsAdapter commentsAdapter = new MyCommentsAdapter(
					mlist.get(position).getReplies(), mlist.get(position)
							.getMsgid());
			commentsAdapter.setAdapter(commentsAdapter);
			holder.comments.setAdapter(commentsAdapter);
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
					String nickname = star.getNickname();
					RequestParams request = new RequestParams();
					request.add("phone",
							PersonInfoLocal.getPhone(getActivity()));
					request.add("msgid",
							String.valueOf(mlist.get(position).getMsgid()));
					if (holder.isPraised) {
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

					} else {
						holder.praise.setImageDrawable(getResources()
								.getDrawable(R.drawable.dianzan2));
						if (holder.person.getVisibility() == View.GONE) {
							holder.person.setVisibility(View.VISIBLE);
							holder.person.setText(nickname + " ");
						} else
							holder.person.setText(text + ", " + nickname);
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
					}
					holder.isPraised = !holder.isPraised;
				}
			});
			// 弹出评论框
			holder.comment.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showCommentPopopWindow(v, mlist.get(position).getMsgid(),
							null, commentsAdapter);

				}
			});
			holder.pinlun.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showCommentPopopWindow(v, mlist.get(position).getMsgid(),
							null, commentsAdapter);

				}
			});
			holder.more.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ComplexPreferences.putObject(getActivity(),
							Constants.ShareAllDownloadApps, mlist.get(position)
									.getApps());
					Intent intent = new Intent(getActivity(),
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
		MyCommentsAdapter adapter;
		int msgid;

		public MyCommentsAdapter(List<Reply> replies, int msgid) {
			this.replies = replies;
			this.msgid = msgid;
		}

		public void setData(List<Reply> replies) {
			this.replies = replies;
		}

		public List<Reply> getData() {
			return this.replies;
		}

		public void setAdapter(MyCommentsAdapter adapter) {
			this.adapter = adapter;
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
			LinkBuilder.on(comment)
					.addLinks(getCommentLinks(reply, msgid, adapter, retval))
					.build();
			return retval;
		}
	}

	private List<Link> getCommentLinks(final Reply reply, final int msgid,
			final MyCommentsAdapter adapter, final View v) {
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
						// Utils.showShortToast(getActivity(), clickedText);
						showCommentPopopWindow(v, msgid, reply, adapter);
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
