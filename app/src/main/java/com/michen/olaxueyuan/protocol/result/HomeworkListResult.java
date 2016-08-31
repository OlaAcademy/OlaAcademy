package com.michen.olaxueyuan.protocol.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mingge on 2016/8/31.
 */
public class HomeworkListResult implements Serializable {
    /**
     * message : 成功
     * result : [{"id":1,"name":"每周一练","count":"10","finishedCount":2,"userName":"小欧4738","avatar":"664c3a2d-8ddd-4acc-a0c3-5382f625cbb5","groupId":"1","groupName":"欧拉学习群","time":"2016-08-02 17:50"}]
     * apicode : 10000
     */

    private String message;
    private int apicode;
    /**
     * id : 1
     * name : 每周一练
     * count : 10
     * finishedCount : 2
     * userName : 小欧4738
     * avatar : 664c3a2d-8ddd-4acc-a0c3-5382f625cbb5
     * groupId : 1
     * groupName : 欧拉学习群
     * time : 2016-08-02 17:50
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
        private int id;
        private String name;
        private String count;
        private int finishedCount;
        private String userName;
        private String avatar;
        private String groupId;
        private String groupName;
        private String time;

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

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public int getFinishedCount() {
            return finishedCount;
        }

        public void setFinishedCount(int finishedCount) {
            this.finishedCount = finishedCount;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
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

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
