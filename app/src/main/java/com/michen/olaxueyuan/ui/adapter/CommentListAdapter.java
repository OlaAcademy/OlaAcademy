package com.michen.olaxueyuan.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.app.SEConfig;
import com.michen.olaxueyuan.common.RoundRectImageView;
import com.michen.olaxueyuan.common.manager.DateUtils;
import com.michen.olaxueyuan.common.manager.Utils;
import com.michen.olaxueyuan.protocol.result.CircleMessageListResult;
import com.michen.olaxueyuan.ui.circle.PostDetailActivity;
import com.squareup.picasso.Picasso;

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
        holder.avatar.setRectAdius(100);
        holder.title.setText(list.get(position).getTitle());

        if (!TextUtils.isEmpty(list.get(position).getUserAvatar())) {
            String avatarUrl = "";
//            if (list.get(position).getUserAvatar().indexOf("jpg")!=-1){
            if (list.get(position).getUserAvatar().contains(".")) {
                avatarUrl = SEConfig.getInstance().getAPIBaseURL() + "/upload/" + list.get(position).getUserAvatar();
            } else {
                avatarUrl = SEAPP.PIC_BASE_URL + list.get(position).getUserAvatar();
            }
            Picasso.with(mContext).load(avatarUrl)
                    .placeholder(R.drawable.ic_default_avatar).error(R.drawable.ic_default_avatar).resize(Utils.dip2px(mContext, 50), Utils.dip2px(mContext, 50))
                    .into(holder.avatar);
        } else {
            holder.avatar.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_default_avatar));
        }
        holder.time.setText(DateUtils.formatTime(list.get(position).getTime()));
        holder.content.setText(list.get(position).getContent());
        holder.praiseNum.setText(list.get(position).getPraiseNumber() + "");
        holder.replyNum.setText("回复   " + list.get(position).getPraiseNumber());
        if (list.get(position).getIsRead() == 1) {
            holder.bulletIV.setVisibility(View.GONE);
        } else {
            holder.bulletIV.setVisibility(View.VISIBLE);
        }
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
        @Bind(R.id.avatar)
        RoundRectImageView avatar;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.content)
        TextView content;
        @Bind(R.id.praise_icon)
        ImageView praiseIcon;
        @Bind(R.id.praise_num)
        TextView praiseNum;
        @Bind(R.id.reply_num)
        TextView replyNum;
        @Bind(R.id.bulletIV)
        ImageView bulletIV;
        @Bind(R.id.time)
        TextView time;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
