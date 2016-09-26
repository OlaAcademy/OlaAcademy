package com.michen.olaxueyuan.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.michen.olaxueyuan.protocol.result.CourseVideoResult;
import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.ui.course.CourseVideoActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 2016/6/14.
 */
public class QuestionResultListAdapter extends BaseAdapter {
    private Context context;
    private List<CourseVideoResult.ResultBean.VideoListBean> videoList = new ArrayList<>();
    private CourseVideoResult result;
    private String pid;

    public QuestionResultListAdapter(Context context) {
        super();
        this.context = context;
    }

    public void updateData(List<CourseVideoResult.ResultBean.VideoListBean> videoList, CourseVideoResult result, String pid) {
        if (videoList != null) {
            this.videoList = videoList;
        }
        if (result != null) {
            this.result = result;
        }
        this.pid = pid;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return videoList.size();
    }

    @Override
    public Object getItem(int position) {
        return videoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.activity_question_result_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.videoName.setText(videoList.get(position).getName());
        holder.videoLength.setText(videoList.get(position).getTimeSpan());
        if (videoList.get(position).isSelected()) {
            holder.videoName.setSelected(true);
        } else {
            holder.videoName.setSelected(false);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CourseVideoActivity.class);
//                intent.putExtra("isFromNet", false);
//                intent.putExtra("result", result);
                intent.putExtra("pid", pid);
                context.startActivity(intent);
//                EventBus.getDefault().post(result);
            }
        });
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.video_name)
        TextView videoName;
        @Bind(R.id.video_length)
        TextView videoLength;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
