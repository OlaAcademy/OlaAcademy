package com.michen.olaxueyuan.ui.me.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.manager.SEUserManager;
import com.michen.olaxueyuan.protocol.result.UserKnowledgeResult;
import com.michen.olaxueyuan.protocol.result.UserLoginNoticeModule;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.michen.olaxueyuan.ui.adapter.UserKnowledgeAdapter;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshExpandableListView;
import com.snail.svprogresshud.SVProgressHUD;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 错题本
 *
 * @deprecated {@link WrongTopicSetActivity}
 */
public class WrongTopicActivity extends SEBaseActivity implements PullToRefreshBase.OnRefreshListener {
    @Bind(R.id.expandableListView)
    PullToRefreshExpandableListView expandableListViews;
    ExpandableListView expandableListView;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.login_view)
    LinearLayout loginView;
    private Context mContext;

    private UserKnowledgeResult module;
    private UserKnowledgeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong_topic);
        mContext = this;
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        setTitleText(getString(R.string.wrong_topic_text));
        expandableListViews.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        expandableListViews.setOnRefreshListener(this);
        expandableListView = expandableListViews.getRefreshableView();
        expandableListView.setDivider(null);
        expandableListView.setGroupIndicator(null);
        adapter = new UserKnowledgeAdapter(mContext);
        expandableListView.setAdapter(adapter);
        isLoginView();
    }

    private void fetchData() {
        SVProgressHUD.showInView(mContext, getString(R.string.request_running), true);
        SEUserManager.getInstance().getStatisticsList("1", SEAuthManager.getInstance().getAccessUser().getId(), new Callback<UserKnowledgeResult>() {
            @Override
            public void success(UserKnowledgeResult userKnowledgeResult, Response response) {
                if (mContext != null && !WrongTopicActivity.this.isFinishing()) {
                    SVProgressHUD.dismiss(mContext);
                    expandableListViews.onRefreshComplete();
//                Logger.json(userKnowledgeResult);
                    if (userKnowledgeResult.getApicode() != 10000) {
                        ToastUtil.showToastShort(mContext, userKnowledgeResult.getMessage());
                    } else {
                        module = userKnowledgeResult;
                        handler.sendEmptyMessage(0);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (mContext != null && !WrongTopicActivity.this.isFinishing()) {
                    expandableListViews.onRefreshComplete();
                    SVProgressHUD.dismiss(mContext);
                    ToastUtil.showToastShort(mContext, R.string.data_request_fail);
                }
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    initAdapter();
                    break;
            }
        }
    };

    private void isLoginView() {
        if (SEAuthManager.getInstance().isAuthenticated()) {
            fetchData();
            loginView.setVisibility(View.GONE);
        } else {
            loginView.setVisibility(View.VISIBLE);
        }
    }

    private void initAdapter() {
        adapter.updateList(module);
        for (int i = 0; i < module.getResult().size(); i++) {
            expandableListView.expandGroup(i);
        }
    }

    @OnClick({R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                startActivity(new Intent(mContext, UserLoginActivity.class));
                break;
        }
    }

    public void onEventMainThread(UserLoginNoticeModule module) {
        isLoginView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        fetchData();
    }

}
