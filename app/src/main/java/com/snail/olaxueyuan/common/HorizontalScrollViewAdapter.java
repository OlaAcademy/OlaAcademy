package com.snail.olaxueyuan.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.snail.olaxueyuan.common.manager.ToastUtil;
import com.snail.olaxueyuan.common.manager.Utils;
import com.snail.olaxueyuan.protocol.manager.SEAuthManager;
import com.snail.olaxueyuan.protocol.manager.SEUserManager;
import com.snail.olaxueyuan.protocol.result.ExamModule;
import com.snail.olaxueyuan.protocol.result.UserLoginNoticeModule;
import com.snail.olaxueyuan.ui.me.activity.UserLoginActivity;
import com.snail.olaxueyuan.ui.question.QuestionWebActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class HorizontalScrollViewAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    int width;
    int height;
    RelativeLayout.LayoutParams params;
    private List<ExamModule.ResultBean> mDatas = new ArrayList<>();

    public HorizontalScrollViewAdapter(Context context, List<ExamModule.ResultBean> mDatas) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        if (mDatas != null) {
            this.mDatas = mDatas;
        }
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
        final int isfree = mDatas.get(position).getIsfree();
        if (isfree==0){
            viewHolder.lockRL.setVisibility(View.VISIBLE);
            viewHolder.lockRL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buyVip();
                }
            });
        }else {
            viewHolder.lockRL.setVisibility(View.GONE);
        }
        viewHolder.examName.setText(mDatas.get(position).getName());
        viewHolder.examNum.setText("已有" + mDatas.get(position).getLearnNum() + "学习");
        viewHolder.startExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, QuestionWebActivity.class);
                intent.putExtra("objectId", mDatas.get(position).getId());
                intent.putExtra("type", 2);
                mContext.startActivity(intent);
            }
        });
        viewHolder.sourceFrom.setText(mDatas.get(position).getSource());
        viewHolder.importantRatingBar.setMax(5);
        viewHolder.importantRatingBar.setRating(mDatas.get(position).getDegree());
        viewHolder.masterRatingBar.setRating((float)(mDatas.get(position).getProgress()*3.0/100));
        viewHolder.progressBar.setProgress(mDatas.get(position).getProgress());
        viewHolder.progressTV.setText(mDatas.get(position).getProgress()+"%");
        return convertView;
    }

    private void buyVip(){
        if (SEAuthManager.getInstance().isAuthenticated()){
            new AlertDialog.Builder(mContext)
                    .setTitle("友情提示")
                    .setMessage("购买会员后即可拥有")
                    .setPositiveButton("去购买", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .show();
        }else{
            mContext.startActivity(new Intent(mContext, UserLoginActivity.class));
        }
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
        @Bind(R.id.progressTV)
        TextView progressTV;
        @Bind(R.id.start_exam)
        Button startExam;
        @Bind(R.id.lockRL)
        RelativeLayout lockRL;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
