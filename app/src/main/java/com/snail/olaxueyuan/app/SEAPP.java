package com.snail.olaxueyuan.app;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.snail.olaxueyuan.R;
import com.snail.olaxueyuan.common.SEThemer;

/**
 * Created by tianxiaopeng on 15-1-7.
 */
public class SEAPP extends Application {
    private static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;
        SEThemer.getInstance().init(this);
        SEThemer.getInstance().setActionBarBackgroundColor(getResources().getColor(R.color.ActionBarBackgroundColor));
        SEThemer.getInstance().setActionBarForegroundColor(getResources().getColor(R.color.ActionBarForegroundColor));

        //final String API_BASE_URL = "http://api.nowthinkgo.com/";
        final String API_BASE_URL = "http://123.59.136.206";
        SEConfig.getInstance().init(API_BASE_URL, "欧拉学院 v1.0", this);

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

    }

    public static Context getAppContext() {
        return mAppContext;
    }
}
