package com.michen.olaxueyuan.ui.course;

import android.os.Bundle;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.manager.SECourseManager;
import com.michen.olaxueyuan.protocol.result.VideoCourseSubResult;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshListView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_refresh_listview);
        ButterKnife.bind(this);
        initView();
        getVideoCourseSubList();
    }

    private void initView() {
        pid = getIntent().getStringExtra("pid");
        setTitleText("课程");
        listview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listview.setOnRefreshListener(this);
        adapter = new CourseSubListAdapter(mContext);
        listview.setAdapter(adapter);
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
