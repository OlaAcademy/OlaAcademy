package com.michen.olaxueyuan.common.manager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Point;
import android.graphics.Rect;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by kevin on 15/5/19.
 */
public class AndUtil {

    public static int getVersionCode(Context context) {
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = context.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            String versionName = packInfo.versionName;
            int versionCode = packInfo.versionCode;
            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getVersionName(Context context) {
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = context.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            String version = "V" + packInfo.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getIMEI(Context context) {
        TelephonyManager mTelephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imei = mTelephonyManager.getDeviceId();
        if (TextUtils.isEmpty(imei)) {
            imei = String.valueOf(System.currentTimeMillis());
        }
        return imei;
    }

    @SuppressLint("NewApi")
    public static String getChannel(Context context) {
        String channel = "";
        ApplicationInfo info;
        try {
            info = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
        if (info == null) {
            return "";
        }

        try {
            channel = (String) info.metaData.get("TD_CHANNEL_ID");
        } catch (Exception e) {
            e.printStackTrace();
        }
        channel += ";" + AndUtil.getVersionName(context) + ";"
                + AndUtil.getVersionCode(context);
        return channel;
    }

    // 获取mac地址。
    public static String getMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }


    /**
     * 获取屏幕的长宽point
     *
     * @param activity
     * @return
     */
    public static Point screenDisplay(Context activity) {
        DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
        Point point = new Point();
        point.x = metrics.widthPixels;
        return point;
    }

    /**
     * 获取屏幕
     *
     * @param activity
     * @return
     */
    public static int getScreenStatusBarHeight(Activity activity) {
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }

    /**
     * 获取屏幕的密度
     *
     * @param activity
     * @return
     */
    public static float getScreenDensity(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.density;
    }

    /**
     * 获取屏幕密度dpi
     *
     * @param activity
     * @return
     */
    public static int getScreenDpi(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.densityDpi;
    }

    /**
     * 获取标题栏高度
     *
     * @param activity
     * @return
     */
    public static int getTitleBarHeight(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int sbh = rect.top;
        int contentTop = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        int titleBarTop = contentTop - sbh;
        return titleBarTop;
    }

    /**
     * 获取屏幕的高宽比
     *
     * @param context
     * @return
     */
    public static float getScreenRate(Context context) {
        Point P = getScreenMetrics(context);
        float H = P.y;
        float W = P.x;
        return (H / W);
    }

    /**
     * 获取屏幕的宽高
     *
     * @param context
     * @return
     */
    public static Point getScreenMetrics(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        int h_screen = dm.heightPixels;
        return new Point(w_screen, h_screen);

    }

    public static float getScreenDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static int dip2px(Context context, int dip) {
        return (int) (getScreenDensity(context) * dip);
    }

    public static int dimen2px(Context context, int dimRes) {
        return context.getResources().getDimensionPixelSize(dimRes);
    }

    /**
     * 获取通知栏的高度
     *
     * @return
     */
    public static int getStatusBarHeight() {
        return Resources.getSystem().getDimensionPixelSize(
                Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android"));
    }

    // 检查是否连接网络。
    public static boolean checkNetWork(Context context) {
        ConnectivityManager cwjManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cwjManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            return networkInfo.isAvailable();
        } else {
            return false;
        }
    }

    public static boolean isPackageAvilible(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        List<String> pName = new ArrayList<String>();// 用于存储所有已安装程序的包名
        // 从pinfo中将包名字逐一取出，压入pName list中
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);// 判断pName中是否有目标程序的包名，有TRUE，没有FALSE
    }

    public static long getIpAddress(Context context) {
        long ip = 0;
        String ipAddressStr = "";
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager.isWifiEnabled()) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();
            ipAddressStr = intToIp(ipAddress);
        } else {
            try {
                for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en
                        .hasMoreElements(); ) {
                    NetworkInterface intf = en.nextElement();
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr
                            .hasMoreElements(); ) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            ipAddressStr = inetAddress.getHostAddress().toString();
                        }
                    }
                }
            } catch (SocketException ex) {
            }
        }
        try {
            ip = IpConvert.ip2long(ipAddressStr);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return ip;
        }
        return ip;
    }

    public static String intToIp(int i) {
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "."
                + (i >> 24 & 0xFF);
    }

    /**
     * 获取手机号码
     *
     * @param context
     * @return
     */
    public static String getCurrentPhoneNumber(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String number = tm.getLine1Number();
        return number;
    }

    /**
     * 获取imei码
     *
     * @param context
     * @return
     */
