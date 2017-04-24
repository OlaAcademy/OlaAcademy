package com.michen.olaxueyuan.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.RoundRectImageView;
import com.michen.olaxueyuan.common.manager.PictureUtils;
import com.michen.olaxueyuan.protocol.event.MessageReadEvent;
import com.michen.olaxueyuan.protocol.result.MessageListResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

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
    public View getView(final int position, View convertView, ViewGroup parent) {
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
        PictureUtils.loadAvatar(mContext, holder.avatar, list.get(position).getImageUrl(), 50);
        if (list.get(position).getTime().length() > 10) {
            holder.time.setText(list.get(position).getTime().substring(0, 10));
        }
        if (list.get(position).getStatus() == 1) {
            holder.bulletIV.setVisibility(View.GONE);
        } else {
            holder.bulletIV.setVisibility(View.VISIBLE);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * {@link com.michen.olaxueyuan.ui.question.SystemMessageFragment#onEventMainThread(MessageReadEvent)}
                 */
                EventBus.getDefault().post(new MessageReadEvent(position, false, list.get(position).getId() + "", list.get(position).getType()));
            }
        });
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
        @Bind(R.id.bulletIV)
        ImageView bulletIV;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
