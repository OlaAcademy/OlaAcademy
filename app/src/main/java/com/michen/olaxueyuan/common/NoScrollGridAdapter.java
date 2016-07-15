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

    public NoScrollGridAdapter(Context ctx, ArrayList<String> urls) {
        this.ctx = ctx;
        this.imageUrls = urls;
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
        int itemWidth = (int) (screenWidth - (Utils.dip2px(ctx, 60 + 15))) / 3;
        int itemHeight = (int) (screenWidth - (Utils.dip2px(ctx, 60 + 15))) / 3;
//        int itemWidth = (int) (screenWidth - (paddingLeft + horizontalSpacing) * 2 )) / 3;
        // 下面根据比例计算item的高度，此处只是h使用itemWidth
        RelativeLayout.LayoutParams param;
        if (imageUrls.size() == 1) {
            itemWidth = itemWidth * 3 + Utils.dip2px(ctx, 5);
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
