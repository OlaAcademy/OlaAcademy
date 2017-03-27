package com.michen.olaxueyuan.protocol.result;

import java.util.List;

/**
 * Created by mingge on 17/3/25.
 */

public class AttendListResult {
    /**
     * message : 成功
     * result : [{"id":381,"name":"欧拉技术支持","phone":"13581574730","avator":"78de30c6-2234-4ac0-af2d-0f0b5f792f91","sign":"123"}]
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
         * id : 381
         * name : 欧拉技术支持
         * phone : 13581574730
         * avator : 78de30c6-2234-4ac0-af2d-0f0b5f792f91
         * sign : 123
         */

        private int id;
        private String name;
        private String phone;
        private String avator;
        private String sign;

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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAvator() {
            return avator;
        }

        public void setAvator(String avator) {
            this.avator = avator;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
