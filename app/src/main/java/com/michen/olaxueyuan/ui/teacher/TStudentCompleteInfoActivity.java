package com.michen.olaxueyuan.ui.teacher;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.manager.TeacherHomeManager;
import com.michen.olaxueyuan.protocol.result.HomeworkStatisticsResult;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.michen.olaxueyuan.ui.adapter.TStudentCompleteInfoListAdapter;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshListView;
import com.snail.svprogresshud.SVProgressHUD;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TStudentCompleteInfoActivity extends SEBaseActivity implements PullToRefreshBase.OnRefreshListener2 {
    @Bind(R.id.correctness_info)
    TextView correctnessInfo;
    @Bind(R.id.complete_info)
    TextView completeInfo;
    @Bind(R.id.listview)
    PullToRefreshListView listview;

    private String homeworkId;
    private String groupId;
    private int pageIndex = 1;//默认从1开始
    public static int pageSize = 20;//一次请求20条数据
    private Context context;
    private TStudentCompleteInfoListAdapter adapter;
    private List<HomeworkStatisticsResult.ResultBean.StatisticsListBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tstudent_complete_info);
        ButterKnife.bind(this);
        context = this;
        initView();
    }

    private void initView() {
        setTitleText(getString(R.string.student_complete_info));
        homeworkId = getIntent().getStringExtra("homeworkId");
        groupId = getIntent().getStringExtra("groupId");
        listview.setOnRefreshListener(this);
        listview.setMode(PullToRefreshBase.Mode.BOTH);
        listview.getRefreshableView().setDivider(null);
        adapter = new TStudentCompleteInfoListAdapter(this);
        listview.setAdapter(adapter);
        list = new ArrayList<>();
        getHomeworkStatistics();
    }

    private void getHomeworkStatistics() {
        SVProgressHUD.showInView(context, getString(R.string.request_running), true);
        TeacherHomeManager.getInstance().getHomeworkStatistics(groupId, homeworkId, pageIndex, pageSize, new Callback<HomeworkStatisticsResult>() {
            @Override
            public void success(HomeworkStatisticsResult homeworkStatisticsResult, Response response) {
                if (context != null && !TStudentCompleteInfoActivity.this.isFinishing()) {
                    listview.onRefreshComplete();
                    SVProgressHUD.dismiss(context);
                    if (homeworkStatisticsResult.getApicode() != 10000) {
                        ToastUtil.showToastShort(context, homeworkStatisticsResult.getMessage());
                    } else {
                        list.addAll(homeworkStatisticsResult.getResult().getStatisticsList());
                        adapter.updateData(list);
                        correctnessInfo.setText(String.valueOf(homeworkStatisticsResult.getResult().getCorrectness()));
                        completeInfo.setText(homeworkStatisticsResult.getResult().getFinishedCount() + "人完成,"
                                + homeworkStatisticsResult.getResult().getUnfinishedCount() + "未完成");
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (context != null && !TStudentCompleteInfoActivity.this.isFinishing()) {
                    SVProgressHUD.dismiss(context);
                    ToastUtil.showToastShort(context, R.string.request_group_fail);
                    listview.onRefreshComplete();
                }
            }
        });
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        pageIndex = 1;
        list.clear();
        getHomeworkStatistics();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        pageIndex++;
        getHomeworkStatistics();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (context != null && !TStudentCompleteInfoActivity.this.isFinishing()) {
            SVProgressHUD.dismiss(context);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
