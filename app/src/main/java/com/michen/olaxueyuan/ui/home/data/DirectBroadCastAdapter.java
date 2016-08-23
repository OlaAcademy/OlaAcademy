package com.michen.olaxueyuan.ui.home.data;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.protocol.result.HomeModule;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mingge on 2016/8/23.
 */
public class DirectBroadCastAdapter extends BaseAdapter {
    private List<HomeModule.ResultBean.GoodsListBean> subCourseList=new ArrayList<>();
    private Context context;

    public DirectBroadCastAdapter(Context context) {
        this.context = context;
    }

    public void updateData(List<HomeModule.ResultBean.GoodsListBean> subCourseList) {
        this.subCourseList = subCourseList;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GridViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.direct_broadcast_list_item, null);
            viewHolder = new GridViewHolder();
            viewHolder.iv_course = (ImageView) convertView.findViewById(R.id.iv_course);
            RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) viewHolder.iv_course.getLayoutParams();
            Resources resources = context.getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            linearParams.width = dm.widthPixels / 2 - 45;
            if (position % 2 == 0)
                linearParams.setMargins(30, 0, 15, 0);
            else
                linearParams.setMargins(15, 0, 30, 0);
            viewHolder.iv_course.setLayoutParams(linearParams);
            viewHolder.iv_course.setScaleType(ImageView.ScaleType.FIT_XY); // 设置缩放方式
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.tv_browser = (TextView) convertView.findViewById(R.id.tv_browser);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (GridViewHolder) convertView.getTag();
        }
        HomeModule.ResultBean.GoodsListBean course = subCourseList.get(position);
        viewHolder.tv_name.setText(course.getName());
        viewHolder.tv_time.setText(String.valueOf(course.getTotaltime()));
        viewHolder.tv_browser.setText(context.getString(R.string.num_watch, course.getVideonum()));
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoader.getInstance().displayImage(course.getImage(), viewHolder.iv_course, options);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        if (subCourseList != null) {
            return subCourseList.get(position);
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return subCourseList.size() <= 2 ? subCourseList.size() : 2;
    }

    class GridViewHolder {
        private TextView tv_name;
        private ImageView iv_course;
        private TextView tv_time;
        private TextView tv_browser;
    }
}
