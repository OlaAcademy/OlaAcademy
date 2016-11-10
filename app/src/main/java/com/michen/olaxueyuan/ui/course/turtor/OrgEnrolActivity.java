package com.michen.olaxueyuan.ui.course.turtor;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.CommonUtil;
import com.michen.olaxueyuan.common.manager.DialogUtils;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.common.manager.Utils;
import com.michen.olaxueyuan.protocol.manager.HomeListManager;
import com.michen.olaxueyuan.protocol.manager.MCOrgManager;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.result.MCCommonResult;
import com.michen.olaxueyuan.protocol.result.OrganizationInfoResult;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.michen.olaxueyuan.ui.manager.OrgEnrolPopManager;
import com.snail.svprogresshud.SVProgressHUD;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class OrgEnrolActivity extends SEBaseActivity implements OrgEnrolPopManager.EnrolClickListener {

    @Bind(R.id.org_enrol_bg)
    ImageView orgEnrolBg;
    @Bind(R.id.option_type)
    TextView optionTypeText;
    @Bind(R.id.name)
    EditText nameEd;
    @Bind(R.id.phone)
    EditText phoneEd;
    @Bind(R.id.option_name)
    TextView optionNameText;

    private String optionTypeName;
    private String optionChildName;

    private int orgType = 0;//默认机构选择第一个
    private OrganizationInfoResult organizationInfoResult;
    private String userName;
    private String userPhone;
    private String orgId;
    private String location;
    private LocationClient mLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_enrol);
        ButterKnife.bind(this);
        locations();
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
        if (SEAuthManager.getInstance().getAccessUser() != null) {
            if (!TextUtils.isEmpty(SEAuthManager.getInstance().getAccessUser().getRealName())) {
                userName = SEAuthManager.getInstance().getAccessUser().getRealName();
            } else {
                userName = SEAuthManager.getInstance().getAccessUser().getName();
            }
            nameEd.setText(userName);
            userPhone = SEAuthManager.getInstance().getAccessUser().getPhone();
            phoneEd.setText(userPhone);
        }
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
                        organizationInfoResult = result;
                        initData();
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

    private void initData() {

    }

    @OnClick({R.id.option_type, R.id.option_name, R.id.enrol_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.option_type:
                if (organizationInfoResult != null) {
                    OrgEnrolPopManager.getInstance().showOrgPop(mContext, optionTypeText, this, 1, organizationInfoResult.getResult());
                }
                break;
            case R.id.option_name:
                if (organizationInfoResult != null) {
                    OrgEnrolPopManager.getInstance().showChildPop(mContext, optionNameText, this, 2, organizationInfoResult.getResult().get(orgType).getOptionList());
                }
                break;
            case R.id.enrol_btn:
                enrol();
                break;
        }
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

    @Override
    public void enrolPosition(int type, int orgType, String name, String id) {
        if (type == 1) {
            this.orgType = orgType;
            optionTypeName = name;
            optionTypeText.setText(optionTypeName);
        } else {
            optionChildName = name;
            optionNameText.setText(optionChildName);
            orgId = id;
        }
    }

    private void enrol() {
        if (TextUtils.isEmpty(orgId)) {
            ToastUtil.showToastShort(mContext, "请选择您想报名的机构或院校");
            return;
        }
        if (TextUtils.isEmpty(userName)) {
            ToastUtil.showToastShort(mContext, "请填写您的真实姓名");
            return;
        }
        if (TextUtils.isEmpty(userPhone)) {
            ToastUtil.showToastShort(mContext, "请填写您的联系方式");
            return;
        }
        SVProgressHUD.showInView(mContext, getString(R.string.request_running), true);
        MCOrgManager.getInstance().enroll(orgId, userPhone, location, CommonUtil.formatDate(), new Callback<MCCommonResult>() {
            @Override
            public void success(MCCommonResult mcCommonResult, Response response) {
                if (mContext != null && !OrgEnrolActivity.this.isFinishing()) {
                    SVProgressHUD.dismiss(mContext);
                    if (!mcCommonResult.apicode.equals("10000")) {
                        ToastUtil.showToastShort(mContext, mcCommonResult.message);
                    } else {
                        DialogUtils.showDialog(mContext, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        }, "", "报名成功，机构/院校招生人员会尽快与您取得联系", "", "");
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

    private void locations() {
        mLocationClient = new LocationClient(mContext);
        initLocation();
        //开始定位 精确到市
        mLocationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                mLocationClient.stop();
                if (bdLocation == null) {
                    return;
                }
//                Log.e("Test", bdLocation.getAddrStr());
                try {
                    String city = bdLocation.getCity();
                    String district = bdLocation.getDistrict();
                    if (city == null) {
                        city = "";
                    }
                    if (district == null) {
                        district = "";
                    }
                    location = city + district;
                } catch (Exception e) {
                    e.printStackTrace();
                    location = "";
                }
            }

        });
        mLocationClient.start();
    }

    /**
     * 设置相关参数
     */
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(false);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(false);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }
}
