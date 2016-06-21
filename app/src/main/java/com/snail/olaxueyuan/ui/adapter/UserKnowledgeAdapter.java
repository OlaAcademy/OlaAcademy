package com.snail.olaxueyuan.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.snail.olaxueyuan.R;
import com.snail.olaxueyuan.protocol.result.UserKnowledgeResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 16/4/28.
 */
public class UserKnowledgeAdapter extends BaseExpandableListAdapter {
    Context context;
    UserKnowledgeResult module;
    private List<UserKnowledgeResult.ResultEntity> list = new ArrayList<>();

    public UserKnowledgeAdapter(Context context) {
        this.context = context;
    }

    public void updateList(UserKnowledgeResult module) {
        this.module = module;
        if (module != null && module.getResult() != null) {
            this.list.clear();
            this.list = module.getResult();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getChild().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getChild();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ParentViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.fragment_user_knowledge_parent_item, null);
            holder = new ParentViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ParentViewHolder) convertView.getTag();
        }
        holder.courseName.setText(list.get(groupPosition).getName());
        return convertView;
    }

    static class ParentViewHolder {
        @Bind(R.id.course_name)
        TextView courseName;

        ParentViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.fragment_user_knowledge_child_item, null);
            holder = new ChildViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        holder.questionName.setText(list.get(groupPosition).getChild().get(childPosition).getName());
        holder.questionKnowledgeCount.setText(list.get(groupPosition).getChild().get(childPosition).getSubAllNum() + "%");
        holder.questionKnowledgeAllCount.setText(context.getString(R.string.num_knowledge, list.get(groupPosition).getChild().get(childPosition).getSubAllNum()));
        try {
            int subAllNum = list.get(groupPosition).getChild().get(childPosition).getSubAllNum();
//            holder.progressBar.setBackgroundColor(context.getResources().getColor(R.color.light_title_blue));
//            holder.progressBar.setIndeterminateDrawable(context.getResources().getDrawable(R.drawable.light_title_blue));
            if (subAllNum == 0) {
                holder.progressBar.setProgress(100);
            } else {
                holder.progressBar.setProgress(50);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    class ChildViewHolder {
        @Bind(R.id.question_add_icon)
        ImageView questionAddIcon;
        @Bind(R.id.question_name)
        TextView questionName;
        @Bind(R.id.question_knowledge_all_count)
        TextView questionKnowledgeAllCount;
        @Bind(R.id.progressBar)
        ProgressBar progressBar;
        @Bind(R.id.question_knowledge_count)
        TextView questionKnowledgeCount;
        @Bind(R.id.line_bottom)
        View lineBottom;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
