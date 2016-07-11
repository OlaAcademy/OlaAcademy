package com.michen.olaxueyuan.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.RoundRectImageView;
import com.michen.olaxueyuan.protocol.result.OLaCircleModule;
import com.michen.olaxueyuan.ui.course.CourseVideoActivity;
import com.snail.photo.util.NoScrollGridView;

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
            convertView = View.inflate(mContext, R.layout.fragment_circle_listview_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.avatar.setRectAdius(100);
        holder.title.setText(list.get(position).getUserName());
        //缺一个头像
//        if (!TextUtils.isEmpty(list.get(position).getUserAvatar())) {
//            Picasso.with(mContext).load(SEConfig.getInstance().getAPIBaseURL() + "/upload/" + list.get(position).getUserAvatar()).placeholder(R.drawable.ic_default_avatar)
//                    .error(R.drawable.ic_default_avatar).resize(Utils.dip2px(mContext, 50), Utils.dip2px(mContext, 50)).into(holder.avatar);
//        } else {
        holder.avatar.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_default_avatar));
//        }
        holder.time.setText(mContext.getString(R.string.study_record, list.get(position).getTime()));
        holder.studyName.setText(list.get(position).getContent());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CourseVideoActivity.class);
                intent.putExtra("pid", list.get(position).getCourseId());
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.avatar)
        RoundRectImageView avatar;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.address)
        TextView address;
        @Bind(R.id.time)
        TextView time;
        @Bind(R.id.study_name)
        TextView studyName;
        @Bind(R.id.gridview)
        NoScrollGridView gridview;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
