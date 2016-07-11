package com.michen.olaxueyuan.protocol;

import com.michen.olaxueyuan.protocol.result.ServiceError;

/**
 * Created by tianxiaopeng on 15-1-5.
 */
public interface SECallBack {
    public void success();

    public void failure(ServiceError error);
}

