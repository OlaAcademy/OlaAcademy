package com.snail.olaxueyuan.ui.course;

import android.os.Bundle;
import android.view.View;

import com.snail.olaxueyuan.R;
import com.snail.olaxueyuan.common.manager.Logger;
import com.snail.olaxueyuan.common.manager.TitleManager;
import com.snail.olaxueyuan.common.manager.ToastUtil;
import com.snail.olaxueyuan.protocol.manager.SEAuthManager;
import com.snail.olaxueyuan.protocol.manager.SECourseManager;
import com.snail.olaxueyuan.protocol.result.GoodsOrderStatusResult;
import com.snail.olaxueyuan.ui.activity.SuperActivity;
import com.snail.svprogresshud.SVProgressHUD;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PaySystemVideoActivity extends SuperActivity {
    TitleManager titleManager;
    private String courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_system_video);
        performRefresh();
    }

    @Override
    public void initView() {
        titleManager = new TitleManager(this, "支付订单", this, true);
        courseId = getIntent().getStringExtra("courseId");
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }

    SECourseManager courseManager = SECourseManager.getInstance();

    public void performRefresh() {
        String userId = "";
        if (SEAuthManager.getInstance().isAuthenticated()) {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        }
        Logger.e("courseId==" + courseId);
        courseManager.getOrderStatus(courseId, userId, new Callback<GoodsOrderStatusResult>() {
            @Override
            public void success(GoodsOrderStatusResult result, Response response) {
                Logger.json(result);
                if (result.getApicode() != 10000) {
                    SVProgressHUD.showInViewWithoutIndicator(PaySystemVideoActivity.this, result.getMessage(), 2.0f);
                } else {

                }
            }

            @Override
            public void failure(RetrofitError error) {
                ToastUtil.showToastShort(PaySystemVideoActivity.this, R.string.data_request_fail);
            }
        });
    }
}
