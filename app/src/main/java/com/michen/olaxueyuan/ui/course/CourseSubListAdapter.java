package com.michen.olaxueyuan.ui.course;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.protocol.result.CourseVieoListResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *
 */
public class CourseSubListAdapter extends BaseAdapter {


    private Context context;
    private List<CourseVieoListResult.ResultBean.CourseListBean> videoList = new ArrayList<>();

    public CourseSubListAdapter(Context context) {
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_course_sublist, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.num.setText(String.valueOf(position + 1));
        holder.tvTitle.setText(videoList.get(position).getName());
        holder.tvTime.setText(videoList.get(position).getSubAllNum() + "学时   " + videoList.get(position).getTotalTime()
                + "  " + videoList.get(position).getPlaycount() + "人学习");
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CourseVideoActivity.class);
                intent.putExtra("pid", String.valueOf(videoList.get(position).getId()));
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.num)
        TextView num;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_time)
        TextView tvTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

