package com.michen.olaxueyuan.protocol.event;

import java.io.Serializable;

/**
 * Created by mingge on 2016/7/14.
 */
public class PostDetailClickEvent implements Serializable {
    /**
     * type:1、帖子详情评论列表点赞
     */
    public int type;
    public int position;

    public PostDetailClickEvent(int type, int position) {
        this.type = type;
        this.position = position;
    }
}
