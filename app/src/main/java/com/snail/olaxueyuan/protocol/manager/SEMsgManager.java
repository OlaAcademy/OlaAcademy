package com.snail.olaxueyuan.protocol.manager;

import com.snail.olaxueyuan.protocol.SECallBack;
import com.snail.olaxueyuan.protocol.model.SEMsg;
import com.snail.olaxueyuan.protocol.result.SEMsgResult;
import com.snail.olaxueyuan.protocol.result.ServiceError;
import com.snail.olaxueyuan.protocol.service.SEMsgService;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by tianxiaopeng on 15-1-10.
 */
public class SEMsgManager {

    private static SEMsgManager s_instance;
    private SEMsgService msgService;
    private ArrayList<SEMsg> msgList = new ArrayList<SEMsg>();
    private int uid = 0;

    private SEMsgManager() {
        msgService = SERestManager.getInstance().create(SEMsgService.class);
    }

    public static SEMsgManager getInstance() {
        if (s_instance == null) {
            s_instance = new SEMsgManager();
        }

        return s_instance;
    }

    public SEMsgService getMsgService() {
        return msgService;
    }

    public ArrayList<SEMsg> getMsgList() {
        return msgList;
    }

    public void refresh(int page, final SECallBack callback) {
        if (SEAuthManager.getInstance().getAccessUser() != null) {
            uid = Integer.valueOf(SEAuthManager.getInstance().getAccessUser().getId());
        }
        msgService.fetchMsg(uid, page, new Callback<SEMsgResult>() {
            @Override
            public void success(SEMsgResult result, Response response) {
                if (result.state) {
                    msgList = result.data;
                }
                if (callback != null) {
                    callback.success();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (callback != null) {
                    callback.failure(new ServiceError(error));
                }
            }
        });
    }
}

