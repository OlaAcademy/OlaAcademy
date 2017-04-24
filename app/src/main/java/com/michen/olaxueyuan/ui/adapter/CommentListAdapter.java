package com.michen.olaxueyuan.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.RoundRectImageView;
import com.michen.olaxueyuan.common.manager.DateUtils;
import com.michen.olaxueyuan.common.manager.PictureUtils;
import com.michen.olaxueyuan.protocol.result.CircleMessageListResult;
import com.michen.olaxueyuan.ui.circle.PostDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 16/7/19.
 */
public class CommentListAdapter extends BaseAdapter {
    private List<CircleMessageListResult.ResultBean> list = new ArrayList<>();
    Context mContext;

    public CommentListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void updateData(List<CircleMessageListResult.ResultBean> list) {
        this.list = list;
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
            convertView = View.inflate(mContext, R.layout.praise_list_listview_item, null);
            holder = new ViewHolder(convertView);
            holder.avatar.setRectAdius(100);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(list.get(position).getUserName());
        holder.study.setText("评论了回答: " + list.get(position).getTitle());
        holder.time.setText(DateUtils.formatTime(list.get(position).getTime()));
        PictureUtils.loadAvatar(mContext, holder.avatar, list.get(position).getUserAvatar(), 52);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(position).setIsRead(1);
                notifyDataSetChanged();
                mContext.startActivity(new Intent(mContext, PostDetailActivity.class).putExtra("circleId", list.get(position).getPostId()));
            }
        });
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.avatar)
        RoundRectImageView avatar;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.time)
        TextView time;
        @Bind(R.id.study)
        TextView study;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
