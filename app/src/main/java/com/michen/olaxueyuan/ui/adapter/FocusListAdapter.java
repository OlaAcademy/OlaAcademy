package com.michen.olaxueyuan.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.app.SEConfig;
import com.michen.olaxueyuan.common.RoundRectImageView;
import com.michen.olaxueyuan.protocol.result.AttendListResult;
import com.michen.olaxueyuan.protocol.result.UserGroupListResult;
import com.michen.olaxueyuan.ui.circle.PersonalHomePageActivityTwo;
import com.michen.olaxueyuan.ui.group.GroupMemberActivity;
import com.michen.olaxueyuan.ui.group.data.JoinGroupEvent;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by mingge on 16/9/7.
 */
public class FocusListAdapter extends BaseAdapter {
    private Context mContext;
    List<AttendListResult.ResultBean> list = new ArrayList<>();

    public FocusListAdapter(Context mContext) {
        this.mContext = mContext;
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
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(list.get(position).getName());
        holder.introduceText.setText(list.get(position).getSign());
        try {
            holder.avatar.setRectAdius(100);
            String avatarUrl = "";
            if (!TextUtils.isEmpty(list.get(position).getAvator())) {
                if (list.get(position).getAvator().contains("http://")) {
                    avatarUrl = list.get(position).getAvator();
                } else if (list.get(position).getAvator().contains(".")) {
                    avatarUrl = SEConfig.getInstance().getAPIBaseURL() + "/upload/" + list.get(position).getAvator();
                } else {
                    avatarUrl = SEAPP.PIC_BASE_URL + list.get(position).getAvator();
                }
            }
            Picasso.with(mContext).load(avatarUrl).config(Bitmap.Config.RGB_565)
                    .placeholder(R.drawable.default_index).error(R.drawable.ic_default_avatar).into(holder.avatar);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
