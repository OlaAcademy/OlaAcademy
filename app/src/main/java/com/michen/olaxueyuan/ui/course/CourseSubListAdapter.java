package com.michen.olaxueyuan.ui.course;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.Utils;
import com.michen.olaxueyuan.protocol.result.CourseVieoListResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

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
        holder.tv_title.setText(videoList.get(position).getName());
        if (!TextUtils.isEmpty(videoList.get(position).getAddress())) {
            Picasso.with(context).load(videoList.get(position).getAddress())
                    .placeholder(R.drawable.system_wu).error(R.drawable.system_wu)
                    .resize(Utils.dip2px(context, 130), Utils.dip2px(context, 80))
                    .into(holder.iv_video);
        }
//        DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .cacheInMemory(true)
//                .cacheOnDisk(true)
//                .bitmapConfig(Bitmap.Config.RGB_565)
//                .build();
//        ImageLoader.getInstance().displayImage(videoList.get(position).getAddress(), holder.iv_video, options);
        holder.tv_time.setText(videoList.get(position).getSubAllNum() + "课时," + videoList.get(position).getTotalTime()
                + " " + videoList.get(position).getPlaycount() + "人学习");
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CourseVideoActivity.class);
                intent.putExtra("pid", videoList.get(position).getPid());
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

