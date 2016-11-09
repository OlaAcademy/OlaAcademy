package com.michen.olaxueyuan.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.app.SEConfig;
import com.michen.olaxueyuan.common.RoundRectImageView;
import com.michen.olaxueyuan.common.manager.PictureUtil;
import com.michen.olaxueyuan.common.manager.Utils;
import com.michen.olaxueyuan.protocol.result.GroupMemberResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 2016/10/31.
 */

public class GroupMemberListAdapter extends BaseAdapter {
    Context context;
    List<GroupMemberResult.ResultBean> list = new ArrayList<>();

    public GroupMemberListAdapter(Context context) {
        this.context = context;
    }

    public void updateData(List<GroupMemberResult.ResultBean> list) {
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
            convertView = View.inflate(context, R.layout.group_member_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(list.get(position).getName());
        holder.avatar.setRectAdius(100);
        if (!TextUtils.isEmpty(list.get(position).getAvator())) {
            String avatarUrl = "";
            if (list.get(position).getAvator().contains(".")) {
                avatarUrl = SEConfig.getInstance().getAPIBaseURL() + "/upload/" + list.get(position).getAvator();
            } else {
                avatarUrl = SEAPP.PIC_BASE_URL + list.get(position).getAvator();
            }
            Picasso.with(context).load(avatarUrl)
                    .placeholder(R.drawable.ic_default_avatar).error(R.drawable.ic_default_avatar)
                    .resize(Utils.dip2px(context, 52), Utils.dip2px(context, 52))
                    .into(holder.avatar);
        }
        holder.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureUtil.viewPictures(context, list.get(position).getAvator());
            }
        });
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.finished_text)
        TextView finishedText;
        @Bind(R.id.avatar)
        RoundRectImageView avatar;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}