package com.michen.olaxueyuan.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.protocol.result.UserPlanDetailResult;
import com.michen.olaxueyuan.ui.me.activity.BuyVipActivity;
import com.michen.olaxueyuan.ui.me.activity.PDFViewActivity;
import com.michen.olaxueyuan.ui.plan.activity.CompleteScheduleActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 17/9/18.
 */
public class CompleteScheduleAdapter extends BaseExpandableListAdapter {
	Context context;
	UserPlanDetailResult userPlanDetailResult;
	List<UserPlanDetailResult.ResultBean.PlanListBean> list = new ArrayList<>();
	CompleteScheduleActivity activity;

	public CompleteScheduleAdapter(Context context, CompleteScheduleActivity activity) {
		this.context = context;
		this.activity = activity;
	}

	public void updateList(UserPlanDetailResult userPlanDetailResult) {
		this.userPlanDetailResult = userPlanDetailResult;
		if (userPlanDetailResult.getResult().getPlanList() != null) {
			list.clear();
			list.addAll(userPlanDetailResult.getResult().getPlanList());
		}
		notifyDataSetChanged();
	}

	@Override
	public int getGroupCount() {
		return list.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return list.get(groupPosition).getCommonList().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return list.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return list.get(groupPosition).getCommonList();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		ParentViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.fragment_plan_parent_item, null);
			holder = new ParentViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ParentViewHolder) convertView.getTag();
		}
		holder.parentIcon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				activity.collectPlanDetail(list.get(groupPosition).getId(), list.get(groupPosition).getItem());
			}
		});
		holder.parentTitle.setText(list.get(groupPosition).getTitle());
//        Picasso.with(context).load(list.get(groupPosition).getAddress()).resize(17, 17).config(Bitmap.Config.RGB_565)
//                .placeholder(R.drawable.ic_launcher_imageview).into(holder.questionAddIcon);
		return convertView;
	}

	static class ParentViewHolder {
		@Bind(R.id.parent_icon)
		ImageView parentIcon;
		@Bind(R.id.parent_title)
		TextView parentTitle;

		ParentViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		final ChildViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.fragment_plan_child_item, null);
			holder = new ChildViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ChildViewHolder) convertView.getTag();
		}
		final UserPlanDetailResult.ResultBean.PlanListBean.CommonListBean commonListBean = list.get(groupPosition).getCommonList().get(childPosition);
		switch (commonListBean.getType()) {
			case 1:
				holder.knowledgeHandoutLayout.setVisibility(View.GONE);
				holder.videoLayout.setVisibility(View.VISIBLE);
				holder.specicalExercisesLayout.setVisibility(View.GONE);
				holder.videoChildTitle.setText(commonListBean.getName());
				holder.videoChildTime.setText(commonListBean.getTime());
				if (commonListBean.getIsfree().equals("1")) {
					holder.videoChildIcon.setImageResource(R.drawable.icon_plan_lock);
				} else {
					holder.videoChildIcon.setImageResource(R.drawable.icon_plan_video);
				}
				break;
			case 2:
				holder.knowledgeHandoutLayout.setVisibility(View.GONE);
				holder.videoLayout.setVisibility(View.GONE);
				holder.specicalExercisesLayout.setVisibility(View.VISIBLE);
				holder.specicalExercisesChildTitle.setText(commonListBean.getName());
				if (!TextUtils.isEmpty(commonListBean.getUrl())) {
					String[] split = commonListBean.getUrl().split(",");
					holder.specicalExercisesChildCourseCount.setText(split.length + "道小题");
				} else {
					holder.specicalExercisesChildCourseCount.setText("0道小题");
				}
				break;
			case 3:
				holder.knowledgeHandoutLayout.setVisibility(View.VISIBLE);
				holder.videoLayout.setVisibility(View.GONE);
				holder.specicalExercisesLayout.setVisibility(View.GONE);
				holder.childTitle.setText(commonListBean.getName());
			default:
				break;
		}
		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				switch (commonListBean.getType()) {
					case 1:
						if (commonListBean.getIsfree().equals("1")) {
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
						} else {

						}
						break;
					case 2:
						break;
					case 3:
						Intent intent = new Intent(context, PDFViewActivity.class);
						intent.putExtra("url", commonListBean.getUrl());
						intent.putExtra("title", commonListBean.getName());
						intent.putExtra("id", commonListBean.getId());
						intent.putExtra("name", commonListBean.getName());
						context.startActivity(intent);
						break;
					default:
						break;
				}
			}
		});
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	class ChildViewHolder {
		@Bind(R.id.child_icon)
		ImageView childIcon;
		@Bind(R.id.child_title)
		TextView childTitle;
		@Bind(R.id.knowledge_handout_layout)
		RelativeLayout knowledgeHandoutLayout;
		@Bind(R.id.top_video_child_line)
		View topVideoChildLine;
		@Bind(R.id.video_child_icon)
		ImageView videoChildIcon;
		@Bind(R.id.bottom_video_child_line)
		View bottomVideoChildLine;
		@Bind(R.id.video_child_layout)
		RelativeLayout videoChildLayout;
		@Bind(R.id.video_child_title)
		TextView videoChildTitle;
		@Bind(R.id.video_child_time)
		TextView videoChildTime;
		@Bind(R.id.video_layout)
		RelativeLayout videoLayout;
		@Bind(R.id.top_specical_exercises_child_line)
		View topSpecicalExercisesChildLine;
		@Bind(R.id.specical_exercises_child_icon)
		ImageView specicalExercisesChildIcon;
		@Bind(R.id.bottom_specical_exercises_child_line)
		View bottomSpecicalExercisesChildLine;
		@Bind(R.id.specical_exercises_child_layout)
		RelativeLayout specicalExercisesChildLayout;
		@Bind(R.id.specical_exercises_child_title)
		TextView specicalExercisesChildTitle;
		@Bind(R.id.specical_exercises_child_course_count)
		TextView specicalExercisesChildCourseCount;
		@Bind(R.id.specical_exercises_layout)
		RelativeLayout specicalExercisesLayout;

		ChildViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
	}
}
