package com.michen.olaxueyuan.protocol.result;

import java.util.List;

/**
 * Created by mingge on 2016/12/12.
 */

public class AppointTeacherListResult {

    @Override
    public String toString() {
        return "AppointTeacherListResult{" +
                "message='" + message + '\'' +
                ", apicode=" + apicode +
                ", result=" + result +
                '}';
    }

    /**
     * message : 成功
     * result : [{"id":378,"avator":"1468744004262.jpg","name":"小欧4743","sign":"","local":"","phone":"13552514748"},{"id":381,"avator":"78de30c6-2234-4ac0-af2d-0f0b5f792f91","name":"欧拉技术支持","sign":"123","local":"北京市,海淀区","phone":"13581574738"},{"id":382,"avator":"default.jpg","name":"小欧","phone":"13513519435"},{"id":517,"avator":"59ba012f-c2d3-4564-889d-76b80c8daef4","name":"欧拉小助手","sign":"","local":"北京市,海淀区","phone":"13004770992"},{"id":907,"avator":"default.jpg","phone":"15877974427"},{"id":1108,"avator":"default.jpg","phone":"13603117557"},{"id":1270,"avator":"default.jpg","phone":"18677430429"},{"id":1585,"avator":"default.jpg","phone":"18688863733"},{"id":1733,"avator":"default.jpg","phone":"15733076397"},{"id":1928,"avator":"default.jpg","phone":"18842801555"},{"id":2300,"avator":"default.jpg","phone":"15911049663"},{"id":2429,"avator":"default.jpg","phone":"18850873369"},{"id":2472,"avator":"default.jpg","phone":"18940934700"},{"id":2489,"avator":"default.jpg","phone":"18301352325"}]
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
         * id : 378
         * avator : 1468744004262.jpg
         * name : 小欧4743
         * sign :
         * local :
         * phone : 13552514748
         */

        private int id;
        private String avator;
        private String name;
        private String sign;
        private String local;
        private String phone;

        @Override
        public String toString() {
            return "ResultBean{" +
                    "id=" + id +
                    ", avator='" + avator + '\'' +
                    ", name='" + name + '\'' +
                    ", sign='" + sign + '\'' +
                    ", local='" + local + '\'' +
                    ", phone='" + phone + '\'' +
                    '}';
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getLocal() {
            return local;
        }

        public void setLocal(String local) {
            this.local = local;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
