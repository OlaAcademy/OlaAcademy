package com.michen.olaxueyuan.ui.me.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.manager.SEUserManager;
import com.michen.olaxueyuan.protocol.result.CoinHistoryResult;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.michen.olaxueyuan.ui.me.adapter.CoinDetailListAdapter;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshListView;
import com.snail.svprogresshud.SVProgressHUD;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 欧拉币明细
 */
public class CoinDetailActivity extends SEBaseActivity implements PullToRefreshBase.OnRefreshListener {

    @Bind(R.id.listview)
    PullToRefreshListView listview;
    private Context context;
    private CoinDetailListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_detail);
        ButterKnife.bind(this);
        context = this;
        initView();
        getCoinHistory();
    }

    private void initView() {
        setTitleText("欧拉币明细");
        listview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listview.setOnRefreshListener(this);
        adapter = new CoinDetailListAdapter(context);
        listview.setAdapter(adapter);
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        getCoinHistory();
    }

    public void getCoinHistory() {
        String userId = "";
        if (SEAuthManager.getInstance().isAuthenticated()) {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        } else {
            startActivity(new Intent(context, UserLoginActivity.class));
            return;
        }
        SVProgressHUD.showInView(context, getString(R.string.request_running), true);
        SEUserManager.getInstance().coinGetHistoryList(userId, new Callback<CoinHistoryResult>() {
            @Override
            public void success(CoinHistoryResult coinHistoryResult, Response response) {
                if (context != null) {
                    listview.onRefreshComplete();
                    SVProgressHUD.dismiss(context);
                    if (coinHistoryResult.getApicode() == 10000) {
                        adapter.updateData(coinHistoryResult);
                    } else {
                        ToastUtil.showToastShort(context, coinHistoryResult.getMessage());
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (context != null) {
                    listview.onRefreshComplete();
                    SVProgressHUD.dismiss(context);
                    ToastUtil.showToastShort(context, R.string.request_failed);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
