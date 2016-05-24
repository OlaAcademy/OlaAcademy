package com.snail.olaxueyuan.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.snail.olaxueyuan.R;
import com.snail.olaxueyuan.protocol.result.CourseVideoResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 2016/5/24.
 */
public class CourseVideoListAdapter extends BaseAdapter {
    private Context context;
    private List<CourseVideoResult.ResultBean.VideoListBean> videoList = new ArrayList<>();

    public CourseVideoListAdapter(Context context) {
        super();
        this.context = context;
    }

    public void updateData(List<CourseVideoResult.ResultBean.VideoListBean> videoList) {
        if (videoList != null) {
            this.videoList = videoList;
        }
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
            convertView = View.inflate(context, R.layout.activity_course_video_listview_item, null);
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
