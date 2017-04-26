package com.michen.olaxueyuan.ui.me.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.manager.SEUserManager;
import com.michen.olaxueyuan.protocol.result.UserBuyGoodsResult;
import com.michen.olaxueyuan.protocol.result.UserLoginNoticeModule;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
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
 * 我的购买
 */
public class MyBuyGoodsActivity extends SEBaseActivity implements PullToRefreshBase.OnRefreshListener {
    @Bind(R.id.listview)
    PullToRefreshListView listview;
    @Bind(R.id.empty)
    ImageView empty;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.login_view)
    LinearLayout loginView;

    UserBuyGoodsAdapter adapter;
    UserBuyGoodsResult module;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_buy_goods);
        mContext = this;
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
    }

    private void fetchData() {
        if (!SEAuthManager.getInstance().isAuthenticated()) {
            startActivity(new Intent(this, UserLoginActivity.class));
            return;
        }
        SEUserManager.getInstance().getBuyGoodsList(SEAuthManager.getInstance().getAccessUser().getId(), new Callback<UserBuyGoodsResult>() {
            @Override
            public void success(UserBuyGoodsResult userBuyGoodsResult, Response response) {
                if (mContext != null && !MyBuyGoodsActivity.this.isFinishing())
                    if (listview != null)
                        listview.onRefreshComplete();
                if (userBuyGoodsResult.getApicode() != 10000) {
                    ToastUtil.showToastShort(mContext, userBuyGoodsResult.getMessage());
                } else {
                    module = userBuyGoodsResult;
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (mContext != null && !MyBuyGoodsActivity.this.isFinishing()) {
                    listview.onRefreshComplete();
                    ToastUtil.showToastShort(mContext, R.string.data_request_fail);
                }
            }
        });
    }

    private void initView() {
        setTitleText(getString(R.string.my_buy_text));
        listview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listview.setOnRefreshListener(this);
        listview.setEmptyView(empty);
        adapter = new UserBuyGoodsAdapter(mContext);
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
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
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
