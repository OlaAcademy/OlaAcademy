package com.michen.olaxueyuan.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.protocol.result.WrongListResult;
import com.michen.olaxueyuan.ui.question.QuestionWebActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 2016/8/12.
 */
public class WrongTopicListViewAdapter extends BaseAdapter {
    Context mContext;
    private List<WrongListResult.ResultBean> mDatas = new ArrayList<>();
    private int courseType; // 1 数学 2 英语 3 逻辑 4 写作

    public WrongTopicListViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void updateData(List<WrongListResult.ResultBean> mDatas, int courseType) {
        this.mDatas = mDatas;
        this.courseType = courseType;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.wrong_topic_listview_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.questionName.setText(mDatas.get(position).getName());
        holder.questionKnowledgeCount.setText("做错" + mDatas.get(position).getWrongNum() + "道题，共" + mDatas.get(position).getSubAllNum() + "道题");
        try {
            int subAllNum = mDatas.get(position).getSubAllNum();
            int subNum = mDatas.get(position).getWrongNum();
            if (subAllNum == 0) {
                holder.progressBar.setProgress(100);
            } else {
                if (subNum == subAllNum) {
                    holder.progressBar.setProgress(100);
                } else {
                    holder.progressBar.setProgress((subNum * 100) / subAllNum);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.questionIV.setBackgroundResource(R.drawable.ic_question_blue);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, QuestionWebActivity.class);
                intent.putExtra("objectId", mDatas.get(position).getId());
                intent.putExtra("type", 2);
                intent.putExtra("courseType", courseType);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.question_name)
        TextView questionName;
        @Bind(R.id.progressBar)
        ProgressBar progressBar;
        @Bind(R.id.question_knowledge_count)
        TextView questionKnowledgeCount;
        @Bind(R.id.questionIV)
        ImageView questionIV;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}