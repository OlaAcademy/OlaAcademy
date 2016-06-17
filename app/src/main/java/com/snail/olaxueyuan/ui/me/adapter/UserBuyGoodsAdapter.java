package com.snail.olaxueyuan.ui.me.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.snail.olaxueyuan.R;
import com.snail.olaxueyuan.protocol.result.UserBuyGoodsResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 16/5/22.
 */
public class UserBuyGoodsAdapter extends BaseAdapter {
    UserBuyGoodsResult module;
    Context context;
    List<UserBuyGoodsResult.ResultEntity> list = new ArrayList<>();

    public UserBuyGoodsAdapter(Context context) {
        this.context = context;
    }

    public void updateData(UserBuyGoodsResult module) {
        this.module = module;
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
            convertView = View.inflate(context, R.layout.fragment_user_buy_goods_listview_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(list.get(position).getName());
        Picasso.with(context).load(list.get(position).getUrl()).config(Bitmap.Config.RGB_565)
                .placeholder(R.drawable.system_wu).error(R.drawable.system_wu).into(holder.iconCollect);
        holder.courseTime.setText(list.get(position).getTotaltime() + "");
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo 跳转到播放视频界面
            }
        });
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.icon_collect)
        ImageView iconCollect;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.icon_time)
        ImageView iconTime;
        @Bind(R.id.course_time)
        TextView courseTime;
        @Bind(R.id.buy_count)
        TextView buyCount;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
