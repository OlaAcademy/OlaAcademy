package com.michen.olaxueyuan.ui.umeng;

import android.content.Context;
import android.content.Intent;

import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.common.manager.Logger;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.event.LenChatUnReadEvents;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.ui.course.CourseVideoActivity;
import com.michen.olaxueyuan.ui.course.WebViewActivity;
import com.michen.olaxueyuan.ui.course.commodity.CommodityActivity;
import com.michen.olaxueyuan.ui.me.activity.UserLoginActivity;
import com.michen.olaxueyuan.ui.question.CommonMessageActivity;
import com.michen.olaxueyuan.ui.question.InformationListActivity;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.common.UmLog;
import com.umeng.message.entity.UMessage;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

//使用自定义的NotificationHandler，来结合友盟统计处理消息通知
//参考http://bbs.umeng.com/thread-11112-1-1.html
public class CustomNotificationHandler extends UmengNotificationClickHandler {

    private static final String TAG = CustomNotificationHandler.class.getName();

    @Override
    public void dismissNotification(Context context, UMessage msg) {
        Logger.e("dismissNotification");
        super.dismissNotification(context, msg);
        MobclickAgent.onEvent(context, "dismiss_notification");
    }

    @Override
    public void launchApp(Context context, UMessage msg) {
        Logger.e("launchApp");
        super.launchApp(context, msg);
        Map<String, String> map = new HashMap<String, String>();
        map.put("action", "launch_app");
        MobclickAgent.onEvent(context, "click_notification", map);

        Logger.e("msg.extra==" + msg.extra);
        int type = Integer.parseInt(msg.extra.get("type"));
        Logger.e("type==" + type);
        switch (type) {
            case 1://纯文本点击进入app，无任何操作
                break;
            case 2://2课程点击进入课程页面
                String courseId = msg.extra.get("courseId");
                Intent intent2 = new Intent(context, CourseVideoActivity.class);
                intent2.addFlags(FLAG_ACTIVITY_NEW_TASK);
                intent2.putExtra("pid", courseId);
                context.startActivity(intent2);
                break;
            case 3://3web页面，点击进去webview
                String url = msg.extra.get("url");
                Intent intent3 = new Intent(context, WebViewActivity.class);
                intent3.addFlags(FLAG_ACTIVITY_NEW_TASK);
                intent3.putExtra("textUrl", url);
                context.startActivity(intent3);
                break;
            case 4://4套装课程
                Intent commodityIntent = new Intent(context, CommodityActivity.class);
                commodityIntent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                commodityIntent.putExtra("title", "精品课程");
                commodityIntent.putExtra("type", "1");
                context.startActivity(commodityIntent);
                break;
            case 5://5帖子消息回复
                ToastUtil.showToastShort(context, msg.text);
                Intent intent5 = new Intent(context, InformationListActivity.class);
                intent5.addFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent5);
//                Utils.jumpLoginOrNot(context, InformationListActivity.class);
                break;
            case 6://6leancloud消息列表
                EventBus.getDefault().post(new LenChatUnReadEvents(true));
                if (!SEAuthManager.getInstance().isAuthenticated()) {
                    Intent loginIntent = new Intent(context, UserLoginActivity.class);
                    context.startActivity(loginIntent);
                    return;
                }
                Intent intent6 = new Intent(context, CommonMessageActivity.class);
                intent6.addFlags(FLAG_ACTIVITY_NEW_TASK);
                intent6.putExtra("type", 4);
                context.startActivity(intent6);
                break;
            case 7://7账号异地登录
//                ToastUtil.showToastShort(context, msg.text);
                if (SEAuthManager.getInstance() != null) {
                    SEAPP.showLoginFromOtherDialog();
                }
                break;
        }
    }

    @Override
    public void openActivity(Context context, UMessage msg) {
        Logger.e("openActivity");
        super.openActivity(context, msg);
        Map<String, String> map = new HashMap<String, String>();
        map.put("action", "open_activity");
        MobclickAgent.onEvent(context, "click_notification", map);
    }

    @Override
    public void openUrl(Context context, UMessage msg) {
        Logger.e("openUrl");
        super.openUrl(context, msg);
        Map<String, String> map = new HashMap<String, String>();
        map.put("action", "open_url");
        MobclickAgent.onEvent(context, "click_notification", map);
    }

    @Override
    public void dealWithCustomAction(Context context, UMessage msg) {
        Logger.e("dealWithCustomAction");
        super.dealWithCustomAction(context, msg);
        Map<String, String> map = new HashMap<String, String>();
        map.put("action", "custom_action");
        MobclickAgent.onEvent(context, "click_notification", map);
    }

    @Override
    public void autoUpdate(Context context, UMessage msg) {
        Logger.e("autoUpdate");
        super.autoUpdate(context, msg);
        Map<String, String> map = new HashMap<String, String>();
        map.put("action", "auto_update");
        MobclickAgent.onEvent(context, "click_notification", map);
    }

}
