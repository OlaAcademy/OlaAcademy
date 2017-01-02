package com.michen.olaxueyuan.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
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
            convertView = View.inflate(mContext, R.layout.comment_list_listview_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.userName.setText(list.get(position).getUserName() + " 评论了回答：");
        holder.title.setText(list.get(position).getTitle());
//        holder.content.setText(list.get(position).getContent());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(position).setIsRead(1);
                mContext.startActivity(new Intent(mContext, PostDetailActivity.class).putExtra("circleId", list.get(position).getPostId()));
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.user_name)
        TextView userName;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.content)
        TextView content;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
