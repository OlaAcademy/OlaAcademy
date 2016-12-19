package com.michen.olaxueyuan.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.CommonConstant;
import com.michen.olaxueyuan.common.manager.Logger;
import com.michen.olaxueyuan.ui.circle.ReviewMultimediaActivity;
import com.snail.photo.util.Bimp;

/**
 * Created by mingge on 2016/12/19.
 */

public class PostDetailVideoGridAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;

    public PostDetailVideoGridAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void update() {
        notifyDataSetChanged();
    }

    public int getCount() {
        if (Bimp.tempSelectBitmap.size() == 9) {
            return 9;
        }
        return Bimp.tempSelectBitmap.size();
    }

    public Object getItem(int arg0) {
        return Bimp.tempSelectBitmap.get(arg0);
    }

    public long getItemId(int arg0) {
        return arg0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_published_grida, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.item_grida_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Logger.e("Bimp.tempSelectBitmap.size()=" + Bimp.tempSelectBitmap.size());
        if (position == Bimp.tempSelectBitmap.size()) {
//            holder.image.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_addpic_unfocused));
            if (position == 9) {
                holder.image.setVisibility(View.GONE);
            }
        } else {
            holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reviewIntent = new Intent(context, ReviewMultimediaActivity.class);
                reviewIntent.putExtra(CommonConstant.EXTRA_INDEX_REVIEW, position);
                context.startActivity(reviewIntent);
            }
        });
        return convertView;
    }

    public class ViewHolder {
        public ImageView image;
    }
}
