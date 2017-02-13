package com.michen.olaxueyuan.ui.course;

import android.os.Bundle;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.snail.pulltorefresh.PullToRefreshListView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CourseVideoSubListActivity extends SEBaseActivity {

    @Bind(R.id.listview)
    PullToRefreshListView listview;
    private CourseSubListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_refresh_listview);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        setTitleText("课程");
        adapter=new CourseSubListAdapter(mContext);
        listview.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
