package com.michen.olaxueyuan.ui.plan.data;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.protocol.result.PlanHomeData;
import com.michen.olaxueyuan.ui.course.CourseVideoActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 2016/9/07.
 * 计划首页我的课程
 */
public class PlanMyCourseAdapter extends BaseAdapter {
	private Context mContext;
	List<PlanHomeData.ResultBean.CollectionListBean> list = new ArrayList<>();

	public PlanMyCourseAdapter(Context mContext) {
		this.mContext = mContext;
	}

	public void updateData(List<PlanHomeData.ResultBean.CollectionListBean> list) {
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
			convertView = View.inflate(mContext, R.layout.recycler_plan_my_course_list_item, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.title.setText(list.get(position).getVideoName());
		holder.time.setText(list.get(position).getTotalTime());
		holder.author.setText(list.get(position).getSubAllNum() + "章节  欧拉学院");
		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, CourseVideoActivity.class);
				intent.putExtra("pid", String.valueOf(list.get(position).getCourseId()));
				mContext.startActivity(intent);
			}
		});
		return convertView;
	}

	class ViewHolder {
		@Bind(R.id.title)
		TextView title;
		@Bind(R.id.time)
		TextView time;
		@Bind(R.id.author)
		TextView author;

		ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
	}
}
