package com.michen.olaxueyuan.protocol.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mingge on 2016/8/31.
 */
public class HomeworkListResult implements Serializable {
    /**
     * message : 成功
     * result : [{"avatar":"","groupId":"2","groupName":"Forever群","id":4,"name":"数学作业","userName":"","count":1,"finishedCount":0,"time":"2016-09-01 10:00"},{"avatar":"","groupId":"2","groupName":"Forever群","id":1,"name":"每周一练","userName":"","count":8,"finishedCount":7,"time":"2016-08-15 00:00"}]
     * apicode : 10000
     */

    private String message;
    private int apicode;
    /**
     * avatar :
     * groupId : 2
     * groupName : Forever群
     * id : 4
     * name : 数学作业
     * userName :
     * count : 1
     * finishedCount : 0
     * time : 2016-09-01 10:00
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

    public static class ResultBean implements Serializable {
        private String avatar;
        private String groupId;
        private String groupName;
        private int id;
        private String name;
        private String userName;
        private int count;
        private int finishedCount;
        private String time;
        private String finishedPercent;

        public String getFinishedPercent() {
            return finishedPercent;
        }

        public ResultBean setFinishedPercent(String finishedPercent) {
            this.finishedPercent = finishedPercent;
            return this;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getFinishedCount() {
            return finishedCount;
        }

        public void setFinishedCount(int finishedCount) {
            this.finishedCount = finishedCount;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
