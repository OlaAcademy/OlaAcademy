package com.snail.olaxueyuan.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.snail.olaxueyuan.R;
import com.snail.olaxueyuan.common.manager.Logger;
import com.snail.olaxueyuan.common.manager.ToastUtil;
import com.snail.olaxueyuan.common.manager.Utils;
import com.snail.olaxueyuan.protocol.result.ExamModule;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HorizontalScrollViewAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    int width;
    int height;
    RelativeLayout.LayoutParams params;
    private List<ExamModule.ResultBean> mDatas;

    public HorizontalScrollViewAdapter(Context context, List<ExamModule.ResultBean> mDatas) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
        width = Utils.getScreenWidth(mContext);
        height = width * 302 / 750;
        params = new RelativeLayout.LayoutParams((int) (width * 0.8), 200);
    }

    public int getCount() {
        return mDatas.size();
    }

    public Object getItem(int position) {
        return mDatas.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.activity_index_gallery_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.examName.setText(mDatas.get(position).getName());
        viewHolder.examNum.setText("已有" + mDatas.get(position).getCid() + "学习");
        viewHolder.startExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showShortToast(mContext, "我是第" + position + "个");
            }
        });
        viewHolder.importantRatingBar.setNumStars(mDatas.get(position).getDegree());
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.exam_name)
        TextView examName;
        @Bind(R.id.exam_num)
        TextView examNum;
        @Bind(R.id.top_bg)
        LinearLayout topBg;
        @Bind(R.id.important_degree)
        TextView importantDegree;
        @Bind(R.id.important_ratingBar)
        RatingBar importantRatingBar;
        @Bind(R.id.master_degree)
        TextView masterDegree;
        @Bind(R.id.master_ratingBar)
        RatingBar masterRatingBar;
        @Bind(R.id.source_from)
        TextView sourceFrom;
        @Bind(R.id.study_degree)
        TextView studyDegree;
        @Bind(R.id.progressBar)
        ProgressBar progressBar;
        @Bind(R.id.question_knowledge_count)
        TextView questionKnowledgeCount;
        @Bind(R.id.start_exam)
        Button startExam;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
