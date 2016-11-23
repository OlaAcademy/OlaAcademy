package com.michen.olaxueyuan.protocol.event;

/**
 * Created by mingge on 2016/11/23.
 */

public class DownloadSuccessEvent {
    public boolean isSuccess;//是否下载成功

    public DownloadSuccessEvent(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
}
