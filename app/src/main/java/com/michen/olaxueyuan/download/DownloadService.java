package com.michen.olaxueyuan.download;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.text.TextUtils;

import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.LogUtils;
import com.michen.olaxueyuan.common.manager.CommonConstant;
import com.michen.olaxueyuan.common.manager.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Author: wyouflf
 * Date: 13-11-10
 * Time: 上午1:04
 */
public class DownloadService extends Service {

    private static DownloadManager DOWNLOAD_MANAGER;

    public static DownloadManager getDownloadManager(Context appContext) {
        if (!DownloadService.isServiceRunning(appContext)) {
//            Intent downloadSvr = new Intent("download.service.action");
            Intent downloadSvr = new Intent();
            downloadSvr.setAction("download.service.action");
            Intent eintent = new Intent(createExplicitFromImplicitIntent(appContext, downloadSvr));
            appContext.startService(eintent);
        }
        if (DownloadService.DOWNLOAD_MANAGER == null) {
            DownloadService.DOWNLOAD_MANAGER = new DownloadManager(appContext);
        }
        return DOWNLOAD_MANAGER;
    }

    private static Timer timer;
    private static SimpleDateFormat dateHourFormat = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
    private static SimpleDateFormat dateYearFormat = new SimpleDateFormat("yyyy:MM:dd", Locale.CHINA);

    public static void startTimer(Context appContext) {
        if (!DownloadService.isServiceRunning(appContext)) {
            Intent downloadSvr = new Intent();
            downloadSvr.setAction("download.service.action");
            Intent eintent = new Intent(createExplicitFromImplicitIntent(appContext, downloadSvr));
            appContext.startService(eintent);
        }
        if (timer == null) {
            timer = new Timer();
        }
        SharedPreferences mSp = appContext.getSharedPreferences(CommonConstant.DAY_STUDY_PREFERENCE, MODE_PRIVATE);
        String currentTime = dateYearFormat.format(new Date());
        if (!TextUtils.isEmpty(currentTime) && !currentTime.equals(mSp.getString(CommonConstant.DAY_STUDY_DATE, "0000:00:00"))) {
            mSp.edit().putInt(CommonConstant.DAY_STUDY_TIME_LENGTH, 0).apply();
            mSp.edit().putString(CommonConstant.DAY_STUDY_DATE, currentTime).apply();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                CommonConstant.DAY_STUDY_TIME++;
                Logger.e("DAY_STUDY_TIME==" + CommonConstant.DAY_STUDY_TIME);
            }
        }, 60000, 60000);
    }

    public DownloadService() {
        super();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        if (DOWNLOAD_MANAGER != null) {
            try {
                DOWNLOAD_MANAGER.stopAllDownload();
                DOWNLOAD_MANAGER.backupDownloadInfoList();
            } catch (DbException e) {
                LogUtils.e(e.getMessage(), e);
            }
        }
        destroyTimer(this);
        super.onDestroy();
    }

    public static void destroyTimer(Context mContext) {
        if (timer != null) {
            try {
                timer.cancel();
                timer = null;
                SharedPreferences mSp = mContext.getSharedPreferences(CommonConstant.DAY_STUDY_PREFERENCE, MODE_PRIVATE);
                String currentTime = dateYearFormat.format(new Date());
                int oldTime = mSp.getInt(CommonConstant.DAY_STUDY_TIME_LENGTH, 0);
                int rawOffset = TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
                int currentLength = (int) ((System.currentTimeMillis() - rawOffset) / 1000 / 60);
                if (oldTime > 0) {
                    oldTime = oldTime > currentLength ? currentLength : oldTime;
                }
                mSp.edit().putInt(CommonConstant.DAY_STUDY_TIME_LENGTH
                        , CommonConstant.DAY_STUDY_TIME + oldTime).apply();
                mSp.edit().putString(CommonConstant.DAY_STUDY_DATE, currentTime).apply();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isServiceRunning(Context context) {
        boolean isRunning = false;

        ActivityManager activityManager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList
                = activityManager.getRunningServices(Integer.MAX_VALUE);

        if (serviceList == null || serviceList.size() == 0) {
            return false;
        }

        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(DownloadService.class.getName())) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    private static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

        // Make sure only one match was found
//        if (resolveInfo == null || resolveInfo.size() != 1) {
        if (resolveInfo == null) {
            return null;
        }

        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);

        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);

        // Set the component to be explicit
        explicitIntent.setComponent(component);

        return explicitIntent;
    }
}
