package com.michen.olaxueyuan.ui.me.activity;

import android.content.Context;
import android.os.Bundle;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.manager.SEUserManager;
import com.michen.olaxueyuan.protocol.result.CheckinStatusResult;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 积分首页
 */
public class CoinHomePageActivity extends SEBaseActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_home_page);
        context = this;
        initView();
    }

    private void initView() {
        setTitleText(getString(R.string.ola_homepage));
        getSignStatus();
    }

    private void getSignStatus() {
        String userId = "";
        if (SEAuthManager.getInstance().isAuthenticated()) {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        } else {
            return;
        }
        SEUserManager.getInstance().getCheckinStatus(userId, new Callback<CheckinStatusResult>() {
            @Override
            public void success(CheckinStatusResult checkinStatusResult, Response response) {
                if (context != null) {
                    if (checkinStatusResult.getApicode() == 10000) {
                       /* olaCoin.setText(checkinStatusResult.getResult().getCoin() + "欧拉币");
                        myCoinText.setText(String.valueOf(checkinStatusResult.getResult().getCoin()));
                        if (checkinStatusResult.getResult().getStatus() == 1) {
                            signDot.setVisibility(View.INVISIBLE);
                        } else {
                            signDot.setVisibility(View.VISIBLE);
                        }*/
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (context != null) {
                    ToastUtil.showToastShort(context, R.string.request_failed);
                }
            }
        });
    }
}
