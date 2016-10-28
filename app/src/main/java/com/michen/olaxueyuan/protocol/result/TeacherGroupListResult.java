package com.michen.olaxueyuan.protocol.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mingge on 2016/8/31.
 */
public class TeacherGroupListResult implements Serializable {
    /**
     * message : 成功
     * result : [{"avatar":"","createUser":0,"id":1,"name":"欧拉学习群","time":"2016-08-02 17:48"}]
     * apicode : 10000
     */

    private String message;
    private int apicode;
    /**
     * avatar :
     * createUser : 0
     * id : 1
     * name : 欧拉学习群
     * time : 2016-08-02 17:48
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
        private String avatar;
        private int createUser;
        private String id;
        private String name;
        private String time;
        private boolean isSelected;

        public boolean isSelected() {
            return isSelected;
        }

        public ResultBean setSelected(boolean selected) {
            isSelected = selected;
            return this;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getCreateUser() {
            return createUser;
        }

        public void setCreateUser(int createUser) {
            this.createUser = createUser;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
