package com.michen.olaxueyuan.ui.plan.data;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.protocol.result.UserPlanFinishedDetailResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 2016/9/24.
 * 计划完成每日详情
 */
public class DailyPlanDetailAdapter extends BaseAdapter {
	private Context mContext;
	List<UserPlanFinishedDetailResult.ResultBean.DailyListBean> list = new ArrayList<>();

	public DailyPlanDetailAdapter(Context mContext) {
		this.mContext = mContext;
	}

	public void updateData(List<UserPlanFinishedDetailResult.ResultBean.DailyListBean> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.daily_plan_detail_list_item, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.dateTime.setText(list.get(position).getStudyDate());
		holder.completeCourseNum.setText("完成" + list.get(position).getSubjectNumber() + "道题");
		holder.completeKnowledgePosintNum.setText("完成" + list.get(position).getDetailNumber() + "个知识点");
		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				Intent intent = new Intent(mContext, CourseVideoActivity.class);
//				intent.putExtra("pid", String.valueOf(list.get(position).getCourseId()));
//				mContext.startActivity(intent);
			}
		});
		return convertView;
	}

	class ViewHolder {
		@Bind(R.id.date_time)
		TextView dateTime;
		@Bind(R.id.icon_tips)
		ImageView iconTips;
		@Bind(R.id.complete_course_num)
		TextView completeCourseNum;
		@Bind(R.id.icon_knowledge_tips)
		ImageView iconKnowledgeTips;
		@Bind(R.id.complete_knowledge_posint_num)
		TextView completeKnowledgePosintNum;

		ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
	}
}
