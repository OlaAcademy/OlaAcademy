package com.michen.olaxueyuan.ui.home.data;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.app.SEConfig;
import com.michen.olaxueyuan.common.RoundRectImageView;
import com.michen.olaxueyuan.protocol.result.HomeModule;
import com.michen.olaxueyuan.ui.BaseRecyclerAdapter;
import com.michen.olaxueyuan.ui.circle.PersonalHomePageActivityTwo;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 2016/8/24.
 */
public class AskQuestionRecyclerAdapter extends BaseRecyclerAdapter<HomeModule.ResultBean.UserListBean, AskQuestionRecyclerAdapter.AskQuestionItemHolder> {

    public AskQuestionRecyclerAdapter(Context context) {
        super(context);
    }

    public class AskQuestionItemHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ic_avatar)
        RoundRectImageView icAvatar;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.sign)
        TextView sign;
        @Bind(R.id.to_complete)
        TextView toComplete;
        @Bind(R.id.root_view)
        LinearLayout rootView;

        public AskQuestionItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            icAvatar.setRectAdius(100);
        }
    }

    @Override
    public AskQuestionItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.fragment_home_ask_question_list_item, parent, false);
        AskQuestionItemHolder holder = new AskQuestionItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AskQuestionItemHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) holder.rootView.getLayoutParams();
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        linearParams.width = dm.widthPixels / 2 - 120;
//        linearParams.height = linearParams.width * 390 / 310;
        if (position % 2 == 0)
            linearParams.setMargins(30, 0, 15, 0);
        else
            linearParams.setMargins(15, 0, 30, 0);
        holder.rootView.setLayoutParams(linearParams);
        final HomeModule.ResultBean.UserListBean course = list.get(position);
        holder.name.setText(course.getIntroduction());
        holder.sign.setText(course.getName());

        try {
            if (!TextUtils.isEmpty(course.getAvator())) {
                String avatarUrl = "";
                if (course.getAvator().contains("http://")) {
                    avatarUrl = course.getAvator();
                } else if (course.getAvator().contains(".")) {
                    avatarUrl = SEConfig.getInstance().getAPIBaseURL() + "/upload/" + course.getAvator();
                } else {
                    avatarUrl = SEAPP.PIC_BASE_URL + course.getAvator();
                }
                Picasso.with(context).load(avatarUrl).config(Bitmap.Config.RGB_565)
                        .placeholder(R.drawable.default_index).error(R.drawable.default_index).into(holder.icAvatar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                context.startActivity(new Intent(context, PersonalHomePageActivity.class)
                context.startActivity(new Intent(context, PersonalHomePageActivityTwo.class)
                        .putExtra("userId", Integer.parseInt(list.get(position).getId())));
            }
        });
    }
}
