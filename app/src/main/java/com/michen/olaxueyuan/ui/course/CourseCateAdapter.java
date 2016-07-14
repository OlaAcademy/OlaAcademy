package com.michen.olaxueyuan.ui.course;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.michen.olaxueyuan.protocol.result.CourseVideoResult;
import com.michen.olaxueyuan.R;

import java.util.List;

/**
 * Created by tianxiaopeng on 15-1-10.
 */
public class CourseCateAdapter extends BaseAdapter {


    private Context context;
    private List<CourseVideoResult.ResultBean.VideoListBean> videoList;

    public CourseCateAdapter(Context context, List<CourseVideoResult.ResultBean.VideoListBean> videoList) {
        super();
        this.context = context;
        this.videoList = videoList;
    }

    @Override
    public int getCount() {
        if (videoList != null) {
            return videoList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int index) {
        if (videoList != null) {
            return videoList.get(index);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_course_section, null);
            holder = new ViewHolder();
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_title.setText(videoList.get(position).getName());
        return convertView;
    }


    class ViewHolder {
        private TextView tv_title;
        private ListView lv_point;
    }

}
