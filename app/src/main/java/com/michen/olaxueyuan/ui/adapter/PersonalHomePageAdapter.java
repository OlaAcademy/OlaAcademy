package com.michen.olaxueyuan.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.app.SEConfig;
import com.michen.olaxueyuan.common.NoScrollThreeGridAdapter;
import com.michen.olaxueyuan.common.RoundRectImageView;
import com.michen.olaxueyuan.common.manager.DateUtils;
import com.michen.olaxueyuan.common.manager.PictureUtils;
import com.michen.olaxueyuan.common.manager.Utils;
import com.michen.olaxueyuan.protocol.result.HomePageDeployPostBean;
import com.michen.olaxueyuan.protocol.result.UserPostListResult;
import com.michen.olaxueyuan.ui.circle.PersonalHomePageActivity;
import com.michen.olaxueyuan.ui.circle.PersonalHomePageActivityTwo;
import com.michen.olaxueyuan.ui.circle.PostDetailActivity;
import com.michen.olaxueyuan.ui.question.QuestionWebActivity;
import com.snail.photo.util.NoScrollGridView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 16/4/28.
 */
public class PersonalHomePageAdapter extends BaseExpandableListAdapter {
    Context mContext;
    List<HomePageDeployPostBean> list = new ArrayList<>();
    private Calendar calendar = Calendar.getInstance();

    public PersonalHomePageAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void updateList(List<HomePageDeployPostBean> listBeen) {
        this.list = listBeen;
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
            convertView = View.inflate(mContext, R.layout.personal_home_page_parent_item, null);
            holder = new ParentViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ParentViewHolder) convertView.getTag();
        }
        if (calendar.get(Calendar.YEAR) == DateUtils.getMonth(list.get(groupPosition).getTime(), 2)) {
            holder.title.setText(String.format("%s月", DateUtils.getMonth(list.get(groupPosition).getTime(), 1)));
        } else {
            holder.title.setText(DateUtils.getMonth(list.get(groupPosition).getTime(), 2) + "年"
                    + String.format("%s月", DateUtils.getMonth(list.get(groupPosition).getTime(), 1)));
        }
        return convertView;
    }

    static class ParentViewHolder {
        @Bind(R.id.title)
        TextView title;

        ParentViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.circle_list_item_v2, null);
            holder = new ChildViewHolder(convertView);
            holder.boldLine.setVisibility(View.GONE);
            holder.fineLine.setVisibility(View.VISIBLE);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        holder.avatar.setRectAdius(100);
        holder.title.setText(list.get(groupPosition).getChild().get(childPosition).getUserName());
        if (!TextUtils.isEmpty(list.get(groupPosition).getChild().get(childPosition).getUserAvatar())) {
            String avatarUrl = "";
            if (list.get(groupPosition).getChild().get(childPosition).getUserAvatar().contains(".")) {
                avatarUrl = SEConfig.getInstance().getAPIBaseURL() + "/upload/" + list.get(groupPosition).getChild().get(childPosition).getUserAvatar();
            } else {
                avatarUrl = SEAPP.PIC_BASE_URL + list.get(groupPosition).getChild().get(childPosition).getUserAvatar();
            }
            Picasso.with(mContext).load(avatarUrl)
                    .placeholder(R.drawable.ic_default_avatar).error(R.drawable.ic_default_avatar).resize(Utils.dip2px(mContext, 50), Utils.dip2px(mContext, 50))
                    .into(holder.avatar);
        } else {
            holder.avatar.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_default_avatar));
        }
        holder.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, PersonalHomePageActivity.class).putExtra("userId", list.get(groupPosition).getChild().get(childPosition).getUserId()));
            }
        });
        holder.name.setText(list.get(groupPosition).getChild().get(childPosition).getUserName());
        holder.time.setText(DateUtils.formatTime(list.get(groupPosition).getChild().get(childPosition).getTime()));
        holder.title.setText(list.get(groupPosition).getChild().get(childPosition).getTitle());
        holder.content.setText(list.get(groupPosition).getChild().get(childPosition).getContent());
        holder.numRead.setText(list.get(groupPosition).getChild().get(childPosition).getReadNumber() + "人浏览");
        holder.numComment.setText(String.valueOf(list.get(groupPosition).getChild().get(childPosition).getCommentNumber()));
        holder.favNum.setText(String.valueOf(list.get(groupPosition).getChild().get(childPosition).getPraiseNumber()));
        holder.postType.setText("类型：" + list.get(groupPosition).getChild().get(childPosition).getType());
        if (!TextUtils.isEmpty(list.get(groupPosition).getChild().get(childPosition).getImageGids())) {
            holder.imageGridview.setVisibility(View.VISIBLE);
            ArrayList<String> imageUrls = PictureUtils.getListFromString(list.get(groupPosition).getChild().get(childPosition).getImageGids());
            final ArrayList<String> imageList = imageUrls;
            if (imageUrls.size() == 1) {
                holder.imageGridview.setNumColumns(1);
            } else {
                holder.imageGridview.setNumColumns(3);
            }
            holder.imageGridview.setAdapter(new NoScrollThreeGridAdapter(mContext, imageUrls, 2));
            // 点击回帖九宫格，查看大图
            holder.imageGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    PictureUtils.viewPictures(mContext, position, imageList);
                }
            });
        } else {
            holder.imageGridview.setVisibility(View.GONE);
        }
//        if (list.get(groupPosition).getChild().get(childPosition).getIsPraised() == 0) {
        holder.favImg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.circle_list_fave_normal_icon));
//        } else {
//            holder.favImg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.circle_list_fave_selected_icon));
//        }
        holder.shareView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext instanceof PersonalHomePageActivityTwo) {
//                    (PersonalHomePageActivityTwo(mContext)).share(position);
                }
            }
        });
        holder.favView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                fragment.praise(position);
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, PostDetailActivity.class);//
                intent.putExtra("circleId", list.get(groupPosition).getChild().get(childPosition).getId());
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    class ChildViewHolder {
        @Bind(R.id.avatar)
        RoundRectImageView avatar;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.time)
        TextView time;
        @Bind(R.id.num_read)
        TextView numRead;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.content)
        TextView content;
        @Bind(R.id.image_gridview)
        NoScrollGridView imageGridview;
        @Bind(R.id.post_type)
        TextView postType;
        @Bind(R.id.bottom_view_line)
        View bottomViewLine;
        @Bind(R.id.share_view)
        LinearLayout shareView;
        @Bind(R.id.num_comment)
        TextView numComment;
        @Bind(R.id.comment_view)
        LinearLayout commentView;
        @Bind(R.id.fav_num)
        TextView favNum;
        @Bind(R.id.fav_img)
        ImageView favImg;
        @Bind(R.id.fav_view)
        LinearLayout favView;
        @Bind(R.id.bold_line)
        View boldLine;
        @Bind(R.id.fine_line)
        View fineLine;


        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
