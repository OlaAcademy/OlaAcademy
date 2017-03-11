package com.michen.olaxueyuan.ui.course;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.protocol.result.CourseVieoListResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *
 */
public class CourseVideoListAdapter extends BaseAdapter {


    private Context context;
    private List<CourseVieoListResult.ResultBean.CourseListBean> videoList = new ArrayList<>();

    public CourseVideoListAdapter(Context context) {
        super();
        this.context = context;
    }

    public void updateData(List<CourseVieoListResult.ResultBean.CourseListBean> videoList) {
        this.videoList = videoList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return videoList.size();
    }

    @Override
    public Object getItem(int index) {
        return videoList.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;//230*214
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_course_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvTitle.setText(videoList.get(position).getName());
        if (!TextUtils.isEmpty(videoList.get(position).getAddress())) {
            Picasso.with(context).load(videoList.get(position).getAddress())
                    .placeholder(R.drawable.system_wu).error(R.drawable.system_wu)
//                    .resize(230, 214)
                    .into(holder.iconVideo);
        }
        holder.tvChapter.setText(videoList.get(position).getSubAllNum() + "章节");
        holder.tvAuthor.setText(videoList.get(position).getTeacherName());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(context, CourseVideoActivity.class);
                intent.putExtra("pid", videoList.get(position).getPid());
                context.startActivity(intent);*/
                context.startActivity(new Intent(context, CourseVideoSubListActivity.class)
                        .putExtra("pid", String.valueOf(videoList.get(position).getId())));
            }
        });
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.icon_video)
        ImageView iconVideo;
        @Bind(R.id.iv_course_layout)
        FrameLayout ivCourseLayout;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_chapter)
        TextView tvChapter;
        @Bind(R.id.tv_author)
        TextView tvAuthor;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

