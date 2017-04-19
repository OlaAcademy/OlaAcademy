package com.michen.olaxueyuan.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.michen.olaxueyuan.ui.question.QuestionWebActivity;
import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.protocol.result.QuestionCourseModule;

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

    public QuestionAdapter(Context context) {
        this.context = context;
    }

    public void updateList(QuestionCourseModule questionCourseModule) {
        this.questionCourseModule = questionCourseModule;
        if (questionCourseModule.getResult().getChild() != null) {
            list.clear();
            list.addAll(questionCourseModule.getResult().getChild());
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
            convertView = View.inflate(context, R.layout.fragment_question_parent_item, null);
            holder = new ParentViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ParentViewHolder) convertView.getTag();
        }
        holder.questionName.setText(list.get(groupPosition).getName());
        holder.questionKnowledgeAllCount.setText(context.getString(R.string.num_knowledge, String.valueOf(list.get(groupPosition).getChild().size())));
        holder.questionKnowledgeCount.setText(list.get(groupPosition).getSubNum() + "/" + list.get(groupPosition).getSubAllNum());
        if (list.get(groupPosition).isExpanded()) {
            holder.questionAddIcon.setSelected(true);
        } else {
            holder.questionAddIcon.setSelected(false);
        }
        try {
            int subAllNum = list.get(groupPosition).getSubAllNum();
            int subNum = list.get(groupPosition).getSubNum();
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
       /* if (list.get(groupPosition).isExpanded()) {
//            Logger.e("isExpanded==" + list.get(groupPosition).isExpanded());
            holder.line.setVisibility(View.VISIBLE);
        } else {
//            Logger.e("isExpanded==" + list.get(groupPosition).isExpanded());
            holder.line.setVisibility(View.GONE);
        }*/
//        Picasso.with(context).load(list.get(groupPosition).getAddress()).resize(17, 17).config(Bitmap.Config.RGB_565)
//                .placeholder(R.drawable.ic_launcher_imageview).into(holder.questionAddIcon);
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
        @Bind(R.id.line)
        View line;

        ParentViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.fragment_question_child_item, null);
            holder = new ChildViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        holder.questionName.setText(list.get(groupPosition).getChild().get(childPosition).getName());
        holder.questionKnowledgeAllCount.setText("预测" + list.get(groupPosition).getChild().get(childPosition).getPlaycount() + "分");
        holder.questionKnowledgeCount.setText(list.get(groupPosition).getChild().get(childPosition).getSubNum() + "/" + list.get(groupPosition).getChild().get(childPosition).getSubAllNum());
        try {
            int subAllNum = list.get(groupPosition).getChild().get(childPosition).getSubAllNum();
            int subNum = list.get(groupPosition).getChild().get(childPosition).getSubNum();
            if (subAllNum == 0) {
                holder.progressBar.setProgress(100);
            } else {
                if (subNum == subAllNum) {
                    holder.progressBar.setProgress(100);
                } else {
                    holder.progressBar.setProgress((subNum * 100) / subAllNum);
                }
            }
           /* if (childPosition == list.get(groupPosition).getChild().size() - 1) {
                holder.lineBottom.setVisibility(View.GONE);
            } else {
                holder.lineBottom.setVisibility(View.VISIBLE);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        final int courseId = list.get(groupPosition).getChild().get(childPosition).getId();
        final String outerURL = list.get(groupPosition).getChild().get(childPosition).getAddress();//外部购买链接
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, QuestionWebActivity.class);
                intent.putExtra("objectId", courseId);
                intent.putExtra("outerURL", outerURL);
                intent.putExtra("type", 1);
                context.startActivity(intent);
            }
        });
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
