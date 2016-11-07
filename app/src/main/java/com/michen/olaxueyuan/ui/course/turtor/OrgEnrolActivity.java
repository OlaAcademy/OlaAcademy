package com.michen.olaxueyuan.ui.course.turtor;

import android.content.Context;
import android.os.Bundle;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.manager.HomeListManager;
import com.michen.olaxueyuan.protocol.result.OrganizationInfoResult;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.snail.svprogresshud.SVProgressHUD;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class OrgEnrolActivity extends SEBaseActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_enrol);
        this.context = this;
        initView();
        getOrganizationInfo();
    }

    private void initView() {
        setTitleText("全国最优质的教育报名通道");
        setRightImage(R.drawable.icon_pdf_share);
    }

    private void getOrganizationInfo() {
        SVProgressHUD.showInView(context, getString(R.string.request_running), true);
        HomeListManager.getInstance().getOrganizationInfo(new Callback<OrganizationInfoResult>() {
            @Override
            public void success(OrganizationInfoResult result, Response response) {
                if (context != null && !OrgEnrolActivity.this.isFinishing()) {
                    if (result.getApicode() != 10000) {
                        ToastUtil.showToastShort(context, result.getMessage());
                    } else {
                        SVProgressHUD.dismiss(context);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (context != null && !OrgEnrolActivity.this.isFinishing()) {
                    ToastUtil.showToastShort(context, R.string.data_request_fail);
                    SVProgressHUD.dismiss(context);
                }
            }
        });
    }
}
