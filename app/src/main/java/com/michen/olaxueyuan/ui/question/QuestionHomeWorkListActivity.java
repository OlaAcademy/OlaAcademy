package com.michen.olaxueyuan.ui.question;

import android.content.Context;
import android.os.Bundle;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.Logger;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.manager.TeacherHomeManager;
import com.michen.olaxueyuan.protocol.result.HomeworkListResult;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.michen.olaxueyuan.ui.adapter.QuestionHomeWorkListAdapter;
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

public class QuestionHomeWorkListActivity extends SEBaseActivity implements PullToRefreshBase.OnRefreshListener2 {

    @Bind(R.id.listview)
    PullToRefreshListView listview;
    private Context context;
    private static final String PAGE_SIZE = "20";//每次加载20条
    private String homeworkId;
    protected List<HomeworkListResult.ResultBean> mList = new ArrayList<>();
    private QuestionHomeWorkListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_home_work_list);
        ButterKnife.bind(this);
        context = this;
        initView();
        fetchData();
    }

    private void initView() {
        setTitleText(getString(R.string.my_homework));
        listview.setMode(PullToRefreshBase.Mode.BOTH);
        listview.setOnRefreshListener(this);
        listview.getRefreshableView().setDivider(null);
        adapter = new QuestionHomeWorkListAdapter(context);
        listview.setAdapter(adapter);
    }

    private void fetchData() {
        SVProgressHUD.showInView(context, getString(R.string.request_running), true);
        String userId = null;
        try {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        } catch (Exception e) {
            e.printStackTrace();
            userId = "381";
        }
        TeacherHomeManager.getInstance().getHomeworkList(userId, "1", homeworkId, PAGE_SIZE, new Callback<HomeworkListResult>() {
            @Override
            public void success(HomeworkListResult homeworkListResult, Response response) {
                Logger.json(homeworkListResult);
                if (context != null) {
                    listview.onRefreshComplete();
                    SVProgressHUD.dismiss(context);
                    if (homeworkListResult.getApicode() != 10000) {
                        SVProgressHUD.showInViewWithoutIndicator(context, homeworkListResult.getMessage(), 2.0f);
                    } else {
                        List<HomeworkListResult.ResultBean> list = homeworkListResult.getResult();
                        mList.addAll(list);
                        if (list.size() > 0) {
                            homeworkId = mList.get(mList.size() - 1).getId() + "";
                        }
                        adapter.updateData(mList);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (context != null) {
                    SVProgressHUD.dismiss(context);
                    listview.onRefreshComplete();
                    ToastUtil.showToastShort(context, R.string.data_request_fail);
                }
            }
        });
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        homeworkId = "";
        mList.clear();
        fetchData();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        fetchData();
    }
}
