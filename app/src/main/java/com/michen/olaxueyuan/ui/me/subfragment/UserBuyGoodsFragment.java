package com.michen.olaxueyuan.ui.me.subfragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.michen.olaxueyuan.protocol.result.UserLoginNoticeModule;
import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.manager.SEUserManager;
import com.michen.olaxueyuan.protocol.result.UserBuyGoodsResult;
import com.michen.olaxueyuan.ui.SuperFragment;
import com.michen.olaxueyuan.ui.me.activity.UserLoginActivity;
import com.michen.olaxueyuan.ui.me.adapter.UserBuyGoodsAdapter;
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
public class UserBuyGoodsFragment extends SuperFragment implements PullToRefreshBase.OnRefreshListener {
    View rootView;
    @Bind(R.id.listview)
    PullToRefreshListView listview;
    UserBuyGoodsAdapter adapter;
    UserBuyGoodsResult module;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.login_view)
    LinearLayout loginView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_user_buy_course, container, false);
        ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        initView();
        return rootView;
    }

    private void fetchData() {
        SEUserManager.getInstance().getBuyGoodsList(SEAuthManager.getInstance().getAccessUser().getId(), new Callback<UserBuyGoodsResult>() {
            @Override
            public void success(UserBuyGoodsResult userBuyGoodsResult, Response response) {
                if (listview!=null)
                    listview.onRefreshComplete();
                if (userBuyGoodsResult.getApicode() != 10000) {
                    ToastUtil.showToastShort(getActivity(), userBuyGoodsResult.getMessage());
                } else {
                    module = userBuyGoodsResult;
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

    private void initView() {
        listview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listview.setOnRefreshListener(this);
        adapter = new UserBuyGoodsAdapter(getActivity());
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

    public void onEventMainThread(UserLoginNoticeModule module) {
        isLoginView();
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

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        fetchData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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
