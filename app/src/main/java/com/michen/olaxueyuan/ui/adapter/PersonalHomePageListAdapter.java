package com.michen.olaxueyuan.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.protocol.result.UserPostListResult;
import com.michen.olaxueyuan.ui.circle.PostDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 2016/7/11.
 */
public class PersonalHomePageListAdapter extends BaseAdapter {
    private Context mContext;
    List<UserPostListResult.ResultBean.DeployListBean> list = new ArrayList<>();
    private int type = 1;

    public PersonalHomePageListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void updateData(List<UserPostListResult.ResultBean.DeployListBean> list, int type) {
        this.list = list;
        this.type = type;
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
            convertView = View.inflate(mContext, R.layout.personal_homepage_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (type == 1) {
            holder.name.setText(list.get(position).getUserName() + " 发表了回答");
        } else {
            holder.name.setText(list.get(position).getUserName() + " 回复了回答");
        }
        holder.title.setText(list.get(position).getTitle());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, PostDetailActivity.class);//
                intent.putExtra("circleId", list.get(position).getId());
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }


    class ViewHolder {
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.title)
        TextView title;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
