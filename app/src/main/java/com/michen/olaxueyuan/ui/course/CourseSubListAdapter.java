package com.michen.olaxueyuan.ui.course;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.protocol.model.MCSubCourse;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by tianxiaopeng on 15-1-10.
 */
public class CourseSubListAdapter extends BaseAdapter {


    private Context context;
    private List<MCSubCourse> videoList;

    public CourseSubListAdapter(Context context) {
        super();
        this.context = context;
    }

    public void updateData(List<MCSubCourse> videoList) {
        this.videoList = videoList;
        notifyDataSetChanged();
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
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_course_sublist, null);
            holder = new ViewHolder();
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.iv_video = (ImageView) convertView.findViewById(R.id.icon_video);
            holder.iv_video.setScaleType(ImageView.ScaleType.FIT_XY); // 设置缩放方式
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_title.setText(videoList.get(position).name);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoader.getInstance().displayImage(videoList.get(position).address, holder.iv_video, options);
        holder.tv_time.setText(videoList.get(position).subAllNum + "课时," + videoList.get(position).totalTime + " " + videoList.get(position).playcount + "人学习");
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CourseVideoActivity.class);
                intent.putExtra("pid", videoList.get(position).id);
                context.startActivity(intent);
            }
        });
        return convertView;
    }


    class ViewHolder {
        private TextView tv_title;
        private ImageView iv_video;
        private TextView tv_time;
    }

}

