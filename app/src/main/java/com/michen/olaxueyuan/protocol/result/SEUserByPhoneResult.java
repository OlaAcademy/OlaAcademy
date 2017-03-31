package com.michen.olaxueyuan.protocol.result;

import java.util.List;

/**
 * <pre>
 *     author : mingge
 *     time   : 2017/03/31
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class SEUserByPhoneResult {
    @Override
    public String toString() {
        return "SEUserByPhoneResult{" +
                "message='" + message + '\'' +
                ", apicode=" + apicode +
                ", result=" + result +
                '}';
    }

    /**
     * message : 成功
     * result : [{"sign":"","id":754,"phone":"18500375369","avator":"1c84834c-1d69-47fb-a009-f426c6ec6d83","name":"微何"},{"sign":"","name":"Forever","avator":"http://qzapp.qlogo.cn/qzapp/1105343675/F9B2D009F57C1FFA6ECFA2A5379C967C/100","id":2830,"phone":"13581574738"}]
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
        @Override
        public String toString() {
            return "ResultBean{" +
                    "sign='" + sign + '\'' +
                    ", id=" + id +
                    ", phone='" + phone + '\'' +
                    ", avator='" + avator + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }

        /**
         * sign :
         * id : 754
         * phone : 18500375369
         * avator : 1c84834c-1d69-47fb-a009-f426c6ec6d83
         * name : 微何
         */

        private String sign;
        private int id;
        private String phone;
        private String avator;
        private String name;

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
