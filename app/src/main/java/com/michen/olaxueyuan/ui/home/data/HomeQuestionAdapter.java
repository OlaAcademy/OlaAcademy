package com.michen.olaxueyuan.ui.home.data;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.protocol.result.HomeModule;
import com.michen.olaxueyuan.ui.circle.PostDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 2016/8/23.
 */
public class HomeQuestionAdapter extends BaseAdapter {
    private Context mContext;
    List<HomeModule.ResultBean.QuestionListBean> list = new ArrayList<>();

    public HomeQuestionAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void updateData(List<HomeModule.ResultBean.QuestionListBean> list) {
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
            convertView = View.inflate(mContext, R.layout.fragment_home_question_listview_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.indexNum.setText("0" + position);
//        holder.title.setText(list.get(position).getTitle());
        holder.title.setText(list.get(position).getContent());
        holder.time.setText(list.get(position).getTime());
        holder.listenNum.setText(mContext.getString(R.string.num_to_listen_in, list.get(position).getNumber()));
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
        @Bind(R.id.index_num)
        TextView indexNum;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.time)
        TextView time;
        @Bind(R.id.listen_num)
        TextView listenNum;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
