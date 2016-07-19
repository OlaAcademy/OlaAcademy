package com.michen.olaxueyuan.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;

import com.baidu.autoupdatesdk.BDAutoUpdateSDK;
import com.baidu.autoupdatesdk.UICheckUpdateCallback;
import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.Logger;
import com.sriramramani.droid.inspector.server.ViewServer;
import com.umeng.analytics.MobclickAgent;
import com.umeng.common.message.UmengMessageDeviceConfig;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengRegistrar;

import cn.sharesdk.framework.ShareSDK;

public class MainActivity extends BaseSearchActivity {

    private ProgressDialog dialog;
    PushAgent mPushAgent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ShareSDK.initSDK(this);
        mPushAgent = PushAgent.getInstance(this);
        mPushAgent.onAppStart();
        mPushAgent.enable(new IUmengRegisterCallback() {
            @Override
            public void onRegistered(String s) {
                Logger.e("s==" + s);
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        updateStatus();
                    }
                });
            }
        });
        String device_token = UmengRegistrar.getRegistrationId(this);
        Logger.e("device_token==" + device_token);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment())
                    .commit();
        }

        ViewServer.get(this).addWindow(this);

        setLeftImageInvisibility();

        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        BDAutoUpdateSDK.uiUpdateAction(this, new MyUICheckUpdateCallback());
    }

    public Handler handler = new Handler();

    private void updateStatus() {
        String pkgName = getApplicationContext().getPackageName();
        String info = String.format("==enabled:%s\nisRegistered:%s\nDeviceToken:%s\n" +
                        "SdkVersion:%s\nAppVersionCode:%s\nAppVersionName:%s",
                mPushAgent.isEnabled(), mPushAgent.isRegistered(),
                mPushAgent.getRegistrationId(), MsgConstant.SDK_VERSION,
                UmengMessageDeviceConfig.getAppVersionCode(this), UmengMessageDeviceConfig.getAppVersionName(this));
        Logger.e("应用包名：" + pkgName + "\n" + info);

        Logger.e("updateStatus===:" + String.format("enabled:%s  isRegistered:%s",
                mPushAgent.isEnabled(), mPushAgent.isRegistered()));
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewServer.get(this).setFocusedWindow(this);
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.dismiss();
        ShareSDK.stopSDK(this);
        ViewServer.get(this).removeWindow(this);
    }

    public MainActivity() {
        super();
    }

    private class MyUICheckUpdateCallback implements UICheckUpdateCallback {

        @Override
        public void onCheckComplete() {
            dialog.dismiss();
        }

    }
}
