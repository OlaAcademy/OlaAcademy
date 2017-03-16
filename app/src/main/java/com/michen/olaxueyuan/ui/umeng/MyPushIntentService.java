/*
package com.michen.olaxueyuan.ui.umeng;

import android.content.Context;
import android.content.Intent;

import com.michen.olaxueyuan.common.manager.Logger;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.common.manager.Utils;
import com.michen.olaxueyuan.ui.question.InformationListActivity;
import com.umeng.message.UmengMessageService;
import com.umeng.message.common.UmLog;
import com.umeng.message.entity.UMessage;

import org.android.agoo.common.AgooConstants;
import org.json.JSONObject;

public class MyPushIntentService extends UmengMessageService {
    private static final String TAG = MyPushIntentService.class.getName();

    @Override
    public void onMessage(Context context, Intent intent) {
        try {
            //可以通过MESSAGE_BODY取得消息体
            String message = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
            UMessage msg = new UMessage(new JSONObject(message));
            UmLog.d(TAG, "message=" + message);      //消息体
            UmLog.d(TAG, "custom=" + msg.custom);    //自定义消息的内容
            UmLog.d(TAG, "title=" + msg.title);      //通知标题
            UmLog.d(TAG, "text=" + msg.text);        //通知内容
            Logger.e("extra=" + msg.extra);        //通知内容

            JSONObject json = new JSONObject(msg.extra);
            int type = json.getInt("type");
            Logger.e("type=" + type);
            switch (type) {
                case 5:
                    Utils.jumpLoginOrNot(this, InformationListActivity.class);
                    break;
                case 7:
                    ToastUtil.showToastShort(this, msg.text);
                    break;
            }

        } catch (Exception e) {
            Logger.e(e.getMessage());
        }
    }
}
*/
