package com.snail.olaxueyuan.ui.me.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.snail.olaxueyuan.R;
import com.snail.olaxueyuan.common.RoundRectImageView;
import com.snail.olaxueyuan.common.manager.Utils;
import com.snail.olaxueyuan.protocol.result.SystemCourseResult;
import com.snail.olaxueyuan.ui.course.SystemVideoActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 16/5/22.
 */
public class SystemCourseAdapter extends BaseAdapter {
    SystemCourseResult module;
    Context context;
    List<SystemCourseResult.ResultEntity> list = new ArrayList<>();

    public SystemCourseAdapter(Context context) {
        this.context = context;
    }

    public void updateData(SystemCourseResult module) {
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
            convertView = View.inflate(context, R.layout.commodity_listview_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.avatarCourse.setRectAdius(100);
        if (!TextUtils.isEmpty(list.get(position).getImage())) {
            Picasso.with(context).load(list.get(position).getImage()).placeholder(R.drawable.ic_default_avatar).error(R.drawable.ic_default_avatar).config(Bitmap.Config.RGB_565)
                    .resize(Utils.dip2px(context, 35), Utils.dip2px(context, 35)).into(holder.avatarCourse);
        }
        holder.name.setText(list.get(position).getName());
        holder.paynum.setText(context.getString(R.string.pay_num, list.get(position).getPaynum()));
//        holder.detail.setText(list.get(position).getDetail());
        holder.detail.setText(list.get(position).getOrg());
        holder.price.setText(String.valueOf(list.get(position).getPrice()));
//        holder.authorCourseNum.setText(String.valueOf(list.get(position).getAttentionnum()));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SystemVideoActivity.class);
                intent.putExtra("pid", String.valueOf(list.get(position).getId()));
                intent.putExtra("ResultEntity",list.get(position));
//                Logger.e("courseId==" + list.get(position).getId());
                context.startActivity(intent);
//                ToastUtil.showShortToast(context, "我是第" + position + "个");
            }
        });
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.paynum)
        TextView paynum;
        @Bind(R.id.avatar_course)
        RoundRectImageView avatarCourse;
        @Bind(R.id.detail)
        TextView detail;
        @Bind(R.id.price)
        TextView price;
        @Bind(R.id.author_course_num)
        TextView authorCourseNum;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
