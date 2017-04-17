package com.michen.olaxueyuan.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.app.SEConfig;
import com.michen.olaxueyuan.common.NoScrollThreeGridAdapter;
import com.michen.olaxueyuan.common.RoundRectImageView;
import com.michen.olaxueyuan.common.manager.CommonUtil;
import com.michen.olaxueyuan.common.manager.DateUtils;
import com.michen.olaxueyuan.common.manager.DialogUtils;
import com.michen.olaxueyuan.common.manager.PictureUtils;
import com.michen.olaxueyuan.common.manager.Utils;
import com.michen.olaxueyuan.protocol.result.OLaCircleModule;
import com.michen.olaxueyuan.ui.circle.CircleFragment;
import com.michen.olaxueyuan.ui.circle.PersonalHomePageActivityTwo;
import com.michen.olaxueyuan.ui.circle.PostDetailActivity;
import com.snail.photo.util.NoScrollGridView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 2016/7/11.
 */
public class CircleAdapter extends BaseAdapter {
    private Context mContext;
    private CircleFragment fragment;
    List<OLaCircleModule.ResultBean> list = new ArrayList<>();

    public CircleAdapter(CircleFragment mContext) {
        this.fragment = mContext;
        this.mContext = fragment.getActivity();
    }

    public void updateData(List<OLaCircleModule.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.circle_list_item_v2, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.avatar.setRectAdius(100);
        holder.title.setText(list.get(position).getUserName());
        if (!TextUtils.isEmpty(list.get(position).getUserAvatar())) {
            String avatarUrl = "";
            if (list.get(position).getUserAvatar().contains("http://")) {
                avatarUrl = list.get(position).getUserAvatar();
            } else if (list.get(position).getUserAvatar().contains(".")) {
                avatarUrl = SEConfig.getInstance().getAPIBaseURL() + "/upload/" + list.get(position).getUserAvatar();
            } else {
                avatarUrl = SEAPP.PIC_BASE_URL + list.get(position).getUserAvatar();
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
                /*if (!TextUtils.isEmpty(list.get(position).getUserAvatar())) {
                    String avatarUrl = "";
                    if (list.get(position).getUserAvatar().contains(".")) {
                        avatarUrl = SEConfig.getInstance().getAPIBaseURL() + "/upload/" + list.get(position).getUserAvatar();
                    } else {
                        avatarUrl = SEAPP.PIC_BASE_URL + list.get(position).getUserAvatar();
                    }
                    PictureUtils.viewPictures(mContext, avatarUrl);
                }*/
                mContext.startActivity(new Intent(mContext, PersonalHomePageActivityTwo.class).putExtra("userId", list.get(position).getUserId()));
            }
        });
        holder.name.setText(list.get(position).getUserName());
        holder.time.setText(DateUtils.formatTime(list.get(position).getTime()));
        holder.title.setText(list.get(position).getTitle());
        holder.content.setText(list.get(position).getContent());
        holder.numRead.setText(list.get(position).getReadNumber() + "人浏览");
        holder.numComment.setText(String.valueOf(list.get(position).getCommentNumber()));
        holder.favNum.setText(String.valueOf(list.get(position).getPraiseNumber()));
        holder.postType.setText("类型：" + list.get(position).getSubject());
        if (!TextUtils.isEmpty(list.get(position).getImageGids())) {
            holder.imageGridview.setVisibility(View.VISIBLE);
            ArrayList<String> imageUrls = PictureUtils.getListFromString(list.get(position).getImageGids());
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
        if (list.get(position).getIsPraised() == 0) {
            holder.favImg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.circle_list_fave_normal_icon));
        } else {
            holder.favImg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.circle_list_fave_selected_icon));
        }
        holder.shareView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.share(position);
            }
        });
        holder.favView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.praise(position);
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, PostDetailActivity.class);//
                intent.putExtra("circleId", list.get(position).getCircleId());
                mContext.startActivity(intent);
            }
        });

        holder.content.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DialogUtils.showSelectListDialog(mContext, 0, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0:
                                CommonUtil.copText(mContext, holder.title.getText().toString() + "\n" + holder.content.getText().toString());
                                break;
                        }
                    }
                }, mContext.getResources().getStringArray(R.array.copy_text_select), false);
                return false;
            }
        });

        return convertView;
    }

    class ViewHolder {
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

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
