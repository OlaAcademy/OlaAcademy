package com.snail.olaxueyuan.common.manager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.snail.olaxueyuan.R;


/**
 * Created by mingge on 16/3/22.
 */
public class DialogUtils {
    public static void showDialog(Context context, final View.OnClickListener listener, String content, String sure, String cancle) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.confirm_collect_dialog, null);
        final Dialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        dialog.getWindow().setContentView(view);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        int screenWidth = Utils.getScreenMetrics(context).x;
        params.width = screenWidth - 160;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(params);
        TextView text = (TextView) view.findViewById(R.id.content);
        TextView noView = (TextView) view.findViewById(R.id.no);
        TextView yesView = (TextView) view.findViewById(R.id.yes);
        text.setText(content);
        if (!TextUtils.isEmpty(cancle)) {
            noView.setText(cancle);
        }
        if (!TextUtils.isEmpty(sure)) {
            yesView.setText(sure);
        }
        noView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                listener.onClick(view);
            }
        });
        yesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                listener.onClick(view);
            }
        });
    }
}
