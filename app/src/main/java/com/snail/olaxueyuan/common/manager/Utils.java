package com.snail.olaxueyuan.common.manager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.io.Closeable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tianxiaopeng on 15/6/14.
 */
public class Utils {
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

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 格式化浏览人数
     *
     * @param number
     * @return
     */
    public static String getReadNumber(int number) {
        String readNumber;
        DecimalFormat format = new DecimalFormat("0.0");
        if (number < 1000) {
            readNumber = String.valueOf(number);
        } else if (number == 1000) {
            readNumber = "1k";
        } else if (number > 1000) {
            readNumber = format.format(number / 1000) + "k";
        } else {
            readNumber = "1";
        }
        return readNumber;
    }

    /**
     * 格式化浏览人数(基数为1000)
     *
     * @param originalNum
     * @return
     */
    public static String getFormatNumber(String originalNum) {
        int baseNum = 1000;
        StringBuilder resultNumber = new StringBuilder();
        try {
            int originalNumber = Integer.parseInt(originalNum);
            int integerNum = originalNumber / baseNum;
            if (integerNum == 0) { //小于基数
                resultNumber.append(originalNum);
            } else { //大于基数
                resultNumber.append(integerNum); //添加整数位
                int remainderNum = originalNumber % baseNum;
                if (remainderNum == 0) { //是基数的整数倍
                    resultNumber.append("k");
                } else { // 查看百位数据
                    int hundredIntegerNum = remainderNum / (baseNum / 10);
                    int hundredRemainderNum = remainderNum % (baseNum / 10);
                    if (hundredIntegerNum == 0) {
                        if (hundredRemainderNum > 0) {
                            resultNumber.append("k+");
                        }
                    } else {//添加百位整数位并查看百位后面数据
                        resultNumber.append(".");
                        resultNumber.append(hundredIntegerNum + "k");
                        if (hundredRemainderNum > 0) {
                            resultNumber.append("+");
                        }
                    }
                }
            }
            return resultNumber.toString();
        } catch (Exception e) {
            return "0";
        }
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
     * 隐藏软键盘
     *
     * @param context
     */
    public static void hideInputMethod(Activity context) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*try {
            ((InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(context.getCurrentFocus()
                                    .getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
        }*/
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

    /**
     * 格式化当前时间
     *
     * @return
     */
    public static String getCurrentDate(String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        String formatTimes = format.format(new Date(System.currentTimeMillis()));
        return formatTimes;
    }

    /**
     * 格式化下载速度和文件大小
     *
     * @param speed   long类型的大小
     * @param isSpeed true为格式化速度，false为格式化文件大小
     * @return
     */
    public static String speedFormat(long speed, boolean isSpeed) {
        String size;
        DecimalFormat format = new DecimalFormat("0.00");
        if (isSpeed) {
            if (speed == 0) {
                size = "0k/s";
            } else if (speed < 1000 * 1000) {
                size = format.format(speed / 1000) + "k/s";
            } else {
                size = format.format(speed / 1000 / 1000) + "M/s";
            }
        } else {
            if (speed < 1024 * 1024) {
                size = format.format(speed / 1024) + "k";
            } else {
                size = format.format(speed / 1024 / 1024) + "M";
            }
        }
        return size;
    }

}
