package com.michen.olaxueyuan.ui.question;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.manager.QuestionCourseManager;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.result.MessageListResult;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.michen.olaxueyuan.ui.adapter.MessageListAdapter;
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

public class MessageActivity extends SEBaseActivity implements PullToRefreshBase.OnRefreshListener2 {

    @Bind(R.id.listview)
    PullToRefreshListView listview;
    Context mContext;
    private String messageId = "";
    private static final String pageSize = "20";
    private List<MessageListResult.ResultEntity> list = new ArrayList<>();
    private MessageListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        mContext = this;
        initView();
        fetchData();
    }

    private void initView() {
        listview.setMode(PullToRefreshBase.Mode.BOTH);
        listview.setOnRefreshListener(this);
        adapter = new MessageListAdapter(mContext);
        listview.setAdapter(adapter);
    }

    private void fetchData() {
        SVProgressHUD.showInView(mContext, getString(R.string.request_running), true);
        String userId = "";
        if (SEAuthManager.getInstance().isAuthenticated()) {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        }
        QuestionCourseManager.getInstance().getMessageList(userId, messageId, pageSize, new Callback<MessageListResult>() {
            @Override
            public void success(MessageListResult messageListResult, Response response) {
                SVProgressHUD.dismiss(mContext);
                listview.onRefreshComplete();
                if (messageListResult.getApicode() != 10000) {
                    SVProgressHUD.showInViewWithoutIndicator(mContext, messageListResult.getMessage(), 2.0f);
                } else {
                    if (TextUtils.isEmpty(messageId)) {
                        list.clear();
                    }
                    list.addAll(messageListResult.getResult());
                    adapter.updateData(list);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                SVProgressHUD.dismiss(mContext);
                listview.onRefreshComplete();
                ToastUtil.showToastShort(mContext, R.string.data_request_fail);
            }
        });
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        messageId = "";
        fetchData();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        if (list.size() > 0) {
            messageId = list.get(list.size() - 1).getId() + "";
            fetchData();
        } else {
            listview.onRefreshComplete();
        }
    }
}
