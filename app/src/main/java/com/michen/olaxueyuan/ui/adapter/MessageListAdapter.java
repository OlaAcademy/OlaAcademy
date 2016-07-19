package com.michen.olaxueyuan.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.RoundRectImageView;
import com.michen.olaxueyuan.common.manager.Utils;
import com.michen.olaxueyuan.protocol.result.MessageListResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 16/7/19.
 */
public class MessageListAdapter extends BaseAdapter {
    private List<MessageListResult.ResultEntity> list = new ArrayList<>();
    Context mContext;

    public MessageListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void updateData(List<MessageListResult.ResultEntity> list) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.message_list_listview_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.avatar.setRectAdius(100);
        holder.title.setText(list.get(position).getTitle());
//        if (!TextUtils.isEmpty(list.get(position).getImageUrl())) {
//            String avatarUrl = "";
//            if (list.get(position).getImageUrl().indexOf("jpg") != -1) {
//                avatarUrl = SEConfig.getInstance().getAPIBaseURL() + "/upload/" + list.get(position).getImageUrl();
//            } else {
//                avatarUrl = SEAPP.PIC_BASE_URL + list.get(position).getImageUrl();
//            }
        String avatarUrl = list.get(position).getImageUrl();
        Picasso.with(mContext).load(avatarUrl)
                .placeholder(R.drawable.ic_default_avatar).error(R.drawable.ic_default_avatar).resize(Utils.dip2px(mContext, 50), Utils.dip2px(mContext, 50))
                .into(holder.avatar);
//        } else {
//            holder.avatar.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_default_avatar));
//        }
        holder.time.setText(list.get(position).getTime());
        holder.content.setText(list.get(position).getContent());
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.avatar)
        RoundRectImageView avatar;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.time)
        TextView time;
        @Bind(R.id.content)
        TextView content;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
