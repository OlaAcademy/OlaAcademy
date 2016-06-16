package com.snail.olaxueyuan.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.snail.olaxueyuan.R;
import com.snail.olaxueyuan.common.manager.Logger;
import com.snail.olaxueyuan.common.manager.ToastUtil;
import com.snail.olaxueyuan.protocol.manager.SEAuthManager;
import com.snail.olaxueyuan.protocol.manager.SEUserManager;
import com.snail.olaxueyuan.protocol.result.UserCourseCollectResult;
import com.snail.olaxueyuan.protocol.result.UserLoginNoticeModule;
import com.snail.olaxueyuan.ui.SuperFragment;
import com.snail.olaxueyuan.ui.me.activity.UserLoginActivity;
import com.snail.olaxueyuan.ui.me.adapter.UserCourseCollectAdapter;
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
 * Created by mingge on 2016/5/20.
 */
public class UserCourseCollectFragment extends SuperFragment implements PullToRefreshBase.OnRefreshListener {
    View rootView;
    UserCourseCollectResult module;
    @Bind(R.id.listview)
    PullToRefreshListView listview;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.login_view)
    LinearLayout loginView;
    private UserCourseCollectAdapter adapter;
    public static boolean isRefreshCourseCollectList;//是否刷新用户收藏列表

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_user_course_collect, container, false);
        ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        initView();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.e("isRefreshCourseCollectList()==" + isRefreshCourseCollectList);
        if (isRefreshCourseCollectList) {
            isRefreshCourseCollectList = false;
            fetchData();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        listview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listview.setOnRefreshListener(this);
        adapter = new UserCourseCollectAdapter(getActivity());
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
//                Logger.json(userCourseCollectResult);
                listview.onRefreshComplete();
                if (userCourseCollectResult.getApicode() != 10000) {
                    ToastUtil.showToastShort(getActivity(), userCourseCollectResult.getMessage());
                } else {
                    module = userCourseCollectResult;
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getActivity() != null) {
                    listview.onRefreshComplete();
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
                startActivity(new Intent(getActivity(), UserLoginActivity.class));
                break;
        }
    }

}
