package com.snail.olaxueyuan.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.snail.olaxueyuan.R;
import com.snail.olaxueyuan.common.manager.ToastUtil;
import com.snail.olaxueyuan.protocol.manager.SEAuthManager;
import com.snail.olaxueyuan.protocol.manager.SEUserManager;
import com.snail.olaxueyuan.protocol.result.UserKnowledgeResult;
import com.snail.olaxueyuan.protocol.result.UserLoginNoticeModule;
import com.snail.olaxueyuan.ui.SuperFragment;
import com.snail.olaxueyuan.ui.adapter.UserKnowledgeAdapter;
import com.snail.olaxueyuan.ui.me.activity.UserLoginActivity;
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
 * Created by mingge on 2016/5/20.
 */
public class UserKnowledgeFragment extends SuperFragment implements PullToRefreshBase.OnRefreshListener {
    View rootView;
    @Bind(R.id.expandableListView)
    PullToRefreshExpandableListView expandableListViews;
    ExpandableListView expandableListView;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.login_view)
    LinearLayout loginView;

    private UserKnowledgeResult module;
    private UserKnowledgeAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_user_knowledge, container, false);
        ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        initView();
        return rootView;
    }

    private void fetchData() {
        SVProgressHUD.showInView(getActivity(), getString(R.string.request_running), true);
        SEUserManager.getInstance().getStatisticsList("1", new Callback<UserKnowledgeResult>() {
            @Override
            public void success(UserKnowledgeResult userKnowledgeResult, Response response) {
                SVProgressHUD.dismiss(getActivity());
                expandableListViews.onRefreshComplete();
//                Logger.json(userKnowledgeResult);
                if (userKnowledgeResult.getApicode() != 10000) {
                    ToastUtil.showToastShort(getActivity(), userKnowledgeResult.getMessage());
                } else {
                    module = userKnowledgeResult;
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getActivity() != null) {
                    expandableListViews.onRefreshComplete();
                    SVProgressHUD.dismiss(getActivity());
                    ToastUtil.showToastShort(getActivity(), R.string.data_request_fail);
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

    private void initView() {
        expandableListViews.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        expandableListViews.setOnRefreshListener(this);
        expandableListView = expandableListViews.getRefreshableView();
        expandableListView.setDivider(null);
        expandableListView.setGroupIndicator(null);
        adapter = new UserKnowledgeAdapter(getActivity());
        expandableListView.setAdapter(adapter);
        isLoginView();
    }

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
                startActivity(new Intent(getActivity(), UserLoginActivity.class));
                break;
        }
    }

    public void onEventMainThread(UserLoginNoticeModule module) {
        isLoginView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        fetchData();
    }

    @OnClick(R.id.btn_login)
    public void onClick() {
    }


}
