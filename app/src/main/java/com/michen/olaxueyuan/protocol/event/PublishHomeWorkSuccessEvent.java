package com.michen.olaxueyuan.protocol.event;

/**
 * Created by mingge on 16/10/29.
 */
public class PublishHomeWorkSuccessEvent {
    public boolean isSuccess;//是否发布成功

    public PublishHomeWorkSuccessEvent(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
}
