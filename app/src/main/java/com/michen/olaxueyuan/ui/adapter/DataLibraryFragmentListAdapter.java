package com.michen.olaxueyuan.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.RoundRectImageView;
import com.michen.olaxueyuan.common.manager.Utils;
import com.michen.olaxueyuan.protocol.result.MaterialListResult;
import com.michen.olaxueyuan.ui.me.activity.PDFViewActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by mingge on 16/9/7.
 */
public class DataLibraryFragmentListAdapter extends BaseAdapter {
    private Context mContext;
    List<MaterialListResult.ResultBean> list = new ArrayList<>();
    private int subjectType;

    public void updateData(List<MaterialListResult.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public DataLibraryFragmentListAdapter(Context mContext, int subjectType) {
        this.mContext = mContext;
        this.subjectType = subjectType;
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
            convertView = View.inflate(mContext, R.layout.datalibrary_fragment_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(list.get(position).getTitle());
        holder.avatarCourse.setRectAdius(100);
        if (!TextUtils.isEmpty(list.get(position).getPic())) {
            Picasso.with(mContext).load(list.get(position).getPic()).placeholder(R.drawable.ic_default_avatar).error(R.drawable.ic_default_avatar).config(Bitmap.Config.RGB_565)
                    .resize(Utils.dip2px(mContext, 35), Utils.dip2px(mContext, 35)).into(holder.avatarCourse);
        }
        holder.name.setText(list.get(position).getTitle());
        holder.paynum.setText("文件大小:" + list.get(position).getSize());
        holder.detail.setText(list.get(position).getProvider());
        holder.price.setText(String.valueOf(list.get(position).getPrice()));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).getStatus() == 1 || list.get(position).getPic().equals("0")) {//1、已兑换
                    Intent intent = new Intent(mContext, PDFViewActivity.class);
                    intent.putExtra("url", list.get(position).getUrl());
                    intent.putExtra("title", list.get(position).getTitle());
                    intent.putExtra("id", list.get(position).getId());
                    intent.putExtra("name", list.get(position).getTitle());
                    mContext.startActivity(intent);
                } else {
                    list.get(position).setCourseType(subjectType);
                    EventBus.getDefault().post(list.get(position));
                }
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
