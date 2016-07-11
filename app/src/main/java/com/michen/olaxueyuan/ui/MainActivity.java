package com.michen.olaxueyuan.ui;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.baidu.autoupdatesdk.BDAutoUpdateSDK;
import com.baidu.autoupdatesdk.UICheckUpdateCallback;
import com.michen.olaxueyuan.R;
import com.sriramramani.droid.inspector.server.ViewServer;
import com.umeng.analytics.MobclickAgent;

import cn.sharesdk.framework.ShareSDK;

public class MainActivity extends BaseSearchActivity {

    private ProgressDialog dialog;

    public MainActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ShareSDK.initSDK(this);

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

    private class MyUICheckUpdateCallback implements UICheckUpdateCallback {

        @Override
        public void onCheckComplete() {
            dialog.dismiss();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.dismiss();
        ShareSDK.stopSDK(this);
        ViewServer.get(this).removeWindow(this);
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

}
