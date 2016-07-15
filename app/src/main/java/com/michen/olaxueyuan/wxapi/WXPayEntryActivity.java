package com.michen.olaxueyuan.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.ui.course.pay.weixin.Constants;
import com.michen.olaxueyuan.ui.me.activity.BuyVipActivity;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import de.greenrobot.event.EventBus;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxpay_entry);

        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
//        Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            finish();
            switch (resp.errCode) {
                case 0://支付成功
                    ToastUtil.showToastShort(this, "支付成功");
                    //关闭购买页面
                    EventBus.getDefault().post(true);
                    break;
                case -1://支付失败
                    ToastUtil.showToastShort(this, "支付失败");
                    break;
                case -2://取消支付
                    ToastUtil.showToastShort(this, "取消支付");
                    break;
                default:
                    ToastUtil.showToastShort(this, "支付失败");
                    break;
            }
        }
    }
}
