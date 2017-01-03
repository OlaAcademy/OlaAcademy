package com.michen.olaxueyuan.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.app.SEConfig;
import com.michen.olaxueyuan.common.RoundRectImageView;
import com.michen.olaxueyuan.common.manager.DateUtils;
import com.michen.olaxueyuan.common.manager.Logger;
import com.michen.olaxueyuan.common.manager.PictureUtils;
import com.michen.olaxueyuan.common.manager.Utils;
import com.michen.olaxueyuan.protocol.result.OLaCircleModule;
import com.michen.olaxueyuan.ui.circle.PostDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 2016/7/11.
 */
public class CircleAdapter extends BaseAdapter {
    private Context mContext;
    List<OLaCircleModule.ResultBean> list = new ArrayList<>();

    public CircleAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void updateData(List<OLaCircleModule.ResultBean> list) {
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
            convertView = View.inflate(mContext, R.layout.circle_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.avatar.setRectAdius(100);
        holder.title.setText(list.get(position).getUserName());
        if (!TextUtils.isEmpty(list.get(position).getUserAvatar())) {
            String avatarUrl = "";
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
        holder.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(list.get(position).getUserAvatar())) {
                    String avatarUrl = "";
                    if (list.get(position).getUserAvatar().contains(".")) {
                        avatarUrl = SEConfig.getInstance().getAPIBaseURL() + "/upload/" + list.get(position).getUserAvatar();
                    } else {
                        avatarUrl = SEAPP.PIC_BASE_URL + list.get(position).getUserAvatar();
                    }
                    PictureUtils.viewPictures(mContext, avatarUrl);
                }
            }
        });
        holder.name.setText(list.get(position).getUserName());
        holder.time.setText(DateUtils.formatTime(list.get(position).getTime()));
        holder.title.setText(list.get(position).getTitle());
        holder.content.setText(list.get(position).getContent());
        holder.numRead.setText(list.get(position).getReadNumber() + "人浏览");
        holder.numComment.setText(list.get(position).getCommentNumber() + "人评论");
        if (!TextUtils.isEmpty(list.get(position).getImageGids())) {
            holder.image.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams layoutParams = holder.image.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = layoutParams.width * 300 / 750;
            holder.image.setLayoutParams(layoutParams);
            ArrayList<String> imageUrls = PictureUtils.getListFromString(list.get(position).getImageGids());
            Logger.e("imageUrls.get(0)==" + imageUrls.get(0));
            Picasso.with(mContext).load(imageUrls.get(0)).placeholder(R.drawable.system_wu).error(R.drawable.system_wu).into(holder.image);
        } else {
            holder.image.setVisibility(View.GONE);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, PostDetailActivity.class);//
                intent.putExtra("circleId", list.get(position).getCircleId());
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.avatar)
        RoundRectImageView avatar;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.time)
        TextView time;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.image)
        ImageView image;
        @Bind(R.id.content)
        TextView content;
        @Bind(R.id.num_read)
        TextView numRead;
        @Bind(R.id.num_comment)
        TextView numComment;
        @Bind(R.id.comment_layout)
        RelativeLayout commentLayout;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
