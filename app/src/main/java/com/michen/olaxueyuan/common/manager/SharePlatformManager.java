package com.michen.olaxueyuan.common.manager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.app.SEConfig;
import com.michen.olaxueyuan.sharesdk.ShareModel;
import com.michen.olaxueyuan.sharesdk.SharePopupWindow;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.utils.UIHandler;

import static android.R.attr.action;

/**
 * Created by mingge on 2016/12/13.
 */

public class SharePlatformManager {
    private static SharePlatformManager shareManager;

    private SharePlatformManager() {
    }

    public static SharePlatformManager getInstance() {
        if (shareManager == null) {
            shareManager = new SharePlatformManager();
        }
        return shareManager;
    }

    private SharePopupWindow shareView;

    public void share(Context context, View view, String avatar, String id, String content) {
        shareView = new SharePopupWindow(context);
        shareView.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Logger.e("onComplete==");
                Message msg = new Message();
                msg.arg1 = 1;
                msg.arg2 = action;
                msg.obj = platform;
                UIHandler.sendMessage(msg, callback);
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Logger.e("onError==");
                Message msg = new Message();
                msg.what = 1;
                UIHandler.sendMessage(msg, callback);
            }

            @Override
            public void onCancel(Platform platform, int i) {
                Logger.e("onCancel==");
                Message msg = new Message();
                msg.what = 0;
                UIHandler.sendMessage(msg, callback);
            }
        });
        ShareModel model = new ShareModel();
//        if (circle.getUserAvatar().indexOf("jpg") != -1) {
        if (avatar.contains(".")) {
            model.setImageUrl("http://api.olaxueyuan.com/upload/" + avatar);
        } else {
            model.setImageUrl(SEAPP.PIC_BASE_URL + avatar);
        }
        model.setText(content);
        model.setTitle("欧拉学院");
        model.setUrl(SEConfig.getInstance().getAPIBaseURL() + "/circlepost.html?circleId=" + id);
        shareView.initShareParams(model);
        shareView.showShareWindow();
        // 显示窗口 (设置layout在PopupWindow中显示的位置)
        shareView.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    public void dismissShareView() {
        if (shareView != null) {
            shareView.dismiss();
        }
    }

    private Handler.Callback callback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            int what = msg.what;
            if (what == 1) {
                Toast.makeText(SEAPP.getAppContext(), "分享失败", Toast.LENGTH_SHORT).show();
            }
            if (shareView != null) {
                shareView.dismiss();
            }
            return false;
        }
    };
}
