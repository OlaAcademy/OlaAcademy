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
import com.michen.olaxueyuan.protocol.result.HomeModule;
import com.michen.olaxueyuan.protocol.result.PlanHomeData;
import com.michen.olaxueyuan.ui.circle.PostDetailActivity;
import com.michen.olaxueyuan.ui.course.CourseVideoActivity;
import com.michen.olaxueyuan.ui.course.WebViewActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 2016/8/23.
 * 首页计划每日推荐
 */
public class PlanRecommendAdapter extends BaseAdapter {
	private Context mContext;
	List<PlanHomeData.ResultBean.RecommendListBean> list = new ArrayList<>();

	public PlanRecommendAdapter(Context mContext) {
		this.mContext = mContext;
	}

	public void updateData(List<PlanHomeData.ResultBean.RecommendListBean> list) {
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
			convertView = View.inflate(mContext, R.layout.recycler_plan_recommend_list_item, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.title.setText(list.get(position).getName());
//		holder.time.setText(list.get(position).getName());
		holder.author.setText(list.get(position).getIntroduction());
		try {
			Picasso.with(mContext).load(list.get(position).getPic()).config(Bitmap.Config.RGB_565)
					.placeholder(R.drawable.system_wu).error(R.drawable.system_wu).into(holder.imageRecommendBg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, WebViewActivity.class);
				intent.putExtra("textUrl", list.get(position).getUrl());
				mContext.startActivity(intent);
			}
		});
		return convertView;
	}

	class ViewHolder {
		@Bind(R.id.image_recommend_bg)
		ImageView imageRecommendBg;
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
