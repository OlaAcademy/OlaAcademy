package com.michen.olaxueyuan.protocol.eventbusmodule;

import java.io.Serializable;

/**
 * Created by mingge on 2016/7/14.
 */
public class CirclePraiseEvent implements Serializable {
    /**
     * type:1、欧拉圈列表点赞
     */
    public int type;
    /**
     * 是否点赞
     */
    public boolean isRequest;
    public int position;

    public CirclePraiseEvent(int type, boolean isRequest, int position) {
        this.type = type;
        this.isRequest = isRequest;
        this.position = position;
    }
}
