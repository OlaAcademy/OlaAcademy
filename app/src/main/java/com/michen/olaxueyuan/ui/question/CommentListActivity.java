package com.michen.olaxueyuan.ui.question;

import android.os.Bundle;
import android.text.TextUtils;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.manager.QuestionCourseManager;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.result.CircleMessageListResult;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.michen.olaxueyuan.ui.adapter.CommentListAdapter;
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

public class CommentListActivity extends SEBaseActivity implements PullToRefreshBase.OnRefreshListener2 {

    @Bind(R.id.listview)
    PullToRefreshListView listview;
    private static final String pageSize = "20";
    private String commentId;
    private String type = "2";//发帖的固定传2
    private CommentListAdapter adapter;
    private List<CircleMessageListResult.ResultBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_refresh_listview);
        ButterKnife.bind(this);
        initView();
        fetchData();
    }

    private void fetchData() {
        SEAPP.showCatDialog(this);
        String userId = "";
        if (SEAuthManager.getInstance().isAuthenticated()) {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        }
        QuestionCourseManager.getInstance().getCircleMessageList(userId, commentId, pageSize, type, new Callback<CircleMessageListResult>() {
            @Override
            public void success(CircleMessageListResult circleMessageListResult, Response response) {
                if (!CommentListActivity.this.isFinishing()) {
                    SEAPP.dismissAllowingStateLoss();
                    listview.onRefreshComplete();
                    if (circleMessageListResult.getApicode() != 10000) {
                        SVProgressHUD.showInViewWithoutIndicator(mContext, circleMessageListResult.getMessage(), 2.0f);
                    } else {
                        if (circleMessageListResult.getResult().size() == 0) {
                            ToastUtil.showToastShort(mContext, R.string.to_end);
                            return;
                        }
                        if (TextUtils.isEmpty(commentId)) {
                            list.clear();
                        }
                        list.addAll(circleMessageListResult.getResult());
                        adapter.updateData(list);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (!CommentListActivity.this.isFinishing()) {
                    SEAPP.dismissAllowingStateLoss();
                    listview.onRefreshComplete();
                    ToastUtil.showToastShort(mContext, R.string.data_request_fail);
                }
            }
        });
    }

    private void initView() {
        setTitleText("消息中心");
        listview.setMode(PullToRefreshBase.Mode.BOTH);
        listview.setOnRefreshListener(this);
        adapter = new CommentListAdapter(this);
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

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        commentId = "";
        fetchData();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        if (list.size() > 0) {
            commentId = list.get(list.size() - 1).getCommentId() + "";
            fetchData();
        }
    }
}
