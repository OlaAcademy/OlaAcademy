package com.michen.olaxueyuan.ui.group.data;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.common.RoundRectImageView;
import com.michen.olaxueyuan.common.manager.Utils;
import com.michen.olaxueyuan.protocol.result.TeacherGroupListResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 2016/10/31.
 */

public class TGroupListAdapter extends BaseAdapter {
    Context context;
    List<TeacherGroupListResult.ResultBean> list = new ArrayList<>();

    public TGroupListAdapter(Context context) {
        this.context = context;
    }

    public void updateData(TeacherGroupListResult module) {
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
            convertView = View.inflate(context, R.layout.tgroup_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(list.get(position).getName());
        holder.time.setText(list.get(position).getTime());
        holder.avatar.setRectAdius(100);
        if (!TextUtils.isEmpty(list.get(position).getAvatar())) {
            Picasso.with(context).load(SEAPP.PIC_BASE_URL+list.get(position).getAvatar())
                    .placeholder(R.drawable.ic_default_avatar).error(R.drawable.ic_default_avatar)
                    .resize(Utils.dip2px(context, 52), Utils.dip2px(context, 52))
                    .into(holder.avatar);
        }
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.time)
        TextView time;
        @Bind(R.id.avatar)
        RoundRectImageView avatar;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}