package com.michen.olaxueyuan.protocol.result;

/**
 * Created by mingge on 16/12/19.
 */

public class MessageUnreadTotalCountResult {
    /**
     * message : 成功
     * result : {"systemCount":0,"circleCount":21,"praiseCount":0}
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
         * systemCount : 0
         * circleCount : 21
         * praiseCount : 0
         */

        private String systemCount;
        private String circleCount;
        private String praiseCount;

        public String getSystemCount() {
            return systemCount;
        }

        public void setSystemCount(String systemCount) {
            this.systemCount = systemCount;
        }

        public String getCircleCount() {
            return circleCount;
        }

        public void setCircleCount(String circleCount) {
            this.circleCount = circleCount;
        }

        public String getPraiseCount() {
            return praiseCount;
        }

        public void setPraiseCount(String praiseCount) {
            this.praiseCount = praiseCount;
        }
    }
}
