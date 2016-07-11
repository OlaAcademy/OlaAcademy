package com.michen.olaxueyuan.common.manager;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    private static Toast toast = null;

    public static void showShortToast(Context context, String resId) {
        showMessage(context, resId, Toast.LENGTH_SHORT);
    }

    public static void showToastShort(Context context, int resId) {
        showMessage(context, context.getString(resId), Toast.LENGTH_SHORT);

    }

    public static void showToastLong(Context context, int resId) {
        showMessage(context, context.getString(resId), Toast.LENGTH_LONG);

    }

    public static void showToastShort(Context context, String resId) {
        showMessage(context, resId, Toast.LENGTH_SHORT);
    }

    public static void showToastLong(Context context, String resId) {
        showMessage(context, resId, Toast.LENGTH_LONG);
    }

    public static void showMessage(final Context context, final String msg, final int length) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, length);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
}