//    public static String getImei(Context context) {
//        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//        return telephonyManager.getDeviceId();
//
//    }

    /**
     * 获取系统的图片文件的目录
     *
     * @param context
     */
    public static LinkedHashMap<String, ArrayList<Integer>> getImageFolderList(Context context,
                                                                               HashMap<Integer, String> idPathMap) {
        LinkedHashMap<String, ArrayList<Integer>> folderList = new LinkedHashMap<String, ArrayList<Integer>>();
        idPathMap = new LinkedHashMap<Integer, String>();
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null,
                (MediaStore.Images.Media.DATE_ADDED + " DESC"));
        folderList.clear();
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int _id = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                    Integer id = Integer.valueOf(_id);
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    idPathMap.put(id, path);
                    path = path.substring(0, path.lastIndexOf(File.separatorChar));
                    path = path.substring(path.lastIndexOf(File.separatorChar) + 1);
                    ArrayList<Integer> list = folderList.get(path);
                    if (list == null) {
                        list = new ArrayList<Integer>();
                    }
                    list.add(id);
                    folderList.put(path, list);
                } while (cursor.moveToNext());

            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return folderList;
    }

    /**
     * 获取文件列表
     *
     * @param context
     * @return
     */
    public static LinkedHashMap<String, ArrayList<String>> getImageFolderList(Context context) {
        LinkedHashMap<String, ArrayList<String>> folderList = new LinkedHashMap<String, ArrayList<String>>();
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null,
                (MediaStore.Images.Media.DATE_ADDED + " DESC"));
        folderList.clear();
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int _id = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                    // Integer id = Integer.valueOf(_id);
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    String tpath = path.substring(0, path.lastIndexOf(File.separatorChar));
                    tpath = tpath.substring(tpath.lastIndexOf(File.separatorChar) + 1);
                    ArrayList<String> list = folderList.get(tpath);
                    if (list == null) {
                        list = new ArrayList<String>();
                    }
                    list.add(path);
                    folderList.put(tpath, list);
                } while (cursor.moveToNext());

            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return folderList;
    }

    /**
     * @param context
     * @return
     */
    public static NetworkInfo.State getWifiState(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo.State wifiState = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        return wifiState;
    }

    public static NetworkInfo.State getMobileState(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo.State state = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        return state;
    }

    /**
     * 检测网络是否连接
     *
     * @return
     */
    public static boolean isNetConnected(Context context) {
        boolean netStatus = false;
        try {
            ConnectivityManager connectManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo[] networkInfo = connectManager.getAllNetworkInfo();
            for (NetworkInfo ni : networkInfo) {
                if (ni.isConnected()) {
                    netStatus = true;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return netStatus;
    }

    /**
     * 检测wifi是否连接
     *
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null
                    && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检测3G是否连接
     *
     * @return
     */
    public static boolean is3gConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null
                    && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检测GPS是否打开
     *
     * @return
     */
    public static boolean isGpsEnabled(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        List<String> accessibleProviders = lm.getProviders(true);
        for (String name : accessibleProviders) {
            if ("gps".equals(name)) {
                return true;
            }
        }
        return false;
    }

//    public static

    /**
     * 隐藏软键盘
     *
     * @param context
     */
    public static void hideInputMethod(Activity context) {
        try {
            ((InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(context.getCurrentFocus()
                                    .getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    private static final int MINUTE = 60;
    private static final int HOUR = 60 * MINUTE;
    private static final int DAY = 24 * HOUR;
    private static final int MONTH = 30 * DAY;

    private static final int SENCOND_MILLI = 1000;
    private static final int MINUTE_MILLI = 60 * SENCOND_MILLI;
    private static final int HOUR_MILLI = 60 * MINUTE_MILLI;
    private static final int DAY_MILLI = 24 * HOUR_MILLI;
    private static final int MONTH_MILLI = 30 * DAY_MILLI;

    /**
     * 获取当前过去的时间
     *
     * @param time
     * @return
     */
    public static String voiceCostTime(long time) {
//        time = time / 1000;
        StringBuffer stringBuffer = new StringBuffer();
        long remain = time;
        if (remain >= HOUR) {
            int hour = (int) remain / HOUR;
            remain = remain % HOUR;
            if (hour < 10) {
                stringBuffer.append("0");
            }
            stringBuffer.append(hour);
            stringBuffer.append(":");
        }
        if (remain >= MINUTE) {
            int minute = (int) remain / MINUTE;
            remain = remain % MINUTE;
            if (minute < 10) {
                stringBuffer.append("0");
            }
            stringBuffer.append(minute);
            stringBuffer.append(":");
        } else {
            stringBuffer.append("00");
            stringBuffer.append(":");
        }
        if (remain > 0) {
            int second = (int) remain;
            if (second < 10) {
                stringBuffer.append("0");
            }
            stringBuffer.append(second);
        } else {
            stringBuffer.append("00");
        }
        return stringBuffer.toString();
    }
}
