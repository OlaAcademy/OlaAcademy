package com.michen.olaxueyuan.protocol.result;

import java.util.List;

/**
 * Created by mingge on 16/12/23.
 */

public class PraiseListResult {
    /**
     * message : 成功
     * result : [{"praiseId":3,"postId":"13616","title":"测试","userId":"754","userName":"微何","userAvatar":"1c84834c-1d69-47fb-a009-f426c6ec6d83","time":"2016-12-19 12:05:50","isRead":1},{"praiseId":2,"postId":"13614","title":"测试","userId":"754","userName":"微何","userAvatar":"1c84834c-1d69-47fb-a009-f426c6ec6d83","time":"2016-12-19 11:07:06","isRead":1}]
     * apicode : 10000
     */

    private String message;
    private int apicode;
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

    public static class ResultBean {
        /**
         * praiseId : 3
         * postId : 13616
         * title : 测试
         * userId : 754
         * userName : 微何
         * userAvatar : 1c84834c-1d69-47fb-a009-f426c6ec6d83
         * time : 2016-12-19 12:05:50
         * isRead : 1
         */

        private int praiseId;
        private int postId;
        private String title;
        private String userId;
        private String userName;
        private String userAvatar;
        private String time;
        private int isRead;

        public int getPraiseId() {
            return praiseId;
        }

        public void setPraiseId(int praiseId) {
            this.praiseId = praiseId;
        }

        public int getPostId() {
            return postId;
        }

        public void setPostId(int postId) {
            this.postId = postId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
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

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getIsRead() {
            return isRead;
        }

        public void setIsRead(int isRead) {
            this.isRead = isRead;
        }
    }
}
