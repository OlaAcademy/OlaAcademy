package com.michen.olaxueyuan.ui.home.data;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.protocol.result.HomeModule;
import com.michen.olaxueyuan.ui.BaseRecyclerAdapter;
import com.michen.olaxueyuan.ui.course.CourseVideoActivity;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 2016/8/24.
 */
public class CourseDatabaseRecyclerAdapter extends BaseRecyclerAdapter<HomeModule.ResultBean.CourseListBean, CourseDatabaseRecyclerAdapter.CourseDatabaseItemHolder> {
    public CourseDatabaseRecyclerAdapter(Context context) {
        super(context);
    }

    public class CourseDatabaseItemHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_course_layout)
        FrameLayout ivCourseLayout;
        @Bind(R.id.iv_course)
        ImageView ivCourse;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.iv_time)
        ImageView ivTime;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.iv_browse)
        ImageView ivBrowse;
        @Bind(R.id.tv_browser)
        TextView tvBrowser;
        @Bind(R.id.text_tip)
        TextView textTip;

        public CourseDatabaseItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public CourseDatabaseItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.course_database_list_item, parent, false);
        CourseDatabaseItemHolder holder = new CourseDatabaseItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CourseDatabaseItemHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) holder.ivCourseLayout.getLayoutParams();
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        linearParams.width = dm.widthPixels / 2 - 45;
        linearParams.height = linearParams.width * 17 / 31;
        if (position % 2 == 0)
            linearParams.setMargins(30, 0, 15, 0);
        else
            linearParams.setMargins(15, 0, 30, 0);
        holder.ivCourseLayout.setLayoutParams(linearParams);

        final HomeModule.ResultBean.CourseListBean course = list.get(position);
        holder.tvName.setText(course.getName());
        holder.tvTime.setText(course.getTotalTime());
        holder.tvBrowser.setText(context.getString(R.string.num_watch, course.getPlaycount()));
        if (position == 0) {
            holder.textTip.setText(R.string.hot);
        } else {
            holder.textTip.setText(R.string.newest);
        }
        try {
            Picasso.with(context).load(course.getAddress()).config(Bitmap.Config.RGB_565)
                    .placeholder(R.drawable.default_index).error(R.drawable.default_index).into(holder.ivCourse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CourseVideoActivity.class);
                intent.putExtra("pid", String.valueOf(course.getId()));
                context.startActivity(intent);
            }
        });
    }
}
