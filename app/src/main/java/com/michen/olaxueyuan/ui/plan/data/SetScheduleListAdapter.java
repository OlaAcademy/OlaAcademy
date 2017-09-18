package com.michen.olaxueyuan.ui.plan.data;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.Logger;
import com.michen.olaxueyuan.protocol.result.PlanConditionResult;
import com.michen.olaxueyuan.protocol.result.PlanHomeData;
import com.michen.olaxueyuan.ui.course.CourseVideoActivity;
import com.michen.olaxueyuan.ui.plan.activity.SchedulePlanActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 2016/8/23.
 */
public class SetScheduleListAdapter extends BaseAdapter {
	private Context mContext;
	private SchedulePlanActivity activity;
	List<PlanConditionResult.ResultBean> list = new ArrayList<>();

	public SetScheduleListAdapter(Context mContext) {
		this.mContext = mContext;
	}

	public SetScheduleListAdapter(Context mContext, SchedulePlanActivity activity) {
		this.mContext = mContext;
		this.activity = activity;
	}

	public void updateData(List<PlanConditionResult.ResultBean> list) {
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
	public View getView(final int position, View convertView, final ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.set_secdule_list_item, null);
			holder = new ViewHolder(convertView);
			holder.setProgress.setThumb(mContext.getResources().getDrawable(R.drawable.icon_anchorpoint));
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.title.setText(list.get(position).getCategory());
		holder.costDay.setText(String.valueOf(list.get(position).getLeast()));
//		holder.costHour
		holder.setProgress.setMax(list.get(position).getMost());
		holder.setProgress.setProgress(list.get(position).getLeast());
		holder.allDay.setText(list.get(position).getMost() + "天");
		holder.setProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				int nowProgress;
				if (progress <= list.get(position).getLeast()) {
					nowProgress = list.get(position).getLeast();
				} else {
					nowProgress = progress;
				}
				holder.costDay.setText(String.valueOf(nowProgress));
				switch (list.get(position).getType()) {
					case 1://数学
						activity.mathLength = nowProgress;
						break;
					case 2://英语
						activity.englishLength = nowProgress;
						break;
					case 3://逻辑
						activity.logicLength = nowProgress;
						break;
					case 4://写作
						activity.writeLength = nowProgress;
						break;
				}
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});
		return convertView;
	}

	class ViewHolder {
		@Bind(R.id.title)
		TextView title;
		@Bind(R.id.cost_day)
		TextView costDay;
		@Bind(R.id.cost_hour)
		TextView costHour;
		@Bind(R.id.close_tips)
		TextView closeTips;
		@Bind(R.id.set_progress)
		SeekBar setProgress;
		@Bind(R.id.all_day)
		TextView allDay;

		ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
	}
}
