package com.michen.olaxueyuan.protocol.result;

/**
 * Created by mingge on 16/12/19.
 */

public class MessageUnreadTotalCountResult {
    /**
     * message : 成功
     * result : {"systemCount":1,"circleCount":2,"praiseCount":0}
     * apicode : 10000
     */

    private String message;
    private ResultBean result;
    private int apicode;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getApicode() {
        return apicode;
    }

    public void setApicode(int apicode) {
        this.apicode = apicode;
    }

    public static class ResultBean {
        /**
         * systemCount : 1
         * circleCount : 2
         * praiseCount : 0
         */

        private int systemCount;
        private int circleCount;
        private int praiseCount;

        public int getSystemCount() {
            return systemCount;
        }

        public void setSystemCount(int systemCount) {
            this.systemCount = systemCount;
        }

        public int getCircleCount() {
            return circleCount;
        }

        public void setCircleCount(int circleCount) {
            this.circleCount = circleCount;
        }

        public int getPraiseCount() {
            return praiseCount;
        }

        public void setPraiseCount(int praiseCount) {
            this.praiseCount = praiseCount;
        }
    }
}
