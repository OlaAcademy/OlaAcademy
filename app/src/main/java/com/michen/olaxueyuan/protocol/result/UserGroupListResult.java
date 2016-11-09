package com.michen.olaxueyuan.protocol.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mingge on 2016/8/31.
 */
public class UserGroupListResult implements Serializable {

    /**
     * message : 成功
     * result : [{"avatar":"","createUser":381,"id":2,"name":"陈剑数学群","profile":"根据《管理类联考数学高分指南》每周发布一套专项试题","type":1,"time":"2016-08-31 00:00","isMember":1,"number":3}]
     * apicode : 10000
     */

    private String message;
    private int apicode;
    /**
     * avatar :
     * createUser : 381
     * id : 2
     * name : 陈剑数学群
     * profile : 根据《管理类联考数学高分指南》每周发布一套专项试题
     * type : 1
     * time : 2016-08-31 00:00
     * isMember : 1
     * number : 3
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
        private String avatar;
        private int createUser;
        private String id;
        private String name;
        private String profile;
        private int type;
        private String time;
        private int isMember;
        private int number;

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public void setCreateUser(int createUser) {
            this.createUser = createUser;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public void setType(int type) {
            this.type = type;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public void setIsMember(int isMember) {
            this.isMember = isMember;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getAvatar() {
            return avatar;
        }

        public int getCreateUser() {
            return createUser;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getProfile() {
            return profile;
        }

        public int getType() {
            return type;
        }

        public String getTime() {
            return time;
        }

        public int getIsMember() {
            return isMember;
        }

        public int getNumber() {
            return number;
        }
    }
}
