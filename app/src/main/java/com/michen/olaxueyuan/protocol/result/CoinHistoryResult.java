package com.michen.olaxueyuan.protocol.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mingge on 2016/10/27.
 */

public class CoinHistoryResult implements Serializable {

    /**
     * message : 成功
     * result : [{"id":17,"userId":381,"name":"每日签到","type":1,"dealNum":5,"date":"2016-10-18"},{"id":16,"userId":381,"name":"解锁题目","type":6,"dealNum":-20,"date":"2016-10-18"},{"id":15,"userId":381,"name":"首次购买课程赠送","type":5,"dealNum":150,"date":"2016-10-18"},{"id":14,"userId":381,"name":"购买课程抵用","type":7,"dealNum":-120,"date":"2016-10-18"},{"id":13,"userId":381,"name":"首次购买会员","type":4,"dealNum":100,"date":"2016-10-18"},{"id":5,"userId":381,"name":"每日签到","type":2,"dealNum":0,"date":"2016-09-23"},{"id":1,"userId":381,"name":"每日签到","type":1,"dealNum":0,"date":"2016-09-21"}]
     * apicode : 10000
     */

    private String message;
    private int apicode;
    /**
     * id : 17
     * userId : 381
     * name : 每日签到
     * type : 1
     * dealNum : 5
     * date : 2016-10-18
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

    public static class ResultBean {
        private int id;
        private int userId;
        private String name;
        private int type;
        private int dealNum;
        private String date;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getDealNum() {
            return dealNum;
        }

        public void setDealNum(int dealNum) {
            this.dealNum = dealNum;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
