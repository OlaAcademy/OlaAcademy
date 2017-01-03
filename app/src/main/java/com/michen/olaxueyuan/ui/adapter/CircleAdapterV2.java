package com.michen.olaxueyuan.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.app.SEConfig;
import com.michen.olaxueyuan.common.NoScrollGridAdapter;
import com.michen.olaxueyuan.common.RoundRectImageView;
import com.michen.olaxueyuan.common.manager.DateUtils;
import com.michen.olaxueyuan.common.manager.PictureUtils;
import com.michen.olaxueyuan.common.manager.Utils;
import com.michen.olaxueyuan.protocol.event.CircleClickEvent;
import com.michen.olaxueyuan.protocol.result.OLaCircleModule;
import com.michen.olaxueyuan.ui.circle.PostDetailActivity;
import com.michen.olaxueyuan.ui.story.activity.ImagePagerActivity;
import com.snail.photo.util.NoScrollGridView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by mingge on 2016/7/11.
 */
public class CircleAdapterV2 extends BaseAdapter {
    private Context mContext;
    List<OLaCircleModule.ResultBean> list = new ArrayList<>();

    public CircleAdapterV2(Context mContext) {
        this.mContext = mContext;
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.fragment_circle_listview_item_v2, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final int type = list.get(position).getType();
        holder.avatar.setRectAdius(100);
        holder.title.setText(list.get(position).getUserName());
        if (!TextUtils.isEmpty(list.get(position).getUserAvatar())) {
            String avatarUrl = "";
//            if (list.get(position).getUserAvatar().indexOf("jpg")!=-1){
            if (list.get(position).getUserAvatar().contains(".")) {
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
                if (!TextUtils.isEmpty(list.get(position).getUserAvatar())) {
                    String avatarUrl = "";
                    if (list.get(position).getUserAvatar().contains(".")) {
                        avatarUrl = SEConfig.getInstance().getAPIBaseURL() + "/upload/" + list.get(position).getUserAvatar();
                    } else {
                        avatarUrl = SEAPP.PIC_BASE_URL + list.get(position).getUserAvatar();
                    }
                    ArrayList<String> imageUrls = new ArrayList<>();
                    imageUrls.add(avatarUrl);
                    imageBrower(0, imageUrls);
                }
            }
        });
        holder.time.setText(DateUtils.formatTime(list.get(position).getTime()));
        holder.studyName.setText(list.get(position).getContent());
        if (!TextUtils.isEmpty(list.get(position).getLocation())) {
            holder.address.setText("@" + list.get(position).getLocation());
        } else {
            holder.address.setText("");
        }
        switch (type) {
            case 1:
            default:
                holder.gridview.setVisibility(View.GONE);
                holder.commentLayout.setVisibility(View.GONE);
                break;
            case 2:
                holder.commentLayout.setVisibility(View.VISIBLE);
                holder.commentPraise.setText(String.valueOf(list.get(position).getPraiseNumber()));
                if (!TextUtils.isEmpty(list.get(position).getImageGids())) {
                    holder.gridview.setVisibility(View.VISIBLE);
//                    ArrayList<String> imageUrls = getListFromString(list.get(position).getImageGids());
                    ArrayList<String> imageUrls = PictureUtils.getListFromString(list.get(position).getImageGids());
                    final ArrayList<String> imageList = imageUrls;
                    if (imageUrls.size() == 1) {
                        holder.gridview.setNumColumns(1);
                    } else {
                        holder.gridview.setNumColumns(3);
                    }
                    holder.gridview.setAdapter(new NoScrollGridAdapter(mContext, imageUrls));
                    // 点击回帖九宫格，查看大图
                    holder.gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            imageBrower(position, imageList);
                            PictureUtils.viewPictures(mContext,position,imageList);
                        }
                    });
                } else {
                    holder.gridview.setVisibility(View.GONE);
                }
                break;
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                switch (type) {
                    case 1:
//                        intent.setClass(mContext, CourseVideoActivity.class);
//                        intent.putExtra("pid", list.get(position).getCourseId() + "");
                    default:
                        break;
                    case 2:
                        intent.setClass(mContext, PostDetailActivity.class);//
//                        intent.putExtra("OLaCircleModule.ResultBean", list.get(position));
                        intent.putExtra("circleId", list.get(position).getCircleId());
                        break;
                }
                mContext.startActivity(intent);
            }
        });
        holder.commentPraise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * {@link com.michen.olaxueyuan.ui.circle.CircleFragment#onEventMainThread(CircleClickEvent)}}method
                 */
                EventBus.getDefault().post(new CircleClickEvent(1, true, position));
            }
        });
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new CircleClickEvent(2, true, position));
            }
        });

        return convertView;
    }

    private ArrayList<String> getListFromString(String images) {
        ArrayList imageUrlList = new ArrayList();
        String[] imageUrlArray = images.split(",");
        for (String imgUrl : imageUrlArray) {
            imageUrlList.add(SEAPP.PIC_BASE_URL + imgUrl);
        }
        return imageUrlList;
    }

    /**
     * 打开图片查看器
     *
     * @param position
     * @param urls2
     */
    protected void imageBrower(int position, ArrayList<String> urls2) {
        Intent intent = new Intent(mContext, ImagePagerActivity.class);
        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
        mContext.startActivity(intent);
    }

    class ViewHolder {
        @Bind(R.id.avatar)
        RoundRectImageView avatar;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.address)
        TextView address;
        @Bind(R.id.time)
        TextView time;
        @Bind(R.id.study_name)
        TextView studyName;
        @Bind(R.id.gridview)
        NoScrollGridView gridview;
        @Bind(R.id.comment_praise)
        TextView commentPraise;
        @Bind(R.id.comment)
        ImageView comment;
        @Bind(R.id.share)
        ImageView share;
        @Bind(R.id.comment_layout)
        RelativeLayout commentLayout;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
