package com.michen.olaxueyuan.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.SEThemer;
import com.michen.olaxueyuan.common.catloading.CatLoadingView;
import com.michen.olaxueyuan.common.manager.CommonConstant;
import com.michen.olaxueyuan.common.manager.Logger;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.ui.circle.chat.CustomUserProvider;
import com.michen.olaxueyuan.ui.umeng.LoginFromOtherDialogActivity;
import com.michen.olaxueyuan.ui.umeng.MyPushIntentService;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.chatkit.LCChatKit;

/**
 * Created by tianxiaopeng on 15-1-7.
 */
public class SEAPP extends Application {
    private static Context mAppContext;
    public static final String PIC_BASE_URL = "http://upload.olaxueyuan.com/SDpic/common/picSelect?gid=";
    public static final String MEDIA_BASE_URL = "http://upload.olaxueyuan.com";
    private String versionNames;
    private static CatLoadingView catLoadingView;
    public static boolean debug = true;
    public static final String API_BASE_URL = "http://english.olaxueyuan.com";
//        final String API_BASE_URL = "http://api.olaxueyuan.com";
//        final String API_BASE_URL = "http://123.59.129.137:8080";

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;
        MultiDex.install(this);
        QbSdk.initX5Environment(getApplicationContext(), null);//调用 QbSdk 的预加载接口 ,当 App 后续创建 webview 时就可以首次加载 x5 内核了
        registerUmeng();
        SEThemer.getInstance().init(this);
        SEThemer.getInstance().setActionBarBackgroundColor(getResources().getColor(R.color.ActionBarBackgroundColor));
        SEThemer.getInstance().setActionBarForegroundColor(getResources().getColor(R.color.ActionBarForegroundColor));

        try {
            versionNames = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
            versionNames = "";

        }
        SEConfig.getInstance().init(API_BASE_URL, "欧拉联考 v" + versionNames, this);

        /**
         * 开源框架 Image-Loader
         */
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder() //
                .showImageForEmptyUri(R.drawable.ic_launcher) //
                .showImageOnFail(R.drawable.ic_launcher) //
                .cacheInMemory(true) //
                .cacheOnDisk(true) //
                .build();//
        ImageLoaderConfiguration config = new ImageLoaderConfiguration//
                .Builder(getApplicationContext())//
                .defaultDisplayImageOptions(defaultOptions)//
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
//                .discCacheSize(50 * 1024 * 1024)//
//                .discCacheFileCount(100)// 缓存一百张图片
                .writeDebugLogs()//
                .build();//
        ImageLoader.getInstance().init(config);
        // 初始化参数依次为 this, AppId, AppKey
        LCChatKit.getInstance().setProfileProvider(CustomUserProvider.getInstance());
        AVOSCloud.setDebugLogEnabled(debug);
        LCChatKit.getInstance().init(getApplicationContext(), CommonConstant.LeanCloud_APP_ID, CommonConstant.LeanCloud_APP_KEY);
        AVIMClient.setAutoOpen(false);
    }

    public PushAgent mPushAgent;

    private void registerUmeng() {
        mPushAgent = PushAgent.getInstance(this);
//        mPushAgent.setNotificationClickHandler(new CustomNotificationHandler());
        mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);
        mPushAgent.setDisplayNotificationNumber(2);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Logger.e("deviceToken-----------------" + deviceToken);
                mPushAgent.addAlias(SEAuthManager.getInstance().getAccessUser().getPhone(), "USER_PHONE", new UTrack.ICallBack() {
                    @Override
                    public void onMessage(boolean b, String s) {
                        Logger.e("b==" + b + ";s==" + s);
                    }
                });
            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });
        mPushAgent.setDebugMode(debug);
        PushAgent.getInstance(this).onAppStart();
    }

    public static Context getAppContext() {
        return mAppContext;
    }

    private static List<CatLoadingView> catLoadingViewList = new ArrayList<>();

    public static CatLoadingView getCatLoadingView() {
        if (catLoadingView != null) {
            catLoadingView.dismissAllowingStateLoss();
        }
        catLoadingView = null;
        catLoadingView = new CatLoadingView();
        catLoadingView.setText("加载中，请稍后...");
        catLoadingViewList.add(catLoadingView);
        return catLoadingView;
    }

    public static CatLoadingView getCatLoadingView(String title) {
        if (catLoadingView != null) {
            catLoadingView.dismissAllowingStateLoss();
        }
        catLoadingView = null;
        catLoadingView = new CatLoadingView();
        catLoadingView.setText(title);
        catLoadingViewList.add(catLoadingView);
        return catLoadingView;
    }

    public static void showCatDialog(FragmentActivity activity) {
        getCatLoadingView().show(activity.getSupportFragmentManager(), "CatLoadingView");
    }

    public static void showCatDialog(Fragment fragment) {
        getCatLoadingView().show(fragment.getChildFragmentManager(), "CatLoadingView");
    }

    public static void showCatDialog(FragmentActivity activity, String title) {
        getCatLoadingView(title).show(activity.getSupportFragmentManager(), "CatLoadingView");
    }

    public static void showCatDialog(Fragment fragment, String title) {
        getCatLoadingView(title).show(fragment.getChildFragmentManager(), "CatLoadingView");
    }

    public static void dismissAllowingStateLoss() {
        getCatLoadingView().dismissAllowingStateLoss();
        for (int i = 0; i < catLoadingViewList.size(); i++) {
            if (catLoadingViewList.get(i) != null) {
                catLoadingViewList.get(i).dismissAllowingStateLoss();
            }
        }
        catLoadingViewList.clear();
    }

    public static void showLoginFromOtherDialog() {
        Intent dialogIntent = new Intent(mAppContext, LoginFromOtherDialogActivity.class);
        dialogIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mAppContext.startActivity(dialogIntent);
    }
}
