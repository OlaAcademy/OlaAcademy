package com.michen.olaxueyuan.ui.me.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.michen.olaxueyuan.protocol.result.UserCourseCollectResult;
import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.ui.course.CourseVideoActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 16/5/22.
 */
public class UserCourseCollectAdapter extends BaseAdapter {
    UserCourseCollectResult module;
    Context context;
    List<UserCourseCollectResult.ResultEntity> list = new ArrayList<>();

    public UserCourseCollectAdapter(Context context) {
        this.context = context;
    }

    public void updateData(UserCourseCollectResult module) {
        this.module = module;
        if (module != null && module.getResult() != null) {
            this.list.clear();
            this.list = module.getResult();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.fragment_user_course_collect_listview_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(list.get(position).getVideoName());
        Picasso.with(context).load(list.get(position).getVideoPic()).config(Bitmap.Config.RGB_565)
                .placeholder(R.drawable.system_wu).into(holder.iconCollect);
        holder.courseTime.setText(list.get(position).getTotalTime());
        holder.buyCount.setText(context.getString(R.string.num_buy, list.get(position).getSubAllNum()));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CourseVideoActivity.class);
                intent.putExtra("pid", String.valueOf(list.get(position).getCourseId()));
                context.startActivity(intent);
            }
        });
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
