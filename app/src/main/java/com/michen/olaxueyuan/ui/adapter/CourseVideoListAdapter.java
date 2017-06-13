package com.michen.olaxueyuan.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.protocol.event.VideoPdfEvent;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.result.CourseVideoResult;
import com.michen.olaxueyuan.ui.course.CourseVideoActivity;
import com.michen.olaxueyuan.ui.course.video.CourseVideoPopupWindowManager;
import com.michen.olaxueyuan.ui.me.activity.BuyVipActivity;
import com.michen.olaxueyuan.ui.me.activity.UserLoginActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 2016/5/24.
 */
public class CourseVideoListAdapter extends BaseAdapter {
	private Context context;
	private List<CourseVideoResult.ResultBean.VideoListBean> videoList = new ArrayList<>();

	public CourseVideoListAdapter(Context context) {
		super();
		this.context = context;
	}

	public void updateData(List<CourseVideoResult.ResultBean.VideoListBean> videoList) {
		if (videoList != null) {
			this.videoList = videoList;
		}
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return videoList.size();
	}

	@Override
	public Object getItem(int position) {
		return videoList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.activity_course_video_listview_item, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.videoName.setText(videoList.get(position).getName());
		holder.videoLength.setText(videoList.get(position).getTimeSpan() + "  未下载");
		if (videoList.get(position).isSelected()) {
			holder.videoName.setSelected(true);
			holder.videoLength.setSelected(true);
			Glide.with(context).load(R.drawable.video_play_item_icon_gif).asGif().into(holder.playIcon);
		} else {
			holder.videoName.setSelected(false);
			holder.videoLength.setSelected(false);
			Glide.with(context).load(R.drawable.video_play_item_icon).centerCrop().into(holder.playIcon);
		}
		holder.pdfViewImg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CourseVideoPopupWindowManager.getInstance().showHandOutPop
						(context, new VideoPdfEvent(videoList.get(position).getUrl()
								, videoList.get(position).getId(), 1, position, videoList.get(position).getName()));
			}
		});
		holder.videoDownloadItemImg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (videoList.get(position).getIsfree() == 0) {
					if (!SEAuthManager.getInstance().isAuthenticated()) {
						context.startActivity(new Intent(context, UserLoginActivity.class));
					} else {
						new AlertDialog.Builder(context)
								.setTitle("友情提示")
								.setMessage("购买会员后即可拥有")
								.setPositiveButton("去购买", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										context.startActivity(new Intent(context, BuyVipActivity.class));
									}
								})
								.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										// do nothing
									}
								})
								.show();
					}
					return;
				}
				((CourseVideoActivity) context).downloadCourse(videoList.get(position));
			}
		});
		return convertView;
	}

	class ViewHolder {
		@Bind(R.id.play_icon)
		ImageView playIcon;
		@Bind(R.id.video_name)
		TextView videoName;
		@Bind(R.id.video_length)
		TextView videoLength;
		@Bind(R.id.pdf_view_img)
		ImageView pdfViewImg;
		@Bind(R.id.video_download_item_img)
		ImageView videoDownloadItemImg;

		ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
	}
}
