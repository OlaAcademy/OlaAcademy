package com.snail.olaxueyuan.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.snail.olaxueyuan.R;
import com.snail.olaxueyuan.protocol.result.QuestionCourseModule;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 16/4/28.
 */
public class QuestionAdapter extends BaseExpandableListAdapter {
    Context context;
    QuestionCourseModule questionCourseModule;
    List<QuestionCourseModule.ResultEntity.ChildEntity> list = new ArrayList<QuestionCourseModule.ResultEntity.ChildEntity>();

    public QuestionAdapter(Context context, QuestionCourseModule questionCourseModule) {
        this.context = context;
        this.questionCourseModule = questionCourseModule;
        if (questionCourseModule.getResult().getChild() != null) {
            list.clear();
            list.addAll(questionCourseModule.getResult().getChild());
        }
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
            convertView = View.inflate(context, R.layout.fragment_question_parent_item, null);
            holder = new ParentViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ParentViewHolder) convertView.getTag();
        }
        return convertView;
    }

    static class ParentViewHolder {
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

        ParentViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.fragment_question_child_item, null);
            holder = new ChildViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
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

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
