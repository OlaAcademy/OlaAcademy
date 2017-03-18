package com.michen.olaxueyuan.ui.umeng;

import android.content.Context;
import android.content.Intent;

import com.michen.olaxueyuan.common.manager.Logger;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.ui.question.InformationListActivity;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.common.UmLog;
import com.umeng.message.entity.UMessage;

import java.util.HashMap;
import java.util.Map;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

//使用自定义的NotificationHandler，来结合友盟统计处理消息通知
//参考http://bbs.umeng.com/thread-11112-1-1.html
public class CustomNotificationHandler extends UmengNotificationClickHandler {

    private static final String TAG = CustomNotificationHandler.class.getName();

    @Override
    public void dismissNotification(Context context, UMessage msg) {
        UmLog.d(TAG, "dismissNotification");
        super.dismissNotification(context, msg);
        MobclickAgent.onEvent(context, "dismiss_notification");
    }

    @Override
    public void launchApp(Context context, UMessage msg) {
        UmLog.d(TAG, "launchApp");
        super.launchApp(context, msg);
        Map<String, String> map = new HashMap<String, String>();
        map.put("action", "launch_app");
        MobclickAgent.onEvent(context, "click_notification", map);

        Logger.e("msg.extra==" + msg.extra);
        int type = Integer.parseInt(msg.extra.get("type"));
        Logger.e("type==" + type);
        switch (type) {
            case 5:
                ToastUtil.showToastShort(context, msg.text);
                Intent intent5=new Intent(context,InformationListActivity.class);
                intent5.addFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent5);
//                Utils.jumpLoginOrNot(context, InformationListActivity.class);
                break;
            case 7:
                ToastUtil.showToastShort(context, msg.text);
                break;
        }
    }

    @Override
    public void openActivity(Context context, UMessage msg) {
        UmLog.d(TAG, "openActivity");
        super.openActivity(context, msg);
        Map<String, String> map = new HashMap<String, String>();
        map.put("action", "open_activity");
        MobclickAgent.onEvent(context, "click_notification", map);
    }

    @Override
    public void openUrl(Context context, UMessage msg) {
        UmLog.d(TAG, "openUrl");
        super.openUrl(context, msg);
        Map<String, String> map = new HashMap<String, String>();
        map.put("action", "open_url");
        MobclickAgent.onEvent(context, "click_notification", map);
    }

    @Override
    public void dealWithCustomAction(Context context, UMessage msg) {
        UmLog.d(TAG, "dealWithCustomAction");
        super.dealWithCustomAction(context, msg);
        Map<String, String> map = new HashMap<String, String>();
        map.put("action", "custom_action");
        MobclickAgent.onEvent(context, "click_notification", map);
    }

    @Override
    public void autoUpdate(Context context, UMessage msg) {
        UmLog.d(TAG, "autoUpdate");
        super.autoUpdate(context, msg);
        Map<String, String> map = new HashMap<String, String>();
        map.put("action", "auto_update");
        MobclickAgent.onEvent(context, "click_notification", map);
    }

}
