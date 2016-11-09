package com.michen.olaxueyuan.ui.course.turtor;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.common.manager.Utils;
import com.michen.olaxueyuan.protocol.manager.HomeListManager;
import com.michen.olaxueyuan.protocol.result.OrganizationInfoResult;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.snail.svprogresshud.SVProgressHUD;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class OrgEnrolActivity extends SEBaseActivity {

    @Bind(R.id.org_enrol_bg)
    ImageView orgEnrolBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_enrol);
        ButterKnife.bind(this);
        ButterKnife.bind(this);
        initView();
        getOrganizationInfo();
    }

    private void initView() {
        setTitleText("全国最优质的教育报名通道");
        setRightImage(R.drawable.icon_pdf_share);
        int width = Utils.getScreenWidth(mContext);
        int height = width * 400 / 750;
        ViewGroup.LayoutParams layoutParams = orgEnrolBg.getLayoutParams();
        layoutParams.height = height;
        orgEnrolBg.setLayoutParams(layoutParams);
    }

    private void getOrganizationInfo() {
        SVProgressHUD.showInView(mContext, getString(R.string.request_running), true);
        HomeListManager.getInstance().getOrganizationInfo(new Callback<OrganizationInfoResult>() {
            @Override
            public void success(OrganizationInfoResult result, Response response) {
                if (mContext != null && !OrgEnrolActivity.this.isFinishing()) {
                    if (result.getApicode() != 10000) {
                        ToastUtil.showToastShort(mContext, result.getMessage());
                    } else {
                        SVProgressHUD.dismiss(mContext);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (mContext != null && !OrgEnrolActivity.this.isFinishing()) {
                    ToastUtil.showToastShort(mContext, R.string.data_request_fail);
                    SVProgressHUD.dismiss(mContext);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mContext != null && !OrgEnrolActivity.this.isFinishing()) {
            SVProgressHUD.dismiss(mContext);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
