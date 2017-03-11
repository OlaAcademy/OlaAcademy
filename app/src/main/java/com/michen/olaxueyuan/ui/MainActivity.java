package com.michen.olaxueyuan.ui;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.baidu.autoupdatesdk.BDAutoUpdateSDK;
import com.baidu.autoupdatesdk.UICheckUpdateCallback;
import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.common.StatusBarCompat;
import com.snail.svprogresshud.SVProgressHUD;
import com.sriramramani.droid.inspector.server.ViewServer;
import com.umeng.analytics.MobclickAgent;

import cn.sharesdk.framework.ShareSDK;

//public class MainActivity extends BaseSearchActivity {
public class MainActivity extends FragmentActivity {

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetStatusBarColor();
        setContentView(R.layout.activity_main);
        ShareSDK.initSDK(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment())
                    .commit();
        }

        ViewServer.get(this).addWindow(this);

//        setLeftImageInvisibility();

        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        BDAutoUpdateSDK.uiUpdateAction(this, new MyUICheckUpdateCallback());
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
        SVProgressHUD.dismiss(this);
        SEAPP.dismissAllowingStateLoss();
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

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    public void SetStatusBarColor() {
        // 无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (Build.VERSION.SDK_INT >= 20) {
            StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.common_blue));
        }
    }
}
