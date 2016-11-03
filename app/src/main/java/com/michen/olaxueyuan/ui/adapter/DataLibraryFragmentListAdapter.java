package com.michen.olaxueyuan.ui.adapter;

import android.content.Context;
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
import com.michen.olaxueyuan.protocol.result.MaterialListResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 16/9/7.
 */
public class DataLibraryFragmentListAdapter extends BaseAdapter {
    private Context mContext;
    List<MaterialListResult.ResultBean> list = new ArrayList<>();

    public DataLibraryFragmentListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void updateData(List<MaterialListResult.ResultBean> list) {
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
            convertView = View.inflate(mContext, R.layout.datalibrary_fragment_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(list.get(position).getTitle());
        holder.memberCount.setText(list.get(position).getProvider() + "人");
        holder.introduceText.setText(list.get(position).getTime());
        if (list.get(position).getStatus() == 1) {
            holder.joinGroup.setText("已兑换");
        } else {
            holder.joinGroup.setText("未兑换");
        }
        try {
            holder.avatar.setRectAdius(100);
            String avatarUrl = "";
            if (!TextUtils.isEmpty(list.get(position).getPic())) {
                if (list.get(position).getPic().contains(".")) {
                    avatarUrl = SEConfig.getInstance().getAPIBaseURL() + "/upload/" + list.get(position).getPic();
                } else {
                    avatarUrl = SEAPP.PIC_BASE_URL + list.get(position).getPic();
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
            }
        });
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.avatar)
        RoundRectImageView avatar;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.member_count)
        TextView memberCount;
        @Bind(R.id.introduce_text)
        TextView introduceText;
        @Bind(R.id.join_group)
        Button joinGroup;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
