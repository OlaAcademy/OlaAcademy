package com.michen.olaxueyuan.ui.course;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.common.RoundRectImageView;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.manager.SECourseManager;
import com.michen.olaxueyuan.protocol.result.VideoCourseSubResult;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshListView;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CourseVideoSubListActivity extends SEBaseActivity implements PullToRefreshBase.OnRefreshListener {

    @Bind(R.id.listview)
    PullToRefreshListView listview;
    private CourseSubListAdapter adapter;
    private String pid;
    private View headerView;
    private TextView titleText, timeText, authorText;
    private RoundRectImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_refresh_listview);
        ButterKnife.bind(this);
        initView();
        initHeader();
        getVideoCourseSubList();
    }

    private void initView() {
        pid = getIntent().getStringExtra("pid");
        setTitleText("课程介绍");
        listview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listview.setOnRefreshListener(this);
        adapter = new CourseSubListAdapter(mContext);
        listview.setAdapter(adapter);
    }

    private void initHeader() {
        headerView = View.inflate(this, R.layout.activity_course_video_header_layout, null);
        titleText = (TextView) headerView.findViewById(R.id.title);
        timeText = (TextView) headerView.findViewById(R.id.time);
        authorText = (TextView) headerView.findViewById(R.id.author);
        avatar = (RoundRectImageView) headerView.findViewById(R.id.avatar);
        avatar.setRectAdius(100);
        listview.getRefreshableView().addHeaderView(headerView);
    }

    private void getVideoCourseSubList() {
        SEAPP.showCatDialog(this);
        SECourseManager.getInstance().getVideoCourseSubList(pid, new Callback<VideoCourseSubResult>() {
            @Override
            public void success(VideoCourseSubResult videoCourseSubResult, Response response) {
                if (mContext != null && !CourseVideoSubListActivity.this.isFinishing()) {
                    SEAPP.dismissAllowingStateLoss();
                    listview.onRefreshComplete();
                    if (videoCourseSubResult.getApicode() != 10000) {
                        ToastUtil.showToastLong(mContext, videoCourseSubResult.getMessage());
                    } else {
                        setHeaderData(videoCourseSubResult.getResult());
                        adapter.updateData(videoCourseSubResult.getResult().getSubList());
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (mContext != null && !CourseVideoSubListActivity.this.isFinishing()) {
                    listview.onRefreshComplete();
                    SEAPP.dismissAllowingStateLoss();
                }
            }
        });
    }

    private void setHeaderData(VideoCourseSubResult.ResultBean result) {
        titleText.setText(result.getName());
        timeText.setText(result.getSubAllNum() + "章节");
        authorText.setText(result.getTeacherName());
        if (!TextUtils.isEmpty(result.getTeacherAvator())) {
            Picasso.with(mContext).load(result.getTeacherAvator())
                    .placeholder(R.drawable.ic_default_avatar).error(R.drawable.ic_default_avatar)
                    .resize(96, 96)
                    .into(avatar);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        getVideoCourseSubList();
    }
}
