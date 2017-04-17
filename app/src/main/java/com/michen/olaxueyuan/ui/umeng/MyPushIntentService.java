package com.michen.olaxueyuan.ui.umeng;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.Logger;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.ui.MainActivity;
import com.michen.olaxueyuan.ui.course.CourseVideoActivity;
import com.michen.olaxueyuan.ui.course.WebViewActivity;
import com.michen.olaxueyuan.ui.course.commodity.CommodityActivity;
import com.michen.olaxueyuan.ui.question.InformationListActivity;
import com.michen.olaxueyuan.ui.question.LenCloudMessageActivity;
import com.umeng.message.UmengMessageService;
import com.umeng.message.entity.UMessage;

import org.android.agoo.common.AgooConstants;
import org.json.JSONObject;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MyPushIntentService extends UmengMessageService {
    private static final String TAG = MyPushIntentService.class.getName();

    @Override
    public void onMessage(Context context, Intent intents) {
        try {
            boolean isShowNotification = true;
            //可以通过MESSAGE_BODY取得消息体
            String message = intents.getStringExtra(AgooConstants.MESSAGE_BODY);
            UMessage msg = new UMessage(new JSONObject(message));
            Logger.e("message=" + message);      //消息体
            Logger.e("custom=" + msg.custom);    //自定义消息的内容
            Logger.e("title=" + msg.title);      //通知标题
            Logger.e("text=" + msg.text);        //通知内容
            Logger.e("extra=" + msg.extra);        //通知内容

            JSONObject json = new JSONObject(msg.extra);
            Intent intent = new Intent();
            int type = json.getInt("type");
            Logger.e("type=" + type);
            switch (type) {
                case 1://纯文本点击进入app，无任何操作
                    break;
                case 2://2课程点击进入课程页面
                    String courseId = msg.extra.get("courseId");
                    intent.setClass(context, CourseVideoActivity.class);
                    intent.putExtra("pid", courseId);
                    Logger.e("pid==" + courseId);
                    break;
                case 3://3web页面，点击进去webview
                    String url = msg.extra.get("url");
                    intent.setClass(context, WebViewActivity.class);
                    intent.putExtra("textUrl", url);
                    Logger.e("url==" + url);
                    break;
                case 4://4套装课程
                    intent.setClass(context, CommodityActivity.class);
                    intent.putExtra("title", "精品课程");
                    intent.putExtra("type", "1");
                    break;
                case 5://5帖子消息回复
                    ToastUtil.showToastShort(context, msg.text);
                    intent.setClass(context, InformationListActivity.class);
                    break;
                case 6://6leancloud消息列表
//                    EventBus.getDefault().post(new LenChatUnReadEvents(true));
                    if (!SEAuthManager.getInstance().isAuthenticated()) {
                        isShowNotification = false;
                    } else {
                        intent.setClass(context, LenCloudMessageActivity.class);
                    }
                    break;
                case 7://7账号异地登录
                    isShowNotification = false;
//                ToastUtil.showToastShort(context, msg.text);
                    if (SEAuthManager.getInstance() != null) {
                        MainActivity.getTokenInfo();
//                        SEAPP.showLoginFromOtherDialog();
                    }
                    break;
            }
            if (isShowNotification) {
                //为了版本兼容  选择V7包下的NotificationCompat进行构造
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                //Ticker是状态栏显示的提示
                builder.setTicker(msg.ticker);
                //第一行内容  通常作为通知栏标题
                builder.setContentTitle(msg.title);
                //第二行内容 通常是通知正文
                builder.setContentText(msg.text);
                //number设计用来显示同种通知的数量和ContentInfo的位置一样，如果设置了ContentInfo则number会被隐藏
                builder.setNumber(2);
                //可以点击通知栏的删除按钮删除
                builder.setAutoCancel(true);
                //系统状态栏显示的小图标
                builder.setSmallIcon(R.drawable.ic_launcher);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                PendingIntent pIntent = PendingIntent.getActivity(this, 1, intent, 0);
                //点击跳转的intent
                builder.setContentIntent(pIntent);
                //通知默认的声音 震动 呼吸灯
                builder.setDefaults(NotificationCompat.DEFAULT_ALL);
                Notification notification = builder.build();
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(0, notification);
            }
        } catch (Exception e) {
            Logger.e(e.getMessage());
        }
    }
}
