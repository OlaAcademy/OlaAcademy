package com.michen.olaxueyuan.ui;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.baidu.autoupdatesdk.BDAutoUpdateSDK;
import com.baidu.autoupdatesdk.UICheckUpdateCallback;
import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.app.SEConfig;
import com.michen.olaxueyuan.common.StatusBarCompat;
import com.michen.olaxueyuan.protocol.manager.HomeListManager;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.model.SEUser;
import com.michen.olaxueyuan.protocol.result.TokenInfoResult;
import com.michen.olaxueyuan.ui.circle.chat.CustomUserProvider;
import com.snail.svprogresshud.SVProgressHUD;
import com.sriramramani.droid.inspector.server.ViewServer;
import com.umeng.analytics.MobclickAgent;

import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.chatkit.LCChatKitUser;
import cn.sharesdk.framework.ShareSDK;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

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
        /*// 测试 SDK 是否正常工作的代码
        AVObject testObject = new AVObject("TestObject");
        testObject.put("words", "Hello World!");
        testObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Logger.e("save----------success!");
                }
            }
        });*/
        loginChat();
        getTokenInfo();
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
        AVUser.logOut();
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

    private LCChatKitUser lcChatKitUser;

    private void loginChat() {
        if (SEAuthManager.getInstance().isAuthenticated()) {
            LCChatKit.getInstance().open(SEAuthManager.getInstance().getAccessUser().getPhone(), new AVIMClientCallback() {
                @Override
                public void done(AVIMClient avimClient, AVIMException e) {
                    if (null == e) {
//                        ToastUtil.showToastShort(UserLoginActivity.this, "登录成功");
                    } else {
                        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            SEUser seUser = SEAuthManager.getInstance().getAccessUser();
            if (seUser != null) {
                String avatarUrl = "";
                if (seUser.getAvator() != null) {
                    if (seUser.getAvator().contains("http://")) {
                        avatarUrl = seUser.getAvator();
                    } else if (seUser.getAvator().contains(".")) {
                        avatarUrl = SEConfig.getInstance().getAPIBaseURL() + "/upload/" + seUser.getAvator();
                    } else {
                        avatarUrl = SEAPP.PIC_BASE_URL + seUser.getAvator();
                    }
                }
                lcChatKitUser = new LCChatKitUser(seUser.getPhone(), seUser.getName(), avatarUrl);
                CustomUserProvider.getInstance().setpartUsers(lcChatKitUser);
            }
        }
    }

    public static void getTokenInfo() {
        try {
            if (!SEAuthManager.getInstance().isAuthenticated()) {
                return;
            }
            String userId = SEAuthManager.getInstance().getAccessUser().getId();
            HomeListManager.getInstance().getTokenInfo(userId, new Callback<TokenInfoResult>() {
                @Override
                public void success(TokenInfoResult tokenInfoResult, Response response) {
                    if (tokenInfoResult != null && tokenInfoResult.getApicode() == 10000) {
                        if (SEAuthManager.getInstance().getTokenInfoResult() != null) {
                            String nativeToken = SEAuthManager.getInstance().getTokenInfoResult().getResult().getToken();
                            if (nativeToken != null && !nativeToken.equals(tokenInfoResult.getResult().getToken())) {
                                SEAPP.showLoginFromOtherDialog();
                            } else {
                                SEAuthManager.getInstance().setTokenInfoResult(tokenInfoResult);
                            }
                        } else {
                            SEAuthManager.getInstance().setTokenInfoResult(tokenInfoResult);
                        }
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
