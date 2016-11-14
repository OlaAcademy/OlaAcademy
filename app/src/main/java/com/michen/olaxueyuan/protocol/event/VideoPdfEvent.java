package com.michen.olaxueyuan.protocol.event;

import java.io.Serializable;

/**
 * Created by mingge on 2016/9/21.
 */

public class VideoPdfEvent implements Serializable {
    public String url;//要下载的pdf的地址
    public long id;//pdf文件对应的id
    public int type;//1为下载pdf
    public int position;//位置
    public String name;

    public VideoPdfEvent(String url, long id, int type, int position, String name) {
        this.url = url;
        this.id = id;
        this.type = type;
        this.position = position;
        this.name = name;
    }

    @Override
    public String toString() {
        return "VideoPdfEvent{" +
                "url='" + url + '\'' +
                ", id=" + id +
                ", type=" + type +
                ", position=" + position +
                ", name='" + name + '\'' +
                '}';
    }
}
