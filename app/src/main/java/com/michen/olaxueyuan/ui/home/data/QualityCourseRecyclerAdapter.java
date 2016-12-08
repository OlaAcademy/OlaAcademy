package com.michen.olaxueyuan.ui.home.data;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.Utils;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.result.SystemCourseResultEntity;
import com.michen.olaxueyuan.ui.BaseRecyclerAdapter;
import com.michen.olaxueyuan.ui.course.SystemVideoActivity;
import com.michen.olaxueyuan.ui.me.activity.UserLoginActivity;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 2016/8/24.
 */
public class QualityCourseRecyclerAdapter extends BaseRecyclerAdapter<SystemCourseResultEntity, QualityCourseRecyclerAdapter.QualityCourseItemHolder> {
    public QualityCourseRecyclerAdapter(Context context) {
        super(context);
    }

    public class QualityCourseItemHolder extends RecyclerView.ViewHolder {
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
        @Bind(R.id.tv_is_free)
        TextView tvIsFree;
        @Bind(R.id.text_tip)
        TextView textTip;

        public QualityCourseItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public QualityCourseItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.quality_course_list_item, parent, false);
        QualityCourseItemHolder holder = new QualityCourseItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(QualityCourseItemHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) holder.ivCourse.getLayoutParams();
//        Resources resources = context.getResources();
//        DisplayMetrics dm = resources.getDisplayMetrics();
//        linearParams.width = dm.widthPixels / 2 - 45;
//        linearParams.width = Utils.getScreenWidth(context) / 2 - 45;
        linearParams.width = Utils.getScreenMetricsPoint(context).x / 2 - 45;
        linearParams.height = linearParams.width * 17 / 31;
        if (position % 2 == 0)
            linearParams.setMargins(30, 0, 15, 0);
        else
            linearParams.setMargins(15, 0, 30, 0);
        holder.ivCourse.setLayoutParams(linearParams);

        final SystemCourseResultEntity course = list.get(position);
        if (course.getPrice() > 0) {
            holder.tvIsFree.setText("￥" + course.getPrice());
            holder.tvIsFree.setTextColor(context.getResources().getColor(R.color.red_f44336));
        } else {
            holder.tvIsFree.setText(R.string.free);
            holder.tvIsFree.setTextColor(context.getResources().getColor(R.color.title_hint_text_color));
        }
        if (position == 0) {
            holder.textTip.setText(R.string.hot);
        } else {
            holder.textTip.setText(R.string.newest);
        }
        holder.tvName.setText(course.getName());
        holder.tvTime.setText(course.getTotaltime() + "分钟");
        holder.tvBrowser.setText(context.getString(R.string.num_watch, String.valueOf(course.getVideonum())));
        try {
            Picasso.with(context).load(course.getUrl()).config(Bitmap.Config.RGB_565)
                    .placeholder(R.drawable.default_index).error(R.drawable.default_index).into(holder.ivCourse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SEAuthManager.getInstance().isAuthenticated()) {
                    Intent intent = new Intent(context, SystemVideoActivity.class);
                    intent.putExtra("pid", String.valueOf(list.get(position).getId()));
                    intent.putExtra("ResultEntity", list.get(position));
                    context.startActivity(intent);
                } else {
                    context.startActivity(new Intent(context, UserLoginActivity.class));
                }
            }
        });
    }
}
