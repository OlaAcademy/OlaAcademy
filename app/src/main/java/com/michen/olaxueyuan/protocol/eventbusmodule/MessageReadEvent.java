package com.michen.olaxueyuan.protocol.eventbusmodule;

/**
 * Created by mingge on 16/7/21.
 */
public class MessageReadEvent {
    public int position;
    public boolean isRefresh;//false不需要刷新未读消息接口，true刷新未读消息接口
    public String messageIds;
    public int type;

    public MessageReadEvent(boolean isRefresh) {
        this.isRefresh = isRefresh;
    }

    public MessageReadEvent(int position, boolean isRefresh, String messageIds, int type) {
        this.position = position;
        this.isRefresh = isRefresh;
        this.messageIds = messageIds;
        this.type = type;
    }

}
