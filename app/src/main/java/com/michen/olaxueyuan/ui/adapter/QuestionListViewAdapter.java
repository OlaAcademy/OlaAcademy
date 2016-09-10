package com.michen.olaxueyuan.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.result.ExamModule;
import com.michen.olaxueyuan.ui.me.activity.BuyVipActivity;
import com.michen.olaxueyuan.ui.me.activity.UserLoginActivity;
import com.michen.olaxueyuan.ui.question.QuestionWebActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 2016/8/12.
 */
public class QuestionListViewAdapter extends BaseAdapter {
    Context mContext;
    private List<ExamModule.ResultBean> mDatas = new ArrayList<>();
    private int courseType; // 1 数学 2 英语 3 逻辑 4 写作

    public QuestionListViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void updateData(List<ExamModule.ResultBean> mDatas, int courseType) {
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
            convertView = View.inflate(mContext, R.layout.fragment_question_listview_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.questionName.setText(mDatas.get(position).getName());
        holder.questionKnowledgeCount.setText("难度"+ mDatas.get(position).getDegree());
        try {
            int subAllNum = mDatas.get(position).getCoverpoint();
            int subNum = mDatas.get(position).getProgress();
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
        if (mDatas.get(position).getIsfree() == 0) {
            holder.questionIV.setBackgroundResource(R.drawable.ic_question_normal);
        }else {
            holder.questionIV.setBackgroundResource(R.drawable.ic_question_blue);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDatas.get(position).getIsfree() == 0) {
                    buyVip();
                } else {
                    Intent intent = new Intent(mContext, QuestionWebActivity.class);
                    intent.putExtra("objectId", mDatas.get(position).getId());
                    intent.putExtra("type", 2);
                    intent.putExtra("courseType", courseType);
                    mContext.startActivity(intent);
                }
            }
        });
        return convertView;
    }

    private void buyVip() {
        if (SEAuthManager.getInstance().isAuthenticated()) {
            new AlertDialog.Builder(mContext)
                    .setTitle("友情提示")
                    .setMessage("购买会员后即可拥有")
                    .setPositiveButton("去购买", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            mContext.startActivity(new Intent(mContext, BuyVipActivity.class));
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .show();
        } else {
            mContext.startActivity(new Intent(mContext, UserLoginActivity.class));
        }
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
