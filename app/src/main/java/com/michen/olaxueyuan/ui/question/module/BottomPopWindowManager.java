package com.michen.olaxueyuan.ui.question.module;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.michen.olaxueyuan.R;

/**
 * Created by mingge on 16/10/23.
 */
public class BottomPopWindowManager {
    private static BottomPopWindowManager bottomPopWindowManager;

    public static BottomPopWindowManager getInstance() {
        if (bottomPopWindowManager == null) {
            bottomPopWindowManager = new BottomPopWindowManager();
        }
        return bottomPopWindowManager;
    }

    private PopupWindow pop = null;

    public PopupWindow showBottomPop(Activity activity, final View.OnClickListener listener, String itemOne, String itemTwo, String itemThree) {
        pop = new PopupWindow(activity);
        View view = activity.getLayoutInflater().inflate(R.layout.item_popupwindows, null);
        final LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);
        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        Button mBt_camera = (Button) view.findViewById(R.id.item_popupwindows_camera);
        Button mBt_photo = (Button) view.findViewById(R.id.item_popupwindows_Photo);
        Button mBt_cancel = (Button) view.findViewById(R.id.item_popupwindows_cancel);
        if (!TextUtils.isEmpty(itemOne)) {
            mBt_camera.setText(itemOne);
        }
        if (!TextUtils.isEmpty(itemTwo)) {
            mBt_photo.setText(itemTwo);
        }
        if (!TextUtils.isEmpty(itemThree)) {
            mBt_cancel.setText(itemThree);
        }

        parent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        mBt_camera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                listener.onClick(v);
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        mBt_photo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                listener.onClick(v);
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        mBt_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
//        pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
        return pop;
    }
}
