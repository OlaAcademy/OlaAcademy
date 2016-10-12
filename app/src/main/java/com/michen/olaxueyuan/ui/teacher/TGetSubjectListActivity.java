package com.michen.olaxueyuan.ui.teacher;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.snail.pulltorefresh.PullToRefreshExpandableListView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 老是发布作业选择教材
 */
public class TGetSubjectListActivity extends SEBaseActivity {

    @Bind(R.id.subject_name)
    TextView subjectName;
    @Bind(R.id.subject_layout)
    RelativeLayout subjectLayout;
    @Bind(R.id.expandableListView)
    PullToRefreshExpandableListView expandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tget_subject_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitleText("选择教材");
    }

    @OnClick(R.id.subject_layout)
    public void onClick() {
    }
}
