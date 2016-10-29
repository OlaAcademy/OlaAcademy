package com.michen.olaxueyuan.protocol.event;

import java.io.Serializable;

/**
 * Created by mingge on 2016/7/14.
 */
public class CircleClickEvent implements Serializable {
    /**
     * type:1、欧拉圈列表点赞
     * type:2、欧拉圈列表分享
     */
    public int type;
    /**
     * 是否点赞
     */
    public boolean isRequest;
    public int position;

    public CircleClickEvent(int type, boolean isRequest, int position) {
        this.type = type;
        this.isRequest = isRequest;
        this.position = position;
    }
}
