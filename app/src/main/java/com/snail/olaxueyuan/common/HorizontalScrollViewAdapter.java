package com.snail.olaxueyuan.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.snail.olaxueyuan.R;
import com.snail.olaxueyuan.common.manager.Utils;
import com.snail.olaxueyuan.protocol.result.ExamModule;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HorizontalScrollViewAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    int width;
    int height;
    RelativeLayout.LayoutParams params;
    private List<ExamModule.ResultEntity> mDatas;

    public HorizontalScrollViewAdapter(Context context, List<ExamModule.ResultEntity> mDatas) {
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

    public View getView(int position, View convertView, ViewGroup parent) {
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
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.exam_name)
        TextView examName;
        @Bind(R.id.exam_num)
        TextView examNum;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
