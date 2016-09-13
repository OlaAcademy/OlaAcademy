package com.michen.olaxueyuan.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.CircleProgressBar;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.result.HomeworkListResult;
import com.michen.olaxueyuan.ui.me.activity.UserLoginActivity;
import com.michen.olaxueyuan.ui.question.QuestionWebActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 16/9/13.
 */
public class QuestionHomeWorkListAdapter extends BaseAdapter {
    private List<HomeworkListResult.ResultBean> list = new ArrayList<>();
    Context mContext;
    private static final int DEFAULT_INDEX = 6;

    public QuestionHomeWorkListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void updateData(List<HomeworkListResult.ResultBean> list) {
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
            convertView = View.inflate(mContext, R.layout.activity_question_home_work_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String month = list.get(position).getTime().substring(DEFAULT_INDEX - 1, DEFAULT_INDEX + 1);
        if (month.startsWith("0")) {
            month = month.substring(1, 2);
        }
        if (position == 0) {
            holder.headerLayout.setVisibility(View.VISIBLE);
            holder.headerLine.setVisibility(View.GONE);
        } else if (position > 0 && position < list.size()) {
            String monthBefore = list.get(position - 1).getTime().substring(DEFAULT_INDEX - 1, DEFAULT_INDEX + 1);
            if (monthBefore.startsWith("0")) {
                monthBefore = monthBefore.substring(1, 2);
            }
            if (monthBefore.equals(month)) {
                holder.headerLayout.setVisibility(View.GONE);
                holder.headerLine.setVisibility(View.GONE);
            } else {
                holder.headerLayout.setVisibility(View.VISIBLE);
                holder.headerLine.setVisibility(View.VISIBLE);
            }
        }
        if (position == list.size() - 1) {
            holder.bottomLine.setVisibility(View.VISIBLE);
        } else {
            holder.bottomLine.setVisibility(View.GONE);
        }
        holder.todayHomework.setText(mContext.getString(R.string.month, month));
        holder.title.setText(list.get(position).getName());
        holder.timeText.setText(list.get(position).getTime());
        holder.courseNumText.setText(list.get(position).getCount() + "道小题");
        holder.groupNameText.setText(list.get(position).getGroupName());
        holder.circleProgress.setProgress(list.get(position).getFinishedCount() * 100 / list.get(position).getCount());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SEAuthManager.getInstance().isAuthenticated()) {
                    Intent intent = new Intent(mContext, QuestionWebActivity.class);
                    intent.putExtra("objectId", list.get(position).getId());
                    intent.putExtra("type", 3);
                    intent.putExtra("courseType", 1); //2 英语阅读
                    mContext.startActivity(intent);
                } else {
                    mContext.startActivity(new Intent(mContext, UserLoginActivity.class));
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.icon_course)
        ImageView iconCourse;
        @Bind(R.id.today_homework)
        TextView todayHomework;
        @Bind(R.id.header_layout)
        RelativeLayout headerLayout;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.time_icon)
        ImageView timeIcon;
        @Bind(R.id.time_text)
        TextView timeText;
        @Bind(R.id.course_num_icon)
        ImageView courseNumIcon;
        @Bind(R.id.course_num_text)
        TextView courseNumText;
        @Bind(R.id.group_name_text)
        TextView groupNameText;
        @Bind(R.id.circle_progress)
        CircleProgressBar circleProgress;
        @Bind(R.id.homeworkRL)
        RelativeLayout homeworkRL;
        @Bind(R.id.header_line)
        View headerLine;
        @Bind(R.id.bottom_line)
        View bottomLine;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
