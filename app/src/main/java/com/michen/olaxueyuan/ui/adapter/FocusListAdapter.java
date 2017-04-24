package com.michen.olaxueyuan.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.RoundRectImageView;
import com.michen.olaxueyuan.common.manager.PictureUtils;
import com.michen.olaxueyuan.protocol.manager.SEUserManager;
import com.michen.olaxueyuan.protocol.result.AttendListResult;
import com.michen.olaxueyuan.ui.circle.FocusedListActivity;
import com.michen.olaxueyuan.ui.circle.PersonalHomePageActivityTwo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 16/9/7.
 */
public class FocusListAdapter extends BaseAdapter {
    private Context mContext;
    List<AttendListResult.ResultBean> list = new ArrayList<>();
    private int type;

    public FocusListAdapter(Context mContext, int type) {
        this.mContext = mContext;
        this.type = type;
    }

    public void updateData(List<AttendListResult.ResultBean> list) {
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
            convertView = View.inflate(mContext, R.layout.focus_list_item, null);
            holder = new ViewHolder(convertView);
            holder.avatar.setRectAdius(100);
            if (type == 1) {
                holder.focusFans.setVisibility(View.GONE);
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (list.get(position).getIsAttend() == 1) {
            holder.focusFans.setText("取消关注");
        } else {
            holder.focusFans.setText("关注");
        }
        holder.title.setText(list.get(position).getName());
        holder.introduceText.setText(list.get(position).getSign());
        PictureUtils.loadAvatar(mContext, holder.avatar, list.get(position).getAvator(), 52);
        holder.focusFans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).getIsAttend() == 0) {
                    ((FocusedListActivity) (mContext)).attendUser(SEUserManager.getInstance().getUserId(), String.valueOf(list.get(position).getId()), 1, position);
                } else {
                    ((FocusedListActivity) (mContext)).attendUser(SEUserManager.getInstance().getUserId(), String.valueOf(list.get(position).getId()), 2, position);
                }
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, PersonalHomePageActivityTwo.class).putExtra("userId", list.get(position).getId()));
            }
        });
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.avatar)
        RoundRectImageView avatar;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.introduce_text)
        TextView introduceText;
        @Bind(R.id.focus_fans)
        Button focusFans;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
