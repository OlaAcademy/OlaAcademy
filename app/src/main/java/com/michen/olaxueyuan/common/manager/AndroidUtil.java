package com.michen.olaxueyuan.common.manager;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Vibrator;
import android.view.WindowManager;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.ui.circle.PostDetailActivity;

/**
 * Created by kevin on 15/9/29.
 */
public class AndroidUtil {

    /**
     * @param activity
     * @return
     */
    public static String getMetaChannel(Activity activity) {
        try {
            ApplicationInfo info = activity.getPackageManager()
                    .getApplicationInfo(activity.getPackageName(),
                            PackageManager.GET_META_DATA);
            String result = info.metaData.getString("TD_CHANNEL_ID");
            result += ";" + AndUtil.getVersionName(activity) + ";"
                    + AndUtil.getVersionCode(activity);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 是否能够有音频权限
     *
     * @return
     */
    public static boolean hasAudioPermission() {
        PackageManager pm = SEAPP.getAppContext().getPackageManager();
        boolean permission = (PackageManager.PERMISSION_GRANTED == pm.checkPermission("android.permission.RECORD_AUDIO", "com.tianhai.app.chatmaster"));
        if (permission) {
            return true;
        } else {
            ToastUtil.showToastLong(SEAPP.getAppContext(), R.string.no_audio_permission);
            return false;
        }
    }

    /**
     * 开启震动
     *
     * @param context
     * @param vibrator
     * @param handler
     */
    public static void startVibrate(Context context, Vibrator vibrator, Handler handler) {
        boolean startVibration = SharedPreferenceUtil.getBoolean(context, "msg_virbration", false);//接听设置里的是否震动
        if (!startVibration) {
            return;
        }
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {10, 300};
        vibrator.vibrate(pattern, -1);
        handler.sendEmptyMessageDelayed(PostDetailActivity.STOP_VIBRATE, 1000);
    }

    /**
     * 停止震动
     *
     * @param vibrator
     */
    public static void stopVibrate(Vibrator vibrator) {
        if (vibrator != null) {
            vibrator.cancel();
            vibrator = null;
        }
    }

    /**
     * 设置背景alpha值
     *
     * @param context
     * @param alpha
     */
    public static void setWindowBackGroundAlpha(Activity context, float alpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = alpha; //0.0-1.0
        context.getWindow().setAttributes(lp);
    }

}
