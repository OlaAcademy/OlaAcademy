package com.michen.olaxueyuan.protocol.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mingge on 16/7/19.
 */
public class MessageListResult implements Serializable {
    /**
     * message : 成功
     * result : [{"content":"消息1","id":1,"otherId":0,"status":0,"title":"消息","type":1,"time":"2016-07-18 11:00:52","imageUrl":"1466843757205.jpg"},{"content":"消息3","id":3,"otherId":188,"status":1,"title":"消息","type":3,"time":"2016-07-15 11:01:24","imageUrl":"http://banner.ufile.ucloud.com.cn/banner4.jpg"},{"content":"消息2","id":2,"otherId":54,"status":1,"title":"消息","type":2,"time":"2016-07-12 11:01:09","imageUrl":"http://math.ufile.ucloud.com.cn/basicmath1.jpg"}]
     * apicode : 10000
     */

    private String message;
    private int apicode;

    @Override
    public String toString() {
        return "MessageListResult{" +
                "message='" + message + '\'' +
                ", apicode=" + apicode +
                ", result=" + result +
                '}';
    }

    /**
     * content : 消息1
     * id : 1
     * otherId : 0
     * status : 0
     * title : 消息
     * type : 1
     * time : 2016-07-18 11:00:52
     * imageUrl : 1466843757205.jpg
     */

    private List<ResultEntity> result;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setApicode(int apicode) {
        this.apicode = apicode;
    }

    public void setResult(List<ResultEntity> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public int getApicode() {
        return apicode;
    }

    public List<ResultEntity> getResult() {
        return result;
    }

    public static class ResultEntity implements Serializable{
        private String content;
        private int id;
        private int otherId;
        private int status;
        private String title;
        private int type;
        private String time;
        private String imageUrl;

        public void setContent(String content) {
            this.content = content;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setOtherId(int otherId) {
            this.otherId = otherId;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setType(int type) {
            this.type = type;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getContent() {
            return content;
        }

        public int getId() {
            return id;
        }

        public int getOtherId() {
            return otherId;
        }

        public int getStatus() {
            return status;
        }

        public String getTitle() {
            return title;
        }

        public int getType() {
            return type;
        }

        public String getTime() {
            return time;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        @Override
        public String toString() {
            return "ResultEntity{" +
                    "content='" + content + '\'' +
                    ", id=" + id +
                    ", otherId=" + otherId +
                    ", status=" + status +
                    ", title='" + title + '\'' +
                    ", type=" + type +
                    ", time='" + time + '\'' +
                    ", imageUrl='" + imageUrl + '\'' +
                    '}';
        }
    }
}
