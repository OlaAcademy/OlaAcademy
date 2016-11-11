package com.michen.olaxueyuan.ui.course.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.RoundRectImageView;
import com.michen.olaxueyuan.protocol.result.SystemCourseResult;
import com.michen.olaxueyuan.ui.me.activity.BaseFragment;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 2016/11/11.
 */

public class SystemDetailFragment extends BaseFragment {
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.leanstage)
    TextView leanstage;
    @Bind(R.id.avatar_course)
    RoundRectImageView avatarCourse;
    @Bind(R.id.org)
    TextView org;
    @Bind(R.id.suitto)
    TextView suitto;
    @Bind(R.id.totaltime)
    TextView totaltime;
    @Bind(R.id.detail)
    TextView detail;
    private String courseId;
    SystemCourseResult.ResultEntity resultEntity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.system_detail_fragment, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        courseId = getArguments().getString("courseId");
        resultEntity = (SystemCourseResult.ResultEntity) getArguments().getSerializable("resultEntity");
        if (resultEntity == null) {
            return;
        }
        avatarCourse.setRectAdius(200);
        name.setText(resultEntity.getName());
        leanstage.setText(resultEntity.getLeanstage());
        Picasso.with(mContext).load(resultEntity.getImage()).into(avatarCourse);
        org.setText(resultEntity.getOrg());
        suitto.setText(resultEntity.getSuitto());
        totaltime.setText(String.valueOf(resultEntity.getTotaltime()));
        detail.setText(resultEntity.getDetail());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
