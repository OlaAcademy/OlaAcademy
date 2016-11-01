package com.michen.olaxueyuan.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.protocol.result.TeacherGroupListResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 2016/8/12.
 */
public class TGetGroupListViewAdapter extends BaseAdapter {
    Context mContext;
    private List<TeacherGroupListResult.ResultBean> mDatas = new ArrayList<>();

    public TGetGroupListViewAdapter(Context mContext, GetSelectedGroupId getSelectedGroupId) {
        this.mContext = mContext;
        this.getSelectedGroupId = getSelectedGroupId;
    }

    public void updateData(List<TeacherGroupListResult.ResultBean> mDatas) {
        this.mDatas = mDatas;
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
        final ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.activity_group_listview_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.groupName.setText(mDatas.get(position).getName());
        if (mDatas.get(position).isSelected()) {
            holder.groupRadio.setSelected(true);
        } else {
            holder.groupRadio.setSelected(false);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDatas.get(position).isSelected()) {
                    mDatas.get(position).setSelected(false);
                    getSelectedGroupId.getGroupId(mDatas.get(position).getId(), false);
                } else {
                    mDatas.get(position).setSelected(true);
                    getSelectedGroupId.getGroupId(mDatas.get(position).getId(), true);
                }
                notifyDataSetChanged();
            }
        });
        holder.groupRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDatas.get(position).isSelected()) {
                    mDatas.get(position).setSelected(false);
                    getSelectedGroupId.getGroupId(mDatas.get(position).getId(), false);
                } else {
                    mDatas.get(position).setSelected(true);
                    getSelectedGroupId.getGroupId(mDatas.get(position).getId(), true);
                }
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.group_name)
        TextView groupName;
        @Bind(R.id.group_radio)
        ImageButton groupRadio;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private GetSelectedGroupId getSelectedGroupId;


    public interface GetSelectedGroupId {
        /**
         * 选择发布到群
         *
         * @param id  群id
         * @param add 是否添加，true添加，false减少
         */
        void getGroupId(String id, boolean add);
    }
}
