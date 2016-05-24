package com.snail.olaxueyuan.ui.index;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.snail.olaxueyuan.R;
import com.snail.olaxueyuan.common.RecyclingPagerAdapter;
import com.snail.olaxueyuan.protocol.model.MCBanner;
import com.snail.olaxueyuan.ui.course.CourseDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by tianxiaopeng on 15-1-7.
 */
public class ImagePagerAdapter extends RecyclingPagerAdapter {
    private Context context;
    private ArrayList<MCBanner> bannerList;

    private int size;

    public ImagePagerAdapter(Context context, ArrayList<MCBanner> videoList) {
        this.context = context;
        this.bannerList = videoList;
        this.size = videoList.size();
    }

    @Override
    public int getCount() {
        return bannerList.size();
    }

    @Override
    public View getView(final int position, View view, ViewGroup container) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = holder.imageView = new ImageView(context);
            view.setTag(holder);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MCBanner videoInfo = bannerList.get(position);
                    Intent intent = new Intent(context,CourseDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("videoInfo", videoInfo);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        } else {
            holder = (ViewHolder) view.getTag();
        }
        int width = context.getResources().getDisplayMetrics().widthPixels;
        Picasso.with(context)
                .load(bannerList.get(position).address)
                .placeholder(R.drawable.default_index)
                .error(R.drawable.default_index)
                .resize(width, width*320/750)
                .into(holder.imageView);
        return view;
    }

    private class ViewHolder {
        ImageView imageView;
    }

    private void handlePagerItemClicked(int index) {
        switch (index) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
        }
    }
}
