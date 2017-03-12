package com.michen.olaxueyuan.ui.home.data;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.RoundRectImageView;
import com.michen.olaxueyuan.protocol.result.HomeModule;
import com.michen.olaxueyuan.ui.BaseRecyclerAdapter;
import com.michen.olaxueyuan.ui.course.CourseVideoActivity;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 2016/8/24.
 */
public class AskQuestionRecyclerAdapter extends BaseRecyclerAdapter<HomeModule.ResultBean.UserListBean, AskQuestionRecyclerAdapter.CourseDatabaseItemHolder> {

    public AskQuestionRecyclerAdapter(Context context) {
        super(context);
    }

    public class CourseDatabaseItemHolder extends RecyclerView.ViewHolder {
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

        public CourseDatabaseItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public CourseDatabaseItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.fragment_home_ask_question_list_item, parent, false);
        CourseDatabaseItemHolder holder = new CourseDatabaseItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CourseDatabaseItemHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) holder.rootView.getLayoutParams();
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        linearParams.width = dm.widthPixels / 2 - 45;
        linearParams.height = linearParams.width * 390 / 310;
        if (position % 2 == 0)
            linearParams.setMargins(30, 0, 15, 0);
        else
            linearParams.setMargins(15, 0, 30, 0);
        holder.rootView.setLayoutParams(linearParams);

        final HomeModule.ResultBean.UserListBean course = list.get(position);
        holder.name.setText(course.getName());
        holder.sign.setText(course.getSign());

        try {
            Picasso.with(context).load(course.getAvator()).config(Bitmap.Config.RGB_565)
                    .placeholder(R.drawable.default_index).error(R.drawable.default_index).into(holder.icAvatar);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CourseVideoActivity.class);
                intent.putExtra("pid", String.valueOf(course.getId()));
                context.startActivity(intent);
            }
        });
    }
}
