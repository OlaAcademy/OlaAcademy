package com.michen.olaxueyuan.ui.question;

import android.os.Bundle;
import android.text.TextUtils;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.manager.QuestionCourseManager;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.result.PraiseListResult;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.michen.olaxueyuan.ui.adapter.PraiseListAdapter;
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

public class PraiseListActivity extends SEBaseActivity implements PullToRefreshBase.OnRefreshListener2 {

    @Bind(R.id.listview)
    PullToRefreshListView listview;
    private String praiseId;
    private static final String pageSize = "20";
    private List<PraiseListResult.ResultBean> list = new ArrayList<>();
    private PraiseListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_refresh_listview);
        ButterKnife.bind(this);
        initView();
        fetchData();
    }

    private void initView() {
        setTitleText("赞了");
        listview.setMode(PullToRefreshBase.Mode.BOTH);
        listview.setOnRefreshListener(this);
        adapter = new PraiseListAdapter(this);
        listview.setAdapter(adapter);
    }

    private void fetchData() {
        SEAPP.showCatDialog(this);
        String userId = "";
        if (SEAuthManager.getInstance().isAuthenticated()) {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        }
        QuestionCourseManager.getInstance().getPraiseList(userId, praiseId, pageSize, new Callback<PraiseListResult>() {
            @Override
            public void success(PraiseListResult praiseListResult, Response response) {
                if (!PraiseListActivity.this.isFinishing()) {
                    SEAPP.dismissAllowingStateLoss();
                    listview.onRefreshComplete();
                    if (praiseListResult.getApicode() != 10000) {
                        SVProgressHUD.showInViewWithoutIndicator(mContext, praiseListResult.getMessage(), 2.0f);
                    } else {
                        if (praiseListResult.getResult().size() == 0) {
                            ToastUtil.showToastShort(mContext, R.string.to_end);
                            return;
                        }
                        if (TextUtils.isEmpty(praiseId)) {
                            list.clear();
                        }
                        list.addAll(praiseListResult.getResult());
                        adapter.updateData(list);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (!PraiseListActivity.this.isFinishing()) {
                    SEAPP.dismissAllowingStateLoss();
                    listview.onRefreshComplete();
                    ToastUtil.showToastShort(mContext, R.string.data_request_fail);
                }
            }
        });
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        praiseId = "";
        fetchData();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        if (list.size() > 0) {
            praiseId = list.get(list.size() - 1).getPraiseId() + "";
            fetchData();
        }
    }
}
