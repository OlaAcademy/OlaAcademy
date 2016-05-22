package com.snail.olaxueyuan.ui.me.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.snail.olaxueyuan.R;
import com.snail.olaxueyuan.protocol.result.UserCourseCollectResult;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 16/5/22.
 */
public class UserCourseCollectAdapter extends BaseAdapter {
    UserCourseCollectResult module;
    Context context;

    public UserCourseCollectAdapter(UserCourseCollectResult module, Context context) {
        this.module = module;
        this.context = context;
    }

    @Override
    public int getCount() {
        return module.getResult().size();
    }

    @Override
    public Object getItem(int position) {
        return module.getResult().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.fragment_user_course_collect_listview_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(module.getResult().get(position).getVideoName());
        Picasso.with(context).load(module.getResult().get(position).getCoursePic()).config(Bitmap.Config.RGB_565)
                .placeholder(R.drawable.system_wu).into(holder.iconCollect);
        holder.courseTime.setText(module.getResult().get(position).getTotalTime());
        holder.buyCount.setText(module.getResult().get(position).getSubAllNum() + "人购买");
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.icon_collect)
        ImageView iconCollect;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.icon_time)
        ImageView iconTime;
        @Bind(R.id.course_time)
        TextView courseTime;
        @Bind(R.id.buy_count)
        TextView buyCount;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
