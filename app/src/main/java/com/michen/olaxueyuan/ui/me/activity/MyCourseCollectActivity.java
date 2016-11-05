package com.michen.olaxueyuan.ui.me.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.manager.SEUserManager;
import com.michen.olaxueyuan.protocol.result.UserCourseCollectResult;
import com.michen.olaxueyuan.protocol.result.UserLoginNoticeModule;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.michen.olaxueyuan.ui.me.adapter.UserCourseCollectAdapter;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshListView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 我的收藏
 */
public class MyCourseCollectActivity extends SEBaseActivity implements PullToRefreshBase.OnRefreshListener {
    UserCourseCollectResult module;
    @Bind(R.id.listview)
    PullToRefreshListView listview;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.login_view)
    LinearLayout loginView;
    private UserCourseCollectAdapter adapter;
    public static boolean isRefreshCourseCollectList;//是否刷新用户收藏列表
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_course_collect);
        mContext = this;
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isRefreshCourseCollectList) {
            isRefreshCourseCollectList = false;
            fetchData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        setTitleText(getString(R.string.my_collect_text));
        listview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listview.setOnRefreshListener(this);
        adapter = new UserCourseCollectAdapter(mContext);
        listview.setAdapter(adapter);
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

    private void fetchData() {
        // userId,316测试
//        SEUserManager.getInstance().getCollectionByUserId("126", new Callback<UserCourseCollectResult>() {
        SEUserManager.getInstance().getCollectionByUserId(SEAuthManager.getInstance().getAccessUser().getId(), new Callback<UserCourseCollectResult>() {
            @Override
            public void success(UserCourseCollectResult userCourseCollectResult, Response response) {
                if (mContext != null && !MyCourseCollectActivity.this.isFinishing())
//                Logger.json(userCourseCollectResult);
                    listview.onRefreshComplete();
                if (userCourseCollectResult.getApicode() != 10000) {
                    ToastUtil.showToastShort(mContext, userCourseCollectResult.getMessage());
                } else {
                    module = userCourseCollectResult;
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (mContext != null && !MyCourseCollectActivity.this.isFinishing()) {
                    listview.onRefreshComplete();
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

    private void initAdapter() {
        adapter.updateData(module);
    }

    public void onEventMainThread(UserLoginNoticeModule module) {
        isLoginView();
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        fetchData();
    }

    @OnClick({R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                startActivity(new Intent(mContext, UserLoginActivity.class));
                break;
        }
    }
}
