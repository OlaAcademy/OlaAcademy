/*
package com.michen.olaxueyuan.ui.umeng;

import android.content.Context;
import android.content.Intent;

import com.michen.olaxueyuan.common.manager.Logger;
import com.umeng.message.UTrack;
import com.umeng.message.UmengBaseIntentService;
import com.umeng.message.entity.UMessage;

import org.json.JSONObject;

*/
/**
 * Developer defined push intent service.
 * Remember to call {@link com.umeng.message.PushAgent#setPushIntentServiceClass(Class)}.
 *
 * @author lucas
 *//*

//完全自定义处理类
//参考文档的1.6.5
//http://dev.umeng.com/push/android/integration#1_6_5
public class MyPushIntentService extends UmengBaseIntentService {
    private static final String TAG = MyPushIntentService.class.getName();

    @Override
    protected void onMessage(Context context, Intent intent) {
        // 需要调用父类的函数，否则无法统计到消息送达
        super.onMessage(context, intent);
        try {
            //可以通过MESSAGE_BODY取得消息体
            String message = intent.getStringExtra(BaseConstants.MESSAGE_BODY);
            UMessage msg = new UMessage(new JSONObject(message));
            Logger.e("message=" + message);    //消息体
            Logger.e("custom=" + msg.custom);    //自定义消息的内容
            Logger.e("title=" + msg.title);    //通知标题
            Logger.e("text=" + msg.text);    //通知内容
            // code  to handle message here
            // ...

            // 对完全自定义消息的处理方式，点击或者忽略
            boolean isClickOrDismissed = true;
            if (isClickOrDismissed) {
                //完全自定义消息的点击统计
                UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
            } else {
                //完全自定义消息的忽略统计
                UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
            }

            // 使用完全自定义消息来开启应用服务进程的示例代码
            // 首先需要设置完全自定义消息处理方式
            // mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);
            // code to handle to start/stop service for app process
            */
/*JSONObject json = new JSONObject(msg.custom);
            String topic = json.getString("topic");
            Log.d(TAG, "topic="+topic);
			if(topic != null && topic.equals("appName:startService")) {
				// 在友盟portal上新建自定义消息，自定义消息文本如下
				//{"topic":"appName:startService"}
				if(Helper.isServiceRunning(context, NotificationService.class.getName())) 
					return; 
				Intent intent1 = new Intent();
				intent1.setClass(context, NotificationService.class); 
				context.startService(intent1);
			} else if(topic != null && topic.equals("appName:stopService")) {
				// 在友盟portal上新建自定义消息，自定义消息文本如下
				//{"topic":"appName:stopService"}
				if(!Helper.isServiceRunning(context, NotificationService.class.getName())) 
					return; 
				Intent intent1 = new Intent();
				intent1.setClass(context, NotificationService.class); 
				context.stopService(intent1);
			}*//*

            String messageExtra = intent.getStringExtra("extra");
            UMessage msgExtra = new UMessage(new JSONObject(messageExtra));
            Logger.e("msgExtra=="+msgExtra.extra.toString());
            JSONObject extras = new JSONObject(msgExtra.extra);
            String alert = extras.getString("alert");
            String type = extras.getString("type");
            String courseId = extras.getString("courseId");
            String url = extras.getString("url");

            Logger.e("alert==" + alert);
            Logger.e("type==" + type);
            Logger.e("courseId==" + courseId);
            Logger.e("url==" + url);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
*/
