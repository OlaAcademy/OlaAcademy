package com.michen.olaxueyuan.protocol.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mingge on 2016/7/14.
 */
public class CommentModule implements Serializable {

    /**
     * message : 成功
     * result : [{"commentId":16,"userId":382,"userName":"小欧0992","userAvatar":"1466843757205.jpg","location":"北京市,海淀区","content":"奋斗","praiseNumber":0,"time":"2016-07-14 12:21:51"}]
     * apicode : 10000
     */

    private String message;
    private int apicode;

    @Override
    public String toString() {
        return "CommentModule{" +
                "message='" + message + '\'' +
                ", apicode=" + apicode +
                ", result=" + result +
                '}';
    }

    /**
     * commentId : 16
     * userId : 382
     * userName : 小欧0992
     * userAvatar : 1466843757205.jpg
     * location : 北京市,海淀区
     * content : 奋斗
     * praiseNumber : 0
     * time : 2016-07-14 12:21:51
     */

    private List<ResultBean> result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getApicode() {
        return apicode;
    }

    public void setApicode(int apicode) {
        this.apicode = apicode;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable{
        private int commentId;
        private int userId;
        private String userName;
        private String userAvatar;
        private String toUserId;
        private String toUserName;
        private String location;
        private String content;
        private int praiseNumber;
        private String time;

        public String getToUserId() {
            return toUserId;
        }

        public void setToUserId(String toUserId) {
            this.toUserId = toUserId;
        }

        public String getToUserName() {
            return toUserName;
        }

        public void setToUserName(String toUserName) {
            this.toUserName = toUserName;
        }

        public int getCommentId() {
            return commentId;
        }

        public void setCommentId(int commentId) {
            this.commentId = commentId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserAvatar() {
            return userAvatar;
        }

        public void setUserAvatar(String userAvatar) {
            this.userAvatar = userAvatar;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getPraiseNumber() {
            return praiseNumber;
        }

        public void setPraiseNumber(int praiseNumber) {
            this.praiseNumber = praiseNumber;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "commentId=" + commentId +
                    ", userId=" + userId +
                    ", userName='" + userName + '\'' +
                    ", userAvatar='" + userAvatar + '\'' +
                    ", toUserId='" + toUserId + '\'' +
                    ", toUserName='" + toUserName + '\'' +
                    ", location='" + location + '\'' +
                    ", content='" + content + '\'' +
                    ", praiseNumber=" + praiseNumber +
                    ", time='" + time + '\'' +
                    '}';
        }
    }
}
