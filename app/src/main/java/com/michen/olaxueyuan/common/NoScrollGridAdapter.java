package com.michen.olaxueyuan.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NoScrollGridAdapter extends BaseAdapter {

    /**
     * 上下文
     */
    private Context ctx;
    /**
     * 图片Url集合
     */
    private ArrayList<String> imageUrls;
    private int type = 1;

    public NoScrollGridAdapter(Context ctx, ArrayList<String> urls) {
        this.ctx = ctx;
        this.imageUrls = urls;
    }

    public NoScrollGridAdapter(Context ctx, ArrayList<String> urls, int type) {
        this.ctx = ctx;
        this.imageUrls = urls;
        this.type = type;
    }

    @Override
    public int getCount() {
        return imageUrls == null ? 0 : imageUrls.size();
    }

    @Override
    public Object getItem(int position) {
        return imageUrls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(ctx).inflate(R.layout.item_gridview, null);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_image);
        // 根据屏幕宽度，以3列为标准计算每列宽度
        int screenWidth = ctx.getResources().getDisplayMetrics().widthPixels;
        int itemWidth = 0;
        int itemHeight = 0;
        RelativeLayout.LayoutParams param;
        switch (type) {
            case 1:
            default:
                itemWidth = (screenWidth - (Utils.dip2px(ctx, 60 + 15))) / 3;
                itemHeight = (screenWidth - (Utils.dip2px(ctx, 60 + 15))) / 3;
                if (imageUrls.size() == 1) {
                    itemWidth = itemWidth * 3 + Utils.dip2px(ctx, 5);
                }
                break;
            case 2:
                itemWidth = (screenWidth - (Utils.dip2px(ctx, 40))) / 3;
                itemHeight = (screenWidth - (Utils.dip2px(ctx, 40))) / 3;
                if (imageUrls.size() == 1) {
                    itemWidth = screenWidth - Utils.dip2px(ctx, 20);
                }
                break;
            case 3:
                itemHeight = (screenWidth - (Utils.dip2px(ctx, 40))) / 3;
                itemWidth = screenWidth - Utils.dip2px(ctx, 20);
                break;
        }
        param = new RelativeLayout.LayoutParams(itemWidth, itemHeight);
        imageView.setLayoutParams(param);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        if (imageUrls.get(position) != null && imageUrls.get(position) != null && !imageUrls.get(position).equals("")) {
            Picasso.with(ctx).load(imageUrls.get(position)).placeholder(R.drawable.system_wu).config(Bitmap.Config.RGB_565)
                    .error(R.drawable.system_wu).into(imageView);
        }
        return convertView;
    }
}
