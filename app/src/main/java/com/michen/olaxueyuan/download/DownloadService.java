package com.michen.olaxueyuan.download;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;

import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.LogUtils;

import java.util.List;

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
        super.onDestroy();
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
        if (resolveInfo == null ) {
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
