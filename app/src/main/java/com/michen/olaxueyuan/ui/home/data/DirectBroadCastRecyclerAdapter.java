package com.michen.olaxueyuan.ui.home.data;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.protocol.result.HomeModule;
import com.michen.olaxueyuan.ui.BaseRecyclerAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 2016/8/24.
 */
public class DirectBroadCastRecyclerAdapter extends BaseRecyclerAdapter<HomeModule.ResultBean.GoodsListBean, DirectBroadCastRecyclerAdapter.DirectBroadCastItemHolder> {


    public DirectBroadCastRecyclerAdapter(Context context, List<HomeModule.ResultBean.GoodsListBean> list) {
        super(context, list);
    }

    public class DirectBroadCastItemHolder extends RecyclerView.ViewHolder {
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

        public DirectBroadCastItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public DirectBroadCastItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.direct_broadcast_list_item, parent, false);
        DirectBroadCastItemHolder holder = new DirectBroadCastItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(DirectBroadCastItemHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) holder.ivCourse.getLayoutParams();
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        linearParams.width = dm.widthPixels / 2 - 45;
        if (position % 2 == 0)
            linearParams.setMargins(30, 0, 15, 0);
        else
            linearParams.setMargins(15, 0, 30, 0);
        holder.ivCourse.setLayoutParams(linearParams);

        HomeModule.ResultBean.GoodsListBean course = list.get(position);
        holder.tvName.setText(course.getName());
        holder.tvTime.setText(String.valueOf(course.getTotaltime()));
        holder.tvBrowser.setText(context.getString(R.string.num_watch, course.getVideonum()));
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoader.getInstance().displayImage(course.getImage(), holder.ivCourse, options);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
